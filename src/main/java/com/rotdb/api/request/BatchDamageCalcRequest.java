package com.rotdb.api.request;

import com.rotdb.ability.AbilityId;
import com.rotdb.api.dto.DamageCalcRequestDto;

import java.util.List;

public record BatchDamageCalcRequest(
        DamageCalcRequestDto base,
        List<AbilityId> abilityIds
) {}