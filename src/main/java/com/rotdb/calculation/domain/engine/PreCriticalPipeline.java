package com.rotdb.calculation.domain.engine;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.modifiers.baseDamage.BaseAbilityDamageModifier;
import com.rotdb.calculation.domain.modifiers.injectors.*;

import java.util.List;

public class PreCriticalPipeline {
    private final List<Modifier> steps;

    public PreCriticalPipeline() {
        steps = List.of(
                new SoulInjector(),
                new NecrosisInjector(),
                new NightmareGauntletsInjector(),
                new MeleeBleedsInjector(),
                new RunicChargeInjector(),
                new BaseAbilityDamageModifier()
        );
    }

    public void run(AggregatedCalculationContext context) {
        for (Modifier step : steps) step.apply(context);
    }
}
