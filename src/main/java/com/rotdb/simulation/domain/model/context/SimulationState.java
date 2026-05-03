package com.rotdb.simulation.domain.model.context;

import com.rotdb.simulation.domain.model.cooldown.CooldownKey;

import java.util.Map;

public class SimulationState {
    private RotationCombatState state;
    private Map<CooldownKey, Integer> cooldownMap;
    private double adrenaline;
    private double maximumAdrenaline = 100.0;

    public RotationCombatState getState() {
        return state;
    }

    public void setState(RotationCombatState state) {
        this.state = state;
    }

    public Map<CooldownKey, Integer> getCooldownMap() {
        return cooldownMap;
    }

    public void setCooldownMap(Map<CooldownKey, Integer> cooldownMap) {
        this.cooldownMap = cooldownMap;
    }

    public double getAdrenaline() {
        return adrenaline;
    }

    public void setAdrenaline(double adrenaline) {
        this.adrenaline = adrenaline;
    }

    public double getMaximumAdrenaline() {
        return maximumAdrenaline;
    }

    public void setMaximumAdrenaline(double maximumAdrenaline) {
        this.maximumAdrenaline = maximumAdrenaline;
    }

    public double getBaseMaximumAdrenaline() {
        return 100.0;
    }
}
