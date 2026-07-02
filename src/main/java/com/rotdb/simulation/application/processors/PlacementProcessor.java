package com.rotdb.simulation.application.processors;

import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.BuffPlacement;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.model.context.TickSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlacementProcessor {
    public static Map<Integer, List<AbilityPlacement>> groupAbilitiesByCastTick(List<AbilityPlacement> abilityPlacements) {
        Map<Integer, List<AbilityPlacement>> abilities = new HashMap<>();

        for (AbilityPlacement abilityPlacement : abilityPlacements) {
            if (abilities.containsKey(abilityPlacement.getCastTick())) {
                abilities.get(abilityPlacement.getCastTick()).add(abilityPlacement);
            } else {
                List<AbilityPlacement> newList = new ArrayList<>();
                newList.add(abilityPlacement);
                abilities.put(abilityPlacement.getCastTick(), newList);
            }
        }
        return abilities;
    }

    public static Map<Integer, List<AbilityPlacement>> groupAbilityByReleaseTick(List<AbilityPlacement> abilityPlacements) {
        Map<Integer, List<AbilityPlacement>> abilities = new HashMap<>();

        for (AbilityPlacement abilityPlacement : abilityPlacements) {
            if (abilities.containsKey(abilityPlacement.getReleaseTick())) {
                abilities.get(abilityPlacement.getReleaseTick()).add(abilityPlacement);
            } else {
                List<AbilityPlacement> newList = new ArrayList<>();
                newList.add(abilityPlacement);
                abilities.put(abilityPlacement.getReleaseTick(), newList);
            }
        }
        return abilities;
    }

    public static Map<Integer, List<BuffPlacement>> groupBuffsByTick(List<BuffPlacement> buffPlacements) {
        Map<Integer, List<BuffPlacement>> buffs = new HashMap<>();

        for (BuffPlacement buffPlacement : buffPlacements) {
            if (buffs.containsKey(buffPlacement.getPlacementTick())) {
                buffs.get(buffPlacement.getPlacementTick()).add(buffPlacement);
            } else {
                List<BuffPlacement> newList = new ArrayList<>();
                newList.add(buffPlacement);
                buffs.put(buffPlacement.getPlacementTick(), newList);
            }
        }
        return buffs;
    }

    public static void generateAbilityReleaseWarnings(SimulationState simulationState, AbilityPlacement abilityPlacement, TickSnapshot tickSnapshot) {
        if (abilityPlacement.getCastTick() > abilityPlacement.getReleaseTick()) {
            tickSnapshot.getWarnings().add("Ability cannot be released before it is cast.");
        }
    }
}
