package com.rotdb.domain.modifiers.abilityDamage;

import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.resolvers.Debug;
import com.rotdb.domain.modifiers.Modifier;
import com.rotdb.domain.resolvers.abilityDamage.multiplicative.MultiplicativeResolver;

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
