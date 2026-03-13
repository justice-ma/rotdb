package com.rotdb.domain.resolvers.hitChance.accuracy;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.CombatStyles;
import com.rotdb.domain.model.enums.Effect;
import com.rotdb.domain.model.equipment.EquipmentSlot;

public class DefenderAccuracyResolver {
    public static double resolve(CalculationContext context) {
        EquipmentSlot offhand = context.getEquipment().getOffhand();
        CombatStyles style = context.getEquipment().getMainhand().getClazz();
        CombatStyles ohStyle = context.getEquipment().getOffhand().getClazz();
        double accuracyModifier = 0;
        if (offhand.getEffect().contains(Effect.DEFENDER) && style == ohStyle) {
            accuracyModifier += 0.03;
        }
        return accuracyModifier;
    }
}
