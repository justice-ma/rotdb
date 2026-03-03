package com.rotdb.model.context;

import com.rotdb.model.equipment.EquipmentModel;

public class DamageContext {
    private int baseDamage, currentDamage, currentMin, currentMax, critMin, critMax, critDamage, nonCritMin, nonCritMax, nonCritDamage;
    private EquipmentModel equipment;
    private double minPercent, maxPercent;

    public int getCurrentDamage() {
        return currentDamage;
    }

    public void setCurrentDamage(int currentDamage) {
        this.currentDamage = currentDamage;
    }

    public int getCurrentMax() {
        return currentMax;
    }

    public void setCurrentMax(int currentMax) {
        this.currentMax = currentMax;
    }

    public int getCurrentMin() {
        return currentMin;
    }

    public void setCurrentMin(int currentMin) {
        this.currentMin = currentMin;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public EquipmentModel getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentModel equipment) {
        this.equipment = equipment;
    }

    public double getMaxPercent() {
        return maxPercent;
    }

    public void setMaxPercent(double maxPercent) {
        this.maxPercent = maxPercent;
    }

    public double getMinPercent() {
        return minPercent;
    }

    public void setMinPercent(double minPercent) {
        this.minPercent = minPercent;
    }

    public int getNonCritDamage() {
        return nonCritDamage;
    }

    public void setNonCritDamage(int nonCritDamage) {
        this.nonCritDamage = nonCritDamage;
    }

    public int getNonCritMax() {
        return nonCritMax;
    }

    public void setNonCritMax(int nonCritMax) {
        this.nonCritMax = nonCritMax;
    }

    public int getNonCritMin() {
        return nonCritMin;
    }

    public void setNonCritMin(int nonCritMin) {
        this.nonCritMin = nonCritMin;
    }

    public int getCritDamage() {
        return critDamage;
    }

    public void setCritDamage(int critDamage) {
        this.critDamage = critDamage;
    }

    public int getCritMax() {
        return critMax;
    }

    public void setCritMax(int critMax) {
        this.critMax = critMax;
    }

    public int getCritMin() {
        return critMin;
    }

    public void setCritMin(int critMin) {
        this.critMin = critMin;
    }
}
