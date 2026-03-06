package com.rotdb.application.validation;


import com.rotdb.domain.model.DamageRequest;

import java.util.Objects;

public final class DamageRequestValidator {
    public void validate(DamageRequest request) {
        Objects.requireNonNull(request, "request");

        if (request.getEquipment() == null) throw new IllegalArgumentException("equipment is required");
        if (request.getAbilityId() == null) throw new IllegalArgumentException("abilityId is required");
        if (request.getSkills() == null) throw new IllegalArgumentException("skills is required");
    }
}
