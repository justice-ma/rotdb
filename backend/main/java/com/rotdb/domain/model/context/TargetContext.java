package com.rotdb.domain.model.context;

import com.rotdb.domain.model.enums.CombatStyles;
import com.rotdb.domain.model.enums.TargetTags;
import com.rotdb.domain.model.enums.WeaponStyle;

import java.util.EnumSet;

public class TargetContext {
    private int maxHp, currentHp, startingTask, currentTask, size, defence, armour, affinity;
    private String name;
    private CombatStyles weakness;
    private WeaponStyle weaponWeakness;
    private EnumSet<TargetTags> tags;

    public boolean has(TargetTags tag) {
        return tags.contains(tag);
    }

    public void normalizeHp() {
        currentHp = Math.min(maxHp, currentHp);
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartingTask() {
        return startingTask;
    }

    public void setStartingTask(int startingTask) {
        this.startingTask = startingTask;
    }

    public int getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(int currentTask) {
        this.currentTask = currentTask;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getArmour() {
        return armour;
    }

    public void setArmour(int armour) {
        this.armour = armour;
    }

    public int getAffinity() {
        return affinity;
    }

    public void setAffinity(int affinity) {
        this.affinity = affinity;
    }

    public CombatStyles getWeakness() {
        return weakness;
    }

    public void setWeakness(CombatStyles weakness) {
        this.weakness = weakness;
    }

    public WeaponStyle getWeaponWeakness() {
        return weaponWeakness;
    }

    public void setWeaponWeakness(WeaponStyle weaponWeakness) {
        this.weaponWeakness = weaponWeakness;
    }

    public EnumSet<TargetTags> getTags() {
        return tags;
    }

    public void setTags(EnumSet<TargetTags> tags) {
        this.tags = tags;
    }
}


