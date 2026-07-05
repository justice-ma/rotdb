package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.Debug;
import com.rotdb.calculation.domain.resolvers.abilityDamage.abilitySpecific.AbilitySpecificResolver;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;

public class AbilitySpecificModifier implements Modifier {
    public void apply(CalculationContext context) {
        int hits = context.getAbility().getHits().size();

        if (context.debug) Debug.stageHeader(context, "Ability Specific Modifier");
        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);

            double mult = AbilitySpecificResolver.resolve(context, hit, hit.getHitIndex() == -1 ? i : hit.getHitIndex());

            if (mult != 1.0) {
                hit.calculateDamages(mult);
            }

            if (context.debug) Debug.stageRow(context, hit.getHitIndex() == -1 ? i : hit.getHitIndex(), hit);
        }
        if (context.debug) Debug.stageFooter(context);
    }
}
