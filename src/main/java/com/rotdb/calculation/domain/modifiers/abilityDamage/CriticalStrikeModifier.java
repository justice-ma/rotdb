package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike.CritAggregator;

public class CriticalStrikeModifier implements Modifier {
    public void apply(CalculationContext context) {
        CritAggregator.apply(context);
    }
}
