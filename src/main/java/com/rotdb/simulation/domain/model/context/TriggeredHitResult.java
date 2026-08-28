package com.rotdb.simulation.domain.model.context;

import com.rotdb.shared.ability.AbilityId;

public record TriggeredHitResult(
        AbilityId abilityId,
        Integer triggerTick,
        Integer parentPlacementId,
        AbilityId parentAbilityId,
        Integer delay
) {
}
