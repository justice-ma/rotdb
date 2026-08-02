package com.rotdb.calculation.domain.resolvers.abilityDamage.invisible;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Effect;

public class PocketInvisibleResolver {
    public static double resolve(CalculationContext context) {
        if (context.getEquipment().getPocket().getEffect().contains(Effect.AMASCUTBOOK)) {
            return 1 + 0.1 * (context.getBuffs().stacks(BuffId.BOOKUPTIME) / 100.0);
        }

        if (context.getEquipment().getPocket().getEffect().contains(Effect.TOME_OF_THE_ICYENE)
                && context.getBuffs().has(BuffId.ICYENIC_FAITH)) {
            System.out.println("Prayer: " + context.getEquipment().getTotalPrayer());
            return 1 + 0.002 * context.getEquipment().getTotalPrayer();
        }
        return 1;
    }
}
