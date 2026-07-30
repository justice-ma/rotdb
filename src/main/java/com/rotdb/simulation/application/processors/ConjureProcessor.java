package com.rotdb.simulation.application.processors;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.application.processors.result.ConjureRemovalResult;
import com.rotdb.simulation.application.processors.result.ConjureSchedulingResult;
import com.rotdb.simulation.application.snapshot.SimulationStateSnapshotCopier;
import com.rotdb.simulation.domain.model.buff.ConjureHitSource;
import com.rotdb.simulation.domain.model.buff.ConsumableStackResult;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.RotationCombatState;
import com.rotdb.simulation.domain.model.context.ScheduledHit;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.provider.BuffProvider;
import com.rotdb.simulation.domain.resolvers.buff.ConjureResolver;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class ConjureProcessor {
    public static @Nullable ConjureSchedulingResult applyConjureHits(AbilityPlacement abilityPlacement,
                                                                     SimulationState simulationState,
                                                                     Map<Integer, List<ScheduledHit>> scheduledHitMap,
                                                                     Map<Integer, Integer> remainingHitsByPlacementId,
                                                                     Map<Integer, RotationCombatState> releaseStateByPlacementId,
                                                                     SimulationStateSnapshotCopier copier, int endingTick) {
        if (!ConjureResolver.isConjureDamage(abilityPlacement)) {
            return null;
        }

        AbilityContext abilityContext = AbilityProvider.get(abilityPlacement.getPlacedAbility(),
                simulationState.getState().getEquipment());
        RotationCombatState combatStateCopy = copier.copyCombatState(simulationState.getState());
        releaseStateByPlacementId.put(abilityPlacement.getPlacementId(), combatStateCopy);

        List<ScheduledHit> scheduledHits = new ArrayList<>();
        List<ConjureHitSource> conjureHitSources = ConjureResolver.resolveHitSources(abilityPlacement, simulationState);

        if (!conjureHitSources.isEmpty()) {
            for (ConjureHitSource conjureHitSource : conjureHitSources) {
                int end = abilityPlacement.getReleaseTick() + BuffProvider.get(conjureHitSource.durationBuffId(),
                        BuffSource.ABILITY_GENERATED, simulationState).getDurationTicks();
                int start = abilityPlacement.getReleaseTick() + conjureHitSource.firstHitOffset()
                        + (conjureHitSource.skipFirstHit() ? conjureHitSource.cadence() : 0);

                for (int landingTick = start; landingTick <= end; landingTick += conjureHitSource.cadence()) {
                    scheduledHits.addAll(scheduleHits(abilityPlacement, simulationState, conjureHitSource, landingTick));
                    endingTick = Math.max(endingTick, landingTick);
                }
            }
        } else {
            int end =
                    abilityPlacement.getReleaseTick() + BuffProvider.get(abilityContext.getGeneratedBuffEffects().getFirst().buffId(),
                            BuffSource.ABILITY_GENERATED, simulationState).getDurationTicks();
            int cadence = abilityContext.getHits().getFirst().getHitTiming();
            int start = abilityPlacement.getReleaseTick() + abilityContext.getHits().getFirst().getHitTiming() +
                    (ConjureResolver.shouldSkipFirstRecursiveHit(abilityContext.getId()) ? cadence : 0);

            for (int landingTick = start; landingTick <= end; landingTick += cadence) {
                scheduledHits.addAll(scheduleHits(abilityPlacement, simulationState, landingTick));
                endingTick = Math.max(endingTick, landingTick);

            }
        }
        remainingHitsByPlacementId.merge(abilityPlacement.getPlacementId(), +scheduledHits.size(), Integer::sum);
        HitsPlacementProcessor.addScheduledHits(scheduledHitMap, scheduledHits);
        return new ConjureSchedulingResult(endingTick);
    }

    public static boolean shouldSuppressScheduledConjureHit(ScheduledHit scheduledHit, SimulationState simulationState) {
        if (scheduledHit.parentAbility() == AbilityId.CONJURESKELETONWARRIOR && simulationState.getState().getBuffs().has(BuffId.COMMANDSKELETONWARRIOR)) {
            return true;
        }
        return false;
    }

    public static ConjureRemovalResult processConjureRemoval(SimulationState simulationState,
                                                             Map<Integer, List<ScheduledHit>> scheduledHitMap,
                                                             Map<Integer, Integer> remainingHitsByPlacementId,
                                                             Map<Integer, RotationCombatState> releaseStateByPlacementId,
                                                             Map<Integer, List<ConsumableStackResult>> postDamageConsumptionsByPlacementId,
                                                             boolean initialBuffStateContainsHaunted) {
        ConjureRemovalResult conjureRemovalResult = new ConjureRemovalResult(false);
        if (hasActiveConjures(simulationState) && !ConjureResolver.hasConjureMaintainingOffhand(simulationState.getState().getEquipment())) {
            simulationState.getState().getBuffs().getBuffSet().remove(BuffId.VENGEFULGHOSTDURATION);
            simulationState.getState().getBuffs().getBuffSet().remove(BuffId.PUTRIDZOMBIEDURATION);
            simulationState.getState().getBuffs().getBuffSet().remove(BuffId.SKELETONWARRIORDURATION);
            simulationState.getState().getBuffs().getBuffSet().remove(BuffId.COMMANDSKELETONWARRIOR);
            simulationState.getState().getBuffs().getBuffStacks().remove(BuffId.RAGE);
            simulationState.getState().getBuffs().getBuffStacks().remove(BuffId.VALOUR);
            if (!initialBuffStateContainsHaunted) simulationState.getState().getTarget().getDebuffs().remove(BuffId.HAUNTED);

            simulationState.getActiveBuffDurationMap().remove(BuffId.VENGEFULGHOSTDURATION);
            simulationState.getActiveBuffDurationMap().remove(BuffId.PUTRIDZOMBIEDURATION);
            simulationState.getActiveBuffDurationMap().remove(BuffId.SKELETONWARRIORDURATION);
            simulationState.getActiveBuffDurationMap().remove(BuffId.COMMANDSKELETONWARRIOR);

            HashSet<Integer> removedPlacementIds = new HashSet<>();
            Iterator<Map.Entry<Integer, List<ScheduledHit>>> iterator = scheduledHitMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, List<ScheduledHit>> entry = iterator.next();
                entry.getValue().removeIf(hit -> {
                    boolean shouldRemove = isConjureHit(hit.parentAbility());
                    if (shouldRemove) {
                        removedPlacementIds.add(hit.placementId());
                    }
                    conjureRemovalResult.setRemoved(true);
                    return shouldRemove;
                });

                if (entry.getValue().isEmpty()) {
                    iterator.remove();
                }
            }

            for (Integer placementId : removedPlacementIds) {
                remainingHitsByPlacementId.remove(placementId);
                releaseStateByPlacementId.remove(placementId);
                postDamageConsumptionsByPlacementId.remove(placementId);
            }
        }
        return conjureRemovalResult;
    }

    private static boolean hasActiveConjures(SimulationState simulationState) {
        return simulationState.getState().getBuffs().has(BuffId.VENGEFULGHOSTDURATION) ||
                simulationState.getState().getBuffs().has(BuffId.PUTRIDZOMBIEDURATION) ||
                simulationState.getState().getBuffs().has(BuffId.SKELETONWARRIORDURATION);
    }

    private static boolean isConjureHit(AbilityId abilityId) {
        return abilityId == AbilityId.COMMANDSKELETONWARRIORHIT || abilityId == AbilityId.CONJURESKELETONWARRIOR ||
                abilityId == AbilityId.CONJUREVENGEFULGHOST || abilityId == AbilityId.PUTRIDZOMBIEHIT ||
                abilityId == AbilityId.PUTRIDZOMBIEPOISON;
    }

    private static List<ScheduledHit> scheduleHits(AbilityPlacement abilityPlacement, SimulationState state,
                                                  int landingTick) {
        List<ScheduledHit> hits = new ArrayList<>();
        AbilityContext abilityContext = AbilityProvider.get(abilityPlacement.getPlacedAbility(), state.getState().getEquipment());
        Integer placementId = abilityPlacement.getPlacementId();
        for (AbilityHitsContext hit : abilityContext.getHits()) {
            ScheduledHit scheduledHit = new ScheduledHit(
                    placementId,
                    abilityContext.getId(),
                    hit.getHitIndex(),
                    hit.getHitTiming(),
                    landingTick,
                    hit.getType(),
                    hit.isDot(),
                    abilityContext.isChannel()
            );
            hits.add(scheduledHit);
        }

        return hits;
    }

    private static List<ScheduledHit> scheduleHits(AbilityPlacement abilityPlacement, SimulationState state,
                                                  ConjureHitSource conjureHitSource, int landingTick) {
        List<ScheduledHit> hits = new ArrayList<>();
        Integer placementId = abilityPlacement.getPlacementId();
        AbilityContext abilityContext = AbilityProvider.get(conjureHitSource.damageAbilityId(), state.getState().getEquipment());
        for (AbilityHitsContext hit : abilityContext.getHits()) {
            ScheduledHit scheduledHit = new ScheduledHit(
                    placementId,
                    conjureHitSource.damageAbilityId(),
                    hit.getHitIndex(),
                    hit.getHitTiming(),
                    landingTick,
                    hit.getType(),
                    hit.isDot(),
                    abilityContext.isChannel()
            );
            hits.add(scheduledHit);
        }

        return hits;
    }
}
