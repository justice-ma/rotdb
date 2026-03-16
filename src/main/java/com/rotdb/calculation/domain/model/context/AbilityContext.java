package com.rotdb.calculation.domain.model.context;

import com.rotdb.calculation.ability.AbilityId;
import com.rotdb.calculation.ability.Handedness;
import com.rotdb.calculation.domain.model.enums.CombatStyles;
import com.rotdb.calculation.domain.model.enums.Targetting;

import java.util.ArrayList;
import java.util.List;

public class AbilityContext {
    private int numberOfHits, adrenaline, cooldownTicks;
    private boolean channel;
    private String name;
    private Handedness handedness;
    private Targetting targetting;
    private CombatStyles combatStyle;
    private AbilityId id;
    private List<AbilityHitsContext> hits;

    public AbilityContext(int numberOfHits, List<AbilityHitsContext> hits, String name, int adrenaline,
                          int cooldownTicks, boolean channel, Handedness handedness,
                          Targetting targetting, CombatStyles combatStyle, AbilityId id) {
        this.numberOfHits = numberOfHits;
        this.hits = new ArrayList<>(hits);
        this.name = name;
        this.adrenaline = adrenaline;
        this.cooldownTicks = cooldownTicks;
        this.channel = channel;
        this.handedness = handedness;
        this.targetting = targetting;
        this.combatStyle = combatStyle;
        this.id = id;
    }

    public AbilityContext copyWithHits(List<AbilityHitsContext> newHits) {
        AbilityContext copy = new AbilityContext(
                this.numberOfHits,
                newHits,
                this.name,
                this.adrenaline,
                this.cooldownTicks,
                this.channel,
                this.handedness,
                this.targetting,
                this.combatStyle,
                this.id
        );
        return copy;
    }

    public int getNumberOfHits() {
        return numberOfHits;
    }

    public void setNumberOfHits(int numberOfHits) {
        this.numberOfHits = numberOfHits;
    }

    public List<AbilityHitsContext> getHits() {
        return hits;
    }

    public void setHits(List<AbilityHitsContext> hits) {
        this.hits = hits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdrenaline() {
        return adrenaline;
    }

    public void setAdrenaline(int adrenaline) {
        this.adrenaline = adrenaline;
    }

    public int getCooldownTicks() {
        return cooldownTicks;
    }

    public void setCooldownTicks(int cooldownTicks) {
        this.cooldownTicks = cooldownTicks;
    }

    public boolean isChannel() {
        return channel;
    }

    public void setChannel(boolean channel) {
        this.channel = channel;
    }

    public Handedness getHandedness() {
        return handedness;
    }

    public void setHandedness(Handedness handedness) {
        this.handedness = handedness;
    }

    public Targetting getTargetting() {
        return targetting;
    }

    public void setTargetting(Targetting targetting) {
        this.targetting = targetting;
    }

    public CombatStyles getCombatStyle() {
        return combatStyle;
    }

    public void setCombatStyle(CombatStyles combatStyle) {
        this.combatStyle = combatStyle;
    }

    public AbilityId getId() {
        return id;
    }

    public void setId(AbilityId id) {
        this.id = id;
    }
}
