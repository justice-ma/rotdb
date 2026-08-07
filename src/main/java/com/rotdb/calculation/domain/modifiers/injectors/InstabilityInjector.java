package com.rotdb.calculation.domain.modifiers.injectors;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.enums.*;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike.CritDamageRangeResolver;

import java.util.List;

import static com.rotdb.shared.combat.domain.model.enums.CombatStyles.MAGIC;

public class InstabilityInjector implements Modifier {
    public void apply(CalculationContext context) {
        if (!context.getBuffs().has(BuffId.INSTABILITY)) return;
        if (context.getAbility().getCombatStyle() != MAGIC) return;

        List<AbilityHitsContext> hits = context.getAbility().getHits();
        int baseCount = hits.size();

        for (int i = 0; i < baseCount; i++) {
            AbilityHitsContext parent = hits.get(i);

            if (!parentCanCrit(context, parent)) {
                continue;
            }

            double procCritChance = context.getEffectiveStatsResult().getGlobalCritChance();
            double procCritDamage = context.getEffectiveStatsResult().getGlobalCritDamage();
            double expectedInstabilityProcs = parent.getExpectedOccurences() * (parent.isForcedCrit() ? 1 : parent.getCritChanceModifier());

            procCritChance = Math.max(0, Math.min(procCritChance, 1));
            procCritDamage = Math.max(0, procCritDamage);

            AbilityHitsContext proc = new AbilityHitsContext(
                    0.7, 0.9,
                    false,
                    AbilityTier.BASIC,
                    parent.getHitTiming() + 1,
                    HitType.INSTABILITY,
                    i
            );

            proc.setCritChanceModifier(procCritChance);
            proc.setCritDamageModifier(procCritDamage);
            proc.setMinCritDamage(CritDamageRangeResolver.resolve(context, proc).getMinMod());
            proc.setMaxCritDamage(CritDamageRangeResolver.resolve(context, proc).getMaxMod());
            proc.setAverageCritDamage((proc.getMinCritDamage() + proc.getMaxCritDamage()) / 2);
            proc.setForcedCrit(parent.isForcedCrit());
            proc.setExpectedOccurences(expectedInstabilityProcs);
            hits.add(i + 1, proc);
            baseCount++;
            i++;
        }
    }

    private boolean parentCanCrit(CalculationContext context, AbilityHitsContext parent) {
        if (parent.isForcedCrit() && !context.getPerks().has(Perks.EQUILIBRIUM)) return true;
        if (parent.isDot()) return false;
        if (parent.getCritChanceModifier() == 0) return false;
        return !context.getAbility().getName().equalsIgnoreCase("Magma Tempest");
    }
}
