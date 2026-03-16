package com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike;

import com.rotdb.calculation.domain.model.context.AbilityContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;
import com.rotdb.calculation.domain.model.player.BuffContext;

import static com.rotdb.calculation.domain.model.enums.CombatStyles.*;

public class GearCritResolver {
    public static CritBonus resolve(CalculationContext context) {
        EquipmentSlot pocket = context.getEquipment().getPocket();
        EquipmentSlot ring = context.getEquipment().getRing();
        EquipmentSlot ammo = context.getEquipment().getAmmo();
        BuffContext buff = context.getBuffs();
        AbilityContext ability = context.getAbility();

        double criticalStrikeChance = 0;
        double criticalStrikeDamage = 0;
        if (pocket.getEffect().contains(Effect.GRIMOIRE)) {
            criticalStrikeChance += 0.12;
        }

        if (ring.getEffect().contains(Effect.REAVERSRING)) {
            criticalStrikeChance += 0.05;
        }

        if (ring.getEffect().contains(Effect.CHAMPIONSRING) && buff.has(BuffId.BLEEDS) && buff.stacks(BuffId.BLEEDS) > 0 && ability.getCombatStyle() == MELEE) {
            if (buff.has(BuffId.ENCHANTMENTOFHEROISM)) {
                criticalStrikeChance += 0.04;
                criticalStrikeDamage += 0.015 * buff.stacks(BuffId.BLEEDS);
            } else {
                criticalStrikeChance += 0.03;
            }
        }

        // TODO: Check that weapon is a bow some day
        if (ring.getEffect().contains(Effect.STALKERSRING) && ability.getCombatStyle() == RANGED) {
            if (buff.has(BuffId.ENCHANTMENTOFSHADOWS)) {
                criticalStrikeChance += 0.04;
                criticalStrikeDamage += 0.03;
            } else {
                criticalStrikeChance += 0.03;
            }
        }

        if (ring.getEffect().contains(Effect.CHANNELLERSRING) && ability.getCombatStyle() == MAGIC &&
                ability.isChannel()) {
            if (buff.has(BuffId.ENCHANTMENTOFMETAPHYSICS)) {
                criticalStrikeChance += 0.04;
                criticalStrikeDamage += 0.025;
            } else {
                criticalStrikeChance += 0.04;
            }
        }

        if (ammo.getEffect().contains(Effect.DEATHSPOREARROWS) && ability.getCombatStyle() == RANGED) {
            criticalStrikeChance += 0.03;
        }

        return new CritBonus(criticalStrikeChance, criticalStrikeDamage);
    }
}
