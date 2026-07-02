package com.rotdb.simulation.domain.model.context;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.BuffCooldownKey;
import com.rotdb.simulation.domain.model.cooldown.AbilityCooldownKey;

import java.util.Map;

public class SimulationState {
    private RotationCombatState state;
    private Map<AbilityCooldownKey, Integer> abilityCooldownMap;
    private Map<BuffCooldownKey, Integer> buffCooldownMap;
    private Map<BuffId, ActiveBuffState> activeBuffDurationMap;
    private double adrenaline;
    private double maximumAdrenaline = 100.0;

    public RotationCombatState getState() {
        return state;
    }

    public void setState(RotationCombatState state) {
        this.state = state;
    }

    public Map<AbilityCooldownKey, Integer> getAbilityCooldownMap() {
        return abilityCooldownMap;
    }

    public void setAbilityCooldownMap(Map<AbilityCooldownKey, Integer> abilityCooldownMap) {
        this.abilityCooldownMap = abilityCooldownMap;
    }

    public Map<BuffCooldownKey, Integer> getBuffCooldownMap() {
        return buffCooldownMap;
    }

    public void setBuffCooldownMap(Map<BuffCooldownKey, Integer> buffCooldownMap) {
        this.buffCooldownMap = buffCooldownMap;
    }

    public Map<BuffId, ActiveBuffState> getActiveBuffDurationMap() {
        return activeBuffDurationMap;
    }

    public void setActiveBuffDurationMap(Map<BuffId, ActiveBuffState> activeBuffDurationMap) {
        this.activeBuffDurationMap = activeBuffDurationMap;
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
