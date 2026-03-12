package com.rotdb.domain.engine;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.modifiers.abilityDamage.*;
import com.rotdb.domain.modifiers.baseDamage.BaseAbilityDamageModifier;
import com.rotdb.domain.modifiers.hitChance.HitChanceModifier;
import com.rotdb.domain.modifiers.injectors.InstabilityInjector;
import com.rotdb.domain.modifiers.injectors.PerfectEquilibriumInjector;
import com.rotdb.domain.modifiers.injectors.SplitSoulInjector;
import com.rotdb.domain.modifiers.stats.DbaStatBoostModifier;
import com.rotdb.domain.modifiers.stats.StatBoostModifier;
import com.rotdb.domain.modifiers.Modifier;
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
