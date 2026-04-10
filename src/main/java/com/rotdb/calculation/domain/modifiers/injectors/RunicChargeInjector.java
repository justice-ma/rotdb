package com.rotdb.calculation.domain.modifiers.injectors;

import com.rotdb.calculation.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.shared.ability.AbilityId;

import java.util.List;

public class RunicChargeInjector implements Modifier {
    public void apply(CalculationContext context) {
        if (context.getBuffs().has(BuffId.RUNICCHARGE) && context.getAbility().getId() == AbilityId.DRAGONBREATH) {
            List<AbilityHitsContext> hits = context.getAbility().getHits();
            hits.getFirst().setMin(2.6);
            hits.getFirst().setMax(3.1);
        }
    }
}
