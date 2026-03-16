package com.rotdb.calculation.domain.resolvers.abilityDamage.invisible;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.context.TargetContext;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.model.enums.CombatStyles;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.enums.TargetTags;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;
import com.rotdb.calculation.domain.model.player.BuffContext;

import static com.rotdb.calculation.domain.model.enums.CombatStyles.*;

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
            if (target.has(TargetTags.KERIS)) {
                return 1.333;
            } else if (target.has(TargetTags.CONSECRATEDKERIS) && mainhand.getEffect().contains(Effect.KERISPLUS)) {
                return 1.333;
            }
        }
        return 1;
    }
}
