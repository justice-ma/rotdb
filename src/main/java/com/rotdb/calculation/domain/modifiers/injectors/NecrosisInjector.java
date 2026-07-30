package com.rotdb.calculation.domain.modifiers.injectors;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;

import java.util.List;

public class NecrosisInjector implements Modifier {
    public void apply(AggregatedCalculationContext aggregatedCalculationContext) {
        CalculationContext context = aggregatedCalculationContext.getSnapshotContext();

        if (context.getAbility().getId() == AbilityId.DEATHGRASP && context.getBuffs().has(BuffId.NECROSIS)) {
            List<AbilityHitsContext> hit = context.getAbility().getHits();
            double min = hit.getFirst().getMin() + context.getBuffs().stacks(BuffId.NECROSIS) * 0.4;
            double max = hit.getFirst().getMax() + context.getBuffs().stacks(BuffId.NECROSIS) * 0.4;
            hit.getFirst().setMin(min);
            hit.getFirst().setMax(max);
        }
    }
}
