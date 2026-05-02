package com.rotdb.simulation.domain.validation;

import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.RotationSnapshot;

public interface Validator {
    boolean validate(RotationSnapshot rotationContext, PerkContext perks, EquipmentModel equipmentModel, BuffContext buff);
}
