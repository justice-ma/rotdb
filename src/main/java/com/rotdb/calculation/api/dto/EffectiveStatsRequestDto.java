package com.rotdb.calculation.api.dto;

import com.rotdb.shared.combat.domain.model.enums.Familiars;
import com.rotdb.shared.combat.domain.model.enums.Prayer;

import java.util.Set;

public record EffectiveStatsRequestDto(
        DamageCalcRequestDto.Skills skills,
        DamageCalcRequestDto.EquipmentIds equipmentIds,
        DamageCalcRequestDto.BuffIds buffs,
        Familiars familiar,
        DamageCalcRequestDto.PerkSelection perks
) {
}
