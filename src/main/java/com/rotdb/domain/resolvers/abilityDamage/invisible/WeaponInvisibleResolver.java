package com.rotdb.domain.resolvers.abilityDamage.invisible;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.context.TargetContext;
import com.rotdb.domain.model.enums.BuffId;
import com.rotdb.domain.model.enums.CombatStyles;
import com.rotdb.domain.model.enums.Effect;
import com.rotdb.domain.model.equipment.EquipmentSlot;
import com.rotdb.domain.model.player.BuffContext;

import static com.rotdb.domain.model.enums.CombatStyles.*;

public class WeaponInvisibleResolver {
    public static double resolve(CalculationContext context) {
        CombatStyles style = context.getEquipment().getCombatStyle();
        BuffContext buff = context.getBuffs();
        EquipmentSlot mainhand = context.getEquipment().getMainhand();
        TargetContext target = context.getTarget();

        if (mainhand.getEffect().contains(Effect.HEX)) {
            if (style == MELEE && buff.has(BuffId.ENCHANTMENTOFSAVAGERY) ||
                    style == MAGIC && buff.has(BuffId.ENCHANTMENTOFAFFLICTION) ||
                    style == RANGED && buff.has(BuffId.ENCHANTMENTOFDISPELLING)) {
                return 1.175;
            } else {
                return 1.125;
            }
        } else if (mainhand.getEffect().contains(Effect.KERIS)) {
            if (target.isScarab()) {
                return 1.333;
            } else if (target.isScarabPlus() && mainhand.getEffect().contains(Effect.KERISPLUS)) {
                return 1.333;
            }
        }
        return 1;
    }
}
