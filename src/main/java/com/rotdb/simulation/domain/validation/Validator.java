package com.rotdb.simulation.domain.validation;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.simulation.domain.model.context.RotationContext;

public interface Validator {
    boolean validate(RotationContext rotationContext, CalculationContext calculationContext);
}
