package com.rotdb.simulation.application.processors;

import com.rotdb.calculation.domain.engine.CalculationEngine;
import com.rotdb.calculation.domain.engine.CalculationMode;
import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.ability.model.AbilityCooldownTiming;
import com.rotdb.shared.ability.model.GeneratedBuffTiming;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.DamageCalculationTiming;
import com.rotdb.simulation.application.processors.result.AbilityReleaseResult;
import com.rotdb.simulation.application.service.DamageRequestFactory;
import com.rotdb.simulation.application.service.HitsScheduler;
import com.rotdb.simulation.application.snapshot.SimulationStateSnapshotCopier;
import com.rotdb.simulation.domain.model.buff.AppliedBuffResult;
import com.rotdb.simulation.domain.model.buff.ConsumableStackResult;
import com.rotdb.simulation.domain.model.buff.enums.StackConsumptionTiming;
import com.rotdb.simulation.domain.model.context.*;
import com.rotdb.simulation.domain.resolvers.buff.HitRecalculationPolicyResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbilityReleaseProcessor {
    public static AbilityReleaseResult processRelease(Map<Integer, List<AbilityPlacement>> abilitiesByReleaseTick,
                                                      Map<Integer, List<TimelineHit>> resolvedHitMap,
                                                      Map<Integer, List<ScheduledHit>> scheduledHitMap,
                                                      Map<Integer, List<ConsumableStackResult>> postDamageConsumptionsByPlacementId,
                                                      Map<Integer, Integer> remainingHitsByPlacementId, TickSnapshot tickSnapshot,
                                                      boolean vestmentsBleedActiveAtTickStart,
                                                      Map<Integer, List<AbilityPlacement>> completionMap,
                                                      List<AbilityPlacement> releasedAbilities,
                                                      int tick, SimulationState simulationState, int endingTick,
                                                      CalculationEngine engine,
                                                      Map<Integer, RotationCombatState> releaseStateByPlacementId,
                                                      SimulationStateSnapshotCopier copier) {
        if (abilitiesByReleaseTick.containsKey(tick)) {
            for (AbilityPlacement abilityPlacement : abilitiesByReleaseTick.get(tick)) {
                DamageResult damageResult = null;
                List<ConsumableStackResult> consumableStackResults = new ArrayList<>();
                for (ConsumableStackResult buff : StackProcessor.prepareConsumableStacksForDamage(abilityPlacement,
                        simulationState)) {
                    if (buff.appliedBuffResult() != null && buff.appliedBuffResult().resolvedDurationTicks() != null) {
                        endingTick = Math.max(buff.appliedBuffResult().resolvedDurationTicks() + tick, endingTick);
                    }
                    if (buff.consumptionTiming() == StackConsumptionTiming.PRE_DAMAGE) {
                        StackProcessor.consumeStacks(simulationState, buff);
                    }
                    if (buff.consumptionTiming() == StackConsumptionTiming.POST_DAMAGE) {
                        consumableStackResults.add(buff);
                    }
                }

                AbilityContext abilityContext = AbilityProvider.get(abilityPlacement.getPlacedAbility(), simulationState.getState().getEquipment());
                boolean requiresRecalculation = HitRecalculationPolicyResolver.requiresRecalculation(abilityContext,
                        simulationState);

                if (abilityContext.getDamageCalculationTiming() == DamageCalculationTiming.ON_RELEASE && !requiresRecalculation) {
                    StackProcessor.applyOnReleaseResolvedHitStacks(abilityPlacement, simulationState);
                    for (AppliedBuffResult appliedBuffresult : BuffProcessor.applyPreDamageReleaseBuffs(abilityPlacement, simulationState)) {
                        if (appliedBuffresult.resolvedDurationTicks() != null) {
                            endingTick = Math.max(endingTick, appliedBuffresult.resolvedDurationTicks() + tick);
                        }
                    }
                    damageResult = engine.calculateAbilityDamage(DamageRequestFactory.getDamageRequest(simulationState.getState(),
                            abilityPlacement.getPlacedAbility()), CalculationMode.ABILITY, null);
                    List<TimelineHit> hits = HitsScheduler.schedule(damageResult, abilityPlacement, simulationState.getState().getEquipment());
                    HitsPlacementProcessor.addTimelineHits(resolvedHitMap, hits);
                    for (TimelineHit hit : hits) {
                        endingTick = Math.max(hit.getLandingTick(), endingTick);
                    }
                } else if (abilityContext.getDamageCalculationTiming() == DamageCalculationTiming.ON_RELEASE) {
                    endingTick = (HitRecalculationProcessor.applyRecalculationPolicy(abilityPlacement, simulationState,
                            scheduledHitMap, remainingHitsByPlacementId, tick, endingTick)).endingTick();
                    releaseStateByPlacementId.put(abilityPlacement.getPlacementId(),
                            copier.copyCombatState(simulationState.getState()));
                }

                List<ScheduledHit> scheduledHits;
                if (abilityContext.getDamageCalculationTiming() == DamageCalculationTiming.ON_HIT) {
                    damageResult = engine.calculateAbilityDamage(DamageRequestFactory.getDamageRequest(simulationState.getState(),
                            abilityPlacement.getPlacedAbility()), CalculationMode.ABILITY, null);
                    scheduledHits = HitsScheduler.schedule(abilityContext, abilityPlacement);
                    HitsPlacementProcessor.addScheduledHits(scheduledHitMap, scheduledHits);

                    List<ConsumableStackResult> filteredConsumableStackResults = new ArrayList<>();
                    for (ConsumableStackResult csr : consumableStackResults) {
                        if (csr.consumptionTiming() == StackConsumptionTiming.POST_DAMAGE) {
                            filteredConsumableStackResults.add(csr);
                        }
                    }

                    if (!filteredConsumableStackResults.isEmpty()) {
                        postDamageConsumptionsByPlacementId.put(
                                abilityPlacement.getPlacementId(),
                                filteredConsumableStackResults
                        );
                    }

                    remainingHitsByPlacementId.put(
                            abilityPlacement.getPlacementId(),
                            scheduledHits.size()
                    );

                    for (ScheduledHit timelineHit : scheduledHits) {
                        int landingTick = timelineHit.landingTick();
                        endingTick = Math.max(endingTick, landingTick);
                    }
                }

                if (abilityContext.getDamageCalculationTiming() == DamageCalculationTiming.ON_RELEASE) {
                    StackProcessor.applyOnReleaseStacks(abilityPlacement, simulationState);

                    if (!requiresRecalculation) {
                        StackProcessor.applyOnReleaseResolvedDamageStacks(damageResult, simulationState);
                    }

                    for (TriggeredHitResult triggeredHitResult :
                            StackProcessor.prepareStackGeneratedAbilities(simulationState, abilityPlacement)) {
                        DamageResult procDamageResult =
                                engine.calculateAbilityDamage(DamageRequestFactory.getDamageRequest(simulationState.getState(),
                                        triggeredHitResult.abilityId()), CalculationMode.ABILITY, null);
                        AbilityPlacement triggeredAbilityPlacement = new AbilityPlacement();
                        triggeredAbilityPlacement.setCastTick(triggeredHitResult.triggerTick());
                        triggeredAbilityPlacement.setPlacedAbility(triggeredHitResult.abilityId());
                        triggeredAbilityPlacement.setReleaseTick(abilityPlacement.getReleaseTick());
                        List<TimelineHit> procHits = HitsScheduler.schedule(procDamageResult, triggeredAbilityPlacement,
                                simulationState.getState().getEquipment());
                        HitsPlacementProcessor.addTimelineHits(resolvedHitMap, procHits);
                        AbilityCooldownProcessor.generateAbilityCooldownWarnings(simulationState, triggeredAbilityPlacement, tickSnapshot);
                        AbilityCooldownProcessor.initializeCooldown(simulationState, triggeredAbilityPlacement);
                        endingTick = Math.max(endingTick, triggeredHitResult.triggerTick() + triggeredHitResult.delay());
                    }

                    for (ConsumableStackResult consumableStackResult : consumableStackResults) {
                        StackProcessor.consumeStacks(simulationState, consumableStackResult);
                    }
                }

                if (abilityContext.getAbilityCooldownTiming() == AbilityCooldownTiming.ON_RELEASE) {
                    AbilityCooldownProcessor.generateAbilityCooldownWarnings(simulationState, abilityPlacement, tickSnapshot);
                    AbilityCooldownProcessor.initializeCooldown(simulationState, abilityPlacement);

                    if (!requiresRecalculation) {
                        AbilityCooldownProcessor.applyPlacementCooldownEffects(simulationState, abilityPlacement, damageResult);
                    }
                }
                for (AppliedBuffResult buff : BuffProcessor.applyAbilityGeneratedBuffsWithTiming(abilityPlacement, simulationState, GeneratedBuffTiming.ON_RELEASE, vestmentsBleedActiveAtTickStart)) {
                    if (buff.resolvedDurationTicks() != null) {
                        endingTick = Math.max(buff.resolvedDurationTicks() + tick, endingTick);
                    }
                }

                if (completionMap.get(abilityPlacement.getCompletionTick()) == null) {
                    List<AbilityPlacement> abilityCompletions = new ArrayList<>();
                    abilityCompletions.add(abilityPlacement);
                    completionMap.put(abilityPlacement.getCompletionTick(), abilityCompletions);
                } else {
                    completionMap.get(abilityPlacement.getCompletionTick()).add(abilityPlacement);
                }
            }
            releasedAbilities.addAll(abilitiesByReleaseTick.get(tick));
        }
        return new AbilityReleaseResult(endingTick);
    }
}
