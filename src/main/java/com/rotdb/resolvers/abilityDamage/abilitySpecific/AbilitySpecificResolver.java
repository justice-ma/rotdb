package com.rotdb.resolvers.abilityDamage.abilitySpecific;

import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.context.CalculationContext;

public class AbilitySpecificResolver {
    public static double resolve(CalculationContext context, AbilityHitsContext hit, int hitIndex) {
        double mod = 1;

        mod *= FlankingAbilityResolver.resolve(context);
        mod *= CorruptionPerHitResolver.resolve(context, hitIndex);
        mod *= MagicAbilitySpecificResolver.resolve(context, hit);
        mod *= MeleeAbilitySpecificResolver.resolve(context, hit);

        return mod;
    }
}
