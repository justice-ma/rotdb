package com.rotdb.api.request;

import com.rotdb.ability.AbilityId;
import com.rotdb.api.dto.DamageCalcRequest;

import java.util.List;

public record BatchDamageCalcRequest(
        DamageCalcRequest base,
        List<AbilityId> abilityIds
) {}