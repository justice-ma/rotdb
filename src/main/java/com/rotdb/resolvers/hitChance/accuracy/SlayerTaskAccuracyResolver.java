package com.rotdb.resolvers.hitChance.accuracy;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.context.TargetContext;
import com.rotdb.model.enums.CombatStyles;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.equipment.EquipmentSlot;

import static com.rotdb.model.enums.CombatStyles.*;
import static com.rotdb.model.enums.CombatStyles.NECROMANCY;

public class SlayerTaskAccuracyResolver {
    public static double resolve(CalculationContext context) {
        EquipmentSlot gloves = context.getEquipment().getGloves();
        EquipmentSlot head = context.getEquipment().getHead();
        TargetContext target = context.getTarget();
        CombatStyles style = context.getEquipment().getCombatStyle();

        double accuracyModifier = 0;

        if (gloves.getEffect().contains(Effect.DRAGONSLAYERGLOVES) && context.getTarget().isDragon() && context.getTarget().isSlayerTask()) accuracyModifier += 0.1;
        if (head.getEffect().contains(Effect.SLAYERHELM) && context.getTarget().isSlayerTask()) {
            if (head.getEffect().contains(Effect.FULL)) {
                accuracyModifier += 0.125;
            } else if (head.getEffect().contains(Effect.REINFORCED)) {
                accuracyModifier += 0.13;
            } else if (head.getEffect().contains(Effect.STRONG)) {
                accuracyModifier += 0.135;
            } else if (head.getEffect().contains(Effect.MIGHTY)) {
                accuracyModifier += 0.14;
            } else if (head.getEffect().contains(Effect.CORRUPTED)) {
                accuracyModifier += 0.145;
            } else {
                if (style == MELEE) {
                    accuracyModifier += 0.125;
                }
            }
        }

        if (head.getEffect().contains(Effect.FOCUSSIGHT) && style == RANGED && context.getTarget().isSlayerTask()) {
            accuracyModifier += 0.125;
        }

        if (head.getEffect().contains(Effect.BLACKMASK) && style == MELEE && context.getTarget().isSlayerTask()) {
            accuracyModifier += 0.125;
        }

        if (head.getEffect().contains(Effect.HEXCREST) && style == MAGIC && context.getTarget().isSlayerTask()) {
            accuracyModifier += 0.125;
        }

        if (head.getEffect().contains(Effect.SPECTRALLENS) && style == NECROMANCY && context.getTarget().isSlayerTask()) {
            accuracyModifier += 0.125;
        }
        return accuracyModifier;
    }
}
