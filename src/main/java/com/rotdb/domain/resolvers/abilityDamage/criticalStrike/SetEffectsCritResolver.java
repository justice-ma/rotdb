package com.rotdb.domain.resolvers.abilityDamage.criticalStrike;

import com.rotdb.domain.model.context.AbilityContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.BuffId;
import com.rotdb.domain.model.equipment.EquipmentModel;
import com.rotdb.domain.model.equipment.EquipmentSlot;
import com.rotdb.domain.model.player.BuffContext;

import java.util.List;

import static com.rotdb.domain.model.enums.CombatStyles.RANGED;
import static com.rotdb.domain.model.enums.Effect.*;

public class SetEffectsCritResolver {
    public static CritBonus resolve(CalculationContext context) {
        double criticalStrikeChance = 0;
        double criticalStrikeDamage = 0;

        BuffContext buff = context.getBuffs();
        AbilityContext ability = context.getAbility();
        EquipmentModel equipment = context.getEquipment();
        EquipmentSlot head = equipment.getHead();
        EquipmentSlot body = equipment.getBody();
        EquipmentSlot legs = equipment.getLegs();
        EquipmentSlot boots = equipment.getBoots();
        EquipmentSlot gloves = equipment.getGloves();
        EquipmentSlot cape = equipment.getCape();
        List<EquipmentSlot> equipmentSlots = List.of(head, body, legs, boots, gloves, cape);
        int tuskaCount = 0;
        int sliskeCount = 0;
        int tectonicCount = 0;
        int eliteTectonicCount = 0;
        int dracolichCount = 0;
        int eliteDracolichCount = 0;
        int tumekensCount = 0;

        for (EquipmentSlot piece : equipmentSlots) {
            if (piece == null) continue;
            if (piece.getEffect().contains(WARPRIESTOFTUSKA)) tuskaCount++;
            if (piece.getEffect().contains(ANIMACOREOFSLISKE)) sliskeCount++;
            if (piece.getEffect().contains(TECTONIC)) tectonicCount++;
            if (piece.getEffect().contains(ELITETECTONIC)) eliteTectonicCount++;
            if (piece.getEffect().contains(DRACOLICH)) dracolichCount++;
            if (piece.getEffect().contains(ELITEDRACOLICH)) eliteDracolichCount++;
            if (piece.getEffect().contains(TUMEKENS)) tumekensCount++;
        }

        if (tuskaCount >= 3) {
            criticalStrikeChance += Math.min(0.06, tuskaCount / 100.0);
        }

        if (sliskeCount == 3) {
            criticalStrikeChance += 0.06;
        }

        if (tectonicCount > 0) {
            criticalStrikeChance += 0.01 * tectonicCount;
        }

        if (eliteTectonicCount > 0) {
            criticalStrikeChance += 0.02 * eliteTectonicCount;
        }

        if (dracolichCount >= 3 && buff.has(BuffId.RAPIDFIREBUFF)  && ability.getCombatStyle() == RANGED) {
            criticalStrikeChance += 0.2;
        }

        if (eliteDracolichCount >= 3 && buff.has(BuffId.RAPIDFIREBUFF) && ability.getCombatStyle() == RANGED) {
            criticalStrikeChance += 0.4;
        }

        if (tumekensCount >= 3 && buff.has(BuffId.SUNSHINE)) {
            criticalStrikeChance += 0.015 * tumekensCount;

            if (tumekensCount == 5 && buff.has(BuffId.ASPHYXIATEBUFF) ) {
                criticalStrikeDamage += 0.5;
            }
        }
        return new CritBonus(criticalStrikeChance, criticalStrikeDamage);
    }
}
