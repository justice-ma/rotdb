package com.rotdb.domain.model;

import com.rotdb.ability.AbilityId;
import com.rotdb.domain.model.context.TargetContext;
import com.rotdb.domain.model.equipment.EquipmentModel;
import com.rotdb.domain.model.equipment.FamiliarContext;
import com.rotdb.domain.model.equipment.PerkContext;
import com.rotdb.domain.model.player.*;

import java.util.List;

public class DamageRequest {
    private EquipmentModel equipment;
    private AbilityId abilityId;
    private BuffContext buffs;
    private TargetContext target;
    private SkillsContext skills;
    private PerkContext perks;
    private FamiliarContext familiar;
    private PrayerContext selectedPrayers;
    private SpellContext spell;
    
    public DamageRequest() {};

    public DamageRequest(EquipmentModel equipment, AbilityId abilityId, BuffContext buffs, TargetContext target,
                         SkillsContext skills, PerkContext perks,
                         FamiliarContext familiar, PrayerContext selectedPrayers, SpellContext spell) {
        this.equipment = equipment;
        this.abilityId = abilityId;
        this.buffs = buffs;
        this.target = target;
        this.skills = skills;
        this.perks = perks;
        this.familiar = familiar;
        this.selectedPrayers = selectedPrayers;
        this.spell = spell;
    }

    public EquipmentModel getEquipment() {
        return equipment;
    }

    public AbilityId getAbilityId() {
        return abilityId;
    }

    public BuffContext getBuffs() {
        return buffs;
    }

    public TargetContext getTarget() {
        return target;
    }

    public SkillsContext getSkills() {
        return skills;
    }

    public PerkContext getPerks() {
        return perks;
    }

    public FamiliarContext getFamiliar() {
        return familiar;
    }

    public PrayerContext getSelectedPrayers() {
        return selectedPrayers;
    }

    public void setEquipment(EquipmentModel equipment) {
        this.equipment = equipment;
    }

    public void setAbilityId(AbilityId abilityId) {
        this.abilityId = abilityId;
    }

    public void setBuffs(BuffContext buffs) {
        this.buffs = buffs;
    }

    public void setTarget(TargetContext target) {
        this.target = target;
    }

    public void setSkills(SkillsContext skills) {
        this.skills = skills;
    }

    public void setPerks(PerkContext perks) {
        this.perks = perks;
    }

    public void setFamiliar(FamiliarContext familiar) {
        this.familiar = familiar;
    }

    public void setSelectedPrayers(PrayerContext selectedPrayers) {
        this.selectedPrayers = selectedPrayers;
    }

    public void setPotion(List<PotionContext> potionContext) {
        buffs.setPotionBuffs(potionContext);
    }

    public SpellContext getSpell() {
        return spell;
    }

    public void setSpell(SpellContext spell) {
        this.spell = spell;
    }
}
