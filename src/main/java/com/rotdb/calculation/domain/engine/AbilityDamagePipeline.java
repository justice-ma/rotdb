package com.rotdb.calculation.domain.engine;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.abilityDamage.*;
import com.rotdb.calculation.domain.modifiers.abilityDamage.*;
import com.rotdb.calculation.domain.modifiers.baseDamage.BaseAbilityDamageModifier;
import com.rotdb.calculation.domain.modifiers.hitChance.HitChanceModifier;
import com.rotdb.calculation.domain.modifiers.injectors.InstabilityInjector;
import com.rotdb.calculation.domain.modifiers.injectors.PerfectEquilibriumInjector;
import com.rotdb.calculation.domain.modifiers.injectors.SplitSoulInjector;
import com.rotdb.calculation.domain.modifiers.stats.DbaStatBoostModifier;
import com.rotdb.calculation.domain.modifiers.stats.StatBoostModifier;
import com.rotdb.calculation.domain.modifiers.Modifier;
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
                new CriticalStrikeModifier(),
                new InstabilityInjector(),
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
