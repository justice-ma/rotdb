package com.rotdb.calculation.domain.modifiers.injectors;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.modifiers.abilityDamage.*;
import com.rotdb.calculation.domain.resolvers.Debug;
import com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike.CritDamageRangeResolver;
import com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike.CritRange;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

import static com.rotdb.shared.combat.domain.model.enums.HitType.*;

public class PerfectEquilibriumInjector implements Modifier {

    // Must be EXACTLY the modifiers that produce pre-crit hit damage (currentMin/currentMax)
    // Do NOT include CritModifier, AggregationModifier, or this injector.
    private static final List<Modifier> PRE_CRIT_PIPELINE = List.of(
            new AbilityRangeModifier(),
            new InvisibleAbilityModifier(),
            new AbilitySpecificModifier(),
            new StyleSpecificModifier(),
            new PreciseModifier(),
            new AdditiveModifier(),
            new MultiplicativeModifier(),
            new CoreModifier()
    );

    @Override
    public void apply(AggregatedCalculationContext aggregatedCalculationContext) {
        CalculationContext context = aggregatedCalculationContext.getSnapshotContext();

        if (context.getAbility().getCombatStyle() != CombatStyles.RANGED) return;

        EquipmentSlot mh = context.getEquipment().getMainhand();
        String mhName = (mh == null || mh.getTitle() == null) ? "" : mh.getTitle().toLowerCase();

        if (!mhName.contains("bow of the last guardian")) return;

        AbilityId ability = context.getAbility().getId();
        boolean isCrystalRain = ability == AbilityId.CRYSTALRAIN;

        List<AbilityHitsContext> hits = context.getAbility().getHits();
        int baseCount = hits.size();

        int stacks = context.getBuffs().has(BuffId.PERFECTEQUILIBRIUMSTACKS) ? context.getBuffs().stacks(BuffId.PERFECTEQUILIBRIUMSTACKS) : 0;
        if (context.getBuffs().has(BuffId.BALANCEBYFORCE)) {
            stacks = Math.min(3, stacks);
        }
        int thresh = context.getBuffs().has(BuffId.BALANCEBYFORCE) ? 4 : 8;

        int baseDamage = context.getDamage().getBaseDamage();
        if (baseDamage <= 0) return;

        boolean crystalRainStackConsumed = false;

        for (int i = 0; i < baseCount; i++) {
            AbilityHitsContext parent = hits.get(i);

            // No stacks from DoTs, and never stack/proc off the proc
            if (parent.isDot()) continue;
            if (parent.getType() == PERFECTEQUILIBRIUM) continue;
            if (parent.getType() == INFERNO_OF_ZAMORAK) continue;
            if (parent.getType() == LIGHT_OF_SARADOMIN) continue;
            stacks++;

            if (isCrystalRain) {
                if (crystalRainStackConsumed) break;
                crystalRainStackConsumed = true;
            }

            if (stacks == thresh) {
                stacks = 0;

                int triggerMin = parent.getBolgMin();
                int triggerMax = parent.getBolgMax();

                if (triggerMin <= 0 || triggerMax <= 0) {
                    if (isCrystalRain) break;
                    continue;
                }

                int[] worldMin = runProcPreCrit(aggregatedCalculationContext, baseDamage, triggerMin);

                int[] worldMax = runProcPreCrit(aggregatedCalculationContext, baseDamage, triggerMax);

                int procMin = worldMin[0];
                int procMax = worldMax[1];

                if (procMax < procMin) procMax = procMin;

                int procAvg = (procMin + procMax) / 2;

                AbilityHitsContext proc = new AbilityHitsContext();
                proc.setType(PERFECTEQUILIBRIUM);
                proc.setParentIndex(i);
                proc.setHitTiming(2);
                proc.setDot(false);
                proc.setTier(parent.getTier());

                proc.setCurrentMin(procMin);
                proc.setCurrentMax(procMax);
                proc.setCurrentDamage(procAvg);

                proc.setCritChanceModifier(parent.getCritChanceModifier());
                proc.setCritDamageModifier(parent.getCritDamageModifier());

                CritRange procCritRange = CritDamageRangeResolver.resolve(context, proc);
                proc.setMinCritDamage(procCritRange.getMinMod());
                proc.setMaxCritDamage(procCritRange.getMaxMod());
                proc.setAverageCritDamage((procCritRange.getMinMod() + procCritRange.getMaxMod()) / 2);

                proc.setNonCritMin(procMin);
                proc.setNonCritMax(procMax);
                proc.setNonCritDamage(procAvg);

                proc.setBolgDamages(procAvg, procMax, procMin);

                proc.setRangeCalculated(true);
                proc.setNeedsRangeRecalc(false);

                hits.add(i + 1, proc);

                baseCount++;
                i++;
                if (context.debug) {
                    Debug.stageHeader(context, "Perfect Equilibrium Proc (inject)");
                    Debug.stageRow(context, proc.getParentIndex(), hits.get(proc.getParentIndex()));
                    Debug.stageRow(context, proc.getParentIndex() + 1, proc);
                    Debug.stageFooter(context);
                }
            }
        }
    }

    private int[] runProcPreCrit(AggregatedCalculationContext aggregatedCalculationContext, int baseDamage, int triggerX) {
        CalculationContext context = aggregatedCalculationContext.getSnapshotContext();

        // Build proc multipliers for THIS world (trigger fixed to X)
        double procMinMult = (((double) triggerX / baseDamage) * 0.33) + 0.12;
        double procMaxMult = (((double) triggerX / baseDamage) * 0.37) + 0.16;

        // Make a one-hit temporary ability
        AbilityHitsContext tmpHit = new AbilityHitsContext(procMinMult, procMaxMult, false, null, 0);
        tmpHit.setType(PERFECTEQUILIBRIUM);
        tmpHit.setParentIndex(-1);
        tmpHit.resetComputed();

        AbilityContext originalAbility = context.getAbility();
        AbilityContext tmpAbility = originalAbility.copyWithHits(new ArrayList<>(List.of(tmpHit)));

        try {
            context.setAbility(tmpAbility);

            for (Modifier m : PRE_CRIT_PIPELINE) {
                m.apply(aggregatedCalculationContext);
            }

            // After pre-crit pipeline, these should be populated
            int min = tmpHit.getCurrentMin();
            int max = tmpHit.getCurrentMax();
            return new int[]{min, max};

        } finally {
            context.setAbility(originalAbility);
        }
    }

}
