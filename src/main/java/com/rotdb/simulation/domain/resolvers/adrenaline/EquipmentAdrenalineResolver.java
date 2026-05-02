package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.RotationSnapshot;

public class EquipmentAdrenalineResolver {
    public static double resolve(RotationSnapshot rc, EquipmentModel eq, BuffContext buff) {
        AbilityContext ability = rc.getAbilityContext();
        double adrenalineDelta = 0;

        if (buff.has(BuffId.VESTMENTSBLEED) && eq.getTotalVestmentsOfHavoc() >= 2 &&
                ability.getId().getTier() == AbilityTier.ULTIMATE && ability.getId().getStyle() == CombatStyles.MELEE) {
            adrenalineDelta += 20;
        }

        if (buff.has(BuffId.BLEEDS) && ability.getId().getStyle() == CombatStyles.MELEE &&
                eq.getHead().getEffect().contains(Effect.JAWSOFTHEABYSS)) {
            adrenalineDelta += (2 * buff.stacks(BuffId.BLEEDS));
        }

        if (buff.has(BuffId.RINGOFDEATHPROC) && eq.getRing().getEffect().contains(Effect.RINGOFDEATH)) {
            adrenalineDelta += 5;
        }

        if (ability.getId() == AbilityId.RAPIDFIRE) {
            if (eq.getTotalDracolichPieces() > 0) {
                adrenalineDelta += (eq.getDracolichPieces() * 0.2);
            }

            if (eq.getTotalEliteDracolichPieces() > 0) {
                adrenalineDelta += (eq.getEliteDracolichPieces() * 0.5);
            }
        }

        if (buff.has(BuffId.FURYOFTHESMALL) && ability.getId().getTier() == AbilityTier.BASIC) {
            adrenalineDelta += 1;
        }

        System.out.println("EQ: " + adrenalineDelta);
        return adrenalineDelta;
    }
}
