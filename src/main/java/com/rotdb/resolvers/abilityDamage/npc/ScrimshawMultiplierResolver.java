package com.rotdb.resolvers.abilityDamage.npc;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.CombatStyles;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.equipment.EquipmentSlot;

import static com.rotdb.model.enums.CombatStyles.MAGIC;
import static com.rotdb.model.enums.CombatStyles.RANGED;

public class ScrimshawMultiplierResolver {
    public static double resolve(CalculationContext context) {
        CombatStyles style = context.getEquipment().getCombatStyle();
        EquipmentSlot pocket = context.getEquipment().getPocket();
        double mod = 1;

        if (style == MAGIC) {
            if (pocket.getEffect().contains(Effect.ELEMENTSSCRIM)) {
                if (pocket.getEffect().contains(Effect.SUPERIOR)) {
                    mod *= 1.0666;
                } else {
                    mod *= 1.05;
                }
            }

        } else if (style == RANGED) {
            if (pocket.getEffect().contains(Effect.CRUELTYSCRIM)) {
                if (pocket.getEffect().contains(Effect.SUPERIOR)) {
                    mod *= 1.0666;
                } else {
                    mod *= 1.05;
                }
            }
        }
        return mod;
    }
}
