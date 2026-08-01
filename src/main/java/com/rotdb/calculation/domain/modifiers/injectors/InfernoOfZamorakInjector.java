package com.rotdb.calculation.domain.modifiers.injectors;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.HitType;

import java.util.ArrayList;
import java.util.List;

public class InfernoOfZamorakInjector implements Modifier {
    public void apply(CalculationContext context) {
        if (!context.getBuffs().has(BuffId.ABYSSAL_CINDERS)) return;

        List<AbilityHitsContext> hits = context.getAbility().getHits();

        List<AbilityHitsContext> originalHits = new ArrayList<>(hits);
        List<AbilityHitsContext> orderedHits = new ArrayList<>();

        for (int i = 0; i < originalHits.size(); i++) {
            AbilityHitsContext parent = originalHits.get(i);
            orderedHits.add(parent);

            if (parent.isDot()) continue;
            if (parent.getType() != HitType.BASE) continue;
            if (parent.getTier() == AbilityTier.CONJURE) continue;

            AbilityHitsContext proc = new AbilityHitsContext(
                    1.15 / 19, 2.15 / 19,
                    false,
                    AbilityTier.BLESSING,
                    parent.getHitTiming() + 1,
                    HitType.INFERNO_OF_ZAMORAK,
                    i
            );
            orderedHits.add(proc);
        }

        hits.clear();
        hits.addAll(orderedHits);
    }
}
