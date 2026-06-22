package com.rotdb.simulation.domain.model.context;

import com.rotdb.simulation.domain.model.buff.BuffCooldownKey;
import com.rotdb.simulation.domain.model.cooldown.AbilityCooldownKey;

import java.util.List;
import java.util.Map;

public class TickSnapshot {
    private int tick;
    private List<AbilityPlacement> placedAbilities;
    private List<BuffPlacement> placedBuffs;
    private RotationCombatState startingCombatState;
    private RotationCombatState endingCombatState;
    private Map<AbilityCooldownKey, Integer> startingAbilityCooldownMap;
    private Map<AbilityCooldownKey, Integer> endingAbilityCooldownMap;
    private Map<BuffCooldownKey, Integer> startingBuffCooldownMap;
    private Map<BuffCooldownKey, Integer> endingBuffCooldownMap;
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

    public List<BuffPlacement> getPlacedBuffs() {
        return placedBuffs;
    }

    public void setPlacedBuffs(List<BuffPlacement> placedBuffs) {
        this.placedBuffs = placedBuffs;
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

    public Map<AbilityCooldownKey, Integer> getStartingAbilityCooldownMap() {
        return startingAbilityCooldownMap;
    }

    public void setStartingAbilityCooldownMap(Map<AbilityCooldownKey, Integer> startingAbilityCooldownMap) {
        this.startingAbilityCooldownMap = startingAbilityCooldownMap;
    }

    public Map<AbilityCooldownKey, Integer> getEndingAbilityCooldownMap() {
        return endingAbilityCooldownMap;
    }

    public void setEndingAbilityCooldownMap(Map<AbilityCooldownKey, Integer> endingAbilityCooldownMap) {
        this.endingAbilityCooldownMap = endingAbilityCooldownMap;
    }

    public Map<BuffCooldownKey, Integer> getStartingBuffCooldownMap() {
        return startingBuffCooldownMap;
    }

    public void setStartingBuffCooldownMap(Map<BuffCooldownKey, Integer> startingBuffCooldownMap) {
        this.startingBuffCooldownMap = startingBuffCooldownMap;
    }

    public Map<BuffCooldownKey, Integer> getEndingBuffCooldownMap() {
        return endingBuffCooldownMap;
    }

    public void setEndingBuffCooldownMap(Map<BuffCooldownKey, Integer> endingBuffCooldownMap) {
        this.endingBuffCooldownMap = endingBuffCooldownMap;
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
