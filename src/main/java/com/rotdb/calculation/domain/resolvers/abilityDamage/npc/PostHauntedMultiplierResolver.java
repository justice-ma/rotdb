package com.rotdb.calculation.domain.resolvers.abilityDamage.npc;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.context.TargetContext;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.enums.TargetTags;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;
import com.rotdb.calculation.domain.model.player.BuffContext;
import com.rotdb.calculation.domain.model.player.SkillsContext;

public class PostHauntedMultiplierResolver {
    public static double resolve(CalculationContext context) {
        BuffContext buff = context.getBuffs();
        SkillsContext skills = context.getSkills();
        EquipmentSlot neck = context.getEquipment().getNeck();
        TargetContext target = context.getTarget();

        double mod = 1;
        if (buff.has(BuffId.BALANCEOFPOWER) && buff.stacks(BuffId.BALANCEOFPOWER) > 0 && (double) skills.getCurrentHp() / skills.getMaxHp() < 0.6) {
            mod *= 1 + (0.06 * buff.stacks(BuffId.BALANCEOFPOWER));
        }

        if (target.has(TargetTags.DINOSAUR) && neck.getEffect().contains(Effect.SALAMANCY)) {
            mod *= 1.15;
        }

        return mod;
    }
}
