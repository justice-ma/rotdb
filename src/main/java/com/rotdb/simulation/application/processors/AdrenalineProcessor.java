package com.rotdb.simulation.application.processors;

import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.model.context.TickSnapshot;
import com.rotdb.simulation.domain.model.context.TimelineHit;
import com.rotdb.simulation.domain.resolvers.adrenaline.*;

import java.util.List;

public class AdrenalineProcessor {
    public static double generateAbilityPlacementAdrenalineDelta(AbilityPlacement abilityPlacement, SimulationState simulationState, DamageResult damageResult) {
        AbilityContext ability = AbilityProvider.get(abilityPlacement.getPlacedAbility(), simulationState.getState().getEquipment());
        double baseDelta = ability.getAdrenaline();
        double additiveDelta =
                AdrenalineBuffUltimateAbilityPlacementResolver.resolve(abilityPlacement, simulationState, damageResult)
                        + EquipmentAdrenalineAbilityPlacementResolver.resolve(simulationState, abilityPlacement)
                        + PerkAdrenalineAbilityPlacementResolver.resolve(abilityPlacement, simulationState);
        double multiplicativeModifier = MultiplicativeAdrenalineAbilityPlacementResolver.resolve(abilityPlacement, simulationState);
        double postNaturalInstinctsDelta = PostNaturalInstinctsAbilityPlacementResolver.resolve(abilityPlacement, simulationState);
        return (baseDelta + additiveDelta) * multiplicativeModifier + postNaturalInstinctsDelta;
    }

    public static double generatePreHitTickAdrenalineDelta(SimulationState simulationState, List<TimelineHit> timelineHits) {
        double additiveDelta =
                AdrenalineBuffUltimatePreHitTickResolver.resolve(simulationState, timelineHits)
                        + EquipmentAdrenalinePreHitTickResolver.resolve(simulationState, timelineHits);
        double multiplicativeModifier = MultiplicativeAdrenalinePreHitTickResolver.resolve(simulationState);
        return additiveDelta * multiplicativeModifier;
    }

    public static double generateCurrentTickAdrenalineDelta(SimulationState simulationState) {
        return EquipmentAdrenalineCurrentTickResolver.resolve(simulationState) + PostNaturalInstinctsCurrentTickResolver.resolve(simulationState);
    }

    public static void applyAdrenalineDelta(SimulationState simulationState, double adrenalineDelta) {
        simulationState.setAdrenaline(simulationState.getAdrenaline() + adrenalineDelta);
    }

    public static void generateWarnings(SimulationState simulationState, TickSnapshot tickSnapshot) {
        if (simulationState.getAdrenaline() < 0) {
            tickSnapshot.getWarnings().add("Adrenaline may not be sufficient.");
        }

        if (simulationState.getAdrenaline() > simulationState.getMaximumAdrenaline()) {
            tickSnapshot.getWarnings().add("Adrenaline capped.");
        }
    }

    public static void applyMaximumAdrenalineBound(SimulationState simulationState) {
        AdrenalineBoundsResolver.resolve(simulationState);
    }

    public static void clampAdrenaline(SimulationState simulationState) {
        if (simulationState.getAdrenaline() > simulationState.getMaximumAdrenaline()) {
            simulationState.setAdrenaline(simulationState.getMaximumAdrenaline());
        }
    }
}
