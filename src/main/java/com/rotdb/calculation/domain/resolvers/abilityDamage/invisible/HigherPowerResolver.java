package com.rotdb.calculation.domain.resolvers.abilityDamage.invisible;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;

public class HigherPowerResolver {
    public static double resolve(CalculationContext context) {
        if (context.getBuffs().has(BuffId.HIGHER_POWER)) {
            return 1.3;
        }
        return 1;
    }
}
