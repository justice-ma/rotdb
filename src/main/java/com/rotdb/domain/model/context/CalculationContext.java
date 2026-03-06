package com.rotdb.domain.model.context;

import com.rotdb.domain.model.enums.Prayer;
import com.rotdb.domain.model.equipment.EquipmentModel;
import com.rotdb.domain.model.equipment.EquipmentSlot;
import com.rotdb.domain.model.equipment.FamiliarContext;
import com.rotdb.domain.model.equipment.PerkContext;
import com.rotdb.domain.model.player.*;

import java.util.EnumSet;

public class CalculationContext {
    private DamageContext damage = new DamageContext();
    private EquipmentModel equipment;
    private EquipmentSlot equipmentSlot;
    private AbilityContext ability;
    private AbilityHitsContext abilityHits;
    private SkillsContext skills;
    private BuffContext buffs;
    private TargetContext target;
    private RelicsContext relics;
    private PerkContext perks;
    private FamiliarContext familiar;
    private PrayerContext prayer;
    private EnumSet<Prayer> selectedPrayers;
    private SpellContext spellContext;
    private boolean zealotsEquipped;
    private boolean perfectEquilibriumSecondPass;
    private double hitChance;

    public boolean debug = false;
    public boolean finalPrint = true;

    public EquipmentSlot getEquipmentSlot() {
        return equipmentSlot;
    }

    public void setEquipmentSlot(EquipmentSlot equipmentSlot) {
        this.equipmentSlot = equipmentSlot;
    }

    public EquipmentModel getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentModel equipment) {
        this.equipment = equipment;
    }

    public DamageContext getDamage() {
        return damage;
    }

    public void setDamage(DamageContext damage) {
        this.damage = damage;
    }

    public SkillsContext getSkills() {
        return skills;
    }

    public void setSkills(SkillsContext skills) {
        this.skills = skills;
    }

    public AbilityHitsContext getAbilityHits() {
        return abilityHits;
    }

    public void setAbilityHits(AbilityHitsContext abilityHits) {
        this.abilityHits = abilityHits;
    }

    public AbilityContext getAbility() {
        return ability;
    }

    public void setAbility(AbilityContext ability) {
        this.ability = ability;
    }

    public BuffContext getBuffs() {
        return buffs;
    }

    public void setBuffs(BuffContext buffs) {
        this.buffs = buffs;
    }

    public TargetContext getTarget() {
        return target;
    }

    public void setTarget(TargetContext target) {
        this.target = target;
    }

    public RelicsContext getRelics() {
        return relics;
    }

    public void setRelics(RelicsContext relics) {
        this.relics = relics;
    }

    public PerkContext getPerks() {
        return perks;
    }

    public void setPerks(PerkContext perks) {
        this.perks = perks;
    }

    public FamiliarContext getFamiliar() {
        return familiar;
    }

    public void setFamiliar(FamiliarContext familiar) {
        this.familiar = familiar;
    }

    public PrayerContext getPrayer() {
        return prayer;
    }

    public void setPrayer(PrayerContext prayer) {
        this.prayer = prayer;
    }

    public boolean isPerfectEquilibriumSecondPass() {
        return perfectEquilibriumSecondPass;
    }

    public void setPerfectEquilibriumSecondPass(boolean perfectEquilibriumSecondPass) {
        this.perfectEquilibriumSecondPass = perfectEquilibriumSecondPass;
    }

    public EnumSet<Prayer> getSelectedPrayers() {
        return selectedPrayers;
    }

    public void setSelectedPrayers(EnumSet<Prayer> selectedPrayers) {
        this.selectedPrayers = selectedPrayers;
    }

    public boolean isZealotsEquipped() {
        return zealotsEquipped;
    }

    public void setZealotsEquipped(boolean zealotsEquipped) {
        this.zealotsEquipped = zealotsEquipped;
    }

    public double getHitChance() {
        return hitChance;
    }

    public void setHitChance(double hitChance) {
        this.hitChance = hitChance;
    }

    public SpellContext getSpellContext() {
        return spellContext;
    }

    public void setSpellContext(SpellContext spellContext) {
        this.spellContext = spellContext;
    }
}
