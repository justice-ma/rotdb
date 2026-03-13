package com.rotdb.domain.resolvers.hitChance.additive;

import com.rotdb.ability.AbilityId;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.Effect;
import com.rotdb.domain.model.equipment.EquipmentSlot;

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
