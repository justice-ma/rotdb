package com.rotdb.resolvers.abilityDamage.styleSpecific;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.context.TargetContext;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.equipment.EquipmentSlot;

public class BoltResolver {
    public static double resolve(CalculationContext context) {
        EquipmentSlot ammo = context.getEquipment().getAmmo();
        TargetContext target = context.getTarget();

        // TODO: Add either toggle for proc or weigh the damage by the chance
        //  Procs from sirenic and gemstone can happen after as well if no proc
        //  Gemstone can activate from all styles ------ needs implementation
        double mod = 1;
            if (ammo.getEffect().contains(Effect.OPALE)) {
                mod *= 1.1;
            } else if (ammo.getEffect().contains(Effect.PEARLE)) {
                // TODO: Add check if target is weak to water/fire +15% if water, -15% if fire
                mod *= 1.15;
            }  else if (ammo.getEffect().contains(Effect.DIAMONDE)) {
                mod *= 1.15;
            } else if (ammo.getEffect().contains(Effect.DRAGONSTONEE)) {
                if (!target.isDragon()) {
                    mod *= 1.25;
                }
            } else if (ammo.getEffect().contains(Effect.ONYXE)) {
                mod *= 1.25;
            }
        return mod;
    }
}
