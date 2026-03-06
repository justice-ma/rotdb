package com.rotdb.domain.resolvers;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.Prayer;
import com.rotdb.domain.model.player.PrayerContext;

import java.util.EnumSet;
import java.util.List;

public class PrayerResolver {
    private static final EnumSet<Prayer> ZEALOTS = EnumSet.of(
            Prayer.BURSTOFSTRENGTH,
            Prayer.UNSTOPPABLEFORCE,
            Prayer.CHARGE,
            Prayer.DECAY,
            Prayer.SUPERHUMANSTRENGTH,
            Prayer.UNRELENTINGFORCE,
            Prayer.SUPERCHARGE,
            Prayer.HASTENEDDECAY,
            Prayer.ULTIMATESTRENGTH,
            Prayer.OVERPOWERINGFORCE,
            Prayer.OVERCHARGE,
            Prayer.ACCELERATEDDECAY,
            Prayer.LEECHMAGICSTRENGTH,
            Prayer.LEECHMELEESTRENGTH,
            Prayer.LEECHNECROMANCYSTRENGTH,
            Prayer.LEECHRANGEDSTRENGTH,
            Prayer.CLARITYOFTHOUGHT,
            Prayer.SHARPEYE,
            Prayer.MYSTICWILL,
            Prayer.HANDOFJUDGEMENT,
            Prayer.IMPROVEDREFLEXES,
            Prayer.HAWKEYE,
            Prayer.MYSTICLORE,
            Prayer.HANDOFFATE,
            Prayer.INCREDIBLEREFLEXES,
            Prayer.EAGLEEYE,
            Prayer.MYSTICMIGHT,
            Prayer.HANDOFDOOM,
            Prayer.LEECHMELEEATTACK,
            Prayer.LEECHRANGEDATTACK,
            Prayer.LEECHMAGICATTACK,
            Prayer.LEECHNECROMANCYATTACK
    );

    public static PrayerContext resolve(CalculationContext context) {
        PrayerContext out = new PrayerContext();

        var selected = context.getSelectedPrayers();
        if (selected == null || selected.isEmpty()) return out;

        boolean zealotsEquipped = context.isZealotsEquipped();
        var style = context.getEquipment().getMainhand().getClazz();

        List<Prayer> applicable = selected.stream()
                .filter(p -> p.appliesTo(style))
                .toList();

        if (applicable.isEmpty()) return out;

        boolean singleApplicable = applicable.size() == 1;

        for (Prayer prayer : applicable) {
            if (singleApplicable || prayer.isStackableWithDivineRage()) {
                out.addPrayerStrengthBonus(prayer.getPrayerStrengthBonus());
                out.addPrayerAccuracyBonus(prayer.getPrayerAccuracyBonus());
            }

            if (zealotsEquipped && ZEALOTS.contains(prayer)) {
                out.setPrayerStrengthBonus(out.getPrayerStrengthBonus() + 10);
                out.setPrayerAccuracyBonus((int) (out.getPrayerAccuracyBonus() * 1.1));
            }
        }

        return out;
    }
}
