package com.rotdb.simulation.application.processors;

import com.rotdb.simulation.domain.model.context.AbilityPlacement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlacementProcessor {
    public static Map<Integer, List<AbilityPlacement>> groupByTick(List<AbilityPlacement> abilityPlacements) {
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
}
