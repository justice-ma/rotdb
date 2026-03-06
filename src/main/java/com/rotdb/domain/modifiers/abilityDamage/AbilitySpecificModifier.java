package com.rotdb.domain.modifiers.abilityDamage;

import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.resolvers.Debug;
import com.rotdb.domain.modifiers.Modifier;
import com.rotdb.domain.resolvers.abilityDamage.abilitySpecific.AbilitySpecificResolver;

public class AbilitySpecificModifier implements Modifier {
    public void apply(CalculationContext context) {
        int hits = context.getAbility().getHits().size();

        if (context.debug) Debug.stageHeader(context, "Ability Specific Modifier");
        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);

            double mult = AbilitySpecificResolver.resolve(context, hit, i);

            if (mult != 1.0) {
                hit.calculateDamages(mult);
            }

            if (context.debug) Debug.stageRow(context, i, hit);
        }
        if (context.debug) Debug.stageFooter(context);
    }
}
