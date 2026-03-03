package com.rotdb.resolvers.abilityDamage.multiplicative;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.BuffId;
import com.rotdb.model.enums.CombatStyles;
import com.rotdb.model.player.BuffContext;

import static com.rotdb.model.enums.CombatStyles.RANGED;

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
