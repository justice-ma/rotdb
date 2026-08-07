package com.rotdb.calculation.domain.model;

import com.rotdb.calculation.persistence.mapper.EquipmentMapper;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.FamiliarContext;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;

public class EffectiveStatsRequest {
    private BuffContext buffContext;
    private SkillsContext skillsContext;
    private EquipmentModel equipmentModel;
    private FamiliarContext familiarContext;
    private PerkContext perkContext;

    public EffectiveStatsRequest() {}

    public EffectiveStatsRequest (BuffContext buffContext, SkillsContext skillsContext, EquipmentModel equipmentModel,
                                  FamiliarContext familiarContext, PerkContext perkContext) {
        this.buffContext = buffContext;
        this.skillsContext = skillsContext;
        this.equipmentModel = equipmentModel;
        this.familiarContext = familiarContext;
        this.perkContext = perkContext;
    }

    public BuffContext getBuffContext() {
        return buffContext;
    }

    public void setBuffContext(BuffContext buffContext) {
        this.buffContext = buffContext;
    }

    public SkillsContext getSkillsContext() {
        return skillsContext;
    }

    public void setSkillsContext(SkillsContext skillsContext) {
        this.skillsContext = skillsContext;
    }

    public EquipmentModel getEquipmentModel() {
        return equipmentModel;
    }

    public void setEquipmentModel(EquipmentModel equipmentModel) {
        this.equipmentModel = equipmentModel;
    }

    public FamiliarContext getFamiliarContext() {
        return familiarContext;
    }

    public void setFamiliarContext(FamiliarContext familiarContext) {
        this.familiarContext = familiarContext;
    }

    public PerkContext getPerkContext() {
        return perkContext;
    }

    public void setPerkContext(PerkContext perkContext) {
        this.perkContext = perkContext;
    }
}
