package com.rotdb.domain.resolvers.abilityDamage.multiplicative;

import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.BuffId;
import com.rotdb.domain.model.enums.CombatStyles;
import com.rotdb.domain.model.player.BuffContext;

import static com.rotdb.domain.model.enums.AbilityTier.BASIC;
import static com.rotdb.domain.model.enums.CombatStyles.MAGIC;

public class MagicMultiplicativeResolver {
    public static double resolve(CalculationContext context, AbilityHitsContext hit) {
        CombatStyles style = context.getEquipment().getCombatStyle();
        BuffContext buff = context.getBuffs();
        double mod = 1;

        if (style == MAGIC) {
            if (buff.has(BuffId.SUNSHINE)) {
                mod *= 1.5;
            }

            if (buff.has(BuffId.TITHESTACKS) && buff.stacks(BuffId.TITHESTACKS) > 0 && hit.getTier() == BASIC) {
                mod *= 1 + buff.stacks(BuffId.TITHESTACKS) / 100.0;
            }
        }
        return mod;
    }
}
