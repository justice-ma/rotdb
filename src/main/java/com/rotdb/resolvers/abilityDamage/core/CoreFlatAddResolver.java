package com.rotdb.resolvers.abilityDamage.core;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.BuffId;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.equipment.EquipmentSlot;
import com.rotdb.model.player.BuffContext;

import static com.rotdb.model.enums.CombatStyles.MELEE;

public class CoreFlatAddResolver {
    public static int resolve(CalculationContext context) {
        EquipmentSlot neck = context.getEquipment().getNeck();
        EquipmentSlot offhand = context.getEquipment().getOffhand();
        BuffContext buffs = context.getBuffs();
        int add = 0;
        if (neck.getEffect().contains(Effect.AMZI) && context.getEquipment().getCombatStyle() == MELEE) {
            add += (int) (context.getSkills().getBoostedAttack() * 1.35);
        }

        if (offhand.getEffect().contains(Effect.OFFHANDLENG) && buffs.has(BuffId.CHILL)) {
            add += (int) (context.getDamage().getBaseDamage() * 0.24);
        }

        return add;
    }
}
