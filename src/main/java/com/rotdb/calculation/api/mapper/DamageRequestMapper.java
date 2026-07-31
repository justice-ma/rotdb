package com.rotdb.calculation.api.mapper;

import com.rotdb.calculation.api.dto.DamageCalcRequestDto;
import com.rotdb.calculation.application.mapper.*;
import com.rotdb.calculation.domain.model.DamageRequest;
import com.rotdb.shared.combat.domain.model.context.TargetContext;
import com.rotdb.shared.combat.domain.model.enums.HitCapMode;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.FamiliarContext;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.*;
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
    private final FamiliarContextMapper familiarContextMapper;
    private final PerkContextMapper perkContextMapper;
    private final HitCapModeMapper hitCapModeMapper;

    public DamageRequestMapper(EquipmentContextMapper equipmentContextMapper, SkillsContextMapper skillsContextMapper,
                               BuffContextMapper buffContextMapper, TargetContextMapper targetContextMapper,
                               PotionContextMapper potionContextMapper, SpellContextMapper spellContextMapper,
                               PrayerContextMapper prayerContextMapper, FamiliarContextMapper familiarContextMapper,
                               PerkContextMapper perkContextMapper, HitCapModeMapper hitCapModeMapper) {
        this.equipmentContextMapper = equipmentContextMapper;
        this.skillsContextMapper = skillsContextMapper;
        this.buffContextMapper = buffContextMapper;
        this.targetContextMapper = targetContextMapper;
        this.potionContextMapper = potionContextMapper;
        this.spellContextMapper = spellContextMapper;
        this.prayerContextMapper = prayerContextMapper;
        this.familiarContextMapper = familiarContextMapper;
        this.perkContextMapper = perkContextMapper;
        this.hitCapModeMapper = hitCapModeMapper;
    }

    public DamageRequest from(DamageCalcRequestDto request) {
        EquipmentModel equipment = equipmentContextMapper.from(request.equipment());
        SkillsContext skills = skillsContextMapper.from(request.skills());
        BuffContext buffs = buffContextMapper.from(request.buffs());
        List<PotionContext> potion = potionContextMapper.from(request.potions());
        SpellContext spell = spellContextMapper.from(request.spell());
        PrayerContext prayer = prayerContextMapper.from(request.selectedPrayers());
        FamiliarContext familiar = familiarContextMapper.from(request.selectedFamiliar());
        PerkContext perks = perkContextMapper.from(request.perks());
        HitCapMode hitCapMode = hitCapModeMapper.from(request.hitCapMode());

        DamageRequest dr = new DamageRequest();
        dr.setEquipment(equipment);
        dr.setAbilityId(request.abilityId());
        dr.setSkills(skills);
        dr.setBuffs(buffs);
        dr.setPotion(potion);
        dr.setHitCapMode(hitCapMode);

        TargetContext target = targetContextMapper.from(
                request.targetTitle(),
                request.targetCurrentHp(),
                request.targetMaxHp(),
                request.targetSize(),
                equipment);
        dr.setTarget(target);
        dr.setSpell(spell);
        dr.setSelectedPrayers(prayer);
        dr.setFamiliar(familiar);
        dr.setPerks(perks);
        return dr;
    }
}
