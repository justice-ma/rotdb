package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.calculation.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.resolvers.Debug;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.abilityDamage.multiplicative.MultiplicativeResolver;

public class MultiplicativeModifier implements Modifier {
    public void apply(CalculationContext context) {
        Debug.stageHeader(context, "Multiplicative Modifier");
        int hits = context.getAbility().getHits().size();
        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);
            if (!hit.isDot()) {
                double mod = MultiplicativeResolver.resolve(context, hit);
                hit.calculateDamages(mod);
            }
            Debug.stageRow(context, i, hit);
        }
        Debug.stageFooter(context);
    }
}
