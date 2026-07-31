package com.rotdb.calculation.domain.resolvers.abilityDamage.npc;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;

public class BigBonedBonusResolver {
    public static HauntedBonus resolve(CalculationContext context) {
        if (!context.getBuffs().has(BuffId.BIG_BONED)) {
            return zero();
        }

        int effectiveMaxHp = (int) ((context.getSkills().getMaxHp() * 1.5) + context.getEquipment().getTotalLife());
        int bonus = Math.min((int) (effectiveMaxHp * 0.05), (int) (context.getDamage().getBaseDamage() * 0.2));

        return new HauntedBonus(bonus, bonus, bonus, bonus, bonus, bonus);
    }

    private static HauntedBonus zero() {
        return new HauntedBonus(0, 0, 0, 0, 0, 0);
    }
}
