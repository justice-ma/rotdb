package com.rotdb.domain.resolvers.abilityDamage.invisible;

import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.Perks;
import com.rotdb.domain.model.equipment.PerkContext;

import static com.rotdb.domain.model.enums.AbilityTier.ULTIMATE;

public class PerkInvisibleResolver {
    public static double resolve(CalculationContext context, AbilityHitsContext hit) {
        PerkContext perks = context.getPerks();
        if (perks.has(Perks.ULTIMATUMS) && hit.getTier() == ULTIMATE) {
            return 1.03 + Math.min(0.04, perks.rank(Perks.ULTIMATUMS) / 100.0);
        }
        return 1;
    }
}
