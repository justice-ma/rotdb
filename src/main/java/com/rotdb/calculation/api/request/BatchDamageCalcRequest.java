package com.rotdb.calculation.api.request;

import com.rotdb.calculation.ability.AbilityId;
import com.rotdb.calculation.api.dto.DamageCalcRequestDto;

import java.util.List;

public record BatchDamageCalcRequest(
        DamageCalcRequestDto base,
        List<AbilityId> abilityIds
) {}