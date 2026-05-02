package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;

public class AdrenalineBuffUltimateResolver {
    public static double resolve(AbilityContext ability, BuffContext buff, EquipmentModel eq) {
        double adrenalineDelta = 0;
        if (buff.has(BuffId.IMBUESHADOWS) && ability.getId().getStyle() == CombatStyles.RANGED) {
            for (AbilityHitsContext hit : ability.getHits()) {
                if (!hit.isDot()) adrenalineDelta += 5;
            }
        }
        return adrenalineDelta;
    }
}
