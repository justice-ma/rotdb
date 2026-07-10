package com.rotdb.calculation.domain.resolvers.abilityDamage.core;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;

import static com.rotdb.shared.combat.domain.model.enums.CombatStyles.MELEE;

public class CoreFlatAddResolver {
    public static int resolve(CalculationContext context) {
        EquipmentSlot neck = context.getEquipment().getNeck();
        EquipmentSlot offhand = context.getEquipment().getOffhand();
        BuffContext buffs = context.getBuffs();
        EquipmentSlot mainhand = context.getEquipment().getMainhand();
        CombatStyles style = context.getEquipment().getCombatStyle();
        SkillsContext skills = context.getSkills();
        BuffContext buff = context.getBuffs();

        int add = 0;
        if (neck.getEffect().contains(Effect.AMZI) && context.getEquipment().getCombatStyle() == MELEE) {
            add += (int) (context.getSkills().getBoostedAttack() * 1.35);
        }

        if (offhand.getEffect().contains(Effect.OFFHANDLENG) && buffs.has(BuffId.FROSTBLADES)) {
            add += (int) (context.getDamage().getBaseDamage() * 0.24);
        }

        return add;
    }
}
