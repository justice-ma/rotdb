package com.rotdb.calculation.domain.resolvers.abilityDamage.additive;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Effect;

public class RangedAdditiveResolver {
    public static double resolve (CalculationContext context) {
        double mod = 0;
        if (context.getBuffs().has(BuffId.CRIMSON_SALVO) && context.getEquipment().getMainhand().getEffect().contains(Effect.MALACHI)
                && context.getEquipment().getOffhand().getEffect().contains(Effect.MALACHI)) {
            mod += 0.2;
        }
        return mod;
    }
}
