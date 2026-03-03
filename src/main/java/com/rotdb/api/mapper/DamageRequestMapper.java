package com.rotdb.api.mapper;

import com.rotdb.api.dto.DamageCalcRequest;
import com.rotdb.domain.DamageRequest;
import com.rotdb.model.context.TargetContext;
import com.rotdb.model.equipment.*;
import com.rotdb.model.player.*;
import com.rotdb.service.mapper.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class DamageRequestMapper {
    private final EquipmentContextMapper equipmentContextMapper;
    private final SkillsContextMapper skillsContextMapper;
    private final BuffContextMapper buffContextMapper;
    private final TargetContextMapper targetContextMapper;
    private final PotionContextMapper potionContextMapper;
    private final SpellContextMapper spellContextMapper;
    private final PrayerContextMapper prayerContextMapper;
    private final RelicsContextMapper relicsContextMapper;
    private final FamiliarContextMapper familiarContextMapper;
    private final PerkContextMapper perkContextMapper;

    public DamageRequestMapper(EquipmentContextMapper equipmentContextMapper, SkillsContextMapper skillsContextMapper,
                               BuffContextMapper buffContextMapper, TargetContextMapper targetContextMapper, PotionContextMapper potionContextMapper, SpellContextMapper spellContextMapper, PrayerContextMapper prayerContextMapper, RelicsContextMapper relicsContextMapper, FamiliarContextMapper familiarContextMapper, PerkContextMapper perkContextMapper) {
        this.equipmentContextMapper = equipmentContextMapper;
        this.skillsContextMapper = skillsContextMapper;
        this.buffContextMapper = buffContextMapper;
        this.targetContextMapper = targetContextMapper;
        this.potionContextMapper = potionContextMapper;
        this.spellContextMapper = spellContextMapper;
        this.prayerContextMapper = prayerContextMapper;
        this.relicsContextMapper = relicsContextMapper;
        this.familiarContextMapper = familiarContextMapper;
        this.perkContextMapper = perkContextMapper;
    }

    public DamageRequest from(DamageCalcRequest request) {
        EquipmentModel equipment = equipmentContextMapper.from(request.equipment());
        SkillsContext skills = skillsContextMapper.from(request.skills());
        BuffContext buffs = buffContextMapper.from(request.buffs());
        List<PotionContext> potion = potionContextMapper.from(request.potions());
        SpellContext spell = spellContextMapper.from(request.spell());
        PrayerContext prayer = prayerContextMapper.from(request.selectedPrayers());
        RelicsContext relics = relicsContextMapper.from(request.berserkersFury());
        FamiliarContext familiar = familiarContextMapper.from(request.familiar());
        PerkContext perks = perkContextMapper.from(request.perks());

        DamageRequest dr = new DamageRequest();
        dr.setEquipment(equipment);
        dr.setAbilityId(request.abilityId());
        dr.setSkills(skills);
        dr.setBuffs(buffs);
        dr.setPotion(potion);

        TargetContext target = targetContextMapper.from(request.targetTitle(), dr.getEquipment());
        dr.setTarget(target);
        dr.setSpell(spell);
        dr.setSelectedPrayers(prayer);
        dr.setRelics(relics);
        dr.setFamiliar(familiar);
        dr.setPerks(perks);
        return dr;
    }
}
