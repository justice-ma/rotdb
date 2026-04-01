package com.rotdb.calculation.domain.resolvers.abilityDamage.npc;

import com.rotdb.calculation.ability.AbilityId;
import com.rotdb.calculation.domain.model.context.AbilityContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.BuffId;

public class AbilityMultiplierResolver {
    public static double resolve(CalculationContext context) {
        AbilityContext ability = context.getAbility();
        if (context.getBuffs().has(BuffId.LIVINGDEATH) && ability.getId() == AbilityId.FINGEROFDEATH) {
            return 1.5;
        }
        if (context.getBuffs().has(BuffId.DEATHSPARK) && context.getBuffs().stacks(BuffId.DEATHSPARK) >= 5
            && ability.getId() == AbilityId.NECROMANCYAUTO) {
            return 2;
        }
        return 1;
    }
}
