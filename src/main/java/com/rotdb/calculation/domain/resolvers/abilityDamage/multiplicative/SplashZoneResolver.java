package com.rotdb.calculation.domain.resolvers.abilityDamage.multiplicative;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.HitType;
import com.rotdb.shared.combat.domain.model.enums.Targetting;

public class SplashZoneResolver {
    public static double resolve(CalculationContext context, AbilityHitsContext hit) {
        if (context.getBuffs().has(BuffId.SPLASH_ZONE) && hit.getType() == HitType.BASE &&
            (context.getAbility().getTargetting() == Targetting.MULTI_TARGET ||
            context.getAbility().getTargetting() == Targetting.AREA_TARGET)){
            return 1.3 + (0.05 * context.getTarget().getSize());
        }
        return 1;
    }
}
