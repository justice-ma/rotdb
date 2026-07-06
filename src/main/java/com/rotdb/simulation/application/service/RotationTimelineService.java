package com.rotdb.simulation.application.service;

import com.rotdb.calculation.domain.engine.CalculationEngine;
import com.rotdb.shared.ability.model.GeneratedBuffTiming;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.application.processors.*;
import com.rotdb.simulation.application.processors.result.AbilityCastResult;
import com.rotdb.simulation.application.processors.result.AbilityReleaseResult;
import com.rotdb.simulation.application.processors.result.HitLandingResult;
import com.rotdb.simulation.application.snapshot.SimulationStateSnapshotCopier;
import com.rotdb.simulation.domain.model.buff.AppliedBuffResult;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.ConsumableStackResult;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
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

        /*
         * Tick flow:
         * 1. Apply user-placed buffs for this tick.
         * 2. Process ability casts.
         * 3. Process ability releases.
         * 4. Apply passive and pre-hit adrenaline changes.
         * 5. Resolve landed hits.
         * 6. Process ability completions.
         * 7. Snapshot the tick, then decay cooldowns and buff durations.
         *
         * Hit damage lifecycle:
         * - ON_RELEASE abilities calculate damage when the ability releases.
         *   Their resolved TimelineHits are stored by landing tick and only applied later.
         *
         * - ON_HIT abilities schedule lightweight ScheduledHits when the ability releases.
         *   Each hit calculates damage when it lands, using the combat state at that time.
         *
         * Stack timing:
         * - PRE_DAMAGE consumptions happen before release-time damage calculation.
         * - ON_RELEASE hit-generated stacks are generated during release before damage is calculated.
         * - ON_HIT hit-generated stacks are generated when each hit lands.
         */

        RotationTimeline timeline = new RotationTimeline();
        timeline.setTimeline(new ArrayList<>());

        SimulationState simulationState = initializeState(state, config);

        int endingTick = 0;
        int startingTick = Integer.MAX_VALUE;

        Map<Integer, List<AbilityPlacement>> abilitiesByCastTick = PlacementProcessor.groupAbilitiesByCastTick(abilityPlacements);
        Map<Integer, List<AbilityPlacement>> abilitiesByReleaseTick = PlacementProcessor.groupAbilityByReleaseTick(abilityPlacements);
        Map<Integer, List<BuffPlacement>> buffs = PlacementProcessor.groupBuffsByTick(buffPlacements);
        Map<Integer, List<ScheduledHit>> scheduledHitsByLandingTick = new HashMap<>();
        Map<Integer, List<TimelineHit>> resolvedHitsByLandingTick = new HashMap<>();
        Map<Integer, List<ConsumableStackResult>> postDamageConsumptionsByPlacementId = new HashMap<>();
        Map<Integer, Integer> remainingHitsByPlacementId = new HashMap<>();
        Map<Integer, AbilityPlacement> placementIdMap = new HashMap<>();
        Map<Integer, List<AbilityPlacement>> abilitiesByCompletionTick = new HashMap<>();

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

            // User buff placements
            if (buffs.containsKey(tick)) {
                newBuffs.addAll(buffs.get(tick));
                for (BuffPlacement buffPlacement : newBuffs) {
                    for (AppliedBuffResult buff : BuffProcessor.applyUserPlacedBuff(buffPlacement, simulationState, tickSnapshot)) {
                        if (buff.resolvedDurationTicks() != null) {
                            endingTick = Math.max(endingTick, buff.resolvedDurationTicks() + tick);
                        }
                    }
                    BuffProcessor.removeBuffsConsumedByBuffPlacement(buffPlacement, simulationState);
                }
            }

            // Ability casts
            AbilityCastResult abilityCastResult = AbilityCastProcessor.processAbilityCast(abilitiesByCastTick, tick,
                    castedAbilities, simulationState, tickSnapshot, vestmentsBleedActiveAtTickStart, endingTick,
                    engine);
            adrenalineDelta = abilityCastResult.adrenalineDelta();
            endingTick = abilityCastResult.endingTick();

            // Ability releases
            AbilityReleaseResult abilityReleaseResult = AbilityReleaseProcessor.processRelease(abilitiesByReleaseTick,
                    resolvedHitsByLandingTick, scheduledHitsByLandingTick, postDamageConsumptionsByPlacementId, remainingHitsByPlacementId, tickSnapshot,
                    vestmentsBleedActiveAtTickStart, abilitiesByCompletionTick, releasedAbilities, tick, simulationState, endingTick, engine);
            endingTick = abilityReleaseResult.endingTick();

            // Passive and pre-hit adrenaline
            adrenalineDelta += AdrenalineProcessor.generateCurrentTickAdrenalineDelta(simulationState);
            adrenalineDelta += AdrenalineProcessor.generatePreHitTickAdrenalineDelta(simulationState, scheduledHitsByLandingTick.get(tick + 1));
            AdrenalineProcessor.applyAdrenalineDelta(simulationState, adrenalineDelta);
            AdrenalineProcessor.generateWarnings(simulationState, tickSnapshot, newBuffs, castedAbilities);
            AdrenalineProcessor.clampAdrenaline(simulationState);

            // Landed hits
            HitLandingResult hitLandingResult = HitLandingProcessor.processHitLanding(resolvedHitsByLandingTick, tick,
                    newTimelineHits, placementIdMap, vestmentsBleedActiveAtTickStart, simulationState,
                    scheduledHitsByLandingTick, engine, remainingHitsByPlacementId, postDamageConsumptionsByPlacementId, state,
                    endingTick);
            endingTick = hitLandingResult.endingTick();

            // Ability completions
            if (abilitiesByCompletionTick.containsKey(tick)) {
                for (AbilityPlacement abilityPlacement : abilitiesByCompletionTick.get(tick)) {
                    for (AppliedBuffResult buff : BuffProcessor.applyAbilityGeneratedBuffsWithTiming(abilityPlacement, simulationState, GeneratedBuffTiming.ON_COMPLETION, vestmentsBleedActiveAtTickStart)) {
                        if (buff.resolvedDurationTicks() != null) {
                            endingTick = Math.max(buff.resolvedDurationTicks() + tick, endingTick);
                        }
                    }
                }
            }

            for (ConsumableStackResult consumableStackResult :
                    StackProcessor.applyEndOfTickStackTriggers(simulationState)) {
                StackProcessor.consumeStacks(simulationState, consumableStackResult);
                if (consumableStackResult.appliedBuffResult() != null && consumableStackResult.appliedBuffResult().resolvedDurationTicks() != null) {
                    endingTick = Math.max(endingTick,
                            consumableStackResult.appliedBuffResult().resolvedDurationTicks() + tick);
                }
            }

            // Snapshot and decay
            StackProcessor.removeStaleStacks(simulationState);
            SimulationState endingStateSnapshot = snapshotCopier.copySimulationState(simulationState);
            finalizeTickSnapshot(tickSnapshot, castedAbilities, releasedAbilities, newBuffs, newTimelineHits, endingStateSnapshot);
            timeline.getTimeline().add(tickSnapshot);

            AbilityCooldownProcessor.decayCooldown(simulationState);
            BuffProcessor.decayCooldown(simulationState);
            BuffProcessor.decayBuffDuration(simulationState);

            Iterator<Map.Entry<Integer, List<ScheduledHit>>> timelineHitsIterator = scheduledHitsByLandingTick.entrySet().iterator();
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
