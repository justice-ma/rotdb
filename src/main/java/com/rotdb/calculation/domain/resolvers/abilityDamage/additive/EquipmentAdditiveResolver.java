package com.rotdb.calculation.domain.resolvers.abilityDamage.additive;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

public class EquipmentAdditiveResolver {
    public static double resolve(CalculationContext context) {
        EquipmentModel equipment = context.getEquipment();
        EquipmentSlot head = equipment.getHead();
        EquipmentSlot body = equipment.getBody();
        EquipmentSlot legs = equipment.getLegs();
        EquipmentSlot gloves = equipment.getGloves();
        EquipmentSlot offhand = equipment.getOffhand();
        List<EquipmentSlot> voidKnight = new ArrayList<>(List.of(head, body, legs, gloves, offhand));
        int pieceValue = 1;

        int superiorVoid = 0;
        int baseVoid = 0;

        double mod = 0;
        for (EquipmentSlot eq : voidKnight) {
            if (eq.getEffect().contains(Effect.VOID)) {
                if (eq.getEffect().contains(Effect.SUPERIOR)) {
                    baseVoid += pieceValue;
                    superiorVoid += pieceValue;
                } else {
                    baseVoid += pieceValue;
                }
            }
        }
        if (superiorVoid > 3 && equipment.getHead().getClazz() == equipment.getCombatStyle()) {
            mod += 0.07;
        } else if (baseVoid > 3 && equipment.getHead().getClazz() == equipment.getCombatStyle()) {
            mod += 0.05;
        }

        return mod;
    }
}
