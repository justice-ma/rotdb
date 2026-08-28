package com.rotdb.calculation.domain.resolvers.baseAbilityDamage;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.enums.EquipmentType;

public class TeragardsAegisResolver {
    public static int resolve(CalculationContext context) {
        double delta = context.getEquipment().getTotalArmour(context.getSkills()) * 0.25;

        if (context.getEquipment().getOffhand().getEffect().contains(Effect.DEFENDER)) {
            delta *= 2;
        } else if (context.getEquipment().getOffhand().getType() == EquipmentType.SHIELD || context.getEquipment().getMainhand().getType() == EquipmentType.SHIELDBOW) {
            delta *= 3;
        }

        return (int) delta;
    }
}
