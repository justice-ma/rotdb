package com.rotdb.calculation.domain.modifiers;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;

public interface Modifier {
    void apply(AggregatedCalculationContext context);
}
