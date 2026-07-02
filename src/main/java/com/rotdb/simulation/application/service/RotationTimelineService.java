package com.rotdb.simulation.application.service;

import com.rotdb.calculation.domain.engine.CalculationEngine;
import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.ability.model.AbilityCooldownTiming;
import com.rotdb.shared.ability.model.GeneratedBuffTiming;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.simulation.application.processors.*;
import com.rotdb.simulation.application.snapshot.SimulationStateSnapshotCopier;
import com.rotdb.simulation.domain.model.buff.AppliedBuffResult;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
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

    public RotationTimeline build(RotationCombatState state, List<AbilityPlacement> abilityPlacements, List<BuffPlacement> buffPlacements) {
        RotationTimeline timeline = new RotationTimeline();
        timeline.setTimeline(new ArrayList<>());

        SimulationState simulationState = initializeState(state);

        int endingTick = 0;
        int startingTick = Integer.MAX_VALUE;

        Map<Integer, List<AbilityPlacement>> abilitiesByCastTick = PlacementProcessor.groupAbilitiesByCastTick(abilityPlacements);
        Map<Integer, List<AbilityPlacement>> abilitiesByReleaseTick = PlacementProcessor.groupAbilityByReleaseTick(abilityPlacements);
        Map<Integer, List<BuffPlacement>> buffs = PlacementProcessor.groupBuffsByTick(buffPlacements);
        Map<Integer, List<TimelineHit>> timelineHitMap = new HashMap<>();
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

            if (buffs.containsKey(tick)) {
                newBuffs.addAll(buffs.get(tick));
                for (BuffPlacement buffPlacement : newBuffs) {
                    if (BuffProcessor.applyUserPlacedBuff(buffPlacement, simulationState, tickSnapshot).getDurationTicks() != null) {
                        endingTick = Math.max(BuffProvider.get(buffPlacement.getBuffId(), BuffSource.USER_PLACED, simulationState).getDurationTicks() + tick, endingTick);
                    }
                }
            }

            if (abilitiesByCastTick.containsKey(tick)) {

                castedAbilities.addAll(abilitiesByCastTick.get(tick));
                adrenalineDelta = 0;

                for (AbilityPlacement abilityPlacement : castedAbilities) {
                    DamageResult damageResult = engine.calculateAbilityDamage(DamageRequestFactory.getDamageRequest(simulationState.getState(), abilityPlacement.getPlacedAbility()));

                    adrenalineDelta += AdrenalineProcessor.generateAbilityPlacementAdrenalineDelta(abilityPlacement, simulationState, damageResult);

                    AbilityCooldownProcessor.generateGlobalCooldownWarnings(simulationState, abilityPlacement, tickSnapshot);
                    AbilityCooldownProcessor.initializeGlobalCooldown(simulationState, abilityPlacement);

                    if (AbilityProvider.get(abilityPlacement.getPlacedAbility(), simulationState.getState().getEquipment()).getAbilityCooldownTiming() == AbilityCooldownTiming.ON_CAST) {
                        AbilityCooldownProcessor.generateAbilityCooldownWarnings(simulationState, abilityPlacement, tickSnapshot);
                        AbilityCooldownProcessor.initializeCooldown(simulationState, abilityPlacement);
                        AbilityCooldownProcessor.applyPlacementCooldownEffects(simulationState, abilityPlacement, damageResult);
                    }
                    for (AppliedBuffResult buff : BuffProcessor.applyAbilityGeneratedBuffsWithTiming(abilityPlacement, simulationState, GeneratedBuffTiming.ON_CAST)) {
                        if (buff.resolvedDurationTicks() != null) {
                            endingTick = Math.max(buff.resolvedDurationTicks() + tick, endingTick);
                        }
                    }
                }
            }

            if (abilitiesByReleaseTick.containsKey(tick)) {
                for (AbilityPlacement abilityPlacement : abilitiesByReleaseTick.get(tick)) {
                    DamageResult damageResult = engine.calculateAbilityDamage(DamageRequestFactory.getDamageRequest(simulationState.getState(), abilityPlacement.getPlacedAbility()));
                    AbilityContext ability = AbilityProvider.get(abilityPlacement.getPlacedAbility(), simulationState.getState().getEquipment());

                    List<TimelineHit> hits = HitsScheduler.schedule(damageResult, abilityPlacement, simulationState.getState().getEquipment());
                    HitsPlacementProcessor.addScheduledHits(timelineHitMap, hits);

                    for (TimelineHit timelineHit : hits) {
                        int landingTick = timelineHit.getLandingTick();
                        endingTick = Math.max(endingTick, landingTick);
                    }

                    if (ability.getAbilityCooldownTiming() == AbilityCooldownTiming.ON_RELEASE) {
                        AbilityCooldownProcessor.generateAbilityCooldownWarnings(simulationState, abilityPlacement, tickSnapshot);
                        AbilityCooldownProcessor.initializeCooldown(simulationState, abilityPlacement);
                        AbilityCooldownProcessor.applyPlacementCooldownEffects(simulationState, abilityPlacement, damageResult);
                    }
                    for (AppliedBuffResult buff : BuffProcessor.applyAbilityGeneratedBuffsWithTiming(abilityPlacement, simulationState, GeneratedBuffTiming.ON_RELEASE)) {
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
            adrenalineDelta += AdrenalineProcessor.generatePreHitTickAdrenalineDelta(simulationState, timelineHitMap.get(tick + 1));
            AdrenalineProcessor.applyAdrenalineDelta(simulationState, adrenalineDelta);
            AdrenalineProcessor.generateWarnings(simulationState, tickSnapshot, newBuffs, castedAbilities);
            AdrenalineProcessor.clampAdrenaline(simulationState);

            if (timelineHitMap.containsKey(tick)) {
                newTimelineHits.addAll(timelineHitMap.get(tick));
                List<AppliedBuffResult> appliedBuffResults = new ArrayList<>();
                for (TimelineHit timelineHit : newTimelineHits) {
                    appliedBuffResults.addAll(BuffProcessor.applyAbilityGeneratedBuffsWithTiming(placementIdMap.get(timelineHit.getPlacementId()), simulationState, GeneratedBuffTiming.ON_HIT));
                }
                for (AppliedBuffResult buff : appliedBuffResults) {
                    if (buff.resolvedDurationTicks() != null) {
                        endingTick = Math.max(endingTick, buff.resolvedDurationTicks() + tick);
                    }
                }
            }

            if (completionMap.containsKey(tick)) {
                for (AbilityPlacement abilityPlacement : completionMap.get(tick)) {
                    for (AppliedBuffResult buff : BuffProcessor.applyAbilityGeneratedBuffsWithTiming(abilityPlacement, simulationState, GeneratedBuffTiming.ON_COMPLETION)) {
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

            Iterator<Map.Entry<Integer, List<TimelineHit>>> timelineHitsIterator = timelineHitMap.entrySet().iterator();
            while (timelineHitsIterator.hasNext()) {
                if (timelineHitsIterator.next().getKey() < tick) {
                    timelineHitsIterator.remove();
                }
            }
        }
        return timeline;
    }

    private SimulationState initializeState(RotationCombatState state) {
        SimulationState simulationState = new SimulationState();
        simulationState.setState(state);
        simulationState.setAdrenaline(100);
        simulationState.setAbilityCooldownMap(new HashMap<>());
        simulationState.setBuffCooldownMap(new HashMap<>());
        simulationState.setActiveBuffDurationMap(new HashMap<>());

        return snapshotCopier.copySimulationState(simulationState);
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
