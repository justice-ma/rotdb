package com.rotdb.calculation.domain.resolvers.abilityDamage.additive;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.HitType;

public class LightOfSaradominResolver {
    public static double resolve(CalculationContext context) {
        if (context.getBuffs().has(BuffId.LORD_OF_LIGHT) && context.getAbility().getHits().getFirst().getType() == HitType.LIGHT_OF_SARADOMIN) {
            return 0.02 * context.getEquipment().getTotalPrayer();
        }
        return 0;
    }
}
