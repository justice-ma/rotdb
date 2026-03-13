package com.rotdb.domain.resolvers.hitChance.additive;

import com.rotdb.domain.model.context.CalculationContext;

public class AdditiveHitchanceModifiersResolver {
    public static double resolve(CalculationContext context) {
        return AmmoAddResolver.resolve(context) +
                GlovesAddResolver.resolve(context) +
                NeckAddResolver.resolve(context) +
                WeaponAddResolver.resolve(context);
    }
}
