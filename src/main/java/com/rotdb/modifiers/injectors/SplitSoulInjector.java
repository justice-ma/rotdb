package com.rotdb.modifiers.injectors;

import com.rotdb.calculator.SoulSplitCalculator;
import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.BuffId;
import com.rotdb.model.enums.CombatStyles;
import com.rotdb.resolvers.Debug;
import com.rotdb.modifiers.Modifier;

import java.util.List;

import static com.rotdb.model.enums.CombatStyles.NECROMANCY;
import static com.rotdb.model.enums.CombatStyles.RANGED;
import static com.rotdb.model.enums.HitType.SPLITSOUL;

public class SplitSoulInjector implements Modifier {
    @Override
    public void apply(CalculationContext context) {
        if (!context.getBuffs().has(BuffId.SPLITSOUL)) return;

        CombatStyles style = context.getAbility().getCombatStyle();
        if (style != RANGED && style != NECROMANCY) return;

        String neck = (context.getEquipment().getNeck() == null)
                ? ""
                : context.getEquipment().getNeck().getTitle().toLowerCase();

        boolean amuletOfSouls = neck.contains("amulet of souls") || neck.contains("essence of finality");

        SoulSplitCalculator calc = new SoulSplitCalculator();

        List<AbilityHitsContext> hits = context.getAbility().getHits();
        int baseCount = hits.size(); // snapshot to prevent chaining

        for (int i = 0; i < baseCount; i++) {
            AbilityHitsContext parent = hits.get(i);

            AbilityHitsContext proc = new AbilityHitsContext();
            proc.setParentIndex(i);
            proc.setType(SPLITSOUL);
            proc.setHitTiming(parent.getHitTiming() + 1);

            int procCritMin = calc.soulSplit(parent.getCritMin(), 0, 0) * 4;
            int procCritMax = calc.soulSplit(parent.getCritMax(), amuletOfSouls ? 0.5 : 0, 1) * 4;

            int procNonCritMin = calc.soulSplit(parent.getNonCritMin(), 0, 0) * 4;
            int procNonCritMax = calc.soulSplit(parent.getNonCritMax(), amuletOfSouls ? 0.5 : 0, 1) * 4;

            int procCurrentMin = calc.soulSplit(parent.getCurrentMin(), amuletOfSouls ? 0.25 : 0, 0.5) * 4;
            int procCurrentMax = calc.soulSplit(parent.getCurrentMax(), amuletOfSouls ? 0.5 : 0, 0.5) * 4;

            proc.setCritMin(procCritMin);
            proc.setCritMax(procCritMax);
            proc.setCritDamage((procCritMin + procCritMax) / 2);

            proc.setNonCritMin(procNonCritMin);
            proc.setNonCritMax(procNonCritMax);
            proc.setNonCritDamage((procNonCritMin + procNonCritMax) / 2);

            proc.setCurrentMin(procCurrentMin);
            proc.setCurrentMax(procCurrentMax);
            proc.setCurrentDamage((procCurrentMin + procCurrentMax) / 2);

            hits.add(i + 1, proc);

            baseCount++;
            i++;

            if (context.debug) {
                Debug.stageHeader(context, "Split Soul Proc (inject)");
                Debug.stageRow(context, i, parent);
                Debug.stageRow(context, i + 1, proc);
                Debug.stageFooter(context);
            }
        }
    }
}