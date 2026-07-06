package com.rotdb.simulation.application.processors;

import com.rotdb.calculation.domain.engine.CalculationEngine;
import com.rotdb.calculation.domain.engine.CalculationMode;
import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.ability.model.AbilityCooldownTiming;
import com.rotdb.shared.ability.model.GeneratedBuffTiming;
import com.rotdb.simulation.application.processors.result.AbilityCastResult;
import com.rotdb.simulation.application.service.DamageRequestFactory;
import com.rotdb.simulation.domain.model.buff.AppliedBuffResult;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.model.context.TickSnapshot;

import java.util.List;
import java.util.Map;

public class AbilityCastProcessor {
    public static AbilityCastResult processAbilityCast(Map<Integer, List<AbilityPlacement>> abilitiesByCastTick,
                                                       int tick, List<AbilityPlacement> castedAbilities,
                                                       SimulationState simulationState, TickSnapshot tickSnapshot,
                                                       boolean vestmentsBleedActiveAtTickStart, int endingTick,
                                                       CalculationEngine engine) {
        double adrenalineDelta = 0;
        if (abilitiesByCastTick.containsKey(tick)) {
            castedAbilities.addAll(abilitiesByCastTick.get(tick));

            for (AbilityPlacement abilityPlacement : castedAbilities) {
                DamageResult damageResult =
                        engine.calculateAbilityDamage(DamageRequestFactory.getDamageRequest(simulationState.getState(), abilityPlacement.getPlacedAbility()), CalculationMode.ABILITY, null);

                adrenalineDelta += AdrenalineProcessor.generateAbilityPlacementAdrenalineDelta(abilityPlacement, simulationState, damageResult);
                BuffProcessor.removeBuffsConsumedByAbilityPlacement(abilityPlacement, simulationState);

                AbilityCooldownProcessor.generateGlobalCooldownWarnings(simulationState, abilityPlacement, tickSnapshot);
                AbilityCooldownProcessor.initializeGlobalCooldown(simulationState, abilityPlacement);

                if (AbilityProvider.get(abilityPlacement.getPlacedAbility(), simulationState.getState().getEquipment()).getAbilityCooldownTiming() == AbilityCooldownTiming.ON_CAST) {
                    AbilityCooldownProcessor.generateAbilityCooldownWarnings(simulationState, abilityPlacement, tickSnapshot);
                    AbilityCooldownProcessor.initializeCooldown(simulationState, abilityPlacement);
                    AbilityCooldownProcessor.applyPlacementCooldownEffects(simulationState, abilityPlacement, damageResult);
                }
                for (AppliedBuffResult buff : BuffProcessor.applyAbilityGeneratedBuffsWithTiming(abilityPlacement, simulationState, GeneratedBuffTiming.ON_CAST, vestmentsBleedActiveAtTickStart)) {
                    if (buff.resolvedDurationTicks() != null) {
                        endingTick = Math.max(buff.resolvedDurationTicks() + tick, endingTick);
                    }
                }
            }
        }
        return new AbilityCastResult(endingTick, adrenalineDelta);
    }
}
