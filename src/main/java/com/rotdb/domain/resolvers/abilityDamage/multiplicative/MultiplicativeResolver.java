package com.rotdb.domain.resolvers.abilityDamage.multiplicative;

import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.context.CalculationContext;

public class MultiplicativeResolver {
    public static double resolve(CalculationContext context, AbilityHitsContext hit) {
        double mod = 1;
        mod *= FamiliarMultiplicativeResolver.resolve(context);
        mod *= MagicMultiplicativeResolver.resolve(context, hit);
        mod *= MeleeMultiplicativeResolver.resolve(context);
        mod *= PerkMultiplicativeResolver.resolve(context);
        mod *= PrayerMultiplicativeResolver.resolve(context);
        mod *= RangedMultiplicativeResolver.resolve(context);
        mod *= SlayerGearMultiplicativeResolver.resolve(context);
        mod *= UndeadGearMultiplicativeResolver.resolve(context);

        return mod;
    }
}
