package com.rotdb.calculation.api.mapper;

import com.rotdb.calculation.api.dto.EffectiveStatsRequestDto;
import com.rotdb.calculation.application.mapper.*;
import com.rotdb.calculation.domain.model.EffectiveStatsRequest;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.FamiliarContext;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;
import org.springframework.stereotype.Component;

@Component
public final class EffectiveStatsMapper {
    private final SkillsContextMapper skillsContextMapper;
    private final BuffContextMapper buffContextMapper;
    private final EquipmentContextMapper equipmentContextMapper;
    private final FamiliarContextMapper familiarContextMapper;
    private final PerkContextMapper perkContextMapper;

    public EffectiveStatsMapper(SkillsContextMapper skillsContextMapper, BuffContextMapper buffContextMapper,
                                EquipmentContextMapper equipmentContextMapper, FamiliarContextMapper familiarContextMapper, PerkContextMapper perkContextMapper) {
        this.skillsContextMapper = skillsContextMapper;
        this.buffContextMapper = buffContextMapper;
        this.equipmentContextMapper = equipmentContextMapper;
        this.familiarContextMapper = familiarContextMapper;
        this.perkContextMapper = perkContextMapper;
    }

    public EffectiveStatsRequest from(EffectiveStatsRequestDto request) {
        BuffContext buffContext = buffContextMapper.from(request.buffs());
        SkillsContext skillsContext = skillsContextMapper.from(request.skills(), buffContext);
        EquipmentModel equipmentModel = equipmentContextMapper.from(request.equipmentIds());
        FamiliarContext familiarContext = familiarContextMapper.from(request.familiar());
        PerkContext perkContext = perkContextMapper.from(request.perks(), buffContext);

        EffectiveStatsRequest effectiveStatsRequest = new EffectiveStatsRequest(
                buffContext,
                skillsContext,
                equipmentModel,
                familiarContext,
                perkContext
        );

        return effectiveStatsRequest;
    }
}
