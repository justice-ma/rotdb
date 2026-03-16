package com.rotdb.calculation.application.validation;


import com.rotdb.calculation.domain.model.DamageRequest;
import com.rotdb.calculation.domain.model.enums.Slots;

import java.util.Objects;

public final class DamageRequestValidator {
    public void validate(DamageRequest request) {
        Objects.requireNonNull(request, "request");

        if (request.getEquipment() == null) throw new IllegalArgumentException("equipment is required");
        if (request.getAbilityId() == null) throw new IllegalArgumentException("abilityId is required");
        if (request.getSkills() == null) throw new IllegalArgumentException("skills is required");

        var equipment = request.getEquipment();

        if (equipment.getMainhand() != null &&
                equipment.getMainhand().getSlot() == Slots.TWOHANDED) {

            equipment.setOffhand(null);
        }
    }
}
