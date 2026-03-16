package com.rotdb.calculation.domain.resolvers.abilityDamage.abilitySpecific;

import com.rotdb.calculation.ability.AbilityId;
import com.rotdb.calculation.domain.model.context.AbilityContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;

public class CorruptionPerHitResolver {
    public static double resolve(CalculationContext context, int hitIndex) {
        AbilityContext ability = context.getAbility();
        if (ability.getId() == AbilityId.CORRUPTIONSHOT || ability.getId() == AbilityId.CORRUPTIONBLAST) {
            return (1 - (hitIndex * 2 / 10.0));
        }
        return 1;
    }
}
