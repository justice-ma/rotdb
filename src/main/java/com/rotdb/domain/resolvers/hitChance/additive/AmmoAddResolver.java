package com.rotdb.domain.resolvers.hitChance.additive;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.context.TargetContext;
import com.rotdb.domain.model.enums.Effect;
import com.rotdb.domain.model.equipment.EquipmentSlot;

public class AmmoAddResolver {
    public static int resolve(CalculationContext context) {
        EquipmentSlot ammo = context.getEquipment().getAmmo();
        TargetContext target = context.getTarget();
        int add = 0;
            if ((ammo.getEffect().contains(Effect.JASDRAGONBANE) && target.isDragon()) ||
                    (ammo.getEffect().contains(Effect.JASDEMONBANE) && target.isDemon())) {
                add += 20;
            }
        return add;
    }
}
