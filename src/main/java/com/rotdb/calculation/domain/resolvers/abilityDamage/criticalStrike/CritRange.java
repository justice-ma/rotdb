package com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike;

public class CritRange {
    private double minMod;
    private double maxMod;

    public CritRange(double minMod, double maxMod) {
        this.minMod = minMod;
        this.maxMod = maxMod;
    }

    public double getMinMod() {
        return minMod;
    }

    public void setMinMod(double minMod) {
        this.minMod = minMod;
    }

    public double getMaxMod() {
        return maxMod;
    }

    public void setMaxMod(double maxMod) {
        this.maxMod = maxMod;
    }
}
