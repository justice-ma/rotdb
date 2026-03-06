package com.rotdb.domain.modifiers.abilityDamage;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.modifiers.Modifier;
import com.rotdb.domain.resolvers.abilityDamage.criticalStrike.CritAggregator;

public class CriticalStrikeModifier implements Modifier {
    public void apply(CalculationContext context) {
        CritAggregator.apply(context);
    }
}
