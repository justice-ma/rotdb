package com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike;

import com.rotdb.calculation.domain.model.ForcedCritResult;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.*;

public class CritAggregator {
    public static void apply(CalculationContext context) {
        int hits = context.getAbility().getHits().size();

        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);

            if (context.getPerks().has(Perks.EQUILIBRIUM)
                    && context.getPerks().rank(Perks.EQUILIBRIUM) > 0) {
                hit.setCritChanceModifier(0);
                continue;
            }

            if (hit.getTier() == AbilityTier.CONJURE || hit.getType() == HitType.POISON) {
                hit.setCritChanceModifier(0);
                continue;
            }

            double hitCritChance = context.getEffectiveStatsResult().getGlobalCritChance();
            double hitCritDamage = context.getEffectiveStatsResult().getGlobalCritDamage();

            CritBonus perHit = PerHitCritAdjustResolver.resolve(context, hit, i);

            hitCritChance += perHit.getChanceDelta();
            hitCritDamage += perHit.getDamageDelta();

            ForcedCritResult forcedCrits = ForcedCritResolver.resolve(context, hit);
            if (forcedCrits.isForcedCrit()) {
                if (forcedCrits.getSource() == ForceCritSource.GREATER_FURY) {
                    hit.setForcedCrit(true);
                    context.getBuffs().getBuffSet().remove(BuffId.GREATERFURYBUFF);
                } else if (forcedCrits.getSource() == ForceCritSource.TENDRILS) {
                    hit.setForcedCrit(true);
                }
            }

            hit.setCritChanceModifier(hitCritChance);
            hit.setCritDamageModifier(hitCritDamage);

            CritRange range = CritDamageRangeResolver.resolve(context, hit);
            hit.setCritDamages(range.getMinMod(), range.getMaxMod());
        }
    }
}
