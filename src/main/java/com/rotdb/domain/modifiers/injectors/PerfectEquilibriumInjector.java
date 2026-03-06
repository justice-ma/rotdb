package com.rotdb.domain.modifiers.injectors;

import com.rotdb.ability.AbilityId;
import com.rotdb.domain.model.context.AbilityContext;
import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.BuffId;
import com.rotdb.domain.model.enums.CombatStyles;
import com.rotdb.domain.model.equipment.EquipmentSlot;
import com.rotdb.domain.modifiers.abilityDamage.AbilityRangeModifier;
import com.rotdb.domain.modifiers.abilityDamage.AbilitySpecificModifier;
import com.rotdb.domain.modifiers.abilityDamage.AdditiveModifier;
import com.rotdb.domain.modifiers.abilityDamage.CoreModifier;
import com.rotdb.domain.resolvers.Debug;
import com.rotdb.domain.modifiers.Modifier;
import com.rotdb.domain.modifiers.abilityDamage.InvisibleAbilityModifier;
import com.rotdb.domain.modifiers.abilityDamage.MultiplicativeModifier;
import com.rotdb.domain.modifiers.abilityDamage.PreciseModifier;
import com.rotdb.domain.modifiers.abilityDamage.StyleSpecificModifier;

import java.util.ArrayList;
import java.util.List;

import static com.rotdb.domain.model.enums.HitType.PERFECTEQUILIBRIUM;

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
    public void apply(CalculationContext context) {
        if (context.getAbility().getCombatStyle() != CombatStyles.RANGED) return;

        EquipmentSlot mh = context.getEquipment().getMainhand();
        String mhName = (mh == null || mh.getTitle() == null) ? "" : mh.getTitle().toLowerCase();

        if (!mhName.contains("bow of the last guardian")) return;

        AbilityId ability = context.getAbility().getId();
        boolean isCrystalRain = ability == AbilityId.CRYSTALRAIN;

        List<AbilityHitsContext> hits = context.getAbility().getHits();
        int baseCount = hits.size();

        int stacks = context.getBuffs().has(BuffId.PERFECTEQUILIBRIUMSTACKS) ? context.getBuffs().stacks(BuffId.PERFECTEQUILIBRIUMSTACKS) : 0;
        int thresh = context.getBuffs().has(BuffId.BALANCEBYFORCE) ? 4 : 8;

        int baseDamage = context.getDamage().getBaseDamage();
        if (baseDamage <= 0) return;

        boolean crystalRainStackConsumed = false;

        for (int i = 0; i < baseCount; i++) {
            AbilityHitsContext parent = hits.get(i);

            // No stacks from DoTs, and never stack/proc off the proc
            if (parent.isDot()) continue;
            if (parent.getType() == PERFECTEQUILIBRIUM) continue;
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

                PreCritResult worldMin = runProcPreCrit(context, baseDamage, triggerMin);

                PreCritResult worldMax = runProcPreCrit(context, baseDamage, triggerMax);

                int procMin = worldMin.min;
                int procMax = worldMax.max;

                if (procMax < procMin) procMax = procMin;

                int procAvg = (procMin + procMax) / 2;

                AbilityHitsContext proc = new AbilityHitsContext();
                proc.setType(PERFECTEQUILIBRIUM);
                proc.setParentIndex(i);
                proc.setHitTiming(parent.getHitTiming() + 1);
                proc.setDot(false);
                proc.setTier(parent.getTier());

                proc.setCurrentMin(procMin);
                proc.setCurrentMax(procMax);
                proc.setCurrentDamage(procAvg);

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

    private PreCritResult runProcPreCrit(CalculationContext context, int baseDamage, int triggerX) {
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
                m.apply(context);
            }

            // After pre-crit pipeline, these should be populated
            int min = tmpHit.getCurrentMin();
            int max = tmpHit.getCurrentMax();
            int avg = tmpHit.getCurrentDamage();
            return new PreCritResult(min, max, avg);

        } finally {
            context.setAbility(originalAbility);
        }
    }

    private static class PreCritResult {
        final int min;
        final int max;
        final int avg;
        PreCritResult(int min, int max, int avg) {
            this.min = min;
            this.max = max;
            this.avg = avg;
        }
    }
}