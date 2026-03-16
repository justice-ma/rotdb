package com.rotdb.calculation.domain.resolvers.abilityDamage.abilityRange;

public class AbilityRangeBonus {
    private final double minDelta;
    private final double maxDelta;

    public AbilityRangeBonus(double minDelta, double maxDelta) {
        this.minDelta = minDelta;
        this.maxDelta = maxDelta;
    }

    public double getMinDelta() {
        return minDelta;
    }

    public double getMaxDelta() {
        return maxDelta;
    }
}
