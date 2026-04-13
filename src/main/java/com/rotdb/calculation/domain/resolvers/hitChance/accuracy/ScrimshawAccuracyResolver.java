package com.rotdb.calculation.domain.resolvers.hitChance.accuracy;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;

public class ScrimshawAccuracyResolver {
    public static double resolve(CalculationContext context) {
        EquipmentSlot pocket = context.getEquipment().getPocket();
        CombatStyles style = context.getEquipment().getCombatStyle();
        double accuracyModifier = 0;
        if (pocket.getEffect().contains(Effect.RANGINGSCRIM) && style == CombatStyles.RANGED) {
            if (pocket.getEffect().contains(Effect.SUPERIOR)) {
                accuracyModifier += 0.04;
            } else {
                accuracyModifier += 0.02;
            }
        }
        if (pocket.getEffect().contains(Effect.MAGICSCRIM) && style == CombatStyles.MAGIC) {
            if (pocket.getEffect().contains(Effect.SUPERIOR)) {
                accuracyModifier += 0.04;
            } else {
                accuracyModifier += 0.02;
            }
        }
        if (pocket.getEffect().contains(Effect.ATTACKSCRIM) && style == CombatStyles.MELEE) {
            if (pocket.getEffect().contains(Effect.SUPERIOR)) {
                accuracyModifier += 0.04;
            } else {
                accuracyModifier += 0.02;
            }
        }
        return accuracyModifier;
    }
}
