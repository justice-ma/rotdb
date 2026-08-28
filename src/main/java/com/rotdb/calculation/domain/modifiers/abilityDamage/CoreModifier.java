package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.Debug;
import com.rotdb.calculation.domain.resolvers.abilityDamage.core.*;
import com.rotdb.shared.combat.domain.model.enums.HitType;

public class CoreModifier implements Modifier {
    public void apply(AggregatedCalculationContext aggregatedCalculationContext) {
        CalculationContext context = aggregatedCalculationContext.getSnapshotContext();

        int hits = context.getAbility().getHits().size();
        Debug.stageHeader(context, "Core Modifier");

        int baseAdd = CoreFlatAddResolver.resolve(context) +
                CorePerkAddResolver.resolve(context) +
                CorePreviousAbilityAddResolver.resolve(context);
        double bf = BerserkersFuryMultiplierResolver.resolve(context);

        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);
            if (hit.getType() == HitType.POISON) continue;
            if (hit.isDot()) continue;
            hit.setCurrentMin(hit.getCurrentMin() + baseAdd);
            hit.setCurrentMax(hit.getCurrentMax() + baseAdd);
            hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);
            hit.calculateDamages(bf);

            // Store damages for BolG
            hit.setBolgDamages(hit.getCurrentDamage(), hit.getCurrentMax(), hit.getCurrentMin());
            Debug.stageRow(context, i, hit);
        }
        Debug.stageFooter(context);
    }
}
