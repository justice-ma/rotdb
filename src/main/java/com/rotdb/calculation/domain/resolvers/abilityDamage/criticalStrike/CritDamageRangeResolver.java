package com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike;

import com.rotdb.calculation.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;

public class CritDamageRangeResolver {
    public static CritRange resolve(CalculationContext context, AbilityHitsContext hit) {
        EquipmentSlot mainhand = context.getEquipment().getMainhand();
        double base = hit.getCritDamageModifier();
        if (mainhand.getEffect().contains(Effect.FSOA)) {
            return new CritRange(base + 0.1, base + 0.25);
        } else {
            return new CritRange(base, base);
        }
    }
}
