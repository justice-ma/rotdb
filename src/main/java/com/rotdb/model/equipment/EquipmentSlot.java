package com.rotdb.model.equipment;

import com.rotdb.model.enums.CombatStyles;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.enums.Slots;
import com.rotdb.model.enums.WeaponStyle;

import java.util.EnumSet;

import static com.rotdb.model.enums.CombatStyles.ALL;
import static com.rotdb.model.enums.WeaponStyle.NONE;

public class EquipmentSlot {
    private Long id;
    private int tier, damageTier, accuracyTier, armourTier, attackRange;
    private double strength, ranged, magic, necromancy;
    private String title, requiredSkill, requiredLevel;
    private boolean members;
    private CombatStyles clazz;
    private Slots slot;
    private EnumSet<Effect> effect;
    private WeaponStyle style;

    public EquipmentSlot() {}

    public EquipmentSlot(String title, CombatStyles clazz, Slots slot, int damageTier, double strength, double ranged, double magic, double necromancy, EnumSet<Effect> effect) {
        this.title = title;
        this.clazz = clazz;
        this.slot = slot;
        this.damageTier = damageTier;
        this.strength = strength;
        this.ranged = ranged;
        this.magic = magic;
        this.necromancy = necromancy;
        this.effect = effect;
    }

    public static EquipmentSlot emptySlot() {
        EquipmentSlot slot = new EquipmentSlot();
        slot.setTitle("None");
        slot.setStrength(0);
        slot.setNecromancy(0);
        slot.setMagic(0);
        slot.setRanged(0);
        slot.setDamageTier(0);
        slot.setAccuracyTier(0);
        slot.setRequiredLevel("0");
        slot.setClazz(ALL);
        slot.setStyle(NONE);
        slot.setEffect(EnumSet.noneOf(Effect.class));
        return slot;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public double getNecromancy() {
        return necromancy;
    }

    public void setNecromancy(double necromancy) {
        this.necromancy = necromancy;
    }

    public double getMagic() {
        return magic;
    }

    public void setMagic(double magic) {
        this.magic = magic;
    }

    public double getRanged() {
        return ranged;
    }

    public void setRanged(double ranged) {
        this.ranged = ranged;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public String getRequiredSkill() {
        return requiredSkill;
    }

    public void setRequiredSkill(String requiredSkill) {
        this.requiredSkill = requiredSkill;
    }

    public String getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(String requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public int getArmourTier() {
        return armourTier;
    }

    public void setArmourTier(int armourTier) {
        this.armourTier = armourTier;
    }

    public int getAccuracyTier() {
        return accuracyTier;
    }

    public void setAccuracyTier(int accuracyTier) {
        this.accuracyTier = accuracyTier;
    }

    public int getDamageTier() {
        return damageTier;
    }

    public void setDamageTier(int damageTier) {
        this.damageTier = damageTier;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public Slots getSlot() {
        return slot;
    }

    public void setSlot(Slots slot) {
        this.slot = slot;
    }

    public CombatStyles getClazz() {
        return clazz;
    }

    public void setClazz(CombatStyles clazz) {
        this.clazz = clazz;
    }

    public boolean isMembers() {
        return members;
    }

    public void setMembers(boolean members) {
        this.members = members;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumSet<Effect> getEffect() {
        return effect;
    }

    public void setEffect(EnumSet<Effect> effect) {
        if (effect == null) {
            this.effect = EnumSet.noneOf(Effect.class);
        } else {
            this.effect = effect;
        }
    }

    public WeaponStyle getStyle() {
        return style;
    }

    public void setStyle(WeaponStyle style) {
        this.style = style;
    }
}
