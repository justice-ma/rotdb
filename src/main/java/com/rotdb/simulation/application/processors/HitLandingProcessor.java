package com.rotdb.simulation.application.processors;

import com.rotdb.calculation.domain.engine.CalculationEngine;
import com.rotdb.calculation.domain.engine.CalculationMode;
import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.calculation.domain.model.HitResult;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.ability.model.GeneratedBuffTiming;
import com.rotdb.shared.combat.domain.model.enums.HitType;
import com.rotdb.simulation.application.processors.result.HitLandingResult;
import com.rotdb.simulation.application.service.DamageRequestFactory;
import com.rotdb.simulation.domain.model.buff.AppliedBuffResult;
import com.rotdb.simulation.domain.model.buff.ConsumableStackResult;
import com.rotdb.simulation.domain.model.context.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HitLandingProcessor {
    public static HitLandingResult processHitLanding(Map<Integer, List<TimelineHit>> resolvedHitMap, int tick,
                                                     List<TimelineHit> newTimelineHits, Map<Integer, AbilityPlacement> placementIdMap,
                                                     boolean vestmentsBleedActiveAtTickStart, SimulationState simulationState,
                                                     Map<Integer, List<ScheduledHit>> scheduledHitMap, CalculationEngine engine,
                                                     Map<Integer, Integer> remainingHitsByPlacementId,
                                                     Map<Integer, List<ConsumableStackResult>> postDamageConsumptionsByPlacementId,
                                                     RotationCombatState state, int endingTick) {
        if (resolvedHitMap.get(tick) != null) {
            for (TimelineHit timelineHit : resolvedHitMap.get(tick)) {
                newTimelineHits.add(timelineHit);
                if (timelineHit.getHitType() != HitType.PERFECTEQUILIBRIUM) {
                    BuffProcessor.applyAbilityGeneratedBuffsWithTiming(placementIdMap.get(timelineHit.getPlacementId()),
                            simulationState, GeneratedBuffTiming.ON_HIT, vestmentsBleedActiveAtTickStart);
                }
            }
        }

        if (scheduledHitMap.get(tick) != null && scheduledHitMap.containsKey(tick)) {
            for (ScheduledHit scheduledHit : scheduledHitMap.get(tick)) {
                DamageResult result =
                        engine.calculateAbilityDamage(DamageRequestFactory.getDamageRequest(simulationState.getState(),
                                scheduledHit.parentAbility()), CalculationMode.HIT, scheduledHit.hitIndex());
                TimelineHit baseTimelineHit = null;
                for (HitResult hitResult : result.getHit()) {
                    int landingTick = tick;
                    if (hitResult.getHitType() == HitType.PERFECTEQUILIBRIUM && hitResult.getParentAbility() != AbilityId.RAPIDFIRE) {
                        landingTick++;
                    }
                    TimelineHit timelineHit = new TimelineHit(
                            hitResult.getHitMinDamage(),
                            hitResult.getHitMaxDamage(),
                            hitResult.getHitAvgDamage(),
                            hitResult.getHitMinCrit(),
                            hitResult.getHitMaxCrit(),
                            hitResult.getHitAvgCrit(),
                            hitResult.getHitMinNonCrit(),
                            hitResult.getHitMaxNonCrit(),
                            hitResult.getHitAvgNonCrit(),
                            hitResult.getHitIndex(),
                            hitResult.getHitTiming(),
                            landingTick,
                            hitResult.getCritChance(),
                            hitResult.getHitType(),
                            hitResult.getParentAbility(),
                            scheduledHit.placementId(),
                            hitResult.isDot(),
                            AbilityProvider.get(hitResult.getParentAbility(), state.getEquipment()).isChannel()
                    );

                    if (timelineHit.getHitType() != HitType.PERFECTEQUILIBRIUM) {
                        baseTimelineHit = timelineHit;
                    }

                    if (landingTick == tick) {
                        newTimelineHits.add(timelineHit);
                    } else {
                        if (resolvedHitMap.get(landingTick) == null) {
                            List<TimelineHit> newHits = new ArrayList<>();
                            newHits.add(timelineHit);
                            resolvedHitMap.put(landingTick, newHits);
                        } else {
                            resolvedHitMap.get(landingTick).add(timelineHit);
                        }
                    }
                    endingTick = Math.max(endingTick, landingTick);
                }

                if (baseTimelineHit != null) {
                    int landedPlacementId = scheduledHit.placementId();
                    remainingHitsByPlacementId.merge(landedPlacementId, -1, Integer::sum);

                    if (remainingHitsByPlacementId.get(landedPlacementId) == 0) {
                        List<ConsumableStackResult> postDamageList =
                                postDamageConsumptionsByPlacementId.get(landedPlacementId);
                        if (postDamageList != null) {
                            for (ConsumableStackResult stackResult : postDamageList) {
                                StackProcessor.consumeStacks(simulationState, stackResult);
                            }
                        }
                        postDamageConsumptionsByPlacementId.remove(landedPlacementId);
                        remainingHitsByPlacementId.remove(landedPlacementId);
                    }

                    List<AppliedBuffResult> appliedBuffResults = new ArrayList<>();

                    appliedBuffResults.addAll(BuffProcessor.applyAbilityGeneratedBuffsWithTiming(placementIdMap.get(baseTimelineHit.getPlacementId()), simulationState, GeneratedBuffTiming.ON_HIT, vestmentsBleedActiveAtTickStart));
                    StackProcessor.applyOnHitStacks(placementIdMap.get(baseTimelineHit.getPlacementId()), simulationState, baseTimelineHit);

                    for (AppliedBuffResult buff : appliedBuffResults) {
                        if (buff.resolvedDurationTicks() != null) {
                            endingTick = Math.max(endingTick, buff.resolvedDurationTicks() + tick);
                        }
                    }
                }
            }
        }
        return new HitLandingResult(endingTick);
    }
}
