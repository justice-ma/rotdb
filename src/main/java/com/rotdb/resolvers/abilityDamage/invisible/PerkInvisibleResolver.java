package com.rotdb.resolvers.abilityDamage.invisible;

import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.Perks;
import com.rotdb.model.equipment.PerkContext;

import static com.rotdb.model.enums.AbilityTier.ULTIMATE;

public class PerkInvisibleResolver {
    public static double resolve(CalculationContext context, AbilityHitsContext hit) {
        PerkContext perks = context.getPerks();
        if (perks.has(Perks.ULTIMATUMS) && hit.getTier() == ULTIMATE) {
            return 1.03 + Math.min(0.04, perks.rank(Perks.ULTIMATUMS) / 100.0);
        }
        return 1;
    }
}
