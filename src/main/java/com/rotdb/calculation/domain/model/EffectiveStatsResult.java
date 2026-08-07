package com.rotdb.calculation.domain.model;

public class EffectiveStatsResult{
    double globalCritChance, globalCritDamage,totalArmour, totalPrayer;

    public EffectiveStatsResult(double globalCritChance, double globalCritDamage, double totalArmour, double totalPrayer) {
        this.globalCritChance = globalCritChance;
        this.globalCritDamage = globalCritDamage;
        this.totalArmour = totalArmour;
        this.totalPrayer = totalPrayer;
    }

    public double getGlobalCritChance() {
        return globalCritChance;
    }

    public void setGlobalCritChance(double globalCritChance) {
        this.globalCritChance = globalCritChance;
    }

    public double getGlobalCritDamage() {
        return globalCritDamage;
    }

    public void setGlobalCritDamage(double globalCritDamage) {
        this.globalCritDamage = globalCritDamage;
    }

    public double getTotalArmour() {
        return totalArmour;
    }

    public void setTotalArmour(double totalArmour) {
        this.totalArmour = totalArmour;
    }

    public double getTotalPrayer() {
        return totalPrayer;
    }

    public void setTotalPrayer(double totalPrayer) {
        this.totalPrayer = totalPrayer;
    }
}
