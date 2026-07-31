package com.rotdb.calculation.domain.resolvers.abilityDamage.npc;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.*;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;

import static com.rotdb.shared.combat.domain.model.enums.CombatStyles.MAGIC;

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
            if (offhand.getEffect().contains(Effect.SONGOFDESTRUCTION) && mainhand.getEffect().contains(Effect.SONGOFDESTRUCTION)) {
                add += corr * 3 + magic;
            }
        }

        if (context.getBuffs().has(BuffId.STRIKING_LIGHT) &&
                (context.getAbility().getId() == AbilityId.LIGHT_OF_SARADOMIN_MAGIC ||
                context.getAbility().getId() == AbilityId.LIGHT_OF_SARADOMIN_MELEE ||
                context.getAbility().getId() == AbilityId.LIGHT_OF_SARADOMIN_RANGED ||
                context.getAbility().getId() == AbilityId.LIGHT_OF_SARADOMIN_NECROMANCY)) {
            add += (int) (context.getEquipment().getTotalArmour() * 2.5);
        }

        if ((context.getEquipment().getOffhand().getType() == EquipmentType.SHIELD
                || context.getEquipment().getOffhand().getEffect().contains(Effect.DEFENDER))
                && (context.getAbility().getId() == AbilityId.BASH_MAGIC
                || context.getAbility().getId() == AbilityId.BASH_MELEE
                || context.getAbility().getId() == AbilityId.BASH_RANGED
                || context.getAbility().getId() == AbilityId.BASH_NECROMANCY)) {
            add += (int) (context.getEquipment().getTotalArmour() * 0.1 + context.getSkills().getBoostedDefence());
        }

        if (context.getBuffs().has(BuffId.BIG_BONED)) {
            int effectiveMaxHp =(int) ((context.getSkills().getMaxHp() * 1.5) + context.getEquipment().getTotalLife());
            add += (int) (effectiveMaxHp * 0.05);
        }

        if (context.getBuffs().has(BuffId.ABYSSAL_CINDERS) && hit.getType() == HitType.BASE) {
            add += (int) (context.getDamage().getBaseDamage() * 0.15);
        }
        return add;
    }
}
