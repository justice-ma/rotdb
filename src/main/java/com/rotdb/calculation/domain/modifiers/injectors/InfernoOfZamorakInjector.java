package com.rotdb.calculation.domain.modifiers.injectors;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike.CritDamageRangeResolver;
import com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike.CritRange;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.HitType;

import java.util.ArrayList;
import java.util.List;

public class InfernoOfZamorakInjector implements Modifier {
    public void apply(CalculationContext context) {
        boolean hasAbyssalCinders = context.getBuffs().has(BuffId.ABYSSAL_CINDERS);
        boolean hasUnholyCritual = context.getBuffs().has(BuffId.UNHOLY_CRITUAL);

        if (!hasAbyssalCinders && !hasUnholyCritual) return;

        List<AbilityHitsContext> hits = context.getAbility().getHits();

        List<AbilityHitsContext> originalHits = new ArrayList<>(hits);
        List<AbilityHitsContext> orderedHits = new ArrayList<>();

        for (int i = 0; i < originalHits.size(); i++) {
            AbilityHitsContext parent = originalHits.get(i);
            orderedHits.add(parent);

            if (parent.isDot()) continue;
            if (parent.getType() != HitType.BASE) continue;
            if (parent.getTier() == AbilityTier.CONJURE) continue;

            double procCritChance = parent.getCritChanceModifier();
            double procCritDamage = parent.getCritDamageModifier();

            if (hasUnholyCritual && isInfernoOfZamorak(context.getAbility().getId())) {
                procCritDamage -= 0.5;
            }

            double baseProcChance = hasAbyssalCinders ? 1.0 / 20.0 : 0;

            if (context.getBuffs().has(BuffId.PERFIDIOUS)) {
                baseProcChance *= 5;
            }

            double parentCritProcChance = 0;
            double infernoCritProcChance = 0;

            if (hasUnholyCritual) {
                parentCritProcChance = Math.min(0.5, parent.getCritChanceModifier());
                infernoCritProcChance = Math.min(0.5, procCritChance);
                procCritDamage += 0.5;
            }

            double initialProcChance = baseProcChance + parentCritProcChance;
            double recursiveProcChance = baseProcChance + infernoCritProcChance;
            double procChance = initialProcChance / (1 - recursiveProcChance);

            double min = 1.15 * procChance;
            double max = 2.15 * procChance;

            AbilityHitsContext proc = new AbilityHitsContext(
                    min, max,
                    false,
                    AbilityTier.BLESSING,
                    parent.getHitTiming() + 1,
                    HitType.INFERNO_OF_ZAMORAK,
                    i
            );

            proc.setCritChanceModifier(procCritChance);
            proc.setCritDamageModifier(procCritDamage);
            CritRange procCritRange = CritDamageRangeResolver.resolve(context, proc);
            proc.setCritDamages(procCritRange.getMinMod(), procCritRange.getMaxMod());

            orderedHits.add(proc);
        }

        hits.clear();
        hits.addAll(orderedHits);
    }

    private static boolean isInfernoOfZamorak(AbilityId abilityId) {
        return abilityId == AbilityId.INFERNO_OF_ZAMORAK_MAGIC ||
                abilityId == AbilityId.INFERNO_OF_ZAMORAK_MELEE ||
                abilityId == AbilityId.INFERNO_OF_ZAMORAK_RANGED ||
                abilityId == AbilityId.INFERNO_OF_ZAMORAK_NECROMANCY;
    }
}
