package com.rotdb.domain.resolvers.hitChance.accuracy;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.CombatStyles;
import com.rotdb.domain.model.enums.Effect;
import com.rotdb.domain.model.equipment.EquipmentSlot;

import static com.rotdb.domain.model.enums.CombatStyles.*;

public class ScrimshawAccuracyResolver {
    public static double resolve(CalculationContext context) {
        EquipmentSlot pocket = context.getEquipment().getPocket();
        CombatStyles style = context.getEquipment().getCombatStyle();
        double accuracyModifier = 0;
        if (pocket.getEffect().contains(Effect.RANGINGSCRIM) && style == RANGED) {
            if (pocket.getEffect().contains(Effect.SUPERIOR)) {
                accuracyModifier += 0.04;
            } else {
                accuracyModifier += 0.02;
            }
        }
        if (pocket.getEffect().contains(Effect.MAGICSCRIM) && style == MAGIC) {
            if (pocket.getEffect().contains(Effect.SUPERIOR)) {
                accuracyModifier += 0.04;
            } else {
                accuracyModifier += 0.02;
            }
        }
        if (pocket.getEffect().contains(Effect.ATTACKSCRIM) && style == MELEE) {
            if (pocket.getEffect().contains(Effect.SUPERIOR)) {
                accuracyModifier += 0.04;
            } else {
                accuracyModifier += 0.02;
            }
        }
        return accuracyModifier;
    }
}
