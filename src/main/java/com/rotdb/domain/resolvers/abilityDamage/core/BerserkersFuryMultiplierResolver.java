package com.rotdb.domain.resolvers.abilityDamage.core;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.BuffId;
import com.rotdb.domain.model.player.BuffContext;

public class BerserkersFuryMultiplierResolver {
    public static double resolve(CalculationContext context) {
        BuffContext buffs = context.getBuffs();
        if (buffs.has(BuffId.BERSERKERSFURY)) {
            class Threshold {
                final double percentIncrease, lower, upper;

                Threshold(double percentIncrease, double lower, double upper) {
                    this.percentIncrease = percentIncrease;
                    this.lower = lower;
                    this.upper = upper;
                }

                boolean matches(double percent) {
                    return percent >= lower && percent < upper;
                }
            }
            Threshold[] thresholds = new Threshold[]{
                    new Threshold(0.0, 1.0, Double.POSITIVE_INFINITY),
                    new Threshold(0.5, 0.91, 1.0),
                    new Threshold(1.0, 0.81, 0.91),
                    new Threshold(1.5, 0.71, 0.81),
                    new Threshold(2.0, 0.61, 0.71),
                    new Threshold(2.5, 0.51, 0.61),
                    new Threshold(3.0, 0.41, 0.51),
                    new Threshold(3.5, 0.31, 0.41),
                    new Threshold(4.0, 0.21, 0.31),
                    new Threshold(4.5, 0.11, 0.21),
                    new Threshold(5.0, 0.01, 0.11),
                    new Threshold(5.5, 0.0, 0.01)
            };
            double currentLp = context.getSkills().getCurrentHp();
            double maxLp = context.getSkills().getMaxHp();
            double percent = maxLp == 0 ? 1.0 : currentLp / maxLp;
            double percentIncrease = 0.0;
            for (Threshold th : thresholds) {
                if (th.matches(percent)) {
                    percentIncrease = th.percentIncrease;
                    break;
                }
            }
            return 1.0 + (percentIncrease / 100.0);
        }
        return 1;
    }
}
