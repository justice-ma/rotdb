package com.rotdb.calculation.domain.modifiers;

import com.rotdb.calculation.domain.model.context.CalculationContext;

public interface Modifier {
    void apply(CalculationContext context);
}