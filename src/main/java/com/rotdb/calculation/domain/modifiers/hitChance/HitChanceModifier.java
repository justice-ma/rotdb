package com.rotdb.calculation.domain.modifiers.hitChance;

import com.rotdb.calculation.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.hitChance.HitChanceResolver;

public class HitChanceModifier implements Modifier {
    public void apply(CalculationContext context) {
        double hitChance = HitChanceResolver.resolve(context);
        context.setHitChance(hitChance);
        int hits = context.getAbility().getHits().size();
        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);
            hit.calculateDamages(hitChance);
        }
    }
}
