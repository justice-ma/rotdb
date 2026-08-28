package com.rotdb.calculation.domain.resolvers.abilityDamage.npc;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;

import java.util.ArrayList;
import java.util.List;

public class FlatRangeAddResolver {
    public static List<Integer> resolve(CalculationContext context, AbilityHitsContext hit) {
        int min = 0;
        int max = 0;

        return new ArrayList<>(List.of(min, max));
    }
}
