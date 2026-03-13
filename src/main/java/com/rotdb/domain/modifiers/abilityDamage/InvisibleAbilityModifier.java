package com.rotdb.domain.modifiers.abilityDamage;

import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.HitType;
import com.rotdb.domain.resolvers.Debug;
import com.rotdb.domain.modifiers.Modifier;
import com.rotdb.domain.resolvers.abilityDamage.invisible.InvisibleAggregator;

public class InvisibleAbilityModifier implements Modifier {
    public void apply(CalculationContext context) {
        int hits = context.getAbility().getHits().size();
        if (context.debug) Debug.stageHeader(context, "Invisible Ability Modifier");

        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);
            if (context.isPerfectEquilibriumSecondPass() && hit.getType() != HitType.PERFECTEQUILIBRIUM) continue;
            if (!context.isPerfectEquilibriumSecondPass() && hit.getType() == HitType.PERFECTEQUILIBRIUM) continue;

            hit.calculateDamages(InvisibleAggregator.resolve(context, hit, i));

            if (context.debug) Debug.stageRow(context, i, hit);
        }
        if (context.debug) Debug.stageFooter(context);
    }
}
