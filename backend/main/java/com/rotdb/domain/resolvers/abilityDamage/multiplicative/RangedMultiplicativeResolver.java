package com.rotdb.domain.resolvers.abilityDamage.multiplicative;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.BuffId;
import com.rotdb.domain.model.enums.CombatStyles;
import com.rotdb.domain.model.player.BuffContext;

import static com.rotdb.domain.model.enums.CombatStyles.RANGED;

public class RangedMultiplicativeResolver {
    public static double resolve(CalculationContext context) {
        CombatStyles style = context.getEquipment().getCombatStyle();
        BuffContext buff = context.getBuffs();

        double mod = 1;
        if (style == RANGED) {
            if (buff.has(BuffId.DEATHSWIFTNESS)) {
                mod *= 1.5;
            }
        }
        return mod;
    }
}
