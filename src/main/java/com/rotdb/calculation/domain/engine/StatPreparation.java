package com.rotdb.calculation.domain.engine;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.modifiers.StatModifier;
import com.rotdb.calculation.domain.modifiers.stats.DbaStatBoostModifier;
import com.rotdb.calculation.domain.modifiers.stats.NaragiEffectModifier;
import com.rotdb.calculation.domain.modifiers.stats.StatBoostModifier;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;

import java.util.List;

public class StatPreparation {
    private final List<StatModifier> steps;

    public StatPreparation() {
        steps = List.of(
                new StatBoostModifier(),
                new NaragiEffectModifier(),
                new DbaStatBoostModifier()
        );
    }

    public void run(SkillsContext skillsContext, BuffContext buffContext) {
        for (StatModifier step : steps) step.apply(skillsContext, buffContext);
    }
}
