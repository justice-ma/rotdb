package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.Debug;
import com.rotdb.calculation.domain.resolvers.abilityDamage.preMultiplicative.LightOfSaradominDamageBonusResolver;
import com.rotdb.calculation.domain.resolvers.abilityDamage.preMultiplicative.TearingThornsFlatAddResolver;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;

import java.util.List;

public class BlessingFlatDamageModifier implements Modifier {
    @Override
    public void apply(CalculationContext context) {
        Debug.stageHeader(context, "Light of Saradomin Damage Modifier");
        int hits = context.getAbility().getHits().size();

        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);
            applyFlatAdd(hit, LightOfSaradominDamageBonusResolver.resolve(context, hit));
            List<Integer> tearingThornsRangeAdd = TearingThornsFlatAddResolver.resolve(context);
            applyFlatRangeAdd(hit, tearingThornsRangeAdd.getFirst(), tearingThornsRangeAdd.getLast());
            Debug.stageRow(context, i, hit);
        }

        Debug.stageFooter(context);
    }

    private void applyFlatAdd(AbilityHitsContext hit, int add) {
        hit.setCurrentMin(hit.getCurrentMin() + add);
        hit.setCurrentMax(hit.getCurrentMax() + add);
        hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);
    }

    private void applyFlatRangeAdd(AbilityHitsContext hit, int minAdd, int maxAdd) {
        hit.setCurrentMin(hit.getCurrentMin() + minAdd);
        hit.setCurrentMax(hit.getCurrentMax() + maxAdd);
        hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);
    }
}
