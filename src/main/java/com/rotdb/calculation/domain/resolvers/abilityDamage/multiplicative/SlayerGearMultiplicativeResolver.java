package com.rotdb.calculation.domain.resolvers.abilityDamage.multiplicative;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.context.TargetContext;
import com.rotdb.shared.combat.domain.model.enums.*;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;

public class SlayerGearMultiplicativeResolver {
    public static double resolve(CalculationContext context) {
        CombatStyles style = context.getEquipment().getCombatStyle();
        BuffContext buff = context.getBuffs();
        PerkContext perk = context.getPerks();
        TargetContext target = context.getTarget();
        EquipmentSlot head = context.getEquipment().getHead();

        double mod = 1.0;

        int slayerHelmTier = 0;

        if (buff.has(BuffId.SLAYERHELM)) {
            slayerHelmTier = buff.stacks(BuffId.SLAYERHELM);
        } else if (head.getEffect().contains(Effect.SLAYERHELM)) {
            if (head.getEffect().contains(Effect.CORRUPTED)) {
                slayerHelmTier = 6;
            } else if (head.getEffect().contains(Effect.MIGHTY)) {
                slayerHelmTier = 5;
            } else if (head.getEffect().contains(Effect.STRONG)) {
                slayerHelmTier = 4;
            } else if (head.getEffect().contains(Effect.REINFORCED)) {
                slayerHelmTier = 3;
            } else if (head.getEffect().contains(Effect.FULL)) {
                slayerHelmTier = 2;
            } else {
                slayerHelmTier = 1;
            }
        }

        switch (slayerHelmTier) {
            case 6 -> mod *= 1.095;
            case 5 -> mod *= 1.09;
            case 4 -> mod *= 1.085;
            case 3 -> mod *= 1.08;
            case 2 -> mod *= 1.075;
            case 1 -> {
                if (style == CombatStyles.MELEE) {
                    mod *= 1.075;
                }
            }
        }

        if (head.getEffect().contains(Effect.FOCUSSIGHT) && style == CombatStyles.RANGED) {
            mod *= 1.075;
        }

        if (head.getEffect().contains(Effect.BLACKMASK) && style == CombatStyles.MELEE) {
            mod *= 1.075;
        }

        if (head.getEffect().contains(Effect.HEXCREST) && style == CombatStyles.MAGIC) {
            mod *= 1.075;
        }

        if (head.getEffect().contains(Effect.SPECTRALLENS) && style == CombatStyles.NECROMANCY) {
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

        if (perk.has(Perks.GENOCIDAL)) {
            mod *= 1 + (perk.getGenocidalRank() / 100.0);
        }
        return mod;
    }
}
