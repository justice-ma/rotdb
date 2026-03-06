package com.rotdb.domain.modifiers;

import com.rotdb.domain.model.context.CalculationContext;

public interface Modifier {
    void apply(CalculationContext context);
}