package com.rotdb.simulation.application.service;

import com.rotdb.calculation.domain.engine.CalculationEngine;
import com.rotdb.calculation.domain.engine.CalculationMode;
import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.calculation.domain.model.HitResult;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.ability.model.AbilityCooldownTiming;
import com.rotdb.shared.ability.model.GeneratedBuffTiming;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.DamageCalculationTiming;
import com.rotdb.simulation.application.processors.*;
import com.rotdb.simulation.application.snapshot.SimulationStateSnapshotCopier;
import com.rotdb.simulation.domain.model.buff.AppliedBuffResult;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.ConsumableStackResult;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
import com.rotdb.simulation.domain.model.buff.enums.StackConsumptionTiming;
import com.rotdb.simulation.domain.model.config.SimulationConfig;
import com.rotdb.simulation.domain.model.context.*;
import com.rotdb.simulation.domain.provider.BuffProvider;

import java.util.*;

public class RotationTimelineService {
    private final CalculationEngine engine;
    private final SimulationStateSnapshotCopier snapshotCopier;

    public RotationTimelineService(CalculationEngine engine, SimulationStateSnapshotCopier snapshotCopier) {
        this.engine = engine;
        this.snapshotCopier = snapshotCopier;
    }

    public RotationTimeline build(RotationCombatState state, List<AbilityPlacement> abilityPlacements, List<BuffPlacement> buffPlacements, SimulationConfig config) {
        RotationTimeline timeline = new RotationTimeline();
        timeline.setTimeline(new ArrayList<>());

        SimulationState simulationState = initializeState(state, config);

        int endingTick = 0;
        int startingTick = Integer.MAX_VALUE;

        Map<Integer, List<AbilityPlacement>> abilitiesByCastTick = PlacementProcessor.groupAbilitiesByCastTick(abilityPlacements);
        Map<Integer, List<AbilityPlacement>> abilitiesByReleaseTick = PlacementProcessor.groupAbilityByReleaseTick(abilityPlacements);
        Map<Integer, List<BuffPlacement>> buffs = PlacementProcessor.groupBuffsByTick(buffPlacements);
        Map<Integer, List<ScheduledHit>> scheduledHitMap = new HashMap<>();
        Map<Integer, List<TimelineHit>> resolvedHitMap = new HashMap<>();
        Map<Integer, List<ConsumableStackResult>> postDamageConsumptionsByPlacementId = new HashMap<>();
        Map<Integer, Integer> remainingHitsByPlacementId = new HashMap<>();
        Map<Integer, AbilityPlacement> placementIdMap = new HashMap<>();
        Map<Integer, List<AbilityPlacement>> completionMap = new HashMap<>();

        int placementId = 0;
        for (AbilityPlacement abilityPlacement : abilityPlacements) {
            endingTick = Math.max(endingTick, abilityPlacement.getReleaseTick());
            startingTick = Math.min(startingTick, abilityPlacement.getCastTick());
            abilityPlacement.setPlacementId(placementId++);
            placementIdMap.put(abilityPlacement.getPlacementId(), abilityPlacement);
        }

        for (BuffPlacement buffPlacement : buffPlacements) {
            BuffDefinition buffDefinition = BuffProvider.get(buffPlacement.getBuffId(), BuffSource.USER_PLACED, simulationState);
            endingTick = Math.max(endingTick, buffPlacement.getPlacementTick());
            startingTick = Math.min(startingTick, buffPlacement.getPlacementTick());
            if (buffDefinition.getDurationTicks() != null) {
                endingTick = Math.max(endingTick, buffPlacement.getPlacementTick() +
                        buffDefinition.getDurationTicks());
            }
        }

        if (abilitiesByCastTick.isEmpty() && buffs.isEmpty()) {
            startingTick = 0;
        }

        for (int tick = startingTick; tick <= endingTick; tick++) {
            SimulationState startingStateSnapshot = snapshotCopier.copySimulationState(simulationState);
            AdrenalineProcessor.applyMaximumAdrenalineBound(simulationState);
            List<AbilityPlacement> castedAbilities = new ArrayList<>();
            List<AbilityPlacement> releasedAbilities = new ArrayList<>();
            List<TimelineHit> newTimelineHits = new ArrayList<>();
            List<BuffPlacement> newBuffs = new ArrayList<>();
            TickSnapshot tickSnapshot = initializeTickSnapshot(startingStateSnapshot, tick);
            double adrenalineDelta = 0;
            boolean vestmentsBleedActiveAtTickStart = simulationState.getState().getBuffs().has(BuffId.VESTMENTSBLEED);

            if (buffs.containsKey(tick)) {
                newBuffs.addAll(buffs.get(tick));
                for (BuffPlacement buffPlacement : newBuffs) {
                    for (AppliedBuffResult buff : BuffProcessor.applyUserPlacedBuff(buffPlacement, simulationState, tickSnapshot)) {
                        if (buff.resolvedDurationTicks() != null) {
                            endingTick = Math.max(endingTick, buff.resolvedDurationTicks() + tick);
                        }
                    }
                }
            }

            if (abilitiesByCastTick.containsKey(tick)) {

                castedAbilities.addAll(abilitiesByCastTick.get(tick));
                adrenalineDelta = 0;

                for (AbilityPlacement abilityPlacement : castedAbilities) {
                    DamageResult damageResult =
                            engine.calculateAbilityDamage(DamageRequestFactory.getDamageRequest(simulationState.getState(), abilityPlacement.getPlacedAbility()), CalculationMode.ABILITY, null);

                    adrenalineDelta += AdrenalineProcessor.generateAbilityPlacementAdrenalineDelta(abilityPlacement, simulationState, damageResult);

                    AbilityCooldownProcessor.generateGlobalCooldownWarnings(simulationState, abilityPlacement, tickSnapshot);
                    AbilityCooldownProcessor.initializeGlobalCooldown(simulationState, abilityPlacement);

                    if (AbilityProvider.get(abilityPlacement.getPlacedAbility(), simulationState.getState().getEquipment()).getAbilityCooldownTiming() == AbilityCooldownTiming.ON_CAST) {
                        AbilityCooldownProcessor.generateAbilityCooldownWarnings(simulationState, abilityPlacement, tickSnapshot);
                        AbilityCooldownProcessor.initializeCooldown(simulationState, abilityPlacement);
                        AbilityCooldownProcessor.applyPlacementCooldownEffects(simulationState, abilityPlacement, damageResult);
                    }
                    for (AppliedBuffResult buff : BuffProcessor.applyAbilityGeneratedBuffsWithTiming(abilityPlacement, simulationState, GeneratedBuffTiming.ON_CAST, vestmentsBleedActiveAtTickStart)) {
                        if (buff.resolvedDurationTicks() != null) {
                            endingTick = Math.max(buff.resolvedDurationTicks() + tick, endingTick);
                        }
                    }
                }
            }

            if (abilitiesByReleaseTick.containsKey(tick)) {
                for (AbilityPlacement abilityPlacement : abilitiesByReleaseTick.get(tick)) {
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
                    DamageResult damageResult =
                            engine.calculateAbilityDamage(DamageRequestFactory.getDamageRequest(simulationState.getState(), abilityPlacement.getPlacedAbility()), CalculationMode.ABILITY, null);
                    AbilityContext abilityContext = AbilityProvider.get(abilityPlacement.getPlacedAbility(), simulationState.getState().getEquipment());

                    if (abilityContext.getDamageCalculationTiming() == DamageCalculationTiming.ON_RELEASE) {
                        List<TimelineHit> hits = HitsScheduler.schedule(damageResult, abilityPlacement,
                                simulationState.getState().getEquipment());
                        HitsPlacementProcessor.addTimelineHits(resolvedHitMap, hits);
                        for (ConsumableStackResult consumableStackResult : consumableStackResults) {
                            StackProcessor.consumeStacks(simulationState, consumableStackResult);
                        }
                        for (TimelineHit hit : hits) {
                            endingTick = Math.max(hit.getLandingTick(), endingTick);
                        }
                    }

                    List<ScheduledHit> scheduledHits = new ArrayList<>();
                    if (abilityContext.getDamageCalculationTiming() == DamageCalculationTiming.ON_HIT) {
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
                    }

                    StackProcessor.applyOnReleaseStacks(abilityPlacement, simulationState);

                    for (ScheduledHit timelineHit : scheduledHits) {
                        int landingTick = timelineHit.landingTick();
                        endingTick = Math.max(endingTick, landingTick);
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

            adrenalineDelta += AdrenalineProcessor.generateCurrentTickAdrenalineDelta(simulationState);
            adrenalineDelta += AdrenalineProcessor.generatePreHitTickAdrenalineDelta(simulationState, scheduledHitMap.get(tick + 1));
            AdrenalineProcessor.applyAdrenalineDelta(simulationState, adrenalineDelta);
            AdrenalineProcessor.generateWarnings(simulationState, tickSnapshot, newBuffs, castedAbilities);
            AdrenalineProcessor.clampAdrenaline(simulationState);

            if (resolvedHitMap.get(tick) != null) {
                for (TimelineHit timelineHit : resolvedHitMap.get(tick)) {
                    newTimelineHits.add(timelineHit);
                    StackProcessor.applyOnHitStacks(placementIdMap.get(timelineHit.getPlacementId()), simulationState,
                            timelineHit);
                    BuffProcessor.applyAbilityGeneratedBuffsWithTiming(placementIdMap.get(timelineHit.getPlacementId()),
                            simulationState, GeneratedBuffTiming.ON_HIT, vestmentsBleedActiveAtTickStart);
                }
            }

            if (scheduledHitMap.get(tick) != null && scheduledHitMap.containsKey(tick)) {
                for (ScheduledHit scheduledHit : scheduledHitMap.get(tick)) {
                    DamageResult result =
                            engine.calculateAbilityDamage(DamageRequestFactory.getDamageRequest(simulationState.getState(),
                                    scheduledHit.parentAbility()), CalculationMode.HIT, scheduledHit.hitIndex());
                    HitResult hitResult = result.getHit().getFirst();
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
                            tick,
                            hitResult.getCritChance(),
                            hitResult.getHitType(),
                            hitResult.getParentAbility(),
                            scheduledHit.placementId(),
                            hitResult.isDot(),
                            AbilityProvider.get(hitResult.getParentAbility(), state.getEquipment()).isChannel()
                    );
                    newTimelineHits.add(timelineHit);

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
                    appliedBuffResults.addAll(BuffProcessor.applyAbilityGeneratedBuffsWithTiming(placementIdMap.get(timelineHit.getPlacementId()), simulationState, GeneratedBuffTiming.ON_HIT, vestmentsBleedActiveAtTickStart));
                    StackProcessor.applyOnHitStacks(placementIdMap.get(timelineHit.getPlacementId()), simulationState, timelineHit);

                    for (AppliedBuffResult buff : appliedBuffResults) {
                        if (buff.resolvedDurationTicks() != null) {
                            endingTick = Math.max(endingTick, buff.resolvedDurationTicks() + tick);
                        }
                    }
                }
            }

            if (completionMap.containsKey(tick)) {
                for (AbilityPlacement abilityPlacement : completionMap.get(tick)) {
                    for (AppliedBuffResult buff : BuffProcessor.applyAbilityGeneratedBuffsWithTiming(abilityPlacement, simulationState, GeneratedBuffTiming.ON_COMPLETION, vestmentsBleedActiveAtTickStart)) {
                        if (buff.resolvedDurationTicks() != null) {
                            endingTick = Math.max(buff.resolvedDurationTicks() + tick, endingTick);
                        }
                    }
                }
            }

            SimulationState endingStateSnapshot = snapshotCopier.copySimulationState(simulationState);
            finalizeTickSnapshot(tickSnapshot, castedAbilities, releasedAbilities, newBuffs, newTimelineHits, endingStateSnapshot);
            timeline.getTimeline().add(tickSnapshot);

            AbilityCooldownProcessor.decayCooldown(simulationState);
            BuffProcessor.decayCooldown(simulationState);
            BuffProcessor.decayBuffDuration(simulationState);

            Iterator<Map.Entry<Integer, List<ScheduledHit>>> timelineHitsIterator = scheduledHitMap.entrySet().iterator();
            while (timelineHitsIterator.hasNext()) {
                if (timelineHitsIterator.next().getKey() < tick) {
                    timelineHitsIterator.remove();
                }
            }
        }
        return timeline;
    }

    public RotationTimeline build(RotationCombatState state, List<AbilityPlacement> abilityPlacements, List<BuffPlacement> buffPlacements) {
        return build(state, abilityPlacements, buffPlacements, SimulationConfig.defaults());
    }

    private SimulationState initializeState(RotationCombatState state, SimulationConfig config) {
        SimulationState simulationState = new SimulationState();
        simulationState.setState(state);
        simulationState.setAdrenaline(100);
        simulationState.setAbilityCooldownMap(new HashMap<>());
        simulationState.setBuffCooldownMap(new HashMap<>());
        simulationState.setActiveBuffDurationMap(new HashMap<>());
        simulationState.setSimulationConfig(config);
        simulationState.setProcAccumulators(new HashMap<>());

        SimulationState initialized = snapshotCopier.copySimulationState(simulationState);
        initialized.setRandom(config.getRandomSeed() == null ? new Random() : new Random(config.getRandomSeed()));
        return initialized;
    }

    private TickSnapshot initializeTickSnapshot(SimulationState state, int tick) {
        TickSnapshot tickSnapshot = new TickSnapshot();
        tickSnapshot.setTick(tick);
        tickSnapshot.setStartingCombatState(state.getState());
        tickSnapshot.setStartingAbilityCooldownMap(new HashMap<>(state.getAbilityCooldownMap()));
        tickSnapshot.setStartingBuffCooldownMap(new HashMap<>(state.getBuffCooldownMap()));
        tickSnapshot.setStartingActiveBuffDurationMap(new HashMap<>(state.getActiveBuffDurationMap()));
        tickSnapshot.setStartingAdrenaline(state.getAdrenaline());
        tickSnapshot.setWarnings(new ArrayList<>());

        return tickSnapshot;
    }

    private void finalizeTickSnapshot(TickSnapshot tickSnapshot, List<AbilityPlacement> castedAbilities,
                                      List<AbilityPlacement> releasedAbilities, List<BuffPlacement> newBuffs,
                                      List<TimelineHit> newTimelineHits, SimulationState simulationState) {
        tickSnapshot.setCastAbilities(castedAbilities);
        tickSnapshot.setReleasedAbilities(releasedAbilities);
        tickSnapshot.setPlacedBuffs(newBuffs);
        tickSnapshot.setLandedHits(newTimelineHits);
        tickSnapshot.setEndingCombatState(simulationState.getState());
        tickSnapshot.setEndingAbilityCooldownMap(new HashMap<>(simulationState.getAbilityCooldownMap()));
        tickSnapshot.setEndingBuffCooldownMap(new HashMap<>(simulationState.getBuffCooldownMap()));
        tickSnapshot.setEndingActiveBuffDurationMap(new HashMap<>(simulationState.getActiveBuffDurationMap()));
        tickSnapshot.setEndingAdrenaline(simulationState.getAdrenaline());
    }
}
