package com.rotdb.calculation.domain.resolvers.abilityDamage.npc;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.context.TargetContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.enums.TargetTags;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;

public class TargetStatusMultiplierResolver {
    public static double resolve(CalculationContext context) {
        TargetContext target = context.getTarget();
        BuffContext buff = context.getBuffs();
        EquipmentModel equipment = context.getEquipment();

        double mod = 1;
        if (buff.has(BuffId.VULNED)) {
            mod *= 1.1;
        }

        if (buff.has(BuffId.CURSED) && !buff.has(BuffId.VULNED)) {
            mod *= 1.05;
        }


        if (buff.has(BuffId.CROESUSSPORED)) {
            mod *= 1.1;
        }

        if (buff.has(BuffId.NOPENOPENOPE) && buff.stacks(BuffId.NOPENOPENOPE) > 0 && target.has(TargetTags.SPIDER)) {
            mod *= 1.01 + (Math.min(2, buff.stacks(BuffId.NOPENOPENOPE)) / 100.0);
        }

        if (target.has(TargetTags.GHOSTHUNTER)) {
            int ghostHunterPieces = equipment.countSetPieces(Effect.GHOSTHUNTER, buff,
                    equipment.getHead(), equipment.getBody(), equipment.getLegs(), equipment.getCape());

            if (ghostHunterPieces == 1) {
                mod *= 1.03;
            } else if (ghostHunterPieces == 2) {
                mod *= 1.06;
            } else if (ghostHunterPieces > 2) {
                mod *= 1.1;
            }
        }

        if (buff.has(BuffId.SLAYERLODGE) && buff.stacks(BuffId.SLAYERLODGE) > 0) {
            mod *= buff.stacks(BuffId.SLAYERLODGE) == 1 ? 1.01 : buff.stacks(BuffId.SLAYERLODGE) == 2 ? 1.03 : 1.06;
        }

        return mod;
    }
}
