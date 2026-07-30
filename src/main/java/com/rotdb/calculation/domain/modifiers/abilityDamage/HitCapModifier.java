package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;

public class HitCapModifier implements Modifier {
    public void apply(AggregatedCalculationContext aggregatedCalculationContext) {
        CalculationContext context = aggregatedCalculationContext.getSnapshotContext();

        int hits = context.getAbility().getHits().size();
        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);
            if (hit.getNonCritMin() > hit.getNonCritMax()) {
                hit.setCurrentMin(hit.getCurrentMax() - 1);
                hit.setNonCritMin(hit.getNonCritMax() - 1);
                hit.setCritMin(hit.getCritMax() - 1);

                hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);
                hit.setCritDamage((hit.getCritMin() + hit.getCritMax()) / 2);
                hit.setNonCritDamage((hit.getNonCritMin() + hit.getNonCritMax()) / 2);
            }
            hit.setCritMin(Math.min(30_000, hit.getCritMin()));
            hit.setCritMax(Math.min(30_000, hit.getCritMax()));
            hit.setCritDamage(Math.min(30_000, hit.getCritDamage()));
            hit.setNonCritMin(Math.min(30_000, hit.getNonCritMin()));
            hit.setNonCritMax(Math.min(30_000, hit.getNonCritMax()));
            hit.setNonCritDamage(Math.min(30_000, hit.getNonCritDamage()));
            hit.setCurrentDamage(Math.min(30_000, hit.getCurrentDamage()));
            hit.setCurrentMin(Math.min(30_000, hit.getCurrentMin()));
            hit.setCurrentMax(Math.min(30_000, hit.getCurrentMax()));
        }
    }
}
