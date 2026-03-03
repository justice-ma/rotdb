package com.rotdb.resolvers.abilityDamage.styleSpecific;

import com.rotdb.model.context.CalculationContext;

public class StyleSpecificAggregator {
    public static double resolve(CalculationContext context) {
        return 1
                * ArrowResolver.resolve(context)
                * BoltResolver.resolve(context);
    }
}
