package com.rotdb.simulation.application.processors;

import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.BuffPlacement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlacementProcessor {
    public static Map<Integer, List<AbilityPlacement>> groupAbilitiesByTick(List<AbilityPlacement> abilityPlacements) {
        Map<Integer, List<AbilityPlacement>> abilities = new HashMap<>();

        for (AbilityPlacement abilityPlacement : abilityPlacements) {
            if (abilities.containsKey(abilityPlacement.getPlacementTick())) {
                abilities.get(abilityPlacement.getPlacementTick()).add(abilityPlacement);
            } else {
                List<AbilityPlacement> newList = new ArrayList<>();
                newList.add(abilityPlacement);
                abilities.put(abilityPlacement.getPlacementTick(), newList);
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
}
