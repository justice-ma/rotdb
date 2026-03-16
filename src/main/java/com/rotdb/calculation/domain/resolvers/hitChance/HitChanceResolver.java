package com.rotdb.calculation.domain.resolvers.hitChance;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.resolvers.hitChance.accuracy.PlayerAccuracyResolver;
import com.rotdb.calculation.domain.resolvers.hitChance.additive.AdditiveHitchanceModifiersResolver;

public class HitChanceResolver {
    public static double resolve(CalculationContext context) {
        int accuracy = PlayerAccuracyResolver.resolve(context);
        int armour = TargetDefenceResolver.resolve(context);
        int affinity = AffinityResolver.resolve(context);
        double add = AdditiveHitchanceModifiersResolver.resolve(context);
        return Math.min(100,
                (Math.floor(10 * Math.floor(100 * (double) accuracy / armour) / 100.0 * affinity + add) / 10.0)) / 100.0;
    }
}
