package com.rotdb.domain.model.context;

import com.rotdb.domain.model.enums.CombatStyles;
import com.rotdb.domain.model.enums.WeaponStyle;

public class TargetContext {
    private int maxHp, currentHp, bleeds, startingTask, currentTask, size, defence, armour, affinity,
            blackStoneArrowStacks, lordOfBonesStacks;
    private boolean combusted, flameboundRival, undead, scarab, scarabPlus, slayerTask, boss, abyssalDemon, dragon,
            revenant, haunted, vulned, cursed, demon, spider, ghostHunter, smokeClouded, dagannoth, obliterate, bandosBook,
            clawsOfGuthix, clobber, sunder, backstab, croesusSpored, dinosaur;
    private String name;
    private CombatStyles weakness;
    private WeaponStyle weaponWeakness;

    public boolean isCombusted() {
        return combusted;
    }

    public void setCombusted(boolean combusted) {
        this.combusted = combusted;
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

    public boolean isFlameboundRival() {
        return flameboundRival;
    }

    public void setFlameboundRival(boolean flameboundRival) {
        this.flameboundRival = flameboundRival;
    }

    public boolean isUndead() {
        return undead;
    }

    public void setUndead(boolean undead) {
        this.undead = undead;
    }

    public boolean isScarab() {
        return scarab;
    }

    public void setScarab(boolean scarab) {
        this.scarab = scarab;
    }

    public boolean isScarabPlus() {
        return scarabPlus;
    }

    public void setScarabPlus(boolean scarabPlus) {
        this.scarabPlus = scarabPlus;
    }

    public boolean isSlayerTask() {
        return slayerTask;
    }

    public void setSlayerTask(boolean slayerTask) {
        this.slayerTask = slayerTask;
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

    public boolean isBoss() {
        return boss;
    }

    public void setBoss(boolean boss) {
        this.boss = boss;
    }

    public boolean isAbyssalDemon() {
        return abyssalDemon;
    }

    public void setAbyssalDemon(boolean abyssalDemon) {
        this.abyssalDemon = abyssalDemon;
    }

    public boolean isDragon() {
        return dragon;
    }

    public void setDragon(boolean dragon) {
        this.dragon = dragon;
    }

    public boolean isRevenant() {
        return revenant;
    }

    public void setRevenant(boolean revenant) {
        this.revenant = revenant;
    }

    public boolean isHaunted() {
        return haunted;
    }

    public void setHaunted(boolean haunted) {
        this.haunted = haunted;
    }

    public boolean isVulned() {
        return vulned;
    }

    public void setVulned(boolean vulned) {
        this.vulned = vulned;
    }

    public boolean isCursed() {
        return cursed;
    }

    public void setCursed(boolean cursed) {
        this.cursed = cursed;
    }

    public boolean isDemon() {
        return demon;
    }

    public void setDemon(boolean demon) {
        this.demon = demon;
    }

    public boolean isSpider() {
        return spider;
    }

    public void setSpider(boolean spider) {
        this.spider = spider;
    }

    public boolean isGhostHunter() {
        return ghostHunter;
    }

    public void setGhostHunter(boolean ghostHunter) {
        this.ghostHunter = ghostHunter;
    }

    public int getBleeds() {
        return bleeds;
    }

    public void setBleeds(int bleeds) {
        this.bleeds = bleeds;
    }

    public boolean isSmokeClouded() {
        return smokeClouded;
    }

    public void setSmokeClouded(boolean smokeClouded) {
        this.smokeClouded = smokeClouded;
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

    public boolean isDagannoth() {
        return dagannoth;
    }

    public void setDagannoth(boolean dagannoth) {
        this.dagannoth = dagannoth;
    }

    public boolean isObliterate() {
        return obliterate;
    }

    public void setObliterate(boolean obliterate) {
        this.obliterate = obliterate;
    }

    public boolean isBandosBook() {
        return bandosBook;
    }

    public void setBandosBook(boolean bandosBook) {
        this.bandosBook = bandosBook;
    }

    public boolean isClawsOfGuthix() {
        return clawsOfGuthix;
    }

    public void setClawsOfGuthix(boolean clawsOfGuthix) {
        this.clawsOfGuthix = clawsOfGuthix;
    }

    public boolean isClobber() {
        return clobber;
    }

    public void setClobber(boolean clobber) {
        this.clobber = clobber;
    }

    public boolean isSunder() {
        return sunder;
    }

    public void setSunder(boolean sunder) {
        this.sunder = sunder;
    }

    public boolean isBackstab() {
        return backstab;
    }

    public void setBackstab(boolean backstab) {
        this.backstab = backstab;
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

    public int getBlackStoneArrowStacks() {
        return blackStoneArrowStacks;
    }

    public void setBlackStoneArrowStacks(int blackStoneArrowStacks) {
        this.blackStoneArrowStacks = blackStoneArrowStacks;
    }

    public int getLordOfBonesStacks() {
        return lordOfBonesStacks;
    }

    public void setLordOfBonesStacks(int lordOfBonesStacks) {
        this.lordOfBonesStacks = lordOfBonesStacks;
    }

    public boolean isCroesusSpored() {
        return croesusSpored;
    }

    public void setCroesusSpored(boolean croesusSpored) {
        this.croesusSpored = croesusSpored;
    }

    public boolean isDinosaur() {
        return dinosaur;
    }

    public void setDinosaur(boolean dinosaur) {
        this.dinosaur = dinosaur;
    }
}


