package com.rotdb.domain.resolvers.abilityDamage.criticalStrike;

import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.BuffId;
import com.rotdb.domain.model.enums.HitType;
import com.rotdb.domain.model.enums.Perks;
import com.rotdb.domain.model.player.BuffContext;

public class CritAggregator {
    public static void apply(CalculationContext context) {
        double baseChance = 0.1;
        double baseDamage = BaseCritResolver.resolve(context);

        BuffContext buffs = context.getBuffs();

        CritBonus setBonus = SetEffectsCritResolver.resolve(context);
        CritBonus gearBonus = GearCritResolver.resolve(context);

        double globalChance = baseChance
                + setBonus.getChanceDelta()
                + gearBonus.getChanceDelta()
                + BuffCritResolver.resolve(context)
                + PerkCritResolver.resolve(context);

        double globalDamage = baseDamage
                + setBonus.getDamageDelta()
                + gearBonus.getDamageDelta()
                + TargetCritResolver.resolve(context);

        int hits = context.getAbility().getHits().size();

        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);

            if (context.getPerks().has(Perks.EQUILIBRIUM)
                    && context.getPerks().rank(Perks.EQUILIBRIUM) > 0) {
                hit.setCritChanceModifier(0);
                continue;
            }

            double hitCritChance = globalChance;
            double hitCritDamage = globalDamage;

            CritBonus perHit = PerHitCritAdjustResolver.resolve(context, hit, i);
            hitCritChance += perHit.getChanceDelta();
            hitCritDamage += perHit.getDamageDelta();

            hitCritChance = Math.max(0, Math.min(hitCritChance, 1));

            hit.setCritChanceModifier(hitCritChance);
            hit.setCritDamageModifier(hitCritDamage);

            CritRange range = CritDamageRangeResolver.resolve(context, hit);
            hit.setCritDamages(range.getMinMod(), range.getMaxMod());
        }
    }
}
