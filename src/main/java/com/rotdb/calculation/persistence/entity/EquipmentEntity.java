package com.rotdb.calculation.persistence.entity;

import com.rotdb.calculation.domain.model.enums.CombatStyles;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.enums.Slots;
import com.rotdb.calculation.domain.model.enums.WeaponStyle;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.EnumSet;

@Entity
@Table(name="equipment")
@Access(AccessType.FIELD)
public class EquipmentEntity {
    @Id
    private Long id;

    private String title;
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="class")
    private CombatStyles clazz;

    @Enumerated(EnumType.STRING)
    private Slots slot;

    private Integer tier;

    @Column(name="damagetier")
    private Integer damageTier;

    @Column(name="accuracytier")
    private Integer accuracyTier;

    @Enumerated(EnumType.STRING)
    private WeaponStyle style;

    @Column(name="requirements_raw")
    private String requirementsRaw;

    private Double strength;
    private Double ranged;
    private Double magic;
    private Double necromancy;
    private Double prayer;

    private String type;

    @Column(name="req_strength")
    private Integer reqStrength;

    @Column(name="raw_infobox", columnDefinition = "jsonb")
    private String rawInfobox;

    @Column(name = "effects", columnDefinition = "text[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    private String[] effects;

    @Transient
    public EnumSet<Effect> getEffectSet() {
        EnumSet<Effect> set = EnumSet.noneOf(Effect.class);
        if (effects == null) {
            return set;
        } else {
            for (String s : effects) set.add(Effect.valueOf(s));
            return set;
        }
    }

    public void setEffectSet(EnumSet<Effect> set) {
        this.effects = set.stream().map(Enum::name).toArray(String[]::new);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CombatStyles getClazz() {
        return clazz;
    }

    public void setClazz(CombatStyles clazz) {
        this.clazz = clazz;
    }

    public Slots getSlot() {
        return slot;
    }

    public void setSlot(Slots slot) {
        this.slot = slot;
    }

    public Integer getDamageTier() {
        return damageTier;
    }

    public void setDamageTier(Integer damageTier) {
        this.damageTier = damageTier;
    }

    public Integer getAccuracyTier() {
        return accuracyTier;
    }

    public void setAccuracyTier(Integer accuracyTier) {
        this.accuracyTier = accuracyTier;
    }

    public WeaponStyle getStyle() {
        return style;
    }

    public void setStyle(WeaponStyle style) {
        this.style = style;
    }

    public Double getStrength() {
        return strength;
    }

    public void setStrength(Double strength) {
        this.strength = strength;
    }

    public Double getRanged() {
        return ranged;
    }

    public void setRanged(Double ranged) {
        this.ranged = ranged;
    }

    public Double getMagic() {
        return magic;
    }

    public void setMagic(Double magic) {
        this.magic = magic;
    }

    public Double getNecromancy() {
        return necromancy;
    }

    public void setNecromancy(Double necromancy) {
        this.necromancy = necromancy;
    }

    public String getRequirementsRaw() {
        return requirementsRaw;
    }

    public void setRequirementsRaw(String levelRequirement) {
        this.requirementsRaw = levelRequirement;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTier() {
        return tier;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    public Double getPrayer() {
        return prayer;
    }

    public void setPrayer(Double prayer) {
        this.prayer = prayer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getReqStrength() {
        return reqStrength;
    }

    public void setReqStrength(Integer reqStrength) {
        this.reqStrength = reqStrength;
    }

    public String getRawInfobox() {
        return rawInfobox;
    }

    public void setRawInfobox(String rawInfobox) {
        this.rawInfobox = rawInfobox;
    }

    public String[] getEffects() {
        return effects;
    }

    public void setEffects(String[] effects) {
        this.effects = effects;
    }
}
