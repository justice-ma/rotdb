package com.rotdb.simulation.domain.model.context;

import com.rotdb.shared.combat.domain.model.context.TargetContext;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.FamiliarContext;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.PrayerContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;
import com.rotdb.shared.combat.domain.model.player.SpellContext;

public class RotationCombatState {
    private EquipmentModel equipment;
    private BuffContext buffs;
    private TargetContext target;
    private SkillsContext skills;
    private PerkContext perk;
    private FamiliarContext familiar;
    private PrayerContext prayer;
    private SpellContext spell;

    public EquipmentModel getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentModel equipment) {
        this.equipment = equipment;
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

    public SkillsContext getSkills() {
        return skills;
    }

    public void setSkills(SkillsContext skills) {
        this.skills = skills;
    }

    public PerkContext getPerk() {
        return perk;
    }

    public void setPerk(PerkContext perk) {
        this.perk = perk;
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

    public SpellContext getSpell() {
        return spell;
    }

    public void setSpell(SpellContext spell) {
        this.spell = spell;
    }
}
