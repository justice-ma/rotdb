package com.rotdb.calculation.domain.resolvers.hitChance.additive;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.context.TargetContext;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.enums.TargetTags;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;

public class AmmoAddResolver {
    public static int resolve(CalculationContext context) {
        EquipmentSlot ammo = context.getEquipment().getAmmo();
        TargetContext target = context.getTarget();
        int add = 0;
            if ((ammo.getEffect().contains(Effect.JASDRAGONBANE) && target.has(TargetTags.DRAGON)) ||
                    (ammo.getEffect().contains(Effect.JASDEMONBANE) && target.has(TargetTags.DEMON))) {
                add += 20;
            }
        return add;
    }
}
