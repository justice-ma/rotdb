package com.rotdb.domain.model.player;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.CombatStyles;

import static com.rotdb.domain.model.enums.CombatStyles.*;

public class SkillsContext {
    private Integer boostedNecromancy, constitution, boostedStrength, boostedRanged, boostedMagic, boostedAttack,
            boostedDefence, summoning, currentHp, maxHp, baseNecromancy, baseStrength, baseRanged, baseMagic,
            baseAttack, baseDefence;


    public Integer getRevelvantStregthLevel(CalculationContext context) {
        CombatStyles style = context.getEquipment().getCombatStyle();
        return style == MAGIC ? boostedMagic : style == RANGED ? boostedRanged : style == MELEE ? boostedStrength : boostedNecromancy;
    }
    
    public void fillMissingWithOne() {
        if (getBaseStrength() == null) setBaseStrength(1);
        if (getBaseAttack() == null) setBaseAttack(1);
        if (getBaseDefence() == null) setBaseDefence(1);
        if (getBaseMagic() == null) setBaseMagic(1);
        if (getBaseNecromancy() == null) setBaseNecromancy(1);
        if (getBaseRanged() == null) setBaseRanged(1);
        if (getCurrentHp() == null) setCurrentHp(1000);
        if (getMaxHp() == null) setMaxHp(1000);
    }

    public void correctBoundaries() {
        if (getBaseStrength() > 120) setBaseStrength(120);
        if (getBaseStrength() < 1) setBaseStrength(1);
        if (getBaseAttack() > 120) setBaseAttack(120);
        if (getBaseAttack() < 1) setBaseAttack(1);
        if (getBaseDefence() > 120) setBaseDefence(120);
        if (getBaseDefence() < 1) setBaseDefence(1);
        if (getBaseMagic() > 120) setBaseMagic(120);
        if (getBaseMagic() < 1) setBaseMagic(1);
        if (getBaseNecromancy() > 120) setBaseNecromancy(120);
        if (getBaseNecromancy() < 1) setBaseNecromancy(1);
        if (getBaseRanged() > 120) setBaseRanged(120);
        if (getBaseRanged() < 1) setBaseRanged(1);
        if (getMaxHp() > 32000) setMaxHp(32000);
        if (getMaxHp() < 1) setMaxHp(1);
        if (getCurrentHp() > 32000) setCurrentHp(32000);
        if (getCurrentHp() < 1) setCurrentHp(1);
    }

    public Integer getSummoning() {
        return summoning;
    }

    public void setSummoning(Integer summoning) {
        this.summoning = summoning;
    }

    public Integer getBoostedDefence() {
        return boostedDefence;
    }

    public void setBoostedDefence(Integer boostedDefence) {
        this.boostedDefence = boostedDefence;
    }

    public Integer getBoostedAttack() {
        return boostedAttack;
    }

    public void setBoostedAttack(Integer boostedAttack) {
        this.boostedAttack = boostedAttack;
    }

    public Integer getBoostedMagic() {
        return boostedMagic;
    }

    public void setBoostedMagic(Integer boostedMagic) {
        this.boostedMagic = boostedMagic;
    }

    public Integer getBoostedRanged() {
        return boostedRanged;
    }

    public void setBoostedRanged(Integer boostedRanged) {
        this.boostedRanged = boostedRanged;
    }

    public Integer getBoostedStrength() {
        return boostedStrength;
    }

    public void setBoostedStrength(Integer boostedStrength) {
        this.boostedStrength = boostedStrength;
    }

    public Integer getConstitution() {
        return constitution;
    }

    public void setConstitution(Integer constitution) {
        this.constitution = constitution;
    }

    public Integer getBoostedNecromancy() {
        return boostedNecromancy;
    }

    public void setBoostedNecromancy(Integer boostedNecromancy) {
        this.boostedNecromancy = boostedNecromancy;
    }

    public Integer getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(Integer currentHp) {
        this.currentHp = currentHp;
    }

    public Integer getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(Integer maxHp) {
        this.maxHp = maxHp;
    }

    public Integer getBaseNecromancy() {
        return baseNecromancy;
    }

    public void setBaseNecromancy(Integer baseNecromancy) {
        this.baseNecromancy = baseNecromancy;
    }

    public Integer getBaseStrength() {
        return baseStrength;
    }

    public void setBaseStrength(Integer baseStrength) {
        this.baseStrength = baseStrength;
    }

    public Integer getBaseRanged() {
        return baseRanged;
    }

    public void setBaseRanged(Integer baseRanged) {
        this.baseRanged = baseRanged;
    }

    public Integer getBaseMagic() {
        return baseMagic;
    }

    public void setBaseMagic(Integer baseMagic) {
        this.baseMagic = baseMagic;
    }

    public Integer getBaseAttack() {
        return baseAttack;
    }

    public void setBaseAttack(Integer baseAttack) {
        this.baseAttack = baseAttack;
    }

    public Integer getBaseDefence() {
        return baseDefence;
    }

    public void setBaseDefence(Integer baseDefence) {
        this.baseDefence = baseDefence;
    }
}
