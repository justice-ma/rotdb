package com.rotdb.resolvers.abilityDamage.npc;

public class HauntedBonus {
    private int minCrit, maxCrit, minNonCrit, maxNonCrit, minAvg, maxAvg;

    public HauntedBonus(int minCrit, int maxCrit, int minNonCrit, int maxNonCrit, int minAvg, int maxAvg) {
        this.minCrit = minCrit;
        this.maxCrit = maxCrit;
        this.minNonCrit = minNonCrit;
        this.maxNonCrit = maxNonCrit;
        this.minAvg = minAvg;
        this.maxAvg = maxAvg;
    }

    public boolean isZero() {
        return minCrit == 0 &&
                maxCrit == 0 &&
                minNonCrit == 0 &&
                maxNonCrit == 0 &&
                minAvg == 0 &&
                maxAvg == 0;
    }

    public int getMinCrit() {
        return minCrit;
    }

    public void setMinCrit(int minCrit) {
        this.minCrit = minCrit;
    }

    public int getMaxCrit() {
        return maxCrit;
    }

    public void setMaxCrit(int maxCrit) {
        this.maxCrit = maxCrit;
    }

    public int getMinNonCrit() {
        return minNonCrit;
    }

    public void setMinNonCrit(int minNonCrit) {
        this.minNonCrit = minNonCrit;
    }

    public int getMaxNonCrit() {
        return maxNonCrit;
    }

    public void setMaxNonCrit(int maxNonCrit) {
        this.maxNonCrit = maxNonCrit;
    }

    public int getMinAvg() {
        return minAvg;
    }

    public void setMinAvg(int minAvg) {
        this.minAvg = minAvg;
    }

    public int getMaxAvg() {
        return maxAvg;
    }

    public void setMaxAvg(int maxAvg) {
        this.maxAvg = maxAvg;
    }
}
