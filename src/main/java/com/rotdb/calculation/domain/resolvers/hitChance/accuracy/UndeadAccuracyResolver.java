package com.rotdb.calculation.domain.resolvers.hitChance.accuracy;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.enums.TargetTags;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;

public class UndeadAccuracyResolver {
    public static double resolve(CalculationContext context) {
        EquipmentSlot neck = context.getEquipment().getNeck();
        double accuracyModifier = 0;
        if (neck.getEffect().contains(Effect.SALVE) && context.getTarget().has(TargetTags.UNDEAD)) {
            if (neck.getEffect().contains(Effect.ENHANCED)) {
                accuracyModifier += 0.20;
            } else {
                accuracyModifier += 0.15;
            }
        }
        return accuracyModifier;
    }
}
