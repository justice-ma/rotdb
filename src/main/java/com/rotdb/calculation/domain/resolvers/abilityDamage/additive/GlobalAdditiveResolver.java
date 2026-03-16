package com.rotdb.calculation.domain.resolvers.abilityDamage.additive;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.context.TargetContext;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;
import com.rotdb.calculation.domain.model.player.BuffContext;

public class GlobalAdditiveResolver {
    public static double resolve(CalculationContext context) {
        BuffContext buff = context.getBuffs();
        TargetContext target = context.getTarget();
        EquipmentSlot pocket = context.getEquipment().getPocket();

        double mod = 0;
        if (buff.has(BuffId.STONEOFJAS) && buff.stacks(BuffId.STONEOFJAS) > 2) {
            mod += Math.min(buff.stacks(BuffId.STONEOFJAS) / 100.0, 0.06);
        }

        if (buff.has(BuffId.DRACONICFRUIT) && target.getName().startsWith("Mutated jadinko")) {
            mod += 0.02;
        }

        if (buff.has(BuffId.RUBYAURORA) && buff.stacks(BuffId.RUBYAURORA) > 0) {
            mod += Math.min(0.03, buff.stacks(BuffId.RUBYAURORA) / 100.0);
        }

        if (pocket.getEffect().contains(Effect.FULBOOK)) {
            mod += 0.2 * (buff.stacks(BuffId.BOOKUPTIME) / 100.0);
        }
        return mod;
    }
}
