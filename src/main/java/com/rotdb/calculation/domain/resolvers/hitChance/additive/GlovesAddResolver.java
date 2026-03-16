package com.rotdb.calculation.domain.resolvers.hitChance.additive;

import com.rotdb.calculation.ability.AbilityId;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;

public class GlovesAddResolver {
    public static int resolve(CalculationContext context) {
        int add = 0;
        EquipmentSlot gloves = context.getEquipment().getGloves();
        if (gloves.getEffect().contains(Effect.NIGHTMAREGAUNTLETS) && context.getAbility().getId() == AbilityId.SNIPE) {
            add += 25;
        }
        return add;
    }
}
