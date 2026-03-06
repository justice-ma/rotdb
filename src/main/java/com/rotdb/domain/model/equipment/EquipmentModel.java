package com.rotdb.domain.model.equipment;

import com.rotdb.domain.model.enums.CombatStyles;

public class EquipmentModel {
    private EquipmentSlot head, body, legs, boots, gloves, cape, pocket, neck, ring, ammo, mainhand, offhand;
    private CombatStyles combatStyle;
    private int abilityDamage;

    public EquipmentModel() {}

    public void fillMissingWithEmpty() {
        if (mainhand == null) setMainhand(EquipmentSlot.emptySlot());
        if (offhand == null) setOffhand(EquipmentSlot.emptySlot());
        if (head == null) setHead(EquipmentSlot.emptySlot());
        if (body == null) setBody(EquipmentSlot.emptySlot());
        if (legs == null) setLegs(EquipmentSlot.emptySlot());
        if (boots == null) setBoots(EquipmentSlot.emptySlot());
        if (gloves == null) setGloves(EquipmentSlot.emptySlot());
        if (cape == null) setCape(EquipmentSlot.emptySlot());
        if (neck == null) setNeck(EquipmentSlot.emptySlot());
        if (ring == null) setRing(EquipmentSlot.emptySlot());
        if (pocket == null) setPocket(EquipmentSlot.emptySlot());
        if (ammo == null) setAmmo(EquipmentSlot.emptySlot());
    }

    // Getters and Setters
    public double getTotalStrength() {
        return getHead().getStrength()
                + getBody().getStrength()
                + getLegs().getStrength()
                + getBoots().getStrength()
                + getGloves().getStrength()
                + getCape().getStrength()
                + getPocket().getStrength()
                + getNeck().getStrength()
                + getRing().getStrength()
                + getAmmo().getStrength()
                + getMainhand().getStrength();
    }

    public double getTotalMagic() {
        return getHead().getMagic()
                + getBody().getMagic()
                + getLegs().getMagic()
                + getBoots().getMagic()
                + getGloves().getMagic()
                + getCape().getMagic()
                + getPocket().getMagic()
                + getNeck().getMagic()
                + getRing().getMagic()
                + getAmmo().getMagic()
                + getMainhand().getMagic();
    }

    public double getTotalRanged() {
        return getHead().getRanged()
                + getBody().getRanged()
                + getLegs().getRanged()
                + getBoots().getRanged()
                + getGloves().getRanged()
                + getCape().getRanged()
                + getPocket().getRanged()
                + getNeck().getRanged()
                + getRing().getRanged()
                + getAmmo().getRanged()
                + getMainhand().getRanged();
    }

    public double getTotalNecromancy() {
        return getHead().getNecromancy()
                + getBody().getNecromancy()
                + getLegs().getNecromancy()
                + getBoots().getNecromancy()
                + getGloves().getNecromancy()
                + getCape().getNecromancy()
                + getPocket().getNecromancy()
                + getNeck().getNecromancy()
                + getRing().getNecromancy()
                + getAmmo().getNecromancy()
                + getMainhand().getNecromancy();
    }

    public EquipmentSlot getOffhand() {
        return offhand == null ? EquipmentSlot.emptySlot() : offhand;
    }

    public void setOffhand(EquipmentSlot offhand) {
        this.offhand = offhand;
    }

    public EquipmentSlot getMainhand() {
        return mainhand == null ? EquipmentSlot.emptySlot() : mainhand;
    }

    public void setMainhand(EquipmentSlot mainhand) {
        this.mainhand = mainhand;
    }

    public EquipmentSlot getAmmo() {
        return ammo == null ? EquipmentSlot.emptySlot() : ammo;
    }

    public void setAmmo(EquipmentSlot ammo) {
        this.ammo = ammo;
    }

    public EquipmentSlot getRing() {
        return ring == null ? EquipmentSlot.emptySlot() : ring;
    }

    public void setRing(EquipmentSlot ring) {
        this.ring = ring;
    }

    public EquipmentSlot getNeck() {
        return neck == null ? EquipmentSlot.emptySlot() : neck;
    }

    public void setNeck(EquipmentSlot neck) {
        this.neck = neck;
    }

    public EquipmentSlot getPocket() {
        return pocket == null ? EquipmentSlot.emptySlot() : pocket;
    }

    public void setPocket(EquipmentSlot pocket) {
        this.pocket = pocket;
    }

    public EquipmentSlot getCape() {
        return cape == null ? EquipmentSlot.emptySlot() : cape;
    }

    public void setCape(EquipmentSlot cape) {
        this.cape = cape;
    }

    public EquipmentSlot getGloves() {
        return gloves == null ? EquipmentSlot.emptySlot() : gloves;
    }

    public void setGloves(EquipmentSlot gloves) {
        this.gloves = gloves;
    }

    public EquipmentSlot getBoots() {
        return boots == null ? EquipmentSlot.emptySlot() : boots;
    }

    public void setBoots(EquipmentSlot boots) {
        this.boots = boots;
    }

    public EquipmentSlot getLegs() {
        return legs == null ? EquipmentSlot.emptySlot() : legs;
    }

    public void setLegs(EquipmentSlot legs) {
        this.legs = legs;
    }

    public EquipmentSlot getBody() {
        return body == null ? EquipmentSlot.emptySlot() : body;
    }

    public void setBody(EquipmentSlot body) {
        this.body = body;
    }

    public EquipmentSlot getHead() {
        return head == null ? EquipmentSlot.emptySlot() : head;
    }

    public void setHead(EquipmentSlot head) {
        this.head = head;
    }

    public CombatStyles getCombatStyle() {
        return combatStyle;
    }

    public void setCombatStyle(CombatStyles combatStyle) {
        this.combatStyle = combatStyle;
    }

    public int getAbilityDamage() {
        return abilityDamage;
    }

    public void setAbilityDamage(int abilityDamage) {
        this.abilityDamage = abilityDamage;
    }
}
