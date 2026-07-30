package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.resolvers.Debug;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.abilityDamage.abilityRange.AbilityRangeBonus;
import com.rotdb.calculation.domain.resolvers.abilityDamage.abilityRange.AbilityRangeBonusResolver;

public class AbilityRangeModifier implements Modifier {
    public void apply(AggregatedCalculationContext aggregatedCalculationContext) {
        CalculationContext snapshotContext = aggregatedCalculationContext.getSnapshotContext();
        CalculationContext liveContext = aggregatedCalculationContext.getLiveOrSnapshotContext();
        if (snapshotContext.debug) Debug.stageHeader(snapshotContext, "Ability Range Modifier");
        AbilityRangeBonus bonus = AbilityRangeBonusResolver.resolve(snapshotContext, liveContext);
        var hits = snapshotContext.getAbility().getHits();

        for (int i = 0; i < hits.size(); i++) {
            AbilityHitsContext hit = hits.get(i);

            if (bonus.getMinDelta() != 0 || bonus.getMaxDelta() != 0) {
                hit.setMin(hit.getMin() + bonus.getMinDelta());
                hit.setMax(hit.getMax() + bonus.getMaxDelta());
                hit.setNeedsRangeRecalc(true);
            }

            if (!hit.isRangeCalculated() || hit.isNeedsRangeRecalc()) {
                hit.setCurrentMin((int) (snapshotContext.getDamage().getBaseDamage() * hit.getMin()));
                hit.setCurrentMax((int) (snapshotContext.getDamage().getBaseDamage() * hit.getMax()));
                hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);

                hit.setRangeCalculated(true);
                hit.setNeedsRangeRecalc(false);
            }

            if (snapshotContext.debug) Debug.stageRow(snapshotContext, i, hit);
        }
        if (snapshotContext.debug) Debug.stageFooter(snapshotContext);
    }
}
