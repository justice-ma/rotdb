package com.rotdb.domain.modifiers.abilityDamage;

import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.resolvers.Debug;
import com.rotdb.domain.modifiers.Modifier;
import com.rotdb.domain.resolvers.abilityDamage.core.BerserkersFuryMultiplierResolver;
import com.rotdb.domain.resolvers.abilityDamage.core.CoreFlatAddResolver;
import com.rotdb.domain.resolvers.abilityDamage.core.CorePerkAddResolver;
import com.rotdb.domain.resolvers.abilityDamage.core.CorePreviousAbilityAddResolver;

public class CoreModifier implements Modifier {
    public void apply(CalculationContext context) {

        int hits = context.getAbility().getHits().size();
        Debug.stageHeader(context, "Core Modifier");

        int add = CoreFlatAddResolver.resolve(context) +
                CorePerkAddResolver.resolve(context) +
                CorePreviousAbilityAddResolver.resolve(context);
        double bf = BerserkersFuryMultiplierResolver.resolve(context);

        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);
            if (hit.isDot()) continue;
            hit.setCurrentMin(hit.getCurrentMin() + add);
            hit.setCurrentMax(hit.getCurrentMax() + add);
            hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);
            hit.calculateDamages(bf);

            // Store damages for BolG
            hit.setBolgDamages(hit.getCurrentDamage(), hit.getCurrentMax(), hit.getCurrentMin());
            Debug.stageRow(context, i, hit);
        }
        Debug.stageFooter(context);
    }
}
