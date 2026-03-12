package com.rotdb.domain.resolvers.abilityDamage.styleSpecific;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.context.TargetContext;
import com.rotdb.domain.model.enums.CombatStyles;
import com.rotdb.domain.model.enums.Effect;
import com.rotdb.domain.model.enums.TargetTags;
import com.rotdb.domain.model.equipment.EquipmentSlot;

public class ArrowResolver {
    public static double resolve(CalculationContext context) {
        EquipmentSlot ammo = context.getEquipment().getAmmo();
        TargetContext target = context.getTarget();

        double mod = 1;
        if (context.getEquipment().getCombatStyle() == CombatStyles.RANGED) {
            if (ammo.getEffect().contains(Effect.JASDRAGONBANE) & target.has(TargetTags.DRAGON) ||
                    ammo.getEffect().contains(Effect.JASDEMONBANE) && target.has(TargetTags.DEMON)) {
                mod *= 1.3;
            }

            if (ammo.getEffect().contains(Effect.FULARROWS)) {
                mod *= 1.15;
            }
        }

        return mod;
    }
}
