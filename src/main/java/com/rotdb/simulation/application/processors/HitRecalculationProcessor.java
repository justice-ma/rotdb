package com.rotdb.simulation.application.processors;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.DamageCalculationTiming;
import com.rotdb.simulation.application.processors.result.HitRecalculationResult;
import com.rotdb.simulation.application.service.HitsScheduler;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.RotationCombatState;
import com.rotdb.simulation.domain.model.context.ScheduledHit;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.resolvers.buff.HitRecalculationPolicyResolver;

import java.util.List;
import java.util.Map;

public class HitRecalculationProcessor {
    public static HitRecalculationResult applyRecalculationPolicy(AbilityPlacement abilityPlacement, SimulationState simulationState,
                                                                  Map<Integer, List<ScheduledHit>> scheduledHitMap, Map<Integer,
                                                Integer> remainingHitsByPlacementId, int tick, int endingTick) {
        AbilityContext abilityContext = AbilityProvider.get(abilityPlacement.getPlacedAbility(),
                simulationState.getState().getEquipment());

        if (abilityContext.getDamageCalculationTiming() == DamageCalculationTiming.ON_RELEASE &&
            HitRecalculationPolicyResolver.requiresRecalculation(abilityContext, simulationState)) {
            List<ScheduledHit> newHits = HitsScheduler.schedule(abilityContext, abilityPlacement);
            HitsPlacementProcessor.addScheduledHits(scheduledHitMap, newHits);
            remainingHitsByPlacementId.put(abilityPlacement.getPlacementId(), newHits.size());

            for (ScheduledHit scheduledHit : newHits) {
                endingTick = Math.max(endingTick, scheduledHit.landingTick());
            }
        }
        return new HitRecalculationResult(endingTick);
    }

    public static void applyBuffStateOverlay(AbilityId abilityId, RotationCombatState currentCombatState, RotationCombatState releaseCombatState) {
        releaseCombatState.getBuffs().getBuffStacks().putAll(HitRecalculationPolicyResolver.resolveRecalculatedStacks(abilityId, currentCombatState));
    }
}
