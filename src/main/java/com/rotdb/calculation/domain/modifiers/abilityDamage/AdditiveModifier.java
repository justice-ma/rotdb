package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.Debug;
import com.rotdb.calculation.domain.resolvers.abilityDamage.additive.AdditiveResolver;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.HitType;

public class AdditiveModifier implements Modifier {
    public void apply(AggregatedCalculationContext aggregatedCalculationContext) {
        CalculationContext context = aggregatedCalculationContext.getSnapshotContext();

        int hits = context.getAbility().getHits().size();
        if (context.debug) Debug.stageHeader(context, "Additive Modifier");
        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);
            if (hit.getType() == HitType.POISON) continue;
            if (!hit.isDot()) {
                double mod = AdditiveResolver.resolve(context, hit.getHitIndex() == -1 ? i : hit.getHitIndex());
                hit.calculateDamages(mod);
            }
            if (context.debug) Debug.stageRow(context, hit.getHitIndex() == -1 ? i : hit.getHitIndex(), hit);
        }
        if (context.debug) Debug.stageFooter(context);
    }
}
