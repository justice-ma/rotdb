package com.rotdb.domain.resolvers.abilityDamage.additive;

import com.rotdb.domain.model.context.CalculationContext;

public class AdditiveResolver {
    public static double resolve(CalculationContext context, int hitIndex) {
        return  1 +
                EquipmentAdditiveResolver.resolve(context) +
                GlobalAdditiveResolver.resolve(context) +
                MeleeAdditiveResolver.resolve(context, hitIndex);
    }
}
