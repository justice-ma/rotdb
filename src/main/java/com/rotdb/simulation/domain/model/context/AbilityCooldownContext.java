package com.rotdb.simulation.domain.model.context;

import com.rotdb.shared.ability.AbilityId;

import java.util.Map;

public class AbilityCooldownContext {
    private Map<AbilityId, Integer> cooldownMap;

    public Map<AbilityId, Integer> getCooldownMap() {
        return cooldownMap;
    }

    public void setCooldownMap(Map<AbilityId, Integer> cooldownMap) {
        this.cooldownMap = cooldownMap;
    }
}
