package com.rotdb.simulation.application.processors;

import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.model.context.TickSnapshot;
import com.rotdb.simulation.domain.model.cooldown.CooldownKey;
import com.rotdb.simulation.domain.resolvers.cooldown.CooldownKeyResolver;
import com.rotdb.simulation.domain.resolvers.cooldown.CooldownReductionAbilityPlacementResolver;

import java.util.Iterator;
import java.util.Map;

public class CooldownProcessor {
    public static void initializeCooldown(SimulationState simulationState, AbilityPlacement abilityPlacement) {
        CooldownKey key = CooldownKeyResolver.resolve(abilityPlacement.getPlacedAbility());
        simulationState.getCooldownMap().put(key, AbilityProvider.get(abilityPlacement.getPlacedAbility(), simulationState.getState().getEquipment()).getCooldownTicks());
        if (abilityPlacement.getPlacedAbility().isConsumesGlobalCooldown()) {
            simulationState.getCooldownMap().put(CooldownKeyResolver.resolveGlobalCooldown(), 3);
        }
    }

    public static void decayCooldown(SimulationState simulationState) {
        for (Map.Entry<CooldownKey, Integer> ability : simulationState.getCooldownMap().entrySet()) {
            ability.setValue(ability.getValue() - 1);
        }

        Iterator<Map.Entry<CooldownKey, Integer>> iterator = simulationState.getCooldownMap().entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getValue() <= 0) {
                iterator.remove();
            }
        }
    }

    public static void applyPlacementCooldownEffects(SimulationState simulationState, AbilityPlacement abilityPlacement, DamageResult damageResult) {
        CooldownReductionAbilityPlacementResolver.resolve(simulationState, abilityPlacement, damageResult);
    }

    public static void generateWarnings(SimulationState simulationState, AbilityPlacement abilityPlacement, TickSnapshot tickSnapshot) {
        CooldownKey key = CooldownKeyResolver.resolve(abilityPlacement.getPlacedAbility());
        if (simulationState.getCooldownMap().containsKey(key)) {
            tickSnapshot.getWarnings().add(abilityPlacement.getPlacedAbility().getName() + " may be on cooldown.");
        }
        if (simulationState.getCooldownMap().containsKey(CooldownKeyResolver.resolveGlobalCooldown())
                && abilityPlacement.getPlacedAbility().isConsumesGlobalCooldown()) {
            tickSnapshot.getWarnings().add("Global cooldown may not be ready.");
        }
    }
}
