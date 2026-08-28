package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.Debug;
import com.rotdb.calculation.domain.resolvers.abilityDamage.npc.FlatAddResolver;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;

public class FlatHitDamageModifier implements Modifier {
    public void apply(AggregatedCalculationContext aggregatedCalculationContext) {
        CalculationContext liveContext = aggregatedCalculationContext.getLiveOrSnapshotContext();
        CalculationContext snapshotContext = aggregatedCalculationContext.getSnapshotContext();

        int hits = snapshotContext.getAbility().getHits().size();
        Debug.stageHeader(liveContext, "Flat Add Modifier");

        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = snapshotContext.getAbility().getHits().get(i);
            hit.setCurrentMin(hit.getCurrentMin() + FlatAddResolver.resolve(liveContext, hit));
            hit.setCurrentMax(hit.getCurrentMax() + FlatAddResolver.resolve(liveContext, hit));
            hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);
        }
        Debug.stageFooter(snapshotContext);
    }
}
