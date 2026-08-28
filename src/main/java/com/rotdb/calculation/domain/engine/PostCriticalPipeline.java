package com.rotdb.calculation.domain.engine;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.modifiers.abilityDamage.*;
import com.rotdb.calculation.domain.modifiers.hitChance.HitChanceModifier;
import com.rotdb.calculation.domain.modifiers.injectors.BloatInjector;
import com.rotdb.calculation.domain.modifiers.injectors.InstabilityInjector;
import com.rotdb.calculation.domain.modifiers.injectors.PerfectEquilibriumInjector;
import com.rotdb.calculation.domain.modifiers.injectors.SplitSoulInjector;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class PostCriticalPipeline {
    private final List<Modifier> steps;

    public PostCriticalPipeline() {
        steps = List.of(
                new CriticalStrikeModifier(),
                new InstabilityInjector(),
                new AbilityRangeModifier(),
                new BashDamageModifier(),
                new HitChanceModifier(),
                new InvisibleAbilityModifier(),
                new AbilitySpecificModifier(),
                new StyleSpecificModifier(),
                new PreciseModifier(),
                new AdditiveModifier(),
                new MultiplicativeModifier(),
                new CoreModifier(),
                new FlatHitDamageModifier(),
                new PerfectEquilibriumInjector(),
                new CritDamageModifier(),
                new CrystalRainArrowsModifier(),
                new BloatInjector(),
                new SplitSoulInjector(),
                new NpcModifier(),
                new PoisonDamageModifier(),
                new HitCapModifier(),
                new AggregationModifier()
        );
    }

    public void run(AggregatedCalculationContext context) {
        for (Modifier step : steps) step.apply(context);
    }
}
