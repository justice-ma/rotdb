package com.rotdb.simulation.domain.model.context;

import com.rotdb.shared.combat.domain.model.enums.BuffId;

import java.util.Map;

public class BuffCooldownContext {
    private Map<BuffId, Integer> cooldownMap;
    private Map<BuffId, Integer> stacksMap;

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
}
