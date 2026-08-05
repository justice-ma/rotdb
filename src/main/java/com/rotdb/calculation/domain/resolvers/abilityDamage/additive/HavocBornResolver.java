package com.rotdb.calculation.domain.resolvers.abilityDamage.additive;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;

public class HavocBornResolver {
    public static double resolve(CalculationContext context) {
        if (context.getBuffs().has(BuffId.HAVOC_BORN)) {
            return 0.2;
        }
        return 0;
    }
}
