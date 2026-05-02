package com.rotdb.simulation.domain.model.context;

import com.rotdb.shared.combat.domain.model.enums.BuffId;

import java.util.Map;

public class BuffRotationContext {
    private Map<BuffId, Integer> cooldownMap;
    private Map<BuffId, Integer> stacksMap;
    private Map<BuffId, Integer> uptimeMap;

    public Map<BuffId, Integer> getCooldownMap() {
        return cooldownMap;
    }

    public void setCooldownMap(Map<BuffId, Integer> cooldownMap) {
        this.cooldownMap = cooldownMap;
    }

    public Map<BuffId, Integer> getStacksMap() {
        return stacksMap;
    }

    public void setStacksMap(Map<BuffId, Integer> stacksMap) {
        this.stacksMap = stacksMap;
    }

    public Map<BuffId, Integer> getUptimeMap() {
        return uptimeMap;
    }

    public void setUptimeMap(Map<BuffId, Integer> uptimeMap) {
        this.uptimeMap = uptimeMap;
    }
}
