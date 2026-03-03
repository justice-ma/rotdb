package com.rotdb.resolvers.abilityDamage.criticalStrike;

import com.rotdb.model.context.AbilityContext;
import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.BuffId;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.enums.HitType;
import com.rotdb.model.equipment.EquipmentSlot;
import com.rotdb.model.player.BuffContext;

import static com.rotdb.ability.AbilityId.*;
import static com.rotdb.model.enums.CombatStyles.MAGIC;

public class PerHitCritAdjustResolver {
    public static CritBonus resolve(CalculationContext context, AbilityHitsContext hit, int hitIndex) {
        AbilityContext ability = context.getAbility();
        EquipmentSlot ring = context.getEquipment().getRing();
        BuffContext buff = context.getBuffs();
        double criticalStrikeChance = 0;
        double criticalStrikeDamage = 0;

        if (ability.getId() == CONCENTRATEDBLAST && hit.getType() == HitType.BASE) {
            criticalStrikeChance += 0.05 * hitIndex;
        } else if (ability.getId() == GREATERCONCENTRATEDBLAST && hit.getType() == HitType.BASE) {
            criticalStrikeChance += 0.07 * hitIndex;
        } else if (ability.getId() == THEFINALFLURRY) {
            if (hitIndex < 2) {
                criticalStrikeChance += 0.25;
            } else {
                criticalStrikeChance += 0.5;
            }
        }

        if (ability.getCombatStyle() == MAGIC && ability.isChannel() &&
                ring.getEffect().contains(Effect.CHANNELLERSRING) && hit.getType() == HitType.BASE) {
            criticalStrikeChance += 0.04 * hitIndex;
            if (buff.has(BuffId.ENCHANTMENTOFMETAPHYSICS)) {
                criticalStrikeDamage += 0.025 * hitIndex;
            }
        }
        return new CritBonus(criticalStrikeChance, criticalStrikeDamage);
    }
}
