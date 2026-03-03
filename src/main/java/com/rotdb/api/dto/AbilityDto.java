package com.rotdb.api.dto;

import com.rotdb.ability.AbilityId;
import com.rotdb.model.enums.AbilityTier;

public record AbilityDto(
        AbilityId ability,
        String name,
        AbilityTier tier
) {}
