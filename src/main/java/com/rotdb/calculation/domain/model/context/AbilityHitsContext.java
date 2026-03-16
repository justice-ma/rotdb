package com.rotdb.calculation.domain.model.context;

import com.rotdb.calculation.domain.model.enums.AbilityTier;
import com.rotdb.calculation.domain.model.enums.HitType;

public class AbilityHitsContext {
    private int parentIndex, critMin, critMax, critDamage, nonCritMin, nonCritMax, nonCritDamage, currentDamage,
            currentMin, currentMax, bolgMin, bolgMax, bolgDamage, hitTiming;
    private double min, max, critChanceModifier, critDamageModifier, minCritDamage, maxCritDamage, averageCritDamage;
    private boolean dot, needsRangeRecalc, rangeCalculated;
    private AbilityTier tier;
    private HitType type;

    public AbilityHitsContext() {}

    public AbilityHitsContext(double min, double max, boolean dot, AbilityTier tier, int hitTiming) {
        this.min = min;
        this.max = max;
        this.dot = dot;
        this.tier = tier;
        this.hitTiming = hitTiming;
        this.type = HitType.BASE;
        this.parentIndex = -1;
    }

    public AbilityHitsContext(double min, double max, boolean dot, AbilityTier tier, int hitTiming, HitType type, int parentIndex) {
        this.min = min;
        this.max = max;
        this.dot = dot;
        this.tier = tier;
        this.hitTiming = hitTiming;
        this.type = type;
        this.parentIndex = parentIndex;
    }

    public void calculateDamages (double mod) {
        setCurrentMin((int) (getCurrentMin() * mod));
        setCurrentMax((int) (getCurrentMax() * mod));
        setCurrentDamage((getCurrentMin() + getCurrentMax()) / 2);
        setCritMin((int) (getCritMin() * mod));
        setCritMax((int) (getCritMax() * mod));
        setCritDamage((getCritMin() + getCritMax()) / 2);
        setNonCritMin((int) (getNonCritMin() * mod));
        setNonCritMax((int) (getNonCritMax() * mod));
        setNonCritDamage((getNonCritMin() + getNonCritMax()) / 2);
    }

    public void setBolgDamages (int damage, int max, int min) {
        this.bolgDamage = damage;
        this.bolgMax = max;
        this.bolgMin = min;
    }

    public void setCritAndNonDamages (double critChance, double minCritDamage, double maxCritDamage, double averageCritDamage) {
        this.critMax = (int) (getCurrentMax() * (1 + maxCritDamage));
        this.critMin = (int) (getCurrentMin() * (1 + minCritDamage));
        this.critDamage = (this.critMax + this.critMin) / 2;
        this.nonCritMax = getCurrentMax();
        this.nonCritMin = getCurrentMin();
        this.nonCritDamage = getCurrentDamage();
        setCurrentMin((int) (getCurrentMin() + (getCurrentMin() * (critChance * minCritDamage))));
        setCurrentMax((int) (getCurrentMax() + (getCurrentMax() * (critChance * maxCritDamage))));
        setCurrentDamage((getCurrentMin() + getCurrentMax()) / 2);
    }

    public void resetComputed() {
        this.critMin = 0;
        this.critMax = 0;
        this.critDamage = 0;

        this.nonCritMin = 0;
        this.nonCritMax = 0;
        this.nonCritDamage = 0;

        this.currentMin = 0;
        this.currentMax = 0;
        this.currentDamage = 0;

        this.bolgMin = 0;
        this.bolgMax = 0;
        this.bolgDamage = 0;

        this.rangeCalculated = false;
        this.needsRangeRecalc = true;
    }

    public void setCritDamages(double minCritDamage, double maxCritDamage) {
        this.minCritDamage = minCritDamage;
        this.maxCritDamage = maxCritDamage;
        this.averageCritDamage = (minCritDamage + maxCritDamage) / 2;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public boolean isDot() {
        return dot;
    }

    public void setDot(boolean dot) {
        this.dot = dot;
    }

    public AbilityTier getTier() {
        return tier;
    }

    public void setTier(AbilityTier tier) {
        this.tier = tier;
    }

    public HitType getType() {
        return type;
    }

    public void setType(HitType type) {
        this.type = type;
    }

    public int getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    public int getCritMin() {
        return critMin;
    }

    public void setCritMin(int critMin) {
        this.critMin = critMin;
    }

    public int getCritMax() {
        return critMax;
    }

    public void setCritMax(int critMax) {
        this.critMax = critMax;
    }

    public int getCritDamage() {
        return critDamage;
    }

    public void setCritDamage(int critDamage) {
        this.critDamage = critDamage;
    }

    public int getNonCritMin() {
        return nonCritMin;
    }

    public void setNonCritMin(int nonCritMin) {
        this.nonCritMin = nonCritMin;
    }

    public int getNonCritMax() {
        return nonCritMax;
    }

    public void setNonCritMax(int nonCritMax) {
        this.nonCritMax = nonCritMax;
    }

    public int getNonCritDamage() {
        return nonCritDamage;
    }

    public void setNonCritDamage(int nonCritDamage) {
        this.nonCritDamage = nonCritDamage;
    }

    public int getCurrentDamage() {
        return currentDamage;
    }

    public void setCurrentDamage(int currentDamage) {
        this.currentDamage = currentDamage;
    }

    public int getCurrentMin() {
        return currentMin;
    }

    public void setCurrentMin(int currentMin) {
        this.currentMin = currentMin;
    }

    public int getCurrentMax() {
        return currentMax;
    }

    public void setCurrentMax(int currentMax) {
        this.currentMax = currentMax;
    }

    public int getBolgMin() {
        return bolgMin;
    }

    public void setBolgMin(int bolgMin) {
        this.bolgMin = bolgMin;
    }

    public int getBolgMax() {
        return bolgMax;
    }

    public void setBolgMax(int bolgMax) {
        this.bolgMax = bolgMax;
    }

    public int getBolgDamage() {
        return bolgDamage;
    }

    public void setBolgDamage(int bolgDamage) {
        this.bolgDamage = bolgDamage;
    }

    public int getHitTiming() {
        return hitTiming;
    }

    public void setHitTiming(int hitTiming) {
        this.hitTiming = hitTiming;
    }

    public boolean isRangeCalculated() {
        return rangeCalculated;
    }

    public void setRangeCalculated(boolean rangeCalculated) {
        this.rangeCalculated = rangeCalculated;
    }

    public boolean isNeedsRangeRecalc() {
        return needsRangeRecalc;
    }

    public void setNeedsRangeRecalc(boolean needsRangeRecalc) {
        this.needsRangeRecalc = needsRangeRecalc;
    }

    public double getCritChanceModifier() {
        return critChanceModifier;
    }

    public void setCritChanceModifier(double critChanceModifier) {
        this.critChanceModifier = critChanceModifier;
    }

    public double getCritDamageModifier() {
        return critDamageModifier;
    }

    public void setCritDamageModifier(double critDamageModifier) {
        this.critDamageModifier = critDamageModifier;
    }

    public double getMinCritDamage() {
        return minCritDamage;
    }

    public void setMinCritDamage(double minCritDamage) {
        this.minCritDamage = minCritDamage;
    }

    public double getMaxCritDamage() {
        return maxCritDamage;
    }

    public void setMaxCritDamage(double maxCritDamage) {
        this.maxCritDamage = maxCritDamage;
    }

    public double getAverageCritDamage() {
        return averageCritDamage;
    }

    public void setAverageCritDamage(double averageCritDamage) {
        this.averageCritDamage = averageCritDamage;
    }
}
