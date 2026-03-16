package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.calculation.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.resolvers.Debug;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.abilityDamage.additive.AdditiveResolver;

public class AdditiveModifier implements Modifier {
    public void apply(CalculationContext context) {

        int hits = context.getAbility().getHits().size();
        if (context.debug) Debug.stageHeader(context, "Additive Modifier");
        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);

            if (!hit.isDot()) {
                double mod = AdditiveResolver.resolve(context, i);
                hit.calculateDamages(mod);
            }
            if (context.debug) Debug.stageRow(context, i, hit);
        }
        if (context.debug) Debug.stageFooter(context);
    }
}
