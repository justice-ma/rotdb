package com.rotdb.domain.resolvers.abilityDamage.invisible;

import com.rotdb.domain.model.context.AbilityContext;
import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.context.TargetContext;
import com.rotdb.domain.model.enums.BuffId;
import com.rotdb.domain.model.enums.CombatStyles;
import com.rotdb.domain.model.enums.Effect;
import com.rotdb.domain.model.enums.TargetTags;
import com.rotdb.domain.model.equipment.EquipmentSlot;
import com.rotdb.domain.model.player.BuffContext;

import static com.rotdb.domain.model.enums.CombatStyles.*;

public class AmmoBuffInvisibleResolver {
    public static double resolve(CalculationContext context, AbilityHitsContext hit, int hitIndex) {
        CombatStyles style = context.getEquipment().getCombatStyle();
        EquipmentSlot ammo = context.getEquipment().getAmmo();
        AbilityContext ability = context.getAbility();
        TargetContext target = context.getTarget();
        BuffContext buff = context.getBuffs();

        if (style == MAGIC) {
            if (/*TODO: NEEDS SPELL CONTEXT*/ target.has(TargetTags.UNDEAD)) {
                return 1.3;
            }
        }

        if (style == MELEE) {
            if (buff.has(BuffId.CHAOSROAR)) {
                if (hit.isDot() || !ability.isChannel()) {
                    return 1.75;
                } else {
                    if (hitIndex == 0) {
                        return 1.75;
                    }
                }
            }
        }

        if (style == RANGED && ammo.getEffect().contains(Effect.WENARROWS)) {
            int wen = buff.has(BuffId.WENSTACKS) && buff.stacks(BuffId.WENSTACKS) >= 10
                    ? buff.stacks(BuffId.WENSTACKS) : 0;
            return 1.3;
        }

        return 1;
    }
}
