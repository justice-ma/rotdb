package com.rotdb.simulation.domain.model.context;

import com.rotdb.shared.ability.AbilityId;

import java.util.HashMap;
import java.util.Map;

public class AbilityCooldownContext {
    private Map<AbilityId, Integer> cooldownMap = new HashMap<>();

    public Map<AbilityId, Integer> getCooldownMap() {
        return cooldownMap;
    }

    public void setCooldownMap(Map<AbilityId, Integer> cooldownMap) {
        this.cooldownMap = cooldownMap;
    }

    public int getRemaining(AbilityId abilityId) {
        return cooldownMap.getOrDefault(abilityId, 0);
    }

    public boolean isOnCooldown(AbilityId abilityId) {
        return getRemaining(abilityId) > 0;
    }

    public void tick() {
        cooldownMap.replaceAll((id, ticks) -> Math.max(0, ticks - 1));
    }
}
