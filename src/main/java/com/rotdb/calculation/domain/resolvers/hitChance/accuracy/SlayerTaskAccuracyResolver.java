package com.rotdb.calculation.domain.resolvers.hitChance.accuracy;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.context.TargetContext;
import com.rotdb.calculation.domain.model.enums.CombatStyles;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.enums.TargetTags;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;

import static com.rotdb.calculation.domain.model.enums.CombatStyles.*;

public class SlayerTaskAccuracyResolver {
    public static double resolve(CalculationContext context) {
        EquipmentSlot gloves = context.getEquipment().getGloves();
        EquipmentSlot head = context.getEquipment().getHead();
        TargetContext target = context.getTarget();
        CombatStyles style = context.getEquipment().getCombatStyle();

        double accuracyModifier = 0;

        if (gloves.getEffect().contains(Effect.DRAGONSLAYERGLOVES) && context.getTarget().has(TargetTags.DRAGON)) accuracyModifier += 0.1;
        if (head.getEffect().contains(Effect.SLAYERHELM)) {
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

        if (head.getEffect().contains(Effect.FOCUSSIGHT) && style == RANGED) {
            accuracyModifier += 0.125;
        }

        if (head.getEffect().contains(Effect.BLACKMASK) && style == MELEE) {
            accuracyModifier += 0.125;
        }

        if (head.getEffect().contains(Effect.HEXCREST) && style == MAGIC) {
            accuracyModifier += 0.125;
        }

        if (head.getEffect().contains(Effect.SPECTRALLENS) && style == NECROMANCY) {
            accuracyModifier += 0.125;
        }
        return accuracyModifier;
    }
}
