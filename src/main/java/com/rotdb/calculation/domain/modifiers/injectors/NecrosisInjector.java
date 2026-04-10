package com.rotdb.calculation.domain.modifiers.injectors;

import com.rotdb.calculation.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.model.enums.CombatStyles;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.shared.ability.AbilityId;

import java.util.List;

public class NecrosisInjector implements Modifier {
    public void apply(CalculationContext context) {
        if (context.getEquipment().getCombatStyle() != CombatStyles.NECROMANCY && !context.getBuffs().has(BuffId.NECROSIS)
            && context.getBuffs().stacks(BuffId.NECROSIS) <= 0 && context.getAbility().getId() != AbilityId.DEATHGRASP) {
            return;
        }

        List<AbilityHitsContext> hit = context.getAbility().getHits();
        double min = hit.getFirst().getMin() + context.getBuffs().stacks(BuffId.NECROSIS) * 0.4;
        double max = hit.getFirst().getMax() + context.getBuffs().stacks(BuffId.NECROSIS) * 0.4;
        hit.getFirst().setMin(min);
        hit.getFirst().setMax(max);
    }
}
