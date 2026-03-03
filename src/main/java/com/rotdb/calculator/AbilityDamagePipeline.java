package com.rotdb.calculator;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.modifiers.abilityDamage.*;
import com.rotdb.modifiers.baseDamage.BaseAbilityDamageModifier;
import com.rotdb.modifiers.hitChance.HitChanceModifier;
import com.rotdb.modifiers.injectors.InstabilityInjector;
import com.rotdb.modifiers.injectors.PerfectEquilibriumInjector;
import com.rotdb.modifiers.injectors.SplitSoulInjector;
import com.rotdb.modifiers.stats.DbaStatBoostModifier;
import com.rotdb.modifiers.stats.StatBoostModifier;
import com.rotdb.modifiers.Modifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class AbilityDamagePipeline {
    private final List<Modifier> steps;

    public AbilityDamagePipeline() {
        steps = List.of(
                new StatBoostModifier(),
                new DbaStatBoostModifier(),
                new BaseAbilityDamageModifier(),
                new InstabilityInjector(),
                new CriticalStrikeModifier(),
                new AbilityRangeModifier(),
                new HitChanceModifier(),
                new InvisibleAbilityModifier(),
                new AbilitySpecificModifier(),
                new StyleSpecificModifier(),
                new PreciseModifier(),
                new AdditiveModifier(),
                new MultiplicativeModifier(),
                new CoreModifier(),
                new PerfectEquilibriumInjector(),
                new CritDamageModifier(),
                new SplitSoulInjector(),
                new NpcModifier(),
                new HitCapModifier(),
                new CrystalRainArrowsModifier(),
                new AggregationModifier()
        );
    }

    public void run(CalculationContext context) {
        for (Modifier step : steps) step.apply(context);
    }
}
