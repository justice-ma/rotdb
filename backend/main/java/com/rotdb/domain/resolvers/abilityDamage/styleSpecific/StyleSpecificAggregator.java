package com.rotdb.domain.resolvers.abilityDamage.styleSpecific;

import com.rotdb.domain.model.context.CalculationContext;

public class StyleSpecificAggregator {
    public static double resolve(CalculationContext context) {
        return 1
                * ArrowResolver.resolve(context)
                * BoltResolver.resolve(context);
    }
}
