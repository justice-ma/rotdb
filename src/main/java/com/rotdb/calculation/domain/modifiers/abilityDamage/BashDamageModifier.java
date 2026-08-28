package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.Debug;
import com.rotdb.calculation.domain.resolvers.abilityDamage.preMultiplicative.BashDamageBonusResolver;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;

import java.util.List;

public class BashDamageModifier implements Modifier {
    @Override
    public void apply(AggregatedCalculationContext aggregatedCalculationContext) {
        CalculationContext context = aggregatedCalculationContext.getSnapshotContext();
        Debug.stageHeader(context, "Bash Damage Modifier");
        int hits = context.getAbility().getHits().size();

        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);
            List<Integer> minMax = BashDamageBonusResolver.resolve(context, hit);
            applyRangeAdd(hit, minMax.getFirst(), minMax.get(1));
            Debug.stageRow(context, i, hit);
        }

        Debug.stageFooter(context);
    }

    private void applyRangeAdd(AbilityHitsContext hit, int minAdd, int maxAdd) {
        hit.setCurrentMin(hit.getCurrentMin() + minAdd);
        hit.setCurrentMax(hit.getCurrentMax() + maxAdd);
        hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);
    }
}
