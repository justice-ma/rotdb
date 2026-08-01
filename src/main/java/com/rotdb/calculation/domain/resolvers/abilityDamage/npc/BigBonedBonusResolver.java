package com.rotdb.calculation.domain.resolvers.abilityDamage.npc;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;

public class BigBonedBonusResolver {
    public static HauntedBonus resolve(CalculationContext context, AbilityHitsContext hit) {
        if (!context.getBuffs().has(BuffId.BIG_BONED)) {
            return zero();
        }

        int effectiveMaxHp = (int) ((context.getSkills().getMaxHp() * 1.5) + context.getEquipment().getTotalLife());
        int bonus = (int) (effectiveMaxHp * 0.05);

        return new HauntedBonus(bonus, bonus, bonus, bonus, bonus, bonus);
    }

    private static HauntedBonus zero() {
        return new HauntedBonus(0, 0, 0, 0, 0, 0);
    }
}
