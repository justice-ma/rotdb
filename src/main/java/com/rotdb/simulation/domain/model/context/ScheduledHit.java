package com.rotdb.simulation.domain.model.context;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.enums.HitType;

public record ScheduledHit(
        Integer placementId,
        AbilityId parentAbility,
        int hitIndex,
        int hitTiming,
        int landingTick,
        HitType hitType,
        boolean dot,
        boolean channelled
) {
}