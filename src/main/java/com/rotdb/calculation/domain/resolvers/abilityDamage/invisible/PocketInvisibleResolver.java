package com.rotdb.calculation.domain.resolvers.abilityDamage.invisible;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.model.enums.Effect;

public class PocketInvisibleResolver {
    public static double resolve(CalculationContext context) {
        if (context.getEquipment().getPocket().getEffect().contains(Effect.AMASCUTBOOK)) {
            return 1 + 0.1 * (context.getBuffs().stacks(BuffId.BOOKUPTIME) / 100.0);
        }
        return 1;
    }
}
