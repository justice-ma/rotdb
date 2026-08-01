package com.rotdb.calculation.domain.resolvers.abilityDamage.core;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.HitType;

public class AbyssalCindersCoreAddResolver {
    public static int resolve(CalculationContext context, AbilityHitsContext hit) {
        if (context.getBuffs().has(BuffId.ABYSSAL_CINDERS) && hit.getType() == HitType.BASE && hit.getTier() != AbilityTier.CONJURE && !hit.isDot()) {
            return (int) (context.getDamage().getBaseDamage() * 0.15);
        }
        return 0;
    }
}
