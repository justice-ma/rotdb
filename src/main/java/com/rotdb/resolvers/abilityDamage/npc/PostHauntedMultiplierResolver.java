package com.rotdb.resolvers.abilityDamage.npc;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.context.TargetContext;
import com.rotdb.model.enums.BuffId;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.equipment.EquipmentSlot;
import com.rotdb.model.player.BuffContext;
import com.rotdb.model.player.SkillsContext;

public class PostHauntedMultiplierResolver {
    public static double resolve(CalculationContext context) {
        BuffContext buff = context.getBuffs();
        SkillsContext skills = context.getSkills();
        EquipmentSlot neck = context.getEquipment().getNeck();
        TargetContext target = context.getTarget();

        double mod = 1;
        if (buff.has(BuffId.BALANCEOFPOWER) && buff.stacks(BuffId.BALANCEOFPOWER) > 0 && (double) skills.getCurrentHp() / skills.getMaxHp() < 0.6) {
            mod *= 1 + (0.6 * buff.stacks(BuffId.BALANCEOFPOWER));
        }

        if (target.isDinosaur() && neck.getEffect().contains(Effect.SALAMANCY)) {
            mod *= 1.15;
        }

        return mod;
    }
}
