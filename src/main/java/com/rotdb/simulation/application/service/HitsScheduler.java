package com.rotdb.simulation.application.service;

import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.calculation.domain.model.HitResult;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.ScheduledHit;
import com.rotdb.simulation.domain.model.context.TimelineHit;

import java.util.ArrayList;
import java.util.List;

public class HitsScheduler {
    public static List<ScheduledHit> schedule(AbilityContext abilityContext, AbilityPlacement abilityPlacement) {
        List<ScheduledHit> hits = new ArrayList<>();
        Integer placementId = abilityPlacement.getPlacementId();
        int releaseTick = abilityPlacement.getReleaseTick();
        for (AbilityHitsContext hit : abilityContext.getHits()) {
            ScheduledHit scheduledHit = new ScheduledHit(
                    placementId,
                    abilityContext.getId(),
                    hit.getHitIndex(),
                    hit.getHitTiming(),
                    hit.getHitTiming() + releaseTick,
                    hit.getType(),
                    hit.isDot(),
                    abilityContext.isChannel()
            );
            hits.add(scheduledHit);
        }
        abilityPlacement.setCompletionTick(hits.getLast().landingTick());
        return hits;
    }

    public static List<TimelineHit> schedule(DamageResult damageResult, AbilityPlacement abilityPlacement,
                                             EquipmentModel equipmentModel) {
        List<TimelineHit> hits = new ArrayList<>();
        Integer placementId = abilityPlacement.getPlacementId();
        int releaseTick = abilityPlacement.getReleaseTick();
        for (HitResult hit : damageResult.getHit()) {
            TimelineHit scheduledHit = new TimelineHit(
                    hit.getHitMinDamage(),
                    hit.getHitMaxDamage(),
                    hit.getHitAvgDamage(),
                    hit.getHitMinCrit(),
                    hit.getHitMaxCrit(),
                    hit.getHitAvgCrit(),
                    hit.getHitMinNonCrit(),
                    hit.getHitMaxNonCrit(),
                    hit.getHitAvgNonCrit(),
                    hit.getHitIndex(),
                    hit.getHitTiming(),
                    hit.getHitTiming() + releaseTick,
                    hit.getCritChance(),
                    hit.getHitType(),
                    hit.getParentAbility(),
                    placementId,
                    hit.isDot(),
                    AbilityProvider.get(hit.getParentAbility(), equipmentModel).isChannel()
            );
            hits.add(scheduledHit);
        }
        abilityPlacement.setCompletionTick(hits.getLast().getLandingTick());
        return hits;
    }
}
