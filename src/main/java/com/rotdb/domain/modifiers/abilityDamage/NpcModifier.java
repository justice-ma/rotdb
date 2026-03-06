package com.rotdb.domain.modifiers.abilityDamage;

import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.resolvers.Debug;
import com.rotdb.domain.resolvers.abilityDamage.npc.*;
import com.rotdb.domain.modifiers.Modifier;

public class NpcModifier implements Modifier {
    public void apply(CalculationContext context) {

        int hits = context.getAbility().getHits().size();
        Debug.stageHeader(context, "On NPC Modifier");

        for (int i = 0; i < hits; i++) {
            double mod = 1;
            AbilityHitsContext hit = context.getAbility().getHits().get(i);
            HauntedBonus b = HauntedBonusResolver.resolve(context, hit);
            mod *= BuffMultiplierResolver.resolve(context, hit);
            mod *= PerkMultiplierResolver.resolve(context);
            mod *= ScrimshawMultiplierResolver.resolve(context);
            mod *= TargetStatusMultiplierResolver.resolve(context);
            if (!b.isZero()) applyHauntedBonus(hit, b);
            mod *= PostHauntedMultiplierResolver.resolve(context);
            hit.calculateDamages(mod);
            Debug.stageRow(context, i, hit);
        }
        Debug.stageFooter(context);
    }

    private void applyHauntedBonus(AbilityHitsContext hit, HauntedBonus b) {
        hit.setCritMin(hit.getCritMin() + b.getMinCrit());
        hit.setCritMax(hit.getCritMax() + b.getMaxCrit());
        hit.setCritDamage((hit.getCritMin() + hit.getCritMax()) / 2);

        hit.setNonCritMin(hit.getNonCritMin() + b.getMinNonCrit());
        hit.setNonCritMax(hit.getNonCritMax() + b.getMaxNonCrit());
        hit.setNonCritDamage((hit.getNonCritMin() + hit.getNonCritMax()) / 2);

        hit.setCurrentMin(hit.getCurrentMin() + b.getMinAvg());
        hit.setCurrentMax(hit.getCurrentMax() + b.getMaxAvg());
        hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);
    }
}
