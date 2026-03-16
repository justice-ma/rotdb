package com.rotdb.calculation.domain.resolvers.abilityDamage.multiplicative;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.context.TargetContext;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.enums.TargetTags;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;

public class UndeadGearMultiplicativeResolver {
    public static double resolve(CalculationContext context) {
        TargetContext target = context.getTarget();
        EquipmentSlot neck = context.getEquipment().getNeck();

        double mod = 1;
        if (neck.getEffect().contains(Effect.SALVE) && target.has(TargetTags.UNDEAD)) {
            if (neck.getEffect().contains(Effect.ENHANCED)) {
                mod *= 1.2;
            } else {
                mod *= 1.15;
            }
        }
        return mod;
    }
}
