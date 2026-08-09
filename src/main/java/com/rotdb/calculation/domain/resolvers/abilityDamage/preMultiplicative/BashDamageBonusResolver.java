package com.rotdb.calculation.domain.resolvers.abilityDamage.preMultiplicative;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.enums.EquipmentType;
import com.rotdb.shared.combat.domain.model.enums.HitType;

import java.util.List;

public class BashDamageBonusResolver {
    public static List<Integer> resolve(CalculationContext context, AbilityHitsContext hit) {
        if (hit.getType() != HitType.BASE || !isBash(context) || !hasShieldOrDefender(context)) {
            return List.of(0, 0);
        }

        int min = (int) ((context.getEquipment().getTotalArmour(context.getSkills()) + context.getSkills().getBoostedDefence()) * 0.2);
        int max = min;

        if (context.getBuffs().has(BuffId.STEADFAST_WILL)) {
            min += (int) (context.getEquipment().getTotalArmour(context.getSkills()) * 3.5);
            max += (int) (context.getEquipment().getTotalArmour(context.getSkills()) * 4.5);
        }

        return List.of(min, max);
    }

    private static boolean isBash(CalculationContext context) {
        return context.getAbility().getId() == AbilityId.BASH_MAGIC
                || context.getAbility().getId() == AbilityId.BASH_MELEE
                || context.getAbility().getId() == AbilityId.BASH_RANGED
                || context.getAbility().getId() == AbilityId.BASH_NECROMANCY;
    }

    private static boolean hasShieldOrDefender(CalculationContext context) {
        return context.getEquipment().getOffhand().getType() == EquipmentType.SHIELD
                || context.getEquipment().getOffhand().getEffect().contains(Effect.DEFENDER)
                || context.getEquipment().getMainhand().getType() == EquipmentType.SHIELDBOW;
    }
}
