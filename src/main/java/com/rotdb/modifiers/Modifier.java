package com.rotdb.modifiers;

import com.rotdb.model.context.CalculationContext;

public interface Modifier {
    void apply(CalculationContext context);
}