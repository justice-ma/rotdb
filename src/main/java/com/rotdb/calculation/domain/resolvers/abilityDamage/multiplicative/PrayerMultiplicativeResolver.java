package com.rotdb.calculation.domain.resolvers.abilityDamage.multiplicative;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.player.PrayerContext;

public class PrayerMultiplicativeResolver {
    public static double resolve(CalculationContext context) {
        PrayerContext prayer = context.getPrayer();

        double mod = 1;
        if (prayer.getPrayerStrengthBonus() > 0) {
            mod *= 1 + prayer.getPrayerStrengthBonus() / 100.0;
        }
        return mod;
    }
}
