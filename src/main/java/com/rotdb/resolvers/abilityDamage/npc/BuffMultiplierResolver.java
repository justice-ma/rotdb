package com.rotdb.resolvers.abilityDamage.npc;

import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.BuffId;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.equipment.EquipmentSlot;
import com.rotdb.model.player.BuffContext;
import com.rotdb.model.player.SkillsContext;

import static com.rotdb.model.enums.AbilityTier.BASIC;

public class BuffMultiplierResolver {
    public static double resolve(CalculationContext context, AbilityHitsContext hit) {
        BuffContext buff = context.getBuffs();
        EquipmentSlot gloves = context.getEquipment().getGloves();
        double mod = 1;
        if (buff.has(BuffId.SMASH) && gloves.getEffect().contains(Effect.GLOVESOFPASSAGE) && hit.isDot()) {
            if (buff.has(BuffId.ENCHANTMENTOFAGONY)) {
                mod *= 1.25;
            } else {
                mod *= 1.2;
            }
        }

        if (buff.has(BuffId.PUZZLEBOX) && buff.stacks(BuffId.PUZZLEBOX) > 1) {
            mod *= 1.03 + (Math.min(6, buff.stacks(BuffId.PUZZLEBOX)) / 100.0);
        }

        if (buff.has(BuffId.GUARDIANSTRIUMPH) && buff.stacks(BuffId.GUARDIANSTRIUMPH) > 0 && hit.getTier() == BASIC) {
            mod *= 1 + (0.2 * buff.stacks(BuffId.GUARDIANSTRIUMPH));
        }
        return mod;
    }
}
