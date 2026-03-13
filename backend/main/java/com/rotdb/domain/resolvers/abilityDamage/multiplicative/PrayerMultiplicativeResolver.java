package com.rotdb.domain.resolvers.abilityDamage.multiplicative;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.player.PrayerContext;

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
