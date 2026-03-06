package com.rotdb.domain.resolvers.abilityDamage.criticalStrike;

import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.Effect;
import com.rotdb.domain.model.equipment.EquipmentSlot;

public class CritDamageRangeResolver {
    public static CritRange resolve(CalculationContext context, AbilityHitsContext hit) {
        EquipmentSlot mainhand = context.getEquipment().getMainhand();
        double base = hit.getCritChanceModifier();
        if (mainhand.getEffect().contains(Effect.FSOA)) {
            return new CritRange(base + 0.15, base + 0.30);
        } else {
            return new CritRange(base, base);
        }
    }
}
