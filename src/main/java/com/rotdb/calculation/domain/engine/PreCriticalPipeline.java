package com.rotdb.calculation.domain.engine;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.modifiers.baseDamage.BaseAbilityDamageModifier;
import com.rotdb.calculation.domain.modifiers.injectors.*;

import java.util.List;

public class PreCriticalPipeline {
    private final List<Modifier> stepsBaseAbilityDamageAbsent;
    private final List<Modifier> stepsBaseAbilityDamagePresent;

    public PreCriticalPipeline() {
        stepsBaseAbilityDamagePresent = List.of(
                new SoulInjector(),
                new NecrosisInjector(),
                new NightmareGauntletsInjector(),
                new MeleeBleedsInjector(),
                new RunicChargeInjector()
        );
        stepsBaseAbilityDamageAbsent = List.of(
                new SoulInjector(),
                new NecrosisInjector(),
                new NightmareGauntletsInjector(),
                new MeleeBleedsInjector(),
                new RunicChargeInjector(),
                new BaseAbilityDamageModifier()
        );
    }

    public void run(CalculationContext context, boolean baseAbilityDamageProvided) {
        if (baseAbilityDamageProvided) {
            for (Modifier step : stepsBaseAbilityDamagePresent) step.apply(context);
        } else {
            for (Modifier step : stepsBaseAbilityDamageAbsent) step.apply(context);
        }
    }
}
