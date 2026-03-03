package com.rotdb.resolvers.abilityDamage.core;

import com.rotdb.model.context.AbilityContext;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.BuffId;
import com.rotdb.model.player.BuffContext;

import static com.rotdb.ability.AbilityId.GALESHOT;
import static com.rotdb.model.enums.CombatStyles.RANGED;

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
