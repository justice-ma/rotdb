package com.rotdb.resolvers.abilityDamage.invisible;

import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.context.CalculationContext;

public class InvisibleAggregator {
    public static double resolve(CalculationContext context, AbilityHitsContext hit, int hitIndex) {
        return 1
                * AmmoBuffInvisibleResolver.resolve(context, hit, hitIndex)
                * PerkInvisibleResolver.resolve(context, hit)
                * PocketInvisibleResolver.resolve(context)
                * WeaponInvisibleResolver.resolve(context);
    }
}
