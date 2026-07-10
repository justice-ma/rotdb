package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.Debug;
import com.rotdb.calculation.domain.resolvers.abilityDamage.npc.FlatAddResolver;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;

public class FlatHitDamageModifier implements Modifier {
    public void apply(CalculationContext context) {
        int hits = context.getAbility().getHits().size();
        Debug.stageHeader(context, "Flat Add Modifier");

        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);
            hit.setCurrentMin(hit.getCurrentMin() + FlatAddResolver.resolve(context, hit));
            hit.setCurrentMax(hit.getCurrentMax() + FlatAddResolver.resolve(context, hit));
            hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);
        }
        Debug.stageFooter(context);
    }
}
