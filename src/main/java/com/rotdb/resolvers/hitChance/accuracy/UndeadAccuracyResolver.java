package com.rotdb.resolvers.hitChance.accuracy;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.equipment.EquipmentSlot;

public class UndeadAccuracyResolver {
    public static double resolve(CalculationContext context) {
        EquipmentSlot neck = context.getEquipment().getNeck();
        double accuracyModifier = 0;
        if (neck.getEffect().contains(Effect.SALVE) && context.getTarget().isUndead()) {
            if (neck.getEffect().contains(Effect.ENHANCED)) {
                accuracyModifier += 0.20;
            } else {
                accuracyModifier += 0.15;
            }
        }
        return accuracyModifier;
    }
}
