package com.rotdb.resolvers.hitChance.additive;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.equipment.EquipmentSlot;

import java.util.HashSet;
import java.util.Set;

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
