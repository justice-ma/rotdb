package com.rotdb.calculation.domain.resolvers.hitChance.accuracy;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.model.enums.CombatStyles;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.enums.WeaponStyle;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;
import com.rotdb.calculation.domain.model.player.BuffContext;

import static com.rotdb.calculation.domain.model.enums.CombatStyles.MELEE;

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
