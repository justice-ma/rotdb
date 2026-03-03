package com.rotdb.modifiers.abilityDamage;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.modifiers.Modifier;
import com.rotdb.resolvers.abilityDamage.criticalStrike.CritAggregator;

public class CriticalStrikeModifier implements Modifier {
    public void apply(CalculationContext context) {
        CritAggregator.apply(context);
    }
}
