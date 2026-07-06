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
import com.rotdb.simulation.domain.model.buff.AppliedBuffResult;
import com.rotdb.simulation.domain.model.buff.ConsumableStackResult;
import com.rotdb.simulation.domain.model.buff.enums.StackConsumptionTiming;
import com.rotdb.simulation.domain.model.context.*;

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
                                                      int tick,
                                                      SimulationState simulationState, int endingTick, CalculationEngine engine) {
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

                if (abilityContext.getDamageCalculationTiming() == DamageCalculationTiming.ON_RELEASE) {
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
                    StackProcessor.applyOnReleaseResolvedDamageStacks(damageResult, simulationState);
                    for (ConsumableStackResult consumableStackResult : consumableStackResults) {
                        StackProcessor.consumeStacks(simulationState, consumableStackResult);
                    }
                }

                if (abilityContext.getAbilityCooldownTiming() == AbilityCooldownTiming.ON_RELEASE) {
                    AbilityCooldownProcessor.generateAbilityCooldownWarnings(simulationState, abilityPlacement, tickSnapshot);
                    AbilityCooldownProcessor.initializeCooldown(simulationState, abilityPlacement);
                    AbilityCooldownProcessor.applyPlacementCooldownEffects(simulationState, abilityPlacement, damageResult);
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
