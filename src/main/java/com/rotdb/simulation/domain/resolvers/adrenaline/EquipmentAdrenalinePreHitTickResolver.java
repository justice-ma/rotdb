package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.model.context.TimelineHit;

import java.util.List;

public class EquipmentAdrenalinePreHitTickResolver {
    public static double resolve(SimulationState simulationState, List<TimelineHit> timelineHits) {
        double adrenalineDelta = 0;
        EquipmentModel eq = simulationState.getState().getEquipment();
        if (timelineHits == null || timelineHits.isEmpty()) {
            return adrenalineDelta;
        }
        for (TimelineHit timelineHit : timelineHits) {
            AbilityContext ability = AbilityProvider.get(timelineHit.getParentAbility(), eq);
            if (ability.getId() == AbilityId.RAPIDFIRE) {
                if (eq.getTotalDracolichPieces() > 0) {
                    adrenalineDelta += (eq.getTotalDracolichPieces() * 0.2);
                }

                if (eq.getTotalEliteDracolichPieces() > 0) {
                    adrenalineDelta += (eq.getTotalEliteDracolichPieces() * 0.5);
                }
            }
        }
        return adrenalineDelta;
    }
}
