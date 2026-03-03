package com.rotdb.resolvers.hitChance.additive;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.BuffId;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.equipment.EquipmentSlot;

public class NeckAddResolver {
    public static double resolve(CalculationContext context) {
        double add = 0;
        EquipmentSlot neck = context.getEquipment().getNeck();
        if (neck.getEffect().contains(Effect.REAPERNECKLACE)) {
            if (context.getBuffs().has(BuffId.REAPERSTACKS) && context.getBuffs().stacks(BuffId.REAPERSTACKS) > 0) {
                add += Math.min(context.getBuffs().stacks(BuffId.REAPERSTACKS) / 10.0, 3);
            }
        }
        return add;
    }
}
