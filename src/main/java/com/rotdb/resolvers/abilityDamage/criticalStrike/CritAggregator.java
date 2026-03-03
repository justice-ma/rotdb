package com.rotdb.resolvers.abilityDamage.criticalStrike;

import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.context.CalculationContext;

public class CritAggregator {
    public static void apply (CalculationContext context) {
        double baseChance = 0.1;
        double baseDamage = BaseCritResolver.resolve(context);

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

            hit.setCritChanceModifier(globalChance);
            hit.setCritDamageModifier(globalDamage);

            CritBonus perHit = PerHitCritAdjustResolver.resolve(context, hit, i);
            hit.setCritChanceModifier(hit.getCritChanceModifier() + perHit.getChanceDelta());
            hit.setCritDamageModifier(hit.getCritDamageModifier() + perHit.getDamageDelta());

            CritRange range = CritDamageRangeResolver.resolve(context, hit);
            hit.setCritDamages(range.getMinMod(), range.getMaxMod());
        }
    }
}
