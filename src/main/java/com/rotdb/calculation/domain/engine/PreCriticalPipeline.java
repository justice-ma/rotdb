package com.rotdb.calculation.domain.engine;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.modifiers.abilityDamage.*;
import com.rotdb.calculation.domain.modifiers.baseDamage.BaseAbilityDamageModifier;
import com.rotdb.calculation.domain.modifiers.hitChance.HitChanceModifier;
import com.rotdb.calculation.domain.modifiers.injectors.*;
import com.rotdb.calculation.domain.modifiers.stats.DbaStatBoostModifier;
import com.rotdb.calculation.domain.modifiers.stats.NaragiEffectModifier;
import com.rotdb.calculation.domain.modifiers.stats.StatBoostModifier;

import java.util.List;

public class PreCriticalPipeline {
    private final List<Modifier> steps;

    public PreCriticalPipeline() {
        steps = List.of(
                new SoulInjector(),
                new NecrosisInjector(),
                new NightmareGauntletsInjector(),
                new MeleeBleedsInjector(),
                new TearingThornsInjector(),
                new RunicChargeInjector(),
                new BaseAbilityDamageModifier()
        );
    }

    public void run(CalculationContext context) {
        for (Modifier step : steps) step.apply(context);
    }
}
