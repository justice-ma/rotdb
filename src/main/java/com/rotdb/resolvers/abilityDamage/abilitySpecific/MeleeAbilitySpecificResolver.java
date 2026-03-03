package com.rotdb.resolvers.abilityDamage.abilitySpecific;

import com.rotdb.ability.AbilityId;
import com.rotdb.model.context.AbilityContext;
import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.context.TargetContext;
import com.rotdb.model.enums.BuffId;
import com.rotdb.model.enums.CombatStyles;
import com.rotdb.model.player.BuffContext;

import static com.rotdb.model.enums.CombatStyles.MELEE;

public class MeleeAbilitySpecificResolver {
    public static double resolve(CalculationContext context, AbilityHitsContext hit) {
        CombatStyles style = context.getEquipment().getCombatStyle();
        TargetContext target = context.getTarget();
        AbilityContext ability = context.getAbility();
        BuffContext buff = context.getBuffs();
        double mod = 1;
        if (style == MELEE) {
            if ((double) target.getCurrentHp() / target.getMaxHp() < 0.5 && ability.getId() == AbilityId.PUNISH) {
                mod *= 2.5;
            }

            if (buff.has(BuffId.BLOODLUST) && buff.stacks(BuffId.BLOODLUST) >= 4 && ability.getId() == AbilityId.GREATERFLURRY) {
                double hpPercent = (double) target.getCurrentHp() / target.getMaxHp();
                double add = 1 + Math.min(1.0 - hpPercent, 0.65);
                mod *= add;
            }
        }
        return mod;
    }
}
