package com.rotdb.calculation.domain.resolvers.abilityDamage.preMultiplicative;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.HitType;

public class LightOfSaradominDamageBonusResolver {
    public static int resolve(CalculationContext context, AbilityHitsContext hit) {
        if (hit.getType() != HitType.BASE) {
            return 0;
        }

        if (context.getBuffs().has(BuffId.STRIKING_LIGHT) &&
                (context.getAbility().getId() == AbilityId.LIGHT_OF_SARADOMIN_MAGIC ||
                context.getAbility().getId() == AbilityId.LIGHT_OF_SARADOMIN_MELEE ||
                context.getAbility().getId() == AbilityId.LIGHT_OF_SARADOMIN_RANGED ||
                context.getAbility().getId() == AbilityId.LIGHT_OF_SARADOMIN_NECROMANCY)) {
            return (int) (context.getEquipment().getTotalArmour() * 2.5);
        }

        return 0;
    }
}
