package com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike;

import com.rotdb.calculation.domain.model.context.AbilityContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.model.player.BuffContext;

import static com.rotdb.calculation.domain.model.enums.CombatStyles.MAGIC;

public class TargetCritResolver {
    public static double resolve(CalculationContext context) {
        AbilityContext ability = context.getAbility();
        BuffContext buff = context.getBuffs();

        double criticalStrikeDamage = 0;
        if (buff.has(BuffId.SMOKECLOUDED)) {
            if (ability.getCombatStyle() == MAGIC) {
                criticalStrikeDamage += 0.15;
            } else {
                criticalStrikeDamage += 0.15 * 0.4;
            }
        }
        return criticalStrikeDamage;
    }
}
