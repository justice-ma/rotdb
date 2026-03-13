package com.rotdb.persistence.entity;

import com.rotdb.domain.model.enums.CombatStyles;
import com.rotdb.domain.model.enums.TargetTags;
import com.rotdb.persistence.converter.TargetTagsConverter;
import jakarta.persistence.*;

import java.util.EnumSet;

@Entity
@Table(name="targets")
public class TargetEntity {
    @Id
    private Long id;
    private String title;
    private String name;
    private Integer size;
    private Integer lifepoints1;
    private Integer lifepoints2;
    private Integer armour1;
    private Integer armour2;
    private Integer defence1;
    private Integer defence2;

    @Column(name="aff_melee")
    private Integer affMelee;

    @Column(name="aff_ranged")
    private Integer affRanged;

    @Column(name="aff_magic")
    private Integer affMagic;

    @Column(name="aff_weakness")
    private Integer affWeakness;

    private String weakness;

    private String susceptibility;

    @Enumerated(EnumType.STRING)
    @Column(name="primarystyle")
    private CombatStyles primaryStyle;

    @Column(name="immune_to_poison")
    private Boolean immuneToPoison;

    @Column(name="immune_to_deflect")
    private Boolean immuneToDeflect;

    @Column(name="immune_to_stun")
    private Boolean immuneToStun;

    @Column(name="immune_to_drain")
    private Boolean immuneToDrain;

    @Column(name="raw_infobox", columnDefinition = "jsonb")
    private String rawInfobox;

    @Convert(converter = TargetTagsConverter.class)
    @Column(name="tags")
    private EnumSet<TargetTags> tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getLifepoints1() {
        return lifepoints1;
    }

    public void setLifepoints1(Integer lifepoints1) {
        this.lifepoints1 = lifepoints1;
    }

    public Integer getLifepoints2() {
        return lifepoints2;
    }

    public void setLifepoints2(Integer lifepoints2) {
        this.lifepoints2 = lifepoints2;
    }

    public Integer getArmour1() {
        return armour1;
    }

    public void setArmour1(Integer armour1) {
        this.armour1 = armour1;
    }

    public Integer getArmour2() {
        return armour2;
    }

    public void setArmour2(Integer armour2) {
        this.armour2 = armour2;
    }

    public Integer getDefence1() {
        return defence1;
    }

    public void setDefence1(Integer defence1) {
        this.defence1 = defence1;
    }

    public Integer getDefence2() {
        return defence2;
    }

    public void setDefence2(Integer defence2) {
        this.defence2 = defence2;
    }

    public Integer getAffMelee() {
        return affMelee;
    }

    public void setAffMelee(Integer affMelee) {
        this.affMelee = affMelee;
    }

    public Integer getAffRanged() {
        return affRanged;
    }

    public void setAffRanged(Integer affRanged) {
        this.affRanged = affRanged;
    }

    public Integer getAffMagic() {
        return affMagic;
    }

    public void setAffMagic(Integer affMagic) {
        this.affMagic = affMagic;
    }

    public Integer getAffWeakness() {
        return affWeakness;
    }

    public void setAffWeakness(Integer affWeakness) {
        this.affWeakness = affWeakness;
    }

    public String getWeakness() {
        return weakness;
    }

    public void setWeakness(String weakness) {
        this.weakness = weakness;
    }

    public String getSusceptibility() {
        return susceptibility;
    }

    public void setSusceptibility(String susceptibility) {
        this.susceptibility = susceptibility;
    }

    public CombatStyles getPrimaryStyle() {
        return primaryStyle;
    }

    public void setPrimaryStyle(CombatStyles primaryStyle) {
        this.primaryStyle = primaryStyle;
    }

    public Boolean getImmuneToPoison() {
        return immuneToPoison;
    }

    public void setImmuneToPoison(Boolean immuneToPoison) {
        this.immuneToPoison = immuneToPoison;
    }

    public Boolean getImmuneToDeflect() {
        return immuneToDeflect;
    }

    public void setImmuneToDeflect(Boolean immuneToDeflect) {
        this.immuneToDeflect = immuneToDeflect;
    }

    public Boolean getImmuneToStun() {
        return immuneToStun;
    }

    public void setImmuneToStun(Boolean immuneToStun) {
        this.immuneToStun = immuneToStun;
    }

    public Boolean getImmuneToDrain() {
        return immuneToDrain;
    }

    public void setImmuneToDrain(Boolean immuneToDrain) {
        this.immuneToDrain = immuneToDrain;
    }

    public String getRawInfobox() {
        return rawInfobox;
    }

    public void setRawInfobox(String rawInfobox) {
        this.rawInfobox = rawInfobox;
    }

    public EnumSet<TargetTags> getTags() {
        return tags;
    }

    public void setTags(EnumSet<TargetTags> tags) {
        this.tags = tags;
    }
}
