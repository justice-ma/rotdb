package com.rotdb.resolvers.abilityDamage.styleSpecific;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.context.TargetContext;
import com.rotdb.model.enums.CombatStyles;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.equipment.EquipmentSlot;

public class ArrowResolver {
    public static double resolve(CalculationContext context) {
        EquipmentSlot ammo = context.getEquipment().getAmmo();
        TargetContext target = context.getTarget();

        double mod = 1;
        if (context.getEquipment().getCombatStyle() == CombatStyles.RANGED) {
            if (ammo.getEffect().contains(Effect.JASDRAGONBANE) & target.isDragon() ||
                    ammo.getEffect().contains(Effect.JASDEMONBANE) && target.isDemon()) {
                mod *= 1.3;
            }

            if (ammo.getEffect().contains(Effect.FULARROWS)) {
                mod *= 1.15;
            }
        }

        return mod;
    }
}
