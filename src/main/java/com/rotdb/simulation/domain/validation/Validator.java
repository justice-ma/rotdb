package com.rotdb.simulation.domain.validation;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.RotationContext;

public interface Validator {
    boolean validate(RotationContext rotationContext, EquipmentModel equipmentModel, BuffContext buff);
}
