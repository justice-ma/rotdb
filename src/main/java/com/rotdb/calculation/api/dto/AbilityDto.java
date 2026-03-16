package com.rotdb.calculation.api.dto;

import com.rotdb.calculation.ability.AbilityId;
import com.rotdb.calculation.domain.model.enums.AbilityTier;

public record AbilityDto(
        AbilityId ability,
        String name,
        AbilityTier tier,
        String iconPath,
        boolean common
) {}
