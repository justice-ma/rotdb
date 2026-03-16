package com.rotdb.calculation.domain.modifiers.injectors;

import com.rotdb.calculation.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.AbilityTier;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.model.enums.HitType;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike.CritDamageRangeResolver;

import java.util.List;

import static com.rotdb.calculation.domain.model.enums.CombatStyles.MAGIC;

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

            double procCritChance = parent.getCritChanceModifier();
            // Instability should not inherit conc / gconc crit bonus
            if (context.getBuffs().has(BuffId.CONCENTRATEDBLASTBUFF)) {
                procCritChance -= context.getBuffs().has(BuffId.RUNICCHARGE) ? 0.51 : 0.21;
            } else if (context.getBuffs().has(BuffId.GREATERCONCENTRATEDBLASTBUFF)) {
                procCritChance -= context.getBuffs().has(BuffId.RUNICCHARGE) ? 0.45 : 0.15;
            }

            procCritChance = Math.max(0, Math.min(procCritChance, 1));

            AbilityHitsContext proc = new AbilityHitsContext(
                    0.7, 0.9,
                    false,
                    AbilityTier.BASIC,
                    parent.getHitTiming() + 1,
                    HitType.INSTABILITY,
                    i
            );

            proc.setCritChanceModifier(procCritChance);
            proc.setCritDamageModifier(parent.getCritDamageModifier());
            proc.setMinCritDamage(CritDamageRangeResolver.resolve(context, proc).getMinMod());
            proc.setMaxCritDamage(CritDamageRangeResolver.resolve(context, proc).getMaxMod());
            proc.setAverageCritDamage((proc.getMinCritDamage() + proc.getMaxCritDamage()) / 2);
            hits.add(i + 1, proc);
            baseCount++;
            i++;
        }
    }

    private boolean parentCanCrit(CalculationContext context, AbilityHitsContext parent) {
        if (parent.isDot()) return false;
        if (parent.getCritChanceModifier() == 0) return false;
        return !context.getAbility().getName().equalsIgnoreCase("Magma Tempest");
    }
}