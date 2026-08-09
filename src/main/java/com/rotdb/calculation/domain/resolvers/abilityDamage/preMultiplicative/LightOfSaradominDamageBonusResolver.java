package com.rotdb.calculation.domain.resolvers.abilityDamage.preMultiplicative;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.HitType;

public class LightOfSaradominDamageBonusResolver {
    public static int resolve(CalculationContext context, AbilityHitsContext hit) {
        if ((context.getBuffs().has(BuffId.STRIKING_LIGHT) || context.getBuffs().has(BuffId.LORD_OF_LIGHT))
                && hit.getType() == HitType.LIGHT_OF_SARADOMIN) {
            return (int) (context.getEquipment().getTotalArmour(context.getSkills()) * 2.5);
        }

        return 0;
    }
}
