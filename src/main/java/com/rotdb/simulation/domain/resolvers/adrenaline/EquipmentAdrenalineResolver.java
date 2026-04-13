package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.AdrenalineContext;
import com.rotdb.simulation.domain.model.context.RotationContext;

public class EquipmentAdrenalineResolver {
    public static double resolve(RotationContext rc, EquipmentModel eq, BuffContext buff) {
        AdrenalineContext ac = rc.getAdrenalineContext();
        AbilityContext ability = rc.getAbilityContext();
        if (buff.has(BuffId.VESTMENTSBLEED)) {
            ac.addAdrenaline(0.5);

            if (eq.getTotalVestmentsOfHavoc() >= 2 && ability.getId().getTier() == AbilityTier.ULTIMATE) {
                ac.addAdrenaline(20);
            }
        }

        if (buff.has(BuffId.ASYLUMSURGEONSRINGPROC) && ability.getAdrenaline() < 0
            && eq.getRing().getEffect().contains(Effect.ASYLUMSURGEONSRING)) {
            ac.addAdrenaline(15);
        }

        if (buff.has(BuffId.BLEEDS) && ability.getId().getStyle() == CombatStyles.MELEE) {
            ac.addAdrenaline(2 * buff.stacks(BuffId.BLEEDS));
        }

        if (buff.has(BuffId.RINGOFDEATHPROC) && eq.getRing().getEffect().contains(Effect.RINGOFDEATH)) {
            ac.addAdrenaline(5);
        }

        if (ability.getId() == AbilityId.RAPIDFIRE) {
            if (eq.getTotalDracolichPieces() > 0) {
                ac.addAdrenaline(eq.getDracolichPieces() * 0.2);
            }

            if (eq.getTotalEliteDracolichPieces() > 0) {
                ac.addAdrenaline(eq.getEliteTectonicPieces() * 0.5);
            }
        }

        return ac.getAdrenaline();
    }
}
