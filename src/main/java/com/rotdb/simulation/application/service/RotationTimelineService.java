package com.rotdb.simulation.application.service;

import com.rotdb.calculation.domain.engine.CalculationEngine;
import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.simulation.application.processors.AdrenalineProcessor;
import com.rotdb.simulation.application.processors.CooldownProcessor;
import com.rotdb.simulation.application.processors.HitsPlacementProcessor;
import com.rotdb.simulation.application.processors.PlacementProcessor;
import com.rotdb.simulation.domain.model.context.*;

import java.util.*;

public class RotationTimelineService {
    private final CalculationEngine engine;

    public RotationTimelineService(CalculationEngine engine) {
        this.engine = engine;
    }

    public RotationTimeline build(RotationCombatState state, List<AbilityPlacement> abilityPlacements) {
        RotationTimeline timeline = new RotationTimeline();
        timeline.setTimeline(new ArrayList<>());

        SimulationState simulationState = initializeState(state);

        int endingTick = 0;
        int startingTick = Integer.MAX_VALUE;

        Map<Integer, List<AbilityPlacement>> abilities = PlacementProcessor.groupByTick(abilityPlacements);
        Map<Integer, List<TimelineHit>> timelineHitMap = new HashMap<>();

        for (AbilityPlacement abilityPlacement : abilityPlacements) {
            endingTick = Math.max(endingTick, abilityPlacement.getPlacementTick());
            startingTick = Math.min(startingTick, abilityPlacement.getPlacementTick());
        }

        if (abilities.isEmpty()) {
            startingTick = 0;
        }

        for (int tick = startingTick; tick <= endingTick; tick++) {
            AdrenalineProcessor.applyMaximumAdrenalineBound(simulationState);
            List<AbilityPlacement> newAbilities = new ArrayList<>();
            List<TimelineHit> newTimelineHits = new ArrayList<>();
            TickSnapshot tickSnapshot = initializeTickSnapshot(simulationState, tick);
            double adrenalineDelta = 0;

            if (abilities.containsKey(tick)) {

                newAbilities.addAll(abilities.get(tick));
                adrenalineDelta = 0;

                for (AbilityPlacement abilityPlacement : newAbilities) {
                    DamageResult damageResult = engine.calculateAbilityDamage(DamageRequestFactory.getDamageRequest(state, abilityPlacement.getPlacedAbility()));

                    List<TimelineHit> hits = HitsScheduler.schedule(damageResult, abilityPlacement.getPlacementTick(), state.getEquipment());
                    HitsPlacementProcessor.addScheduledHits(timelineHitMap, hits);

                    for (TimelineHit timelineHit : hits) {
                        int landingTick = timelineHit.getLandingTick();
                        endingTick = Math.max(endingTick, landingTick);
                    }

                    adrenalineDelta += AdrenalineProcessor.generateAbilityPlacementAdrenalineDelta(abilityPlacement, simulationState, damageResult);

                    CooldownProcessor.generateWarnings(simulationState, abilityPlacement, tickSnapshot);
                    CooldownProcessor.initializeCooldown(simulationState, abilityPlacement);
                    CooldownProcessor.applyPlacementCooldownEffects(simulationState, abilityPlacement, damageResult);
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

            finalizeTickSnapshot(tickSnapshot, newAbilities, newTimelineHits, simulationState);
            timeline.getTimeline().add(tickSnapshot);

            CooldownProcessor.decayCooldown(simulationState);

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
        simulationState.setCooldownMap(new HashMap<>());

        return simulationState;
    }

    private TickSnapshot initializeTickSnapshot(SimulationState state, int tick) {
        TickSnapshot tickSnapshot = new TickSnapshot();
        tickSnapshot.setTick(tick);
        tickSnapshot.setStartingCombatState(state.getState());
        tickSnapshot.setStartingCooldownMap(new HashMap<>(state.getCooldownMap()));
        tickSnapshot.setStartingAdrenaline(state.getAdrenaline());
        tickSnapshot.setWarnings(new ArrayList<>());

        return tickSnapshot;
    }

    private void finalizeTickSnapshot(TickSnapshot tickSnapshot, List<AbilityPlacement> newAbilities,
                                      List<TimelineHit> newTimelineHits, SimulationState simulationState) {
        tickSnapshot.setPlacedAbilities(newAbilities);
        tickSnapshot.setLandedHits(newTimelineHits);
        tickSnapshot.setEndingCombatState(simulationState.getState());
        tickSnapshot.setEndingCooldownMap(new HashMap<>(simulationState.getCooldownMap()));
        tickSnapshot.setEndingAdrenaline(simulationState.getAdrenaline());
    }
}
