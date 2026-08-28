package com.rotdb.calculation.domain.model.context;

import com.rotdb.calculation.domain.model.EffectiveStatsResult;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.context.TargetContext;
import com.rotdb.shared.combat.domain.model.enums.HitCapMode;
import com.rotdb.shared.combat.domain.model.enums.Prayer;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;
import com.rotdb.shared.combat.domain.model.equipment.FamiliarContext;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.PrayerContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;
import com.rotdb.shared.combat.domain.model.player.SpellContext;

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
    private PerkContext perks;
    private FamiliarContext familiar;
    private PrayerContext prayer;
    private EnumSet<Prayer> selectedPrayers;
    private SpellContext spellContext;
    private boolean zealotsEquipped;
    private boolean perfectEquilibriumSecondPass;
    private double hitChance;
    private HitCapMode hitCapMode;
    private EffectiveStatsResult effectiveStatsResult;

    public boolean debug = false;
    public boolean finalPrint = false;

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

    public HitCapMode getHitCapMode() {
        return hitCapMode;
    }

    public void setHitCapMode(HitCapMode hitCapMode) {
        this.hitCapMode = hitCapMode;
    }

    public EffectiveStatsResult getEffectiveStatsResult() {
        return effectiveStatsResult;
    }

    public void setEffectiveStatsResult(EffectiveStatsResult effectiveStatsResult) {
        this.effectiveStatsResult = effectiveStatsResult;
    }
}
