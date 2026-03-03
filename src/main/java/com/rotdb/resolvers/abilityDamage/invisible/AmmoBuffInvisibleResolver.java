package com.rotdb.resolvers.abilityDamage.invisible;

import com.rotdb.model.context.AbilityContext;
import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.context.TargetContext;
import com.rotdb.model.enums.BuffId;
import com.rotdb.model.enums.CombatStyles;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.equipment.EquipmentSlot;
import com.rotdb.model.player.BuffContext;

import static com.rotdb.model.enums.CombatStyles.*;

public class AmmoBuffInvisibleResolver {
    public static double resolve(CalculationContext context, AbilityHitsContext hit, int hitIndex) {
        CombatStyles style = context.getEquipment().getCombatStyle();
        EquipmentSlot ammo = context.getEquipment().getAmmo();
        AbilityContext ability = context.getAbility();
        TargetContext target = context.getTarget();
        BuffContext buff = context.getBuffs();

        if (style == MAGIC) {
            if (/*TODO: NEEDS SPELL CONTEXT*/ target.isUndead()) {
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

        if (style == RANGED &&
                ammo.getEffect().contains(Effect.WENARROWS)) {
            int wen = buff.has(BuffId.WENSTACKS) ? buff.stacks(BuffId.WENSTACKS) : 0;
            return 1 + (0.02 * wen);
        }

        return 1;
    }
}
