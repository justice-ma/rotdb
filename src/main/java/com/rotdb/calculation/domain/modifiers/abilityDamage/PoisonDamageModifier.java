package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.abilityDamage.poisonDamage.PoisonDamageResolver;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;

public class PoisonDamageModifier implements Modifier {
    public void apply(AggregatedCalculationContext aggregatedCalculationContext) {
        CalculationContext context = aggregatedCalculationContext.getLiveOrSnapshotContext();

        int hits = context.getAbility().getHits().size();
        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);
            applyFlatAdd(hit, PoisonDamageResolver.resolve(hit, context));
        }
    }

    private void applyFlatAdd(AbilityHitsContext hit, double mod) {
        hit.setCurrentMin((int) (hit.getCurrentMin() * mod));
        hit.setCurrentMax((int) (hit.getCurrentMax() * mod));
        hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);

        hit.setCritMin((int) (hit.getCritMin() * mod));
        hit.setCritMax((int) (hit.getCritMax() * mod));
        hit.setCritDamage((hit.getCritMin() + hit.getCritMax()) / 2);

        hit.setNonCritMin((int) (hit.getNonCritMin() * mod));
        hit.setNonCritMax((int) (hit.getNonCritMax() * mod));
        hit.setNonCritDamage((hit.getNonCritMin() + hit.getNonCritMax()) / 2);
    }
}
