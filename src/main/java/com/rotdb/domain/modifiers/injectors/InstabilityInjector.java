package com.rotdb.domain.modifiers.injectors;

import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.AbilityTier;
import com.rotdb.domain.model.enums.BuffId;
import com.rotdb.domain.model.enums.HitType;
import com.rotdb.domain.modifiers.Modifier;

import java.util.List;
import static com.rotdb.domain.model.enums.CombatStyles.MAGIC;

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

            AbilityHitsContext proc = new AbilityHitsContext(
                    0.9, 1.1,
                    false,
                    AbilityTier.BASIC,
                    parent.getHitTiming() + 1,
                    HitType.INSTABILITY,
                    i
            );

            hits.add(i + 1, proc);
            baseCount++;
            i++;
        }
    }
    private boolean parentCanCrit(CalculationContext context, AbilityHitsContext parent) {
        if (parent.isDot()) return false;
        return !context.getAbility().getName().equalsIgnoreCase("Magma Tempest");
    }
}
