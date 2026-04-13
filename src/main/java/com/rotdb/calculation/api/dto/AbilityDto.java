package com.rotdb.calculation.api.dto;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;

public record AbilityDto(
        AbilityId ability,
        String name,
        AbilityTier tier,
        String iconPath,
        boolean common
) {}
