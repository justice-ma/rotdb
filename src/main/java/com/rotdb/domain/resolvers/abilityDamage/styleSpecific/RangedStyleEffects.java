package com.rotdb.domain.resolvers.abilityDamage.styleSpecific;

import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.context.TargetContext;
import com.rotdb.domain.model.enums.Effect;
import com.rotdb.domain.model.equipment.EquipmentSlot;

public class RangedStyleEffects {
    public static void apply(CalculationContext context, AbilityHitsContext hit) {
        EquipmentSlot ammo = context.getEquipment().getAmmo();
        TargetContext target = context.getTarget();

        if (ammo.getEffect().contains(Effect.RUBYE)) {
            int m = target.getMaxHp();
            int c = target.getCurrentHp();
            double ad = 0.25 + (c * 1.0 / m);
            int add = (int) (ad * context.getDamage().getBaseDamage());
            hit.setCurrentMin(context.getDamage().getCurrentMin() + add);
            hit.setCurrentMax(context.getDamage().getCurrentMax() + add);
            hit.setCurrentDamage((context.getDamage().getCurrentMin() + context.getDamage().getCurrentMax()) / 2);
        }

        if (/* TODO: Add quiver slot for pernix's quiver */ (double) target.getCurrentHp() / target.getMaxHp() < 0.25) {
            hit.setCurrentMax((int) (context.getDamage().getCurrentMax() * 1.04));
            hit.setCurrentDamage((context.getDamage().getCurrentMin() + context.getDamage().getCurrentMax()) / 2);
        }
    }
}
