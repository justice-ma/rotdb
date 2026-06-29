package com.rotdb.simulation.application.service;

import com.rotdb.calculation.domain.engine.CalculationEngine;
import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.simulation.application.processors.*;
import com.rotdb.simulation.application.snapshot.SimulationStateSnapshotCopier;
import com.rotdb.simulation.domain.model.context.*;

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

        Map<Integer, List<AbilityPlacement>> abilities = PlacementProcessor.groupAbilitiesByTick(abilityPlacements);
        Map<Integer, List<BuffPlacement>> buffs = PlacementProcessor.groupBuffsByTick(buffPlacements);
        Map<Integer, List<TimelineHit>> timelineHitMap = new HashMap<>();

        for (AbilityPlacement abilityPlacement : abilityPlacements) {
            endingTick = Math.max(endingTick, abilityPlacement.getPlacementTick());
            startingTick = Math.min(startingTick, abilityPlacement.getPlacementTick());
        }

        if (abilities.isEmpty()) {
            startingTick = 0;
        }

        for (int tick = startingTick; tick <= endingTick; tick++) {
            SimulationState startingStateSnapshot = snapshotCopier.copySimulationState(simulationState);
            AdrenalineProcessor.applyMaximumAdrenalineBound(simulationState);
            List<AbilityPlacement> newAbilities = new ArrayList<>();
            List<TimelineHit> newTimelineHits = new ArrayList<>();
            List<BuffPlacement> newBuffs = new ArrayList<>();
            TickSnapshot tickSnapshot = initializeTickSnapshot(startingStateSnapshot, tick);
            double adrenalineDelta = 0;

            if (buffs.containsKey(tick)) {
                newBuffs.addAll(buffs.get(tick));
                for (BuffPlacement buffPlacement : newBuffs) {
                    simulationState.getState().getBuffs().getBuffSet().add(buffPlacement.getBuffId());
                    BuffProcessor.initializeCooldown(buffPlacement.getBuffId(), simulationState);
                    BuffProcessor.initializeBuffDuration(buffPlacement.getBuffId(), simulationState);
                }
            }

            if (abilities.containsKey(tick)) {

                newAbilities.addAll(abilities.get(tick));
                adrenalineDelta = 0;

                for (AbilityPlacement abilityPlacement : newAbilities) {
                    DamageResult damageResult = engine.calculateAbilityDamage(DamageRequestFactory.getDamageRequest(simulationState.getState(), abilityPlacement.getPlacedAbility()));

                    List<TimelineHit> hits = HitsScheduler.schedule(damageResult, abilityPlacement.getPlacementTick(), simulationState.getState().getEquipment());
                    HitsPlacementProcessor.addScheduledHits(timelineHitMap, hits);

                    for (TimelineHit timelineHit : hits) {
                        int landingTick = timelineHit.getLandingTick();
                        endingTick = Math.max(endingTick, landingTick);
                    }

                    adrenalineDelta += AdrenalineProcessor.generateAbilityPlacementAdrenalineDelta(abilityPlacement, simulationState, damageResult);

                    AbilityCooldownProcessor.generateWarnings(simulationState, abilityPlacement, tickSnapshot);
                    AbilityCooldownProcessor.initializeCooldown(simulationState, abilityPlacement);
                    AbilityCooldownProcessor.applyPlacementCooldownEffects(simulationState, abilityPlacement, damageResult);
                }
            }
            adrenalineDelta += AdrenalineProcessor.generateCurrentTickAdrenalineDelta(simulationState);
            adrenalineDelta += AdrenalineProcessor.generatePreHitTickAdrenalineDelta(simulationState, timelineHitMap.get(tick + 1));
            AdrenalineProcessor.applyAdrenalineDelta(simulationState, adrenalineDelta);
            AdrenalineProcessor.generateWarnings(simulationState, tickSnapshot);
            AdrenalineProcessor.clampAdrenaline(simulationState);

            if (timelineHitMap.containsKey(tick)) {
                newTimelineHits.addAll(timelineHitMap.get(tick));
            }

            SimulationState endingStateSnapshot = snapshotCopier.copySimulationState(simulationState);
            finalizeTickSnapshot(tickSnapshot, newAbilities, newBuffs, newTimelineHits, endingStateSnapshot);
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

    private void finalizeTickSnapshot(TickSnapshot tickSnapshot, List<AbilityPlacement> newAbilities,
                                      List<BuffPlacement> newBuffs, List<TimelineHit> newTimelineHits,
                                      SimulationState simulationState) {
        tickSnapshot.setPlacedAbilities(newAbilities);
        tickSnapshot.setPlacedBuffs(newBuffs);
        tickSnapshot.setLandedHits(newTimelineHits);
        tickSnapshot.setEndingCombatState(simulationState.getState());
        tickSnapshot.setEndingAbilityCooldownMap(new HashMap<>(simulationState.getAbilityCooldownMap()));
        tickSnapshot.setEndingBuffCooldownMap(new HashMap<>(simulationState.getBuffCooldownMap()));
        tickSnapshot.setEndingActiveBuffDurationMap(new HashMap<>(simulationState.getActiveBuffDurationMap()));
        tickSnapshot.setEndingAdrenaline(simulationState.getAdrenaline());
    }
}
