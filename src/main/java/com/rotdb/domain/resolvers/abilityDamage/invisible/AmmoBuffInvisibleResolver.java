package com.rotdb.domain.resolvers.abilityDamage.invisible;

import com.rotdb.domain.model.context.AbilityContext;
import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.context.TargetContext;
import com.rotdb.domain.model.enums.*;
import com.rotdb.domain.model.equipment.EquipmentSlot;
import com.rotdb.domain.model.player.BuffContext;
import com.rotdb.domain.model.player.SpellContext;

import static com.rotdb.domain.model.enums.CombatStyles.*;

public class AmmoBuffInvisibleResolver {
    public static double resolve(CalculationContext context, AbilityHitsContext hit, int hitIndex) {
        CombatStyles style = context.getEquipment().getCombatStyle();
        EquipmentSlot ammo = context.getEquipment().getAmmo();
        AbilityContext ability = context.getAbility();
        TargetContext target = context.getTarget();
        BuffContext buff = context.getBuffs();
        SpellContext spell = context.getSpellContext();

        if (style == MAGIC) {
            if (spell.getSpell() == Spells.CRUMBLEUNDEAD && target.has(TargetTags.UNDEAD)) {
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
