package com.rotdb.domain.resolvers.abilityDamage.criticalStrike;

import com.rotdb.domain.model.context.AbilityContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.BuffId;
import com.rotdb.domain.model.enums.Familiars;
import com.rotdb.domain.model.equipment.FamiliarContext;
import com.rotdb.domain.model.player.BuffContext;

import static com.rotdb.ability.AbilityId.*;
import static com.rotdb.domain.model.enums.CombatStyles.*;

public class BuffCritResolver {
    public static double resolve(CalculationContext context) {
        BuffContext buff = context.getBuffs();
        AbilityContext ability = context.getAbility();
        FamiliarContext familiar = context.getFamiliar();

        double criticalStrikeChance = 0;
        if (buff.has(BuffId.FURYBUFF) && ability.getCombatStyle() == MELEE) criticalStrikeChance += 0.25;
        if (buff.has(BuffId.GREATERFURYBUFF) && ability.getCombatStyle() == MELEE) criticalStrikeChance = 1;
        if (ability.getId() == SMOKETENDRILS || ability.getId() == SHADOWTENDRILS) criticalStrikeChance = 1;
        if (buff.has(BuffId.CONCENTRATEDBLASTBUFF)  && ability.getCombatStyle() == MAGIC) {
            if (buff.has(BuffId.RUNICCHARGE)) {
                criticalStrikeChance += 0.75;
            } else {
                criticalStrikeChance += 0.15;
            }
        }
        if (buff.has(BuffId.GREATERCONCENTRATEDBLASTBUFF)  && ability.getCombatStyle() == MAGIC) {
            if (buff.has(BuffId.RUNICCHARGE)) {
                criticalStrikeChance += 0.81;
            } else {
                criticalStrikeChance += 0.21;
            }
        }


        if (familiar.getName() == Familiars.KALGERIONDEMON) {
            criticalStrikeChance += 0.01;
        }

        if (buff.has(BuffId.KALG)) {
            criticalStrikeChance += 0.05;
        }

        if (buff.has(BuffId.ECLIPSEDSOUL)) {
            criticalStrikeChance += 0.04;
        }

        if (buff.has(BuffId.NOFEAR) && buff.stacks(BuffId.NOFEAR) > 0) {
            criticalStrikeChance += 0.2 * buff.stacks(BuffId.NOFEAR);
        }
        return criticalStrikeChance;
    }
}
