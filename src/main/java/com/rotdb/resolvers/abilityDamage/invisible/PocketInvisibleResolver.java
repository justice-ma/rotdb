package com.rotdb.resolvers.abilityDamage.invisible;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.Effect;

public class PocketInvisibleResolver {
    public static double resolve(CalculationContext context) {
        if (context.getEquipment().getPocket().getEffect().contains(Effect.AMASCUTBOOK)) {
            return 1.1;
        }
        return 1;
    }
}
