package com.rotdb.calculation.domain.resolvers.abilityDamage.npc;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.*;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;
import static com.rotdb.shared.combat.domain.model.enums.CombatStyles.MAGIC;
import static com.rotdb.shared.combat.domain.model.enums.CombatStyles.NECROMANCY;

public class FlatAddResolver {
    public static int resolve(CalculationContext context, AbilityHitsContext hit) {
        EquipmentSlot mainhand = context.getEquipment().getMainhand();
        EquipmentSlot offhand = context.getEquipment().getOffhand();
        CombatStyles style = context.getEquipment().getCombatStyle();
        SkillsContext skills = context.getSkills();
        BuffContext buff = context.getBuffs();

        int add = 0;
        if (style == MAGIC) {
            int corr = buff.has(BuffId.ESSENCECORRUPTIONSTACKS) ? buff.stacks(BuffId.ESSENCECORRUPTIONSTACKS) : 0;
            int magic = skills.getBoostedMagic();
            if (offhand.getEffect().contains(Effect.SONGOFDESTRUCTION) && mainhand.getEffect().contains(Effect.SONGOFDESTRUCTION)
                && buff.has(BuffId.ESSENCECORRUPTIONSTACKS)) {
                add += corr * 3 + magic;
            }
        }

        if (style == NECROMANCY &&
                buff.has(BuffId.ARISEN_FLESH) &&
                context.getAbility().getId() == AbilityId.CONJUREPUTRIDZOMBIE &&
                hit.getType() == HitType.BASE) {
            if (((double) skills.getCurrentHp() / (double) context.getDerivedStatsResult().effectiveMaxHp()) < 0.5) {
                add += ((double) context.getDerivedStatsResult().effectiveMaxHp() * 0.06) * 1.75;
            } else {
                add += ((double) context.getDerivedStatsResult().effectiveMaxHp() * 0.06) * 0.75;
            }
        }

        return add;
    }
}
