package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.Debug;
import com.rotdb.calculation.domain.resolvers.abilityDamage.preMultiplicative.LightOfSaradominDamageBonusResolver;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;

public class LightOfSaradominDamageModifier implements Modifier {
    @Override
    public void apply(CalculationContext context) {
        Debug.stageHeader(context, "Light of Saradomin Damage Modifier");
        int hits = context.getAbility().getHits().size();

        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);
            applyFlatAdd(hit, LightOfSaradominDamageBonusResolver.resolve(context, hit));
            Debug.stageRow(context, i, hit);
        }

        Debug.stageFooter(context);
    }

    private void applyFlatAdd(AbilityHitsContext hit, int add) {
        hit.setCurrentMin(hit.getCurrentMin() + add);
        hit.setCurrentMax(hit.getCurrentMax() + add);
        hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);
    }
}
