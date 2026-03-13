package com.rotdb.application.mapper;

import com.rotdb.api.dto.DamageCalcRequestDto;
import com.rotdb.domain.model.player.SkillsContext;
import org.springframework.stereotype.Component;

@Component
public class SkillsContextMapper {
    public SkillsContext from(DamageCalcRequestDto.Skills request) {
        SkillsContext skills = new SkillsContext();
        if (request == null) skills.fillMissingWithOne();
        if (request.strength() != null) skills.setBaseStrength(request.strength());
        if (request.necromancy() != null) skills.setBaseNecromancy(request.necromancy());
        if (request.ranged() != null) skills.setBaseRanged(request.ranged());
        if (request.magic() != null) skills.setBaseMagic(request.magic());
        if (request.attack() != null) skills.setBaseAttack(request.attack());
        if (request.currentHp() != null) skills.setCurrentHp(request.currentHp());
        if (request.maxHp() != null) skills.setMaxHp(request.maxHp());

        skills.fillMissingWithOne();
        skills.correctBoundaries();
        return skills;
    }
}
