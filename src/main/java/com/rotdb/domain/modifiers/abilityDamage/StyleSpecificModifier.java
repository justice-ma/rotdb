package com.rotdb.domain.modifiers.abilityDamage;

import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.resolvers.Debug;
import com.rotdb.domain.modifiers.Modifier;
import com.rotdb.domain.resolvers.abilityDamage.styleSpecific.RangedStyleEffects;
import com.rotdb.domain.resolvers.abilityDamage.styleSpecific.StyleSpecificAggregator;


public class StyleSpecificModifier implements Modifier {
    public void apply(CalculationContext context) {
        int hits = context.getAbility().getHits().size();
        Debug.stageHeader(context, "Style Specific Modifier");

        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);

            if (!hit.isDot()) {
                double mod = StyleSpecificAggregator.resolve(context);
                hit.calculateDamages(mod);

                RangedStyleEffects.apply(context, hit);
                Debug.stageRow(context, i, hit);
            }
        }
        Debug.stageFooter(context);
    }
}
