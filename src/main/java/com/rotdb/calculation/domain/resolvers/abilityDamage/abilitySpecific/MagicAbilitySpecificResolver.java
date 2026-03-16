package com.rotdb.calculation.domain.resolvers.abilityDamage.abilitySpecific;

import com.rotdb.calculation.ability.AbilityId;
import com.rotdb.calculation.domain.model.context.AbilityContext;
import com.rotdb.calculation.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.model.enums.CombatStyles;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;
import com.rotdb.calculation.domain.model.player.BuffContext;

import static com.rotdb.calculation.domain.model.enums.CombatStyles.MAGIC;

public class MagicAbilitySpecificResolver {
    public static double resolve(CalculationContext context, AbilityHitsContext hit) {
        CombatStyles style = context.getEquipment().getCombatStyle();
        AbilityContext ability = context.getAbility();
        EquipmentSlot mainhand = context.getEquipment().getMainhand();
        EquipmentSlot offhand = context.getEquipment().getOffhand();
        EquipmentSlot gloves = context.getEquipment().getGloves();
        BuffContext buff = context.getBuffs();
        double mod = 1;
        if (style == MAGIC) {
            if (buff.has(BuffId.CONFLAGRATE) && ability.getId() == AbilityId.COMBUST) {
                mod *= 1.4;
            }

            if (mainhand.getEffect().contains(Effect.SONGOFDESTRUCTION) &&
                    offhand.getEffect().contains(Effect.SONGOFDESTRUCTION) &&
                    hit.isDot()) {
                mod *= 1.3;
            }

            if (gloves.getEffect().contains(Effect.KERAPACSWRISTWRAPS) && buff.has(BuffId.COMBUSTED)) {
                if (buff.has(BuffId.ENCHANTMENTOFFLAMES) && gloves.getEffect().contains(Effect.ENHANCED)) {
                    mod *= 1.4;
                } else {
                    mod *= 1.25;
                }

            }

            if (ability.getId() == AbilityId.DRAGONBREATH && buff.has(BuffId.COMBUSTED)) {
               mod *= 1.25;
            }
        }
        return mod;
    }
}
