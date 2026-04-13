package com.rotdb.calculation.domain.model.player;

import com.rotdb.calculation.domain.model.enums.Prayer;

import java.util.EnumSet;

public class PrayerContext {
    private int prayerStrengthBonus, prayerAccuracyBonus, eclipsedSoul;
    private EnumSet<Prayer> selected;

    public int getPrayerStrengthBonus() {
        return prayerStrengthBonus;
    }

    public void setPrayerStrengthBonus(int prayerStrengthBonus) {
        this.prayerStrengthBonus = prayerStrengthBonus;
    }

    public void addPrayerStrengthBonus(int prayerStrengthBonus) {
        this.prayerStrengthBonus += prayerStrengthBonus;
    }

    public int getPrayerAccuracyBonus() {
        return prayerAccuracyBonus;
    }

    public void setPrayerAccuracyBonus(int prayerAccuracyBonus) {
        this.prayerAccuracyBonus = prayerAccuracyBonus;
    }

    public void addPrayerAccuracyBonus(int prayerAccuracyBonus) {
        this.prayerAccuracyBonus += prayerAccuracyBonus;
    }

    public int getEclipsedSoul() {
        return eclipsedSoul;
    }

    public void setEclipsedSoul(int eclipsedSoul) {
        this.eclipsedSoul = eclipsedSoul;
    }

    public EnumSet<Prayer> getSelected() {
        return selected;
    }

    public void setSelected(EnumSet<Prayer> selected) {
        this.selected = selected;
    }
}
