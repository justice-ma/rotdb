package com.rotdb.calculation.domain.resolvers.abilityDamage.multiplicative;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.context.TargetContext;
import com.rotdb.calculation.domain.model.enums.*;
import com.rotdb.calculation.domain.model.enums.*;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;
import com.rotdb.calculation.domain.model.equipment.PerkContext;
import com.rotdb.calculation.domain.model.player.BuffContext;

import static com.rotdb.calculation.domain.model.enums.CombatStyles.*;

public class SlayerGearMultiplicativeResolver {
    public static double resolve(CalculationContext context) {
        CombatStyles style = context.getEquipment().getCombatStyle();
        BuffContext buff = context.getBuffs();
        PerkContext perk = context.getPerks();
        TargetContext target = context.getTarget();
        EquipmentSlot head = context.getEquipment().getHead();

        double mod = 1;
        if (head.getEffect().contains(Effect.SLAYERHELM)) {
            if (head.getEffect().contains(Effect.FULL)) {
                mod *= 1.075;
            } else if (head.getEffect().contains(Effect.REINFORCED)) {
                mod *= 1.08;
            } else if (head.getEffect().contains(Effect.STRONG)) {
                mod *= 1.085;
            } else if (head.getEffect().contains(Effect.MIGHTY)) {
                mod *= 1.09;
            } else if (head.getEffect().contains(Effect.CORRUPTED)) {
                mod *= 1.095;
            } else {
                if (style == MELEE) {
                    mod *= 1.075;
                }
            }
        }

        if (head.getEffect().contains(Effect.FOCUSSIGHT) && style == RANGED) {
            mod *= 1.075;
        }

        if (head.getEffect().contains(Effect.BLACKMASK) && style == MELEE) {
            mod *= 1.075;
        }

        if (head.getEffect().contains(Effect.HEXCREST) && style == MAGIC) {
            mod *= 1.075;
        }

        if (head.getEffect().contains(Effect.SPECTRALLENS) && style == NECROMANCY) {
            mod *= 1.075;
        }

        if (buff.has(BuffId.GUARDHOUSE) && buff.stacks(BuffId.GUARDHOUSE) > 0) {
            if (target.has(TargetTags.UNDEAD)) {
                mod *= 1.02;
            } else {
                mod *= 1.01;
            }
            if (buff.stacks(BuffId.GUARDHOUSE) > 2 && (double) target.getCurrentHp() / target.getMaxHp() < 0.25 && !target.has(TargetTags.BOSS)) {
                mod *= 1.1;
            }
        }

        if (perk.has(Perks.GENOCIDAL) && target.getCurrentTask() > 0) {
            double modifier = (1 / 10.0) * Math.floor(5 * (1 - ((target.getCurrentTask() * 1.0) / target.getStartingTask())) * 10) / 100.0;
            mod *= 1 + modifier;
        }
        return mod;
    }
}
