package com.rotdb.domain.resolvers.abilityDamage.multiplicative;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.context.TargetContext;
import com.rotdb.domain.model.enums.Effect;
import com.rotdb.domain.model.equipment.EquipmentSlot;

public class UndeadGearMultiplicativeResolver {
    public static double resolve(CalculationContext context) {
        TargetContext target = context.getTarget();
        EquipmentSlot neck = context.getEquipment().getNeck();

        double mod = 1;
        if (neck.getEffect().contains(Effect.SALVE) && target.isUndead()) {
            if (neck.getEffect().contains(Effect.ENHANCED)) {
                mod *= 1.2;
            } else {
                mod *= 1.15;
            }
        }
        return mod;
    }
}
