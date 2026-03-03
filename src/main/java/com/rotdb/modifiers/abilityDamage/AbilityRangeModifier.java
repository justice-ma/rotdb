package com.rotdb.modifiers.abilityDamage;

import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.resolvers.Debug;
import com.rotdb.modifiers.Modifier;
import com.rotdb.resolvers.abilityDamage.abilityRange.AbilityRangeBonus;
import com.rotdb.resolvers.abilityDamage.abilityRange.AbilityRangeBonusResolver;

public class AbilityRangeModifier implements Modifier {
    public void apply(CalculationContext context) {
        if (context.debug) Debug.stageHeader(context, "Ability Range Modifier");
        AbilityRangeBonus bonus = AbilityRangeBonusResolver.resolve(context);
        var hits = context.getAbility().getHits();

        for (int i = 0; i < hits.size(); i++) {
            AbilityHitsContext hit = hits.get(i);

            if (bonus.getMinDelta() != 0 || bonus.getMaxDelta() != 0) {
                hit.setMin(hit.getMin() + bonus.getMinDelta());
                hit.setMax(hit.getMax() + bonus.getMaxDelta());
                hit.setNeedsRangeRecalc(true);
            }

            if (!hit.isRangeCalculated() || hit.isNeedsRangeRecalc()) {
                hit.setCurrentMin((int) (context.getDamage().getBaseDamage() * hit.getMin()));
                hit.setCurrentMax((int) (context.getDamage().getBaseDamage() * hit.getMax()));
                hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);

                hit.setRangeCalculated(true);
                hit.setNeedsRangeRecalc(false);
            }

            if (context.debug) Debug.stageRow(context, i, hit);
        }
        if (context.debug) Debug.stageFooter(context);
    }
}
