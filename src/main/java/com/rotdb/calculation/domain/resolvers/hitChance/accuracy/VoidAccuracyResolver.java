package com.rotdb.calculation.domain.resolvers.hitChance.accuracy;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;

import java.util.List;

public class VoidAccuracyResolver {
    public static double resolve(CalculationContext context) {
        EquipmentModel equipment = context.getEquipment();
        EquipmentSlot head = equipment.getHead();
        EquipmentSlot body = equipment.getBody();
        EquipmentSlot legs = equipment.getLegs();
        EquipmentSlot gloves = equipment.getGloves();
        EquipmentSlot offhand = equipment.getOffhand();
        List<EquipmentSlot> voidKnight = List.of(head, body, legs, gloves, offhand);
        int pieceValue = 1;

        double accuracyModifier = 0;
        int baseVoid = 0;

        for (EquipmentSlot eq : voidKnight) {
            if (eq.getEffect().contains(Effect.VOID)) {
                baseVoid += pieceValue;
            }
        }
        if (baseVoid > 3 && equipment.getHead().getClazz() == equipment.getCombatStyle()) {
            accuracyModifier += 0.03;
        }
        return accuracyModifier;
    }
}
