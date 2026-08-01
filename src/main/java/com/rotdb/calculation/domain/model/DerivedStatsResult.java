package com.rotdb.calculation.domain.model;

public record DerivedStatsResult(
        int baseMaxHp,
        int equipmentLifeBonus,
        int effectiveMaxHp
) {
}
