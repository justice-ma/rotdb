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

        for (EquipmentSlot piece : equipmentSlots) {
            if (piece == null) continue;
            if (piece.getEffect().contains(WARPRIESTOFTUSKA)) equipment.setTuskaPieces(equipment.getTuskaPieces() + 1);
            if (piece.getEffect().contains(ANIMACOREOFSLISKE)) equipment.setSliskePieces(equipment.getSliskePieces() + 1);
            if (piece.getEffect().contains(TECTONIC)) equipment.setTectonicPieces(equipment.getEliteTectonicPieces() + 1);
            if (piece.getEffect().contains(ELITETECTONIC)) equipment.setEliteTectonicPieces(equipment.getEliteTectonicPieces() + 1);
            if (piece.getEffect().contains(DRACOLICH)) equipment.setDracolichPieces(equipment.getDracolichPieces() + 1);
            if (piece.getEffect().contains(ELITEDRACOLICH)) equipment.setEliteDracolichPieces(equipment.getEliteDracolichPieces() + 1);
            if (piece.getEffect().contains(TUMEKENS)) equipment.setTumekensPieces(equipment.getTumekensPieces() + 1);
        }

        if (equipment.getTuskaPieces() >= 3) {
            criticalStrikeChance += Math.min(0.06, equipment.getTuskaPieces() / 100.0);
        }

        if (equipment.getSliskePieces() == 3) {
            criticalStrikeChance += 0.06;
        }

        if (equipment.getTectonicPieces() > 0) {
            criticalStrikeChance += 0.01 * equipment.getTectonicPieces();
        }

        if (equipment.getEliteTectonicPieces() > 0) {
            criticalStrikeChance += 0.02 * equipment.getEliteTectonicPieces();
        }

        if (equipment.getDracolichPieces() >= 3 && buff.has(BuffId.RAPIDFIREBUFF)  && ability.getCombatStyle() == RANGED) {
            criticalStrikeChance += 0.2;
        }

        if (equipment.getEliteDracolichPieces() >= 3 && buff.has(BuffId.RAPIDFIREBUFF) && ability.getCombatStyle() == RANGED) {
            criticalStrikeChance += 0.4;
        }

        if (equipment.getTumekensPieces() >= 3 && buff.has(BuffId.SUNSHINE)) {
            criticalStrikeChance += 0.015 * equipment.getTumekensPieces();

            if (equipment.getTumekensPieces() == 5 && buff.has(BuffId.ASPHYXIATEBUFF) ) {
                criticalStrikeDamage += 0.35;
            }
        }
        return new CritBonus(criticalStrikeChance, criticalStrikeDamage);
    }
}
