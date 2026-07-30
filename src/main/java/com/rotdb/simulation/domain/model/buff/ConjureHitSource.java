package com.rotdb.simulation.domain.model.buff;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.enums.BuffId;

public record ConjureHitSource(
        AbilityId damageAbilityId,
        Integer firstHitOffset,
        Integer cadence,
        BuffId durationBuffId,
        boolean skipFirstHit
) {
}
