package com.rotdb.calculation.domain.resolvers.abilityDamage.preMultiplicative;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.enums.BuffId;

import java.util.ArrayList;
import java.util.List;

public class TearingThornsFlatAddResolver {
    public static List<Integer> resolve(CalculationContext context) {
        if (context.getBuffs().has(BuffId.TEARING_THORNS) && (
                context.getAbility().getId() == AbilityId.BARKSCALES_MAGIC ||
                context.getAbility().getId() == AbilityId.BARKSCALES_MELEE ||
                context.getAbility().getId() == AbilityId.BARKSCALES_RANGED ||
                context.getAbility().getId() == AbilityId.BARKSCALES_NECROMANCY)) {
            int effectiveMaxHp = (int) (context.getEquipment().getTotalLife() + context.getSkills().getMaxHp());

            if (context.getBuffs().has(BuffId.BIG_BONED)) {
                effectiveMaxHp = (int) (effectiveMaxHp * 1.5);
            }

            return new ArrayList<>(List.of((int) (effectiveMaxHp * 0.2), (int) (effectiveMaxHp * 0.3)));
        }
        return new ArrayList<>(List.of(0, 0));
    }
}
