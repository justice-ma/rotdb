package com.rotdb.domain.resolvers.hitChance.additive;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.Effect;
import com.rotdb.domain.model.equipment.EquipmentSlot;

public class WeaponAddResolver {
    public static int resolve(CalculationContext context) {
        int add = 0;
        EquipmentSlot mainhand = context.getEquipment().getMainhand();
        EquipmentSlot neck = context.getEquipment().getNeck();
        if (mainhand.getEffect().contains(Effect.KERIS)) {
            if (neck.getEffect().contains(Effect.DESERTAMULET)) {
                add += 25;
            } else {
                add += 15;
            }
        }

        if (mainhand.getEffect().contains(Effect.DARKLIGHT) && context.getTarget().isDemon()) {
            add += 2;
        }

        if (mainhand.getEffect().contains(Effect.BALMUNG) &&
                context.getTarget().isDagannoth()) {
            if (!mainhand.getEffect().contains(Effect.ENHANCED)) {
                add += 45;
            } else {

                add += 3;
            }
        }

        if (mainhand.getEffect().contains(Effect.HEX)) {
            add += 10;
        }
        return add;
    }
}
