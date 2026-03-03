package com.rotdb.resolvers.abilityDamage.criticalStrike;

import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.equipment.EquipmentSlot;

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
