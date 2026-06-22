package com.rotdb.simulation.application.processors;

import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.model.context.TickSnapshot;
import com.rotdb.simulation.domain.model.cooldown.AbilityCooldownKey;
import com.rotdb.simulation.domain.resolvers.cooldown.AbilityCooldownKeyResolver;
import com.rotdb.simulation.domain.resolvers.cooldown.CooldownReductionAbilityPlacementResolver;

import java.util.Iterator;
import java.util.Map;

public class AbilityCooldownProcessor {
    public static void initializeCooldown(SimulationState simulationState, AbilityPlacement abilityPlacement) {
        AbilityCooldownKey key = AbilityCooldownKeyResolver.resolve(abilityPlacement.getPlacedAbility());
        simulationState.getAbilityCooldownMap().put(key, AbilityProvider.get(abilityPlacement.getPlacedAbility(), simulationState.getState().getEquipment()).getCooldownTicks());
        if (abilityPlacement.getPlacedAbility().isConsumesGlobalCooldown()) {
            simulationState.getAbilityCooldownMap().put(AbilityCooldownKeyResolver.resolveGlobalCooldown(), 3);
        }
    }

    public static void decayCooldown(SimulationState simulationState) {
        for (Map.Entry<AbilityCooldownKey, Integer> ability : simulationState.getAbilityCooldownMap().entrySet()) {
            ability.setValue(ability.getValue() - 1);
        }

        Iterator<Map.Entry<AbilityCooldownKey, Integer>> iterator = simulationState.getAbilityCooldownMap().entrySet().iterator();
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
        AbilityCooldownKey key = AbilityCooldownKeyResolver.resolve(abilityPlacement.getPlacedAbility());
        if (simulationState.getAbilityCooldownMap().containsKey(key)) {
            tickSnapshot.getWarnings().add(abilityPlacement.getPlacedAbility().getName() + " may be on cooldown.");
        }
        if (simulationState.getAbilityCooldownMap().containsKey(AbilityCooldownKeyResolver.resolveGlobalCooldown())
                && abilityPlacement.getPlacedAbility().isConsumesGlobalCooldown()) {
            tickSnapshot.getWarnings().add("Global cooldown may not be ready.");
        }
    }
}
