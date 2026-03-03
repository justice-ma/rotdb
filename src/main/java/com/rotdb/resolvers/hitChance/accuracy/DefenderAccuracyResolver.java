package com.rotdb.resolvers.hitChance.accuracy;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.CombatStyles;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.equipment.EquipmentSlot;

import static com.rotdb.model.enums.CombatStyles.*;

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
