package com.rotdb.resolvers.abilityDamage.abilityRange;

import com.rotdb.ability.AbilityId;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.BuffId;

public class AbilityRangeBonusResolver {
    public static AbilityRangeBonus resolve(CalculationContext context) {
        double min = 0;
        double max = 0;

        int lengStacks = context.getBuffs().has(BuffId.PRIMORDIALICESTACKS) ? context.getBuffs().stacks(BuffId.PRIMORDIALICESTACKS): 0;
        int time = context.getBuffs().has(BuffId.TIMESINCELASTATTACK) ? context.getBuffs().stacks(BuffId.TIMESINCELASTATTACK) : 0;

        if (lengStacks > 0 && context.getAbility().getId() == AbilityId.ICYTEMPEST) {
            min += 0.18 * lengStacks;
            max += 0.22 * lengStacks;
        }

        if (time > 0 && context.getAbility().getId() == AbilityId.GREATERBARGE) {
            min += 0.05 * time;
            max += 0.07 * time;
        }

        return new AbilityRangeBonus(min, max);
    }
}
