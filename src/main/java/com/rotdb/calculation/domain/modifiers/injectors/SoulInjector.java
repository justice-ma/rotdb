package com.rotdb.calculation.domain.modifiers.injectors;

import com.rotdb.calculation.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.AbilityTier;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.shared.ability.AbilityId;

import java.util.List;

public class SoulInjector implements Modifier {
    public void apply(CalculationContext context) {
        if (context.getAbility().getId() == AbilityId.VOLLEYOFSOULS) {
            List<AbilityHitsContext> hits = context.getAbility().getHits();
            if (context.getBuffs().has(BuffId.SOULSTACKS) && context.getBuffs().stacks(BuffId.SOULSTACKS) >= 2) {
                for (int stack = 2; stack < context.getBuffs().stacks(BuffId.SOULSTACKS); stack++) {
                    hits.add(new AbilityHitsContext(
                        1.35, 1.65, false, AbilityTier.THRESHOLD, 2)
                    );
                }
            }
        }

        if (context.getAbility().getId() == AbilityId.SOULCRUSH) {
            int souls = !context.getBuffs().has(BuffId.SOULSTACKS) ? 0 : context.getBuffs().stacks(BuffId.SOULSTACKS);
            double min = 1.35 + souls * 1.35;
            double max = 1.65 + souls * 1.65;
            List<AbilityHitsContext> hits = context.getAbility().getHits();
            hits.getFirst().setMin(min);
            hits.getFirst().setMax(max);
        }
    }
}
