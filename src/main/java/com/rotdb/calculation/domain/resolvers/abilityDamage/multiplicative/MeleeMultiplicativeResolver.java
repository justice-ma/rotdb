package com.rotdb.calculation.domain.resolvers.abilityDamage.multiplicative;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.context.TargetContext;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.model.enums.CombatStyles;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.enums.TargetTags;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;
import com.rotdb.calculation.domain.model.player.BuffContext;

import static com.rotdb.calculation.domain.model.enums.CombatStyles.MELEE;

public class MeleeMultiplicativeResolver {
    public static double resolve(CalculationContext context) {
        CombatStyles style = context.getEquipment().getCombatStyle();
        BuffContext buff = context.getBuffs();
        TargetContext target = context.getTarget();
        EquipmentSlot mainhand = context.getEquipment().getMainhand();
        EquipmentSlot offhand = context.getEquipment().getOffhand();

        double mod = 1;
        if (style == MELEE) {
            if (buff.has(BuffId.BERSERK)) {
                mod *= 1.75;
            } else if (buff.has(BuffId.ZGS) && !buff.has(BuffId.BERSERK)) {
                mod *= 1.25;
            }

            if (buff.has(BuffId.DBA)) {
                mod *= 1.2;
            }

            if (mainhand.getEffect().contains(Effect.ABYSSALBANE) && target.has(TargetTags.ABYSSALDEMON) ||
                    mainhand.getEffect().contains(Effect.DRAGONBANE) && target.has(TargetTags.DRAGON) ||
                    mainhand.getEffect().contains(Effect.REVENANTBANE) && target.has(TargetTags.REVENANT)) {
                mod *= 1.25;
            }

            if (mainhand.getEffect().contains(Effect.RIPPERCLAWS) && offhand.getEffect().contains(Effect.RIPPERCLAWS)) {
                double hp = (double) target.getCurrentHp() / target.getMaxHp();
                if (hp < 0.5) {
                    double bonus = ((0.5 - hp) / 0.49) * 0.049;
                    mod *= 1 + bonus;
                }
            }
        }
        return mod;
    }
}
