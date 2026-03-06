package com.rotdb.domain.resolvers.abilityDamage.invisible;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.Effect;

public class PocketInvisibleResolver {
    public static double resolve(CalculationContext context) {
        if (context.getEquipment().getPocket().getEffect().contains(Effect.AMASCUTBOOK)) {
            return 1.1;
        }
        return 1;
    }
}
