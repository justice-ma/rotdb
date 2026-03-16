package com.rotdb.calculation.domain.model;

import java.util.List;

public class DamageResult {
    private final int totalMinDamage, totalMaxDamage, totalAvgDamage, totalMinCrit,
            totalMaxCrit, totalAvgCrit, totalMinNonCrit, totalMaxNonCrit, totalAvgNonCrit, minCoeff, maxCoeff;
    private final List<HitResult> hit;
    public DamageResult(int totalMinDamage, int totalMaxDamage, int totalAvgDamage, int totalMinCrit, int totalMaxCrit,
                        int totalAvgCrit, int totalMinNonCrit, int totalMaxNonCrit, int totalAvgNonCrit, int minCoeff,
                        int maxCoeff, List<HitResult> hit) {
        this.totalMinDamage = totalMinDamage;
        this.totalMaxDamage = totalMaxDamage;
        this.totalAvgDamage = totalAvgDamage;
        this.totalMinCrit = totalMinCrit;
        this.totalMaxCrit = totalMaxCrit;
        this.totalAvgCrit = totalAvgCrit;
        this.totalMinNonCrit = totalMinNonCrit;
        this.totalMaxNonCrit = totalMaxNonCrit;
        this.totalAvgNonCrit = totalAvgNonCrit;
        this.minCoeff = minCoeff;
        this.maxCoeff = maxCoeff;
        this.hit = hit;
    }

    public int getTotalMinDamage() {
        return totalMinDamage;
    }

    public int getTotalMaxDamage() {
        return totalMaxDamage;
    }

    public int getTotalAvgDamage() {
        return totalAvgDamage;
    }

    public int getTotalMinCrit() {
        return totalMinCrit;
    }

    public int getTotalMaxCrit() {
        return totalMaxCrit;
    }

    public int getTotalAvgCrit() {
        return totalAvgCrit;
    }

    public int getTotalMinNonCrit() {
        return totalMinNonCrit;
    }

    public int getTotalMaxNonCrit() {
        return totalMaxNonCrit;
    }

    public int getTotalAvgNonCrit() {
        return totalAvgNonCrit;
    }

    public int getMinCoeff() {
        return minCoeff;
    }

    public int getMaxCoeff() {
        return maxCoeff;
    }

    public List<HitResult> getHit() {
        return hit;
    }
}
