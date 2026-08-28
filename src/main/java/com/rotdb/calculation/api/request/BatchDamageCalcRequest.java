package com.rotdb.calculation.api.request;

import com.rotdb.calculation.api.dto.DamageCalcRequestDto;
import com.rotdb.shared.ability.AbilityId;

import java.util.List;

public record BatchDamageCalcRequest(
        DamageCalcRequestDto base,
        List<AbilityId> abilityIds
) {
}