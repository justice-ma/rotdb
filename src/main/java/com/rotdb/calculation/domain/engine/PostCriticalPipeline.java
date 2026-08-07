package com.rotdb.calculation.domain.engine;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.abilityDamage.*;
import com.rotdb.calculation.domain.modifiers.baseDamage.BaseAbilityDamageModifier;
import com.rotdb.calculation.domain.modifiers.hitChance.HitChanceModifier;
import com.rotdb.calculation.domain.modifiers.injectors.*;
import com.rotdb.calculation.domain.modifiers.stats.DbaStatBoostModifier;
import com.rotdb.calculation.domain.modifiers.stats.NaragiEffectModifier;
import com.rotdb.calculation.domain.modifiers.stats.StatBoostModifier;
import com.rotdb.calculation.domain.modifiers.Modifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class PostCriticalPipeline {
    private final List<Modifier> steps;

    public PostCriticalPipeline() {
        steps = List.of(
                new CriticalStrikeModifier(),
                new InfernoOfZamorakInjector(),
                new InstabilityInjector(),
                new AbilityRangeModifier(),
                new BashDamageModifier(),
                new BlessingFlatDamageModifier(),
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
                new CrystalRainArrowsModifier(),
                new BloatInjector(),
                new SplitSoulInjector(),
                new NpcModifier(),
                new PoisonDamageModifier(),
                new HitCapModifier(),
                new AggregationModifier()
        );
    }

    public void run(CalculationContext context) {
        for (Modifier step : steps) step.apply(context);
    }
}
