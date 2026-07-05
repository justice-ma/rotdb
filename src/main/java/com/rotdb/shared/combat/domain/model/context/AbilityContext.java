package com.rotdb.shared.combat.domain.model.context;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.Handedness;
import com.rotdb.shared.ability.model.AbilityCooldownTiming;
import com.rotdb.shared.ability.model.GeneratedBuffEffect;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.DamageCalculationTiming;
import com.rotdb.shared.combat.domain.model.enums.Targetting;

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
    private List<GeneratedBuffEffect> generatedBuffEffects;
    private AbilityCooldownTiming abilityCooldownTiming = AbilityCooldownTiming.ON_CAST;
    private boolean stallable = true;
    private DamageCalculationTiming damageCalculationTiming = DamageCalculationTiming.ON_RELEASE;

    public AbilityContext(int numberOfHits, List<AbilityHitsContext> hits, String name, int adrenaline,
                          int cooldownTicks, boolean channel, Handedness handedness,
                          Targetting targetting, CombatStyles combatStyle, AbilityId id,
                          List<GeneratedBuffEffect> generatedBuffEffects, AbilityCooldownTiming abilityCooldownTiming,
                          boolean stallable, DamageCalculationTiming damageCalculationTiming) {
        this.numberOfHits = numberOfHits;
        this.hits = hits;
        this.name = name;
        this.adrenaline = adrenaline;
        this.cooldownTicks = cooldownTicks;
        this.channel = channel;
        this.handedness = handedness;
        this.targetting = targetting;
        this.combatStyle = combatStyle;
        this.id = id;
        this.generatedBuffEffects = generatedBuffEffects;
        this.abilityCooldownTiming = abilityCooldownTiming;
        this.stallable = stallable;
        this.damageCalculationTiming = damageCalculationTiming;
        assignMissingHitIndexes(hits);
    }

    public AbilityContext(int numberOfHits, List<AbilityHitsContext> hits, String name, int adrenaline,
                          int cooldownTicks, boolean channel, Handedness handedness,
                          Targetting targetting, CombatStyles combatStyle, AbilityId id,
                          List<GeneratedBuffEffect> generatedBuffEffects, AbilityCooldownTiming abilityCooldownTiming,
                          boolean stallable) {
        this.numberOfHits = numberOfHits;
        this.hits = hits;
        this.name = name;
        this.adrenaline = adrenaline;
        this.cooldownTicks = cooldownTicks;
        this.channel = channel;
        this.handedness = handedness;
        this.targetting = targetting;
        this.combatStyle = combatStyle;
        this.id = id;
        this.generatedBuffEffects = generatedBuffEffects;
        this.abilityCooldownTiming = abilityCooldownTiming;
        this.stallable = stallable;
        assignMissingHitIndexes(hits);
    }

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
        this.generatedBuffEffects = new ArrayList<GeneratedBuffEffect>();
        assignMissingHitIndexes(hits);
    }

    public AbilityContext(int numberOfHits, List<AbilityHitsContext> hits, String name, int adrenaline,
                          int cooldownTicks, boolean channel, Handedness handedness,
                          Targetting targetting, CombatStyles combatStyle, AbilityId id, DamageCalculationTiming damageCalculationTiming) {
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
        this.generatedBuffEffects = new ArrayList<GeneratedBuffEffect>();
        this.damageCalculationTiming = damageCalculationTiming;
        assignMissingHitIndexes(hits);
    }

    public AbilityContext(int numberOfHits, List<AbilityHitsContext> hits, String name, int adrenaline,
                          int cooldownTicks, boolean channel, Handedness handedness,
                          Targetting targetting, CombatStyles combatStyle, AbilityId id, AbilityCooldownTiming abilityCooldownTiming) {
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
        this.generatedBuffEffects = new ArrayList<GeneratedBuffEffect>();
        this.abilityCooldownTiming = abilityCooldownTiming;
        assignMissingHitIndexes(hits);
    }

    public AbilityContext(int numberOfHits, List<AbilityHitsContext> hits, String name, int adrenaline,
                          int cooldownTicks, boolean channel, Handedness handedness,
                          Targetting targetting, CombatStyles combatStyle, AbilityId id, boolean stallable) {
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
        this.generatedBuffEffects = new ArrayList<GeneratedBuffEffect>();
        this.stallable = stallable;
        assignMissingHitIndexes(hits);
    }

    public AbilityContext(int numberOfHits, List<AbilityHitsContext> hits, String name, int adrenaline,
                          int cooldownTicks, boolean channel, Handedness handedness,
                          Targetting targetting, CombatStyles combatStyle, AbilityId id, List<GeneratedBuffEffect> generatedBuffEffects) {
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
        setGeneratedBuffEffects(generatedBuffEffects);
        assignMissingHitIndexes(hits);
    }

    private static void assignMissingHitIndexes(List<AbilityHitsContext> hits) {
        for (int i = 0; i < hits.size(); i++) {
            AbilityHitsContext hit = hits.get(i);
            hit.setHitIndex(hit.getHitIndex() == -1 ? i : hit.getHitIndex());
        }
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
                this.id,
                new ArrayList<>(this.generatedBuffEffects),
                this.abilityCooldownTiming,
                this.stallable
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

    public List<GeneratedBuffEffect> getGeneratedBuffEffects() {
        return generatedBuffEffects;
    }

    public void setGeneratedBuffEffects(List<GeneratedBuffEffect> generatedBuffEffects) {
        if (generatedBuffEffects == null) {
            this.generatedBuffEffects = new ArrayList<GeneratedBuffEffect>();
        } else {
            this.generatedBuffEffects = new ArrayList<>(generatedBuffEffects);
        }
    }

    public AbilityCooldownTiming getAbilityCooldownTiming() {
        return abilityCooldownTiming;
    }

    public void setAbilityCooldownTiming(AbilityCooldownTiming abilityCooldownTiming) {
        this.abilityCooldownTiming = abilityCooldownTiming;
    }

    public boolean isStallable() {
        return stallable;
    }

    public void setStallable(boolean stallable) {
        this.stallable = stallable;
    }

    public DamageCalculationTiming getDamageCalculationTiming() {
        return damageCalculationTiming;
    }
}
