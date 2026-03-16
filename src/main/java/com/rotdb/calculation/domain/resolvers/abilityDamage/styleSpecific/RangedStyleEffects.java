package com.rotdb.calculation.domain.resolvers.abilityDamage.styleSpecific;

import com.rotdb.calculation.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.context.TargetContext;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;

public class RangedStyleEffects {
    public static void apply(CalculationContext context, AbilityHitsContext hit) {
        EquipmentSlot ammo = context.getEquipment().getAmmo();
        EquipmentSlot quiver = context.getEquipment().getQuiver();
        TargetContext target = context.getTarget();

        if (ammo.getEffect().contains(Effect.RUBYE)) {
            int m = target.getMaxHp();
            int c = target.getCurrentHp();
            double ad = 0.25 + (c * 1.0 / m);
            int add = (int) (ad * context.getDamage().getBaseDamage());
            hit.setCurrentMin(hit.getCurrentMin() + add);
            hit.setCurrentMax(hit.getCurrentMax() + add);
            hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);
        }
        if (quiver.getEffect().contains(Effect.PERNIXQUIVER) && (double) target.getCurrentHp() / target.getMaxHp() < 0.25) {
            hit.setCurrentMax((int) (hit.getCurrentMax() * 1.04));
            hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);
        }
    }
}
