package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.calculation.domain.model.HitResult;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.ScheduledHit;
import com.rotdb.simulation.domain.model.context.SimulationState;

import java.util.List;

public class AdrenalineBuffUltimatePreHitTickResolver {
    public static double resolve(SimulationState simulationState, List<ScheduledHit> scheduledHits, HitResult hitResult) {
        double adrenalineDelta = 0;
        BuffContext buff = simulationState.getState().getBuffs();
        if (scheduledHits == null || scheduledHits.isEmpty()) {
            return adrenalineDelta;
        }
        for (ScheduledHit timelineHit : scheduledHits) {
            AbilityContext ability = AbilityProvider.get(timelineHit.parentAbility(),
                    simulationState.getState().getEquipment());
            if (!ability.isChannel()) continue;
            if (buff.has(BuffId.IMBUESHADOWS) && ability.getCombatStyle() == CombatStyles.RANGED) {
                if (!timelineHit.dot()) adrenalineDelta += 5;
            }

            if (buff.has(BuffId.TSUNAMI) && ability.getCombatStyle() == CombatStyles.MAGIC) {
                if (!timelineHit.dot()) adrenalineDelta += 8 * hitResult.getCritChance();
            }
        }
        return adrenalineDelta;
    }
}
