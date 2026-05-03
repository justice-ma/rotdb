package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.model.context.TimelineHit;

import java.util.List;

public class AdrenalineBuffUltimatePreHitTickResolver {
    public static double resolve(SimulationState simulationState, List<TimelineHit> timelineHits) {
        double adrenalineDelta = 0;
        BuffContext buff = simulationState.getState().getBuffs();
        if (timelineHits == null || timelineHits.isEmpty()) {
            return adrenalineDelta;
        }
        for (TimelineHit timelineHit : timelineHits) {
            AbilityContext ability = AbilityProvider.get(timelineHit.getParentAbility(), simulationState.getState().getEquipment());
            if (!ability.isChannel()) continue;
            if (buff.has(BuffId.IMBUESHADOWS) && ability.getCombatStyle() == CombatStyles.RANGED) {
                if (!timelineHit.isDot()) adrenalineDelta += 5;
            }

            if (buff.has(BuffId.TSUNAMI) && ability.getCombatStyle() == CombatStyles.MAGIC) {
                if (!timelineHit.isDot()) adrenalineDelta += 8 * timelineHit.getCritChance();
            }
        }
        return adrenalineDelta;
    }
}
