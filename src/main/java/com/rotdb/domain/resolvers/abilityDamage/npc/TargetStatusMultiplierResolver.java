package com.rotdb.domain.resolvers.abilityDamage.npc;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.context.TargetContext;
import com.rotdb.domain.model.enums.BuffId;
import com.rotdb.domain.model.enums.Effect;
import com.rotdb.domain.model.equipment.EquipmentSlot;
import com.rotdb.domain.model.player.BuffContext;

public class TargetStatusMultiplierResolver {
    public static double resolve(CalculationContext context) {
        TargetContext target = context.getTarget();
        BuffContext buff = context.getBuffs();
        EquipmentSlot head = context.getEquipment().getHead();
        EquipmentSlot body = context.getEquipment().getBody();
        EquipmentSlot legs = context.getEquipment().getLegs();
        EquipmentSlot cape = context.getEquipment().getCape();

        double mod = 1;
        if (target.isVulned()) {
            mod *= 1.1;
        }

        if (target.isCursed() && !target.isVulned()) {
            mod *= 1.05;
        }


        if (target.isCroesusSpored()) {
            mod *= 1.1;
        }

        if (buff.has(BuffId.NOPENOPENOPE) && buff.stacks(BuffId.NOPENOPENOPE) > 0 && target.isSpider()) {
            mod *= 1.01 + (Math.min(2, buff.stacks(BuffId.NOPENOPENOPE)) / 100.0);
        }

        if (target.isGhostHunter()) {
            int ghostHunterPieces = 0;
            if (head.getEffect().contains(Effect.GHOSTHUNTER)) ghostHunterPieces++;
            if (body.getEffect().contains(Effect.GHOSTHUNTER)) ghostHunterPieces++;
            if (legs.getEffect().contains(Effect.GHOSTHUNTER)) ghostHunterPieces++;
            if (cape.getEffect().contains(Effect.GHOSTHUNTER)) ghostHunterPieces++;

            if (ghostHunterPieces == 1) {
                mod *= 1.03;
            } else if (ghostHunterPieces == 2) {
                mod *= 1.06;
            } else if (ghostHunterPieces > 2) {
                mod *= 1.1;
            }
        }

        if (buff.has(BuffId.SLAYERLODGE) && buff.stacks(BuffId.SLAYERLODGE) > 0 && target.isSlayerTask()) {
            mod *= buff.stacks(BuffId.SLAYERLODGE) == 1 ? 1.01 : buff.stacks(BuffId.SLAYERLODGE) == 2 ? 1.03 : 1.06;
        }

        return mod;
    }
}
