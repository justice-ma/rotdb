package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.calculation.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.HitType;
import com.rotdb.calculation.domain.resolvers.Debug;
import com.rotdb.calculation.domain.modifiers.Modifier;

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
                    minCoeff += hit.getMin() * 100 + 1E-9;
                    maxCoeff += hit.getMax() * 100 + 1E-9;
                }

            } else if (hit.getType() == HitType.INSTABILITY) {
                double w = hit.getCritChanceModifier();
                avg += (int) Math.floor(hit.getCurrentDamage() * w);
                max += hit.getCritMax();
                critMin += hit.getNonCritMin();
                critMax += hit.getCritMax();
                critAvg += (hit.getNonCritMin() + hit.getCritMax()) / 2;

                hit.setCurrentMin((int) (hit.getCurrentMin() * w));
                hit.setCurrentMax((int) (hit.getCurrentMax() * w));
                hit.setCurrentDamage((int) (hit.getCurrentDamage() * w));

                hit.setNonCritMin((int) (hit.getNonCritMin() * w));
                hit.setNonCritMax((int) (hit.getNonCritMax() * w));
                hit.setNonCritDamage((int) (hit.getNonCritDamage() * w));
            }
        }

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