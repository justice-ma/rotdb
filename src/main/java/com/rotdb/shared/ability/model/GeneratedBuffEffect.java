package com.rotdb.shared.ability.model;

import com.rotdb.shared.combat.domain.model.enums.BuffId;

public record GeneratedBuffEffect(
        BuffId buffId,
        GeneratedBuffTiming buffTiming,
        Integer durationOverrideTicks
) {
    public GeneratedBuffEffect(BuffId buffId, GeneratedBuffTiming buffTiming) {
        this(buffId, buffTiming, null);
    }
}
