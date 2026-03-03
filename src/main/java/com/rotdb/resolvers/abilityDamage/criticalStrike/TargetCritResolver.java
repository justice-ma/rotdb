package com.rotdb.resolvers.abilityDamage.criticalStrike;

import com.rotdb.model.context.AbilityContext;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.context.TargetContext;

import static com.rotdb.model.enums.CombatStyles.MAGIC;

public class TargetCritResolver {
    public static double resolve(CalculationContext context) {
        TargetContext target = context.getTarget();
        AbilityContext ability = context.getAbility();

        double criticalStrikeDamage = 0;
        if (target.isSmokeClouded()) {
            if (ability.getCombatStyle() == MAGIC) {
                criticalStrikeDamage += 0.15;
            } else {
                criticalStrikeDamage += 0.15 * 0.4;
            }
        }
        return criticalStrikeDamage;
    }
}
