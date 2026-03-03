package com.rotdb.resolvers.abilityDamage.npc;

import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.BuffId;
import com.rotdb.model.enums.CombatStyles;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.equipment.EquipmentSlot;
import com.rotdb.model.player.BuffContext;
import com.rotdb.model.player.SkillsContext;

import static com.rotdb.model.enums.CombatStyles.MAGIC;

public class FlatAddResolver {
    public static double resolve(CalculationContext context, AbilityHitsContext hit) {
        EquipmentSlot mainhand = context.getEquipment().getMainhand();
        EquipmentSlot offhand = context.getEquipment().getOffhand();
        CombatStyles style = context.getEquipment().getCombatStyle();
        SkillsContext skills = context.getSkills();
        BuffContext buff = context.getBuffs();

        int add = 0;
        if (style == MAGIC) {
            int corr = buff.has(BuffId.ESSENCECORRUPTIONSTACKS) ? buff.stacks(BuffId.ESSENCECORRUPTIONSTACKS) : 0;
            int magic = skills.getBoostedMagic();
            if (offhand.getEffect().contains(Effect.SONGOFDESTRUCTION) && mainhand.getEffect().contains(Effect.SONGOFDESTRUCTION)) {
                add += corr * 3 + magic;
            }
        }
        return add;
    }
}
