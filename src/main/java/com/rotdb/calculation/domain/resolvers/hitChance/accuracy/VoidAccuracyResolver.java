package com.rotdb.calculation.domain.resolvers.hitChance.accuracy;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;

import java.util.List;

public class VoidAccuracyResolver {
    public static double resolve(CalculationContext context) {
        EquipmentSlot head = context.getEquipment().getHead();
        EquipmentSlot body = context.getEquipment().getBody();
        EquipmentSlot legs = context.getEquipment().getLegs();
        EquipmentSlot gloves = context.getEquipment().getGloves();
        EquipmentSlot offhand = context.getEquipment().getOffhand();
        List<EquipmentSlot> voidKnight = List.of(head, body, legs, gloves, offhand);

        double accuracyModifier = 0;
        int baseVoid = 0;

        for (EquipmentSlot eq : voidKnight) {
            if (eq.getEffect().contains(Effect.VOID)) {
                baseVoid++;
            }
        }
        if (baseVoid > 3 && context.getEquipment().getHead().getClazz() == context.getEquipment().getCombatStyle()) {
            accuracyModifier += 0.03;
        }
        return accuracyModifier;
    }
}
