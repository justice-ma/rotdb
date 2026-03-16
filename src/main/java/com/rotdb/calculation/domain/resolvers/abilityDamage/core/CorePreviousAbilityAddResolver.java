package com.rotdb.calculation.domain.resolvers.abilityDamage.core;

import com.rotdb.calculation.domain.model.context.AbilityContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.model.player.BuffContext;

import static com.rotdb.calculation.domain.model.enums.CombatStyles.RANGED;

public class CorePreviousAbilityAddResolver {
    public static int resolve(CalculationContext context) {
        BuffContext buffs = context.getBuffs();
        AbilityContext ability = context.getAbility();
        if (buffs.has(BuffId.GALES) && ability.getCombatStyle() == RANGED) {
            return (int) (context.getDamage().getBaseDamage() * 0.2);
        }
        return 0;
    }
}
