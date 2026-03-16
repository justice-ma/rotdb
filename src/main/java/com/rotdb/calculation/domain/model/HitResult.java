package com.rotdb.calculation.domain.model;

import com.rotdb.calculation.domain.model.enums.HitType;

public class HitResult {
    private final int hitMinDamage, hitMaxDamage, hitAvgDamage, hitMinCrit,
            hitMaxCrit, hitAvgCrit, hitMinNonCrit, hitMaxNonCrit, hitAvgNonCrit, hitIndex;
    private final HitType hitType;

    public HitResult(int hitMinDamage, int hitMaxDamage, int hitAvgDamage, int hitMinCrit, int hitMaxCrit,
                        int hitAvgCrit, int hitMinNonCrit, int hitMaxNonCrit, int hitAvgNonCrit, int hitIndex,
                     HitType hitType) {
        this.hitMinDamage = hitMinDamage;
        this.hitMaxDamage = hitMaxDamage;
        this.hitAvgDamage = hitAvgDamage;
        this.hitMinCrit = hitMinCrit;
        this.hitMaxCrit = hitMaxCrit;
        this.hitAvgCrit = hitAvgCrit;
        this.hitMinNonCrit = hitMinNonCrit;
        this.hitMaxNonCrit = hitMaxNonCrit;
        this.hitAvgNonCrit = hitAvgNonCrit;
        this.hitIndex = hitIndex;
        this.hitType = hitType;
    }

    public int getHitMinDamage() {
        return hitMinDamage;
    }

    public int getHitMaxDamage() {
        return hitMaxDamage;
    }

    public int getHitAvgDamage() {
        return hitAvgDamage;
    }

    public int getHitMinCrit() {
        return hitMinCrit;
    }

    public int getHitMaxCrit() {
        return hitMaxCrit;
    }

    public int getHitAvgCrit() {
        return hitAvgCrit;
    }

    public int getHitMinNonCrit() {
        return hitMinNonCrit;
    }

    public int getHitMaxNonCrit() {
        return hitMaxNonCrit;
    }

    public int getHitAvgNonCrit() {
        return hitAvgNonCrit;
    }

    public int getHitIndex() {
        return hitIndex;
    }

    public HitType getHitType() {
        return hitType;
    }
}
