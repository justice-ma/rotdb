package com.rotdb.calculation.domain.resolvers.abilityDamage.styleSpecific;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.context.TargetContext;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.enums.TargetTags;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;

public class ArrowResolver {
    public static double resolve(CalculationContext context) {
        EquipmentSlot ammo = context.getEquipment().getAmmo();
        TargetContext target = context.getTarget();

        double mod = 1;
        if (context.getEquipment().getCombatStyle() == CombatStyles.RANGED) {
            if (ammo.getEffect().contains(Effect.JASDRAGONBANE) & target.has(TargetTags.DRAGONSLAYER) ||
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
