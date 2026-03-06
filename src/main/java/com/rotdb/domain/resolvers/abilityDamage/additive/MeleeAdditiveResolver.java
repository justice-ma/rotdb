package com.rotdb.domain.resolvers.abilityDamage.additive;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.context.TargetContext;
import com.rotdb.domain.model.enums.BuffId;
import com.rotdb.domain.model.enums.CombatStyles;
import com.rotdb.domain.model.enums.Effect;
import com.rotdb.domain.model.equipment.EquipmentSlot;
import com.rotdb.domain.model.player.BuffContext;

public class MeleeAdditiveResolver {
    public static double resolve(CalculationContext context, int hitIndex) {
        BuffContext buff = context.getBuffs();
        TargetContext target = context.getTarget();
        EquipmentSlot neck = context.getEquipment().getNeck();
        EquipmentSlot gloves = context.getEquipment().getGloves();
        EquipmentSlot mainhand = context.getEquipment().getMainhand();

        double mod = 0;
        if (context.getEquipment().getCombatStyle() == CombatStyles.MELEE) {
            // TODO: Check for dw - does it apply to only mh? both?
            if (neck.getEffect().contains(Effect.BERSERKERNECKLACE) &&
                    mainhand.getEffect().contains(Effect.OBSIDIANWEAPON)) {
                mod += 0.05;
            }

            if (mainhand.getEffect().contains(Effect.EKZEKKIL) && target.isFlameboundRival()) {
                mod += 0.12;
            }

            if (gloves.getEffect().contains(Effect.GLOVESOFPASSAGE) && buff.has(BuffId.SMASH)) {
                if (context.getAbility().isChannel()) {
                    if (hitIndex == 0) {
                        mod += buff.has(BuffId.ENCHANTMENTOFAGONY) ? 0.16 : 0.1;
                    }
                } else {
                    mod += buff.has(BuffId.ENCHANTMENTOFAGONY) ? 0.16 : 0.1;
                }
            }
            if (buff.has(BuffId.GRAVITATE) && buff.stacks(BuffId.GRAVITATE) > 0) {
                mod += Math.min(0.2, buff.stacks(BuffId.GRAVITATE) / 100.0);
            }

            if (neck.getEffect().contains(Effect.AMHEJ)) {
                double value = (context.getSkills().getBoostedStrength() * 0.05) / 100.0;
                double floored = Math.floor(value * 100) / 100.0;
                mod += floored;
            }
        }
        return mod;
    }
}
