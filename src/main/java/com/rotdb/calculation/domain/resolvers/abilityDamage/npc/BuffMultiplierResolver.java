package com.rotdb.calculation.domain.resolvers.abilityDamage.npc;

import com.rotdb.calculation.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;
import com.rotdb.calculation.domain.model.player.BuffContext;

import static com.rotdb.calculation.domain.model.enums.AbilityTier.BASIC;

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
