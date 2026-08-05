package com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.HitType;
import com.rotdb.shared.combat.domain.model.enums.Perks;

public class CritAggregator {
    public static void apply(CalculationContext context) {
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

            if (context.getPerks().has(Perks.EQUILIBRIUM)
                    && context.getPerks().rank(Perks.EQUILIBRIUM) > 0) {
                hit.setCritChanceModifier(0);
                continue;
            }

            if (hit.getTier() == AbilityTier.CONJURE || hit.getType() == HitType.POISON) {
                hit.setCritChanceModifier(0);
                continue;
            }

            double hitCritChance = globalChance;
            double hitCritDamage = globalDamage;

            if ((context.getAbility().getId() == AbilityId.INFERNO_OF_ZAMORAK_MAGIC ||
                    context.getAbility().getId() == AbilityId.INFERNO_OF_ZAMORAK_MELEE ||
                    context.getAbility().getId() == AbilityId.INFERNO_OF_ZAMORAK_RANGED ||
                    context.getAbility().getId() == AbilityId.INFERNO_OF_ZAMORAK_NECROMANCY ||
                    hit.getType() == HitType.INFERNO_OF_ZAMORAK) && context.getBuffs().has(BuffId.UNHOLY_CRITUAL)) {
                hitCritDamage += 0.5;
            }

            if (context.getBuffs().has(BuffId.TRUE_EQUILIBRIUM)) {
                hitCritChance += 0.05 * context.getBuffs().getBlessingsPerAlignment();
                hitCritDamage += 0.075 * context.getBuffs().getBlessingsPerAlignment();
            }

            CritBonus perHit = PerHitCritAdjustResolver.resolve(context, hit, i);

            hitCritChance += perHit.getChanceDelta();
            hitCritDamage += perHit.getDamageDelta();

            if (context.getAbility().getId() == AbilityId.SMOKETENDRILS || context.getAbility().getId() == AbilityId.SHADOWTENDRILS) {
                hitCritChance = 1;
            }

            if (!context.getBuffs().has(BuffId.UNHOLY_CRITUAL)) {
                hitCritChance = Math.max(0, Math.min(hitCritChance, 1));
            } else {
                hitCritChance += 0.15;
                double excess = Math.max(0, hitCritChance - 0.5);
                hitCritChance = Math.max(0, Math.min(hitCritChance, 0.5));
                hitCritDamage += excess;
            }

            hit.setCritChanceModifier(hitCritChance);
            hit.setCritDamageModifier(hitCritDamage);

            CritRange range = CritDamageRangeResolver.resolve(context, hit);
            hit.setCritDamages(range.getMinMod(), range.getMaxMod());
        }
    }
}
