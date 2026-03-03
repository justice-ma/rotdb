package com.rotdb.modifiers.abilityDamage;

import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.HitType;
import com.rotdb.resolvers.Debug;
import com.rotdb.modifiers.Modifier;

public class AggregationModifier implements Modifier {
    @Override
    public void apply(CalculationContext context) {

        int min = 0;
        int max = 0;
        int avg = 0;

        int critMin = 0;
        int critMax = 0;
        int critAvg = 0;

        int nonCritMin = 0;
        int nonCritMax = 0;
        int nonCritAvg = 0;

        double minCoeff = 0;
        double maxCoeff = 0;

        int hits = context.getAbility().getHits().size();

        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);

            if (hit.getType() == HitType.BASE || hit.getType() == HitType.SPLITSOUL || hit.getType() == HitType.PERFECTEQUILIBRIUM) {
                min += hit.getCurrentMin();
                max += hit.getCurrentMax();
                avg += hit.getCurrentDamage();

                critMin += hit.getCritMin();
                critMax += hit.getCritMax();
                critAvg += hit.getCritDamage();

                nonCritMin += hit.getNonCritMin();
                nonCritMax += hit.getNonCritMax();
                nonCritAvg += hit.getNonCritDamage();

                if (hit.getType() == HitType.BASE) {
                    minCoeff += hit.getMin();
                    maxCoeff += hit.getMax();
                }

            } else if (hit.getType() == HitType.INSTABILITY) {
                double w = hit.getCritChanceModifier();
                avg += (int) Math.floor(hit.getCurrentDamage() * w);
                max += hit.getCritMax();
                critMin += hit.getNonCritMin();
                critMax += hit.getCritMax();
                critAvg += (hit.getNonCritMin() + hit.getCritMax()) / 2;
            }
        }

        // ===== SET FINAL TOTALS ONCE =====
        context.getDamage().setCurrentMin(min);
        context.getDamage().setCurrentMax(max);
        context.getDamage().setCurrentDamage(avg);

        context.getDamage().setCritMin(critMin);
        context.getDamage().setCritMax(critMax);
        context.getDamage().setCritDamage(critAvg);

        context.getDamage().setNonCritMin(nonCritMin);
        context.getDamage().setNonCritMax(nonCritMax);
        context.getDamage().setNonCritDamage(nonCritAvg);

        context.getDamage().setMinPercent(minCoeff);
        context.getDamage().setMaxPercent(maxCoeff);

        Debug.finalDamageReport(context);
    }
}