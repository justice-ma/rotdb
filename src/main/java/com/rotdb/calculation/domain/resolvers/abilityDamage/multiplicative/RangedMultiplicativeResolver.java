package com.rotdb.calculation.domain.resolvers.abilityDamage.multiplicative;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.player.BuffContext;

import static com.rotdb.shared.combat.domain.model.enums.CombatStyles.RANGED;

public class RangedMultiplicativeResolver {
    public static double resolve(CalculationContext context) {
        CombatStyles style = context.getEquipment().getCombatStyle();
        BuffContext buff = context.getBuffs();

        double mod = 1;
        if (style == RANGED) {
            if (!buff.has(BuffId.HIGHER_POWER)) {
                if (buff.has(BuffId.DEATHSWIFTNESS)) {
                    mod *= 1.5;
                }
            }
        }
        return mod;
    }
}
