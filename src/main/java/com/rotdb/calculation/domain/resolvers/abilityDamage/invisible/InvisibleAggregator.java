package com.rotdb.calculation.domain.resolvers.abilityDamage.invisible;

import com.rotdb.calculation.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;

public class InvisibleAggregator {
    public static double resolve(CalculationContext context, AbilityHitsContext hit, int hitIndex) {
        return 1
                * AmmoBuffInvisibleResolver.resolve(context, hit, hitIndex)
                * PerkInvisibleResolver.resolve(context, hit)
                * PocketInvisibleResolver.resolve(context)
                * WeaponInvisibleResolver.resolve(context);
    }
}
