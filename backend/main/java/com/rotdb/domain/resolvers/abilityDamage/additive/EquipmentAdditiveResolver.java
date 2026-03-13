package com.rotdb.domain.resolvers.abilityDamage.additive;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.Effect;
import com.rotdb.domain.model.equipment.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

public class EquipmentAdditiveResolver {
    public static double resolve(CalculationContext context) {
        EquipmentSlot head = context.getEquipment().getHead();
        EquipmentSlot body = context.getEquipment().getBody();
        EquipmentSlot legs = context.getEquipment().getLegs();
        EquipmentSlot gloves = context.getEquipment().getGloves();
        EquipmentSlot offhand = context.getEquipment().getOffhand();
        List<EquipmentSlot> voidKnight = new ArrayList<>(List.of(head, body, legs, gloves, offhand));

        int superiorVoid = 0;
        int baseVoid = 0;

        double mod = 0;
        for (EquipmentSlot eq : voidKnight) {
            if (eq.getEffect().contains(Effect.VOID)) {
                if (eq.getEffect().contains(Effect.SUPERIOR)) {
                    baseVoid++;
                    superiorVoid++;
                } else {
                    baseVoid++;
                }
            }
        }
        if (superiorVoid > 3 && context.getEquipment().getHead().getClazz() == context.getEquipment().getCombatStyle()) {
            mod += 0.07;
        } else if (baseVoid > 3 && context.getEquipment().getHead().getClazz() == context.getEquipment().getCombatStyle()) {
            mod += 0.05;
        }

        return mod;
    }
}
