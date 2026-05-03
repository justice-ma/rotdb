package com.rotdb.simulation.application.service;

import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.calculation.domain.model.HitResult;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.simulation.domain.model.context.TimelineHit;

import java.util.ArrayList;
import java.util.List;

public class HitsScheduler {
    public static List<TimelineHit> schedule(DamageResult damageResult, int placementTick, EquipmentModel equipment) {
        List<TimelineHit> hits = new ArrayList<>();
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
                    hit.getHitTiming() + placementTick,
                    hit.getCritChance(),
                    hit.getHitType(),
                    hit.getParentAbility(),
                    hit.isDot(),
                    AbilityProvider.get(hit.getParentAbility(), equipment).isChannel()
            );
            hits.add(scheduledHit);
        }
        return hits;
    }
}
