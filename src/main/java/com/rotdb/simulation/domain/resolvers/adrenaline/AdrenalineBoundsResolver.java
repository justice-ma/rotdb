package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.AdrenalineContext;
import com.rotdb.simulation.domain.model.context.RotationContext;

public class AdrenalineBoundsResolver {
    public static void resolve(RotationContext rc, EquipmentModel eq, BuffContext buff) {
        AdrenalineContext ac = rc.getAdrenalineContext();

        if (eq.getTotalVestmentsOfHavoc() >= 4 && eq.getMainhand().getClazz() == CombatStyles.MELEE) {
            ac.setMaximumBound(ac.getMaximumBound() + 20);
        }

        if (buff.has(BuffId.HEIGHTENEDSENSES)) {
            ac.setMaximumBound(ac.getMaximumBound() + 10);
        }
    }
}
