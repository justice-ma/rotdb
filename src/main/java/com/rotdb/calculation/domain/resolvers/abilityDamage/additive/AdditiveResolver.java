package com.rotdb.calculation.domain.resolvers.abilityDamage.additive;

import com.rotdb.calculation.domain.model.context.CalculationContext;

public class AdditiveResolver {
    public static double resolve(CalculationContext context, int hitIndex) {
        return  1 +
                EquipmentAdditiveResolver.resolve(context) +
                GlobalAdditiveResolver.resolve(context) +
                MeleeAdditiveResolver.resolve(context, hitIndex);
    }
}
