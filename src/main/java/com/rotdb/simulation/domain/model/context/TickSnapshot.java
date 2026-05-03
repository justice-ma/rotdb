package com.rotdb.simulation.domain.model.context;

import com.rotdb.simulation.domain.model.cooldown.CooldownKey;

import java.util.List;
import java.util.Map;

public class TickSnapshot {
    private int tick;
    private List<AbilityPlacement> placedAbilities;
    private RotationCombatState startingCombatState;
    private RotationCombatState endingCombatState;
    private Map<CooldownKey, Integer> startingCooldownMap;
    private Map<CooldownKey, Integer> endingCooldownMap;
    private double startingAdrenaline;
    private double endingAdrenaline;
    private List<TimelineHit> landedHits;
    private List<String> warnings; // I think ideally in the future there is a determinant amount of things that can go wrong, this should likely be an enum of warnings.

    public int getTick() {
        return tick;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public List<AbilityPlacement> getPlacedAbilities() {
        return placedAbilities;
    }

    public void setPlacedAbilities(List<AbilityPlacement> placedAbilities) {
        this.placedAbilities = placedAbilities;
    }

    public RotationCombatState getStartingCombatState() {
        return startingCombatState;
    }

    public void setStartingCombatState(RotationCombatState startingCombatState) {
        this.startingCombatState = startingCombatState;
    }

    public RotationCombatState getEndingCombatState() {
        return endingCombatState;
    }

    public void setEndingCombatState(RotationCombatState endingCombatState) {
        this.endingCombatState = endingCombatState;
    }

    public Map<CooldownKey, Integer> getStartingCooldownMap() {
        return startingCooldownMap;
    }

    public void setStartingCooldownMap(Map<CooldownKey, Integer> startingCooldownMap) {
        this.startingCooldownMap = startingCooldownMap;
    }

    public Map<CooldownKey, Integer> getEndingCooldownMap() {
        return endingCooldownMap;
    }

    public void setEndingCooldownMap(Map<CooldownKey, Integer> endingCooldownMap) {
        this.endingCooldownMap = endingCooldownMap;
    }

    public double getStartingAdrenaline() {
        return startingAdrenaline;
    }

    public void setStartingAdrenaline(double startingAdrenaline) {
        this.startingAdrenaline = startingAdrenaline;
    }

    public double getEndingAdrenaline() {
        return endingAdrenaline;
    }

    public void setEndingAdrenaline(double endingAdrenaline) {
        this.endingAdrenaline = endingAdrenaline;
    }

    public List<TimelineHit> getLandedHits() {
        return landedHits;
    }

    public void setLandedHits(List<TimelineHit> landedHits) {
        this.landedHits = landedHits;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }
}
