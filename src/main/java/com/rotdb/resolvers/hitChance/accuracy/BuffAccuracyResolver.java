package com.rotdb.resolvers.hitChance.accuracy;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.BuffId;
import com.rotdb.model.enums.CombatStyles;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.enums.WeaponStyle;
import com.rotdb.model.equipment.EquipmentSlot;
import com.rotdb.model.player.BuffContext;

import static com.rotdb.model.enums.CombatStyles.MELEE;

public class BuffAccuracyResolver {
    public static double resolve(CalculationContext context) {
        BuffContext buff = context.getBuffs();
        EquipmentSlot neck = context.getEquipment().getNeck();
        boolean slashWeapon = context.getEquipment().getMainhand().getStyle() == WeaponStyle.SLASH;
        CombatStyles style = context.getEquipment().getCombatStyle();

        double accuracyModifier = 0;

        if (buff.has(BuffId.DRAGONSCIMITAR) && slashWeapon) {
            accuracyModifier += 0.25;
        }

        if (neck.getEffect().contains(Effect.DOMINIONMEDALLION)) {
            accuracyModifier += 0.01;
        }

        if (buff.has(BuffId.DBA) && style == MELEE) {
            accuracyModifier -= 0.1;
        }
        return accuracyModifier;
    }
}
