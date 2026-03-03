package com.rotdb.modifiers.abilityDamage;

import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.modifiers.Modifier;

public class HitCapModifier implements Modifier {
    public void apply(CalculationContext context) {
        int hits = context.getAbility().getHits().size();
        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);
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
