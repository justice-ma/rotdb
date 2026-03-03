package com.rotdb.resolvers.abilityDamage.multiplicative;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.context.TargetContext;
import com.rotdb.model.enums.BuffId;
import com.rotdb.model.enums.CombatStyles;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.equipment.EquipmentSlot;
import com.rotdb.model.player.BuffContext;

import java.util.List;

import static com.rotdb.model.enums.CombatStyles.MELEE;

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

            if (mainhand.getEffect().contains(Effect.ABYSSALBANE) && target.isAbyssalDemon() ||
                    mainhand.getEffect().contains(Effect.DRAGONBANE) && target.isDragon() ||
                    mainhand.getEffect().contains(Effect.REVENANTBANE) && target.isRevenant()) {
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
