package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.shared.combat.domain.model.enums.HitCapMode;

public class HitCapModifier implements Modifier {
    public void apply(CalculationContext context) {
        int hits = context.getAbility().getHits().size();
        int hitCap = context.getHitCapMode() == HitCapMode.CAP_30000 ? 30_000 :
                context.getHitCapMode() == HitCapMode.CAP_32500 ? 32_500 : 2_147_483_647;
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
            hit.setCritMin(Math.min(hitCap, hit.getCritMin()));
            hit.setCritMax(Math.min(hitCap, hit.getCritMax()));
            hit.setCritDamage(Math.min(hitCap, hit.getCritDamage()));
            hit.setNonCritMin(Math.min(hitCap, hit.getNonCritMin()));
            hit.setNonCritMax(Math.min(hitCap, hit.getNonCritMax()));
            hit.setNonCritDamage(Math.min(hitCap, hit.getNonCritDamage()));
            hit.setCurrentDamage(Math.min(hitCap, hit.getCurrentDamage()));
            hit.setCurrentMin(Math.min(hitCap, hit.getCurrentMin()));
            hit.setCurrentMax(Math.min(hitCap, hit.getCurrentMax()));
        }
    }
}
