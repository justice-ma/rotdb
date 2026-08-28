package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.Debug;
import com.rotdb.calculation.domain.resolvers.abilityDamage.npc.*;
import com.rotdb.shared.combat.domain.model.enums.HitType;

import java.util.List;

public class NpcModifier implements Modifier {
    public void apply(AggregatedCalculationContext aggregatedCalculationContext) {
        CalculationContext context = aggregatedCalculationContext.getSnapshotContext();

        int hits = context.getAbility().getHits().size();
        Debug.stageHeader(context, "On NPC Modifier");

        for (int i = 0; i < hits; i++) {
            double mod = 1;
            AbilityHitsContext hit = context.getAbility().getHits().get(i);

            if (hit.getType() != HitType.SPLITSOUL && hit.getType() != HitType.POISON) {
                HauntedBonus hauntedBonus = HauntedBonusResolver.resolve(context, hit);
                mod *= BuffMultiplierResolver.resolve(context, hit);
                mod *= PerkMultiplierResolver.resolve(context);
                mod *= ScrimshawMultiplierResolver.resolve(context);
                mod *= TargetStatusMultiplierResolver.resolve(context);
                mod *= SigilMultiplierResolver.resolve(context);

                hit.calculateDamages(mod);

                if (!hauntedBonus.isZero()) applyHauntedBonus(hit, hauntedBonus);

                double postStoredMod = 1;
                postStoredMod *= PostHauntedMultiplierResolver.resolve(context);
                postStoredMod *= AbilityMultiplierResolver.resolve(context, hit);

                int flatAdd = FlatAddResolver.resolve(context, hit);
                if (flatAdd != 0) applyFlatAdd(hit, flatAdd);

                List<Integer> minMax = FlatRangeAddResolver.resolve(context, hit);
                applyFlatRangeAdd(hit, minMax.getFirst(), minMax.get(1));
                hit.calculateDamages(postStoredMod);
            }

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

    private void applyFlatAdd(AbilityHitsContext hit, int add) {
        hit.setCurrentMin(hit.getCurrentMin() + add);
        hit.setCurrentMax(hit.getCurrentMax() + add);
        hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);

        hit.setCritMin(hit.getCritMin() + add);
        hit.setCritMax(hit.getCritMax() + add);
        hit.setCritDamage((hit.getCritMin() + hit.getCritMax()) / 2);

        hit.setNonCritMin(hit.getNonCritMin() + add);
        hit.setNonCritMax(hit.getNonCritMax() + add);
        hit.setNonCritDamage((hit.getNonCritMin() + hit.getNonCritMax()) / 2);
    }

    private void applyFlatRangeAdd(AbilityHitsContext hit, int minAdd, int maxAdd) {
        hit.setCurrentMin(hit.getCurrentMin() + minAdd);
        hit.setCurrentMax(hit.getCurrentMax() + maxAdd);
        hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);

        hit.setCritMin(hit.getCritMin() + minAdd);
        hit.setCritMax(hit.getCritMax() + maxAdd);
        hit.setCritDamage((hit.getCritMin() + hit.getCritMax()) / 2);

        hit.setNonCritMin(hit.getNonCritMin() + minAdd);
        hit.setNonCritMax(hit.getNonCritMax() + maxAdd);
        hit.setNonCritDamage((hit.getNonCritMin() + hit.getNonCritMax()) / 2);
    }
}
