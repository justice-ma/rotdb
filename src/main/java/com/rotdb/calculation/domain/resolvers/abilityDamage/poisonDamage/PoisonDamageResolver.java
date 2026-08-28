package com.rotdb.calculation.domain.resolvers.abilityDamage.poisonDamage;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.enums.HitType;

public class PoisonDamageResolver {
    public static double resolve(AbilityHitsContext hit, CalculationContext context) {
        double mod = 1;
        if (hit.getType() == HitType.POISON) {
            if (context.getEquipment().getMainhand().getEffect().contains(Effect.LANIAKEA)) {
                mod *= 1.05;
            }

            if (context.getEquipment().getGloves().getEffect().contains(Effect.CINDERBANES)) {
                mod *= 8.0 / 7;
            }

            if (context.getBuffs().has(BuffId.KWUARM)) {
                mod *= (1 + 0.025 * context.getBuffs().stacks(BuffId.KWUARM));
            }

            if (context.getBuffs().has(BuffId.BIK_ARROWS)) {
                mod *= (1 + 0.03 * context.getBuffs().stacks(BuffId.BIK_ARROWS));
            }
        }

        return mod;
    }
}
