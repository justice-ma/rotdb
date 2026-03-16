package com.rotdb.calculation.domain.resolvers.abilityDamage.styleSpecific;

import com.rotdb.calculation.domain.model.context.CalculationContext;

public class StyleSpecificAggregator {
    public static double resolve(CalculationContext context) {
        return 1
                * ArrowResolver.resolve(context)
                * BoltResolver.resolve(context);
    }
}
