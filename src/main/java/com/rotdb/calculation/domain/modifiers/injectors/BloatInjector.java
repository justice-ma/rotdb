package com.rotdb.calculation.domain.modifiers.injectors;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;

import java.util.List;

public class BloatInjector implements Modifier {
    public void apply(AggregatedCalculationContext aggregatedCalculationContext) {
        CalculationContext context = aggregatedCalculationContext.getSnapshotContext();

        if (context.getAbility().getId() != AbilityId.BLOAT) {
            return;
        }

        List<AbilityHitsContext> hits = context.getAbility().getHits();
        AbilityHitsContext base = context.getAbility().getHits().getFirst();

        int minCritDamage = (int) (base.getCritMin() * 0.25);
        int critDamage = (int) (base.getCritDamage() * 0.25);
        int maxCritDamage = (int) (base.getCritMax() * 0.25);

        int minNonCritDamage = (int) (base.getNonCritMin() * 0.25);
        int nonCritDamage = (int) (base.getNonCritDamage() * 0.25);
        int maxNonCritDamage = (int) (base.getNonCritMax() * 0.25);

        int minDamage = (int) (base.getCurrentMin() * 0.25);
        int damage = (int) (base.getCurrentDamage() * 0.25);
        int maxDamage = (int) (base.getCurrentMax() * 0.25);

        for (int i = 0; i < 10; i++) {
            AbilityHitsContext bleed = new AbilityHitsContext();
            bleed.setHitTiming(hits.get(i).getHitTiming() + 4);
            bleed.setDot(true);
            bleed.setTier(AbilityTier.BASIC);
            bleed.setParentIndex(i);

            bleed.setCritMin(minCritDamage);
            bleed.setCritDamage(critDamage);
            bleed.setCritMax(maxCritDamage);

            bleed.setNonCritMin(minNonCritDamage);
            bleed.setNonCritDamage(nonCritDamage);
            bleed.setNonCritMax(maxNonCritDamage);

            bleed.setCurrentMin(minDamage);
            bleed.setCurrentDamage(damage);
            bleed.setCurrentMax(maxDamage);

            hits.add(bleed);
        }
    }
}
