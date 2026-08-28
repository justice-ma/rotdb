package com.rotdb.simulation.domain.model.context;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.enums.HitType;

public class TimelineHit {
    private final int hitMinDamage, hitMaxDamage, hitAvgDamage, hitMinCrit,
            hitMaxCrit, hitAvgCrit, hitMinNonCrit, hitMaxNonCrit, hitAvgNonCrit, hitIndex, hitTiming, landingTick;
    private final double critChance;
    private final HitType hitType;
    private final AbilityId parentAbility;
    private final Integer placementId;
    private final boolean dot, channelled;

    public TimelineHit(int hitMinDamage, int hitMaxDamage, int hitAvgDamage, int hitMinCrit, int hitMaxCrit,
                       int hitAvgCrit, int hitMinNonCrit, int hitMaxNonCrit, int hitAvgNonCrit, int hitIndex,
                       int hitTiming, int landingTick, double critChance, HitType hitType, AbilityId parentAbility, Integer placementId,
                       boolean dot, boolean channelled) {
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
        this.hitTiming = hitTiming;
        this.landingTick = landingTick;
        this.critChance = critChance;
        this.hitType = hitType;
        this.parentAbility = parentAbility;
        this.placementId = placementId;
        this.dot = dot;
        this.channelled = channelled;
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

    public int getHitTiming() {
        return hitTiming;
    }

    public int getLandingTick() {
        return landingTick;
    }

    public double getCritChance() {
        return critChance;
    }

    public HitType getHitType() {
        return hitType;
    }

    public AbilityId getParentAbility() {
        return parentAbility;
    }

    public boolean isDot() {
        return dot;
    }

    public boolean isChannelled() {
        return channelled;
    }

    public Integer getPlacementId() {
        return placementId;
    }
}
