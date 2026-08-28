package com.rotdb.calculation.domain.resolvers.abilityDamage.npc;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;

public class AbilityMultiplierResolver {
    public static double resolve(CalculationContext context, AbilityHitsContext hit) {
        AbilityContext ability = context.getAbility();
        double mod = 1;
        if (context.getBuffs().has(BuffId.LIVINGDEATH) && ability.getId() == AbilityId.FINGEROFDEATH) {
                mod *= 1.5;
        }
        if (context.getBuffs().has(BuffId.DEATHSPARK) && context.getBuffs().stacks(BuffId.DEATHSPARK) >= 5
            && ability.getId() == AbilityId.NECROMANCYAUTO) {
            mod *= 2;
        }
        return mod;
    }
}
