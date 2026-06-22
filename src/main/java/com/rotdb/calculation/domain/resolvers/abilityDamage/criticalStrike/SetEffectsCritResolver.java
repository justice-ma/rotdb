package com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;
import com.rotdb.shared.combat.domain.model.player.BuffContext;

import java.util.List;

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
            if (piece.getEffect().contains(Effect.WARPRIESTOFTUSKA))
                equipment.setTuskaPieces(equipment.getTuskaPieces() + 1);
            if (piece.getEffect().contains(Effect.ANIMACOREOFSLISKE))
                equipment.setSliskePieces(equipment.getSliskePieces() + 1);
            if (piece.getEffect().contains(Effect.TECTONIC))
                equipment.setTectonicPieces(equipment.getEliteTectonicPieces() + 1);
            if (piece.getEffect().contains(Effect.ELITETECTONIC))
                equipment.setEliteTectonicPieces(equipment.getEliteTectonicPieces() + 1);
            if (piece.getEffect().contains(Effect.DRACOLICH))
                equipment.setDracolichPieces(equipment.getDracolichPieces() + 1);
            if (piece.getEffect().contains(Effect.ELITEDRACOLICH))
                equipment.setEliteDracolichPieces(equipment.getEliteDracolichPieces() + 1);
            if (piece.getEffect().contains(Effect.TUMEKENS))
                equipment.setTumekensPieces(equipment.getTumekensPieces() + 1);
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

        if (equipment.getDracolichPieces() >= 3 && buff.has(BuffId.RAPIDFIREBUFF) && ability.getCombatStyle() == CombatStyles.RANGED) {
            criticalStrikeChance += 0.2;
        }

        if (equipment.getEliteDracolichPieces() >= 3 && buff.has(BuffId.RAPIDFIREBUFF) && ability.getCombatStyle() == CombatStyles.RANGED) {
            criticalStrikeChance += 0.4;
        }

        if (buff.has(BuffId.ASPHYXIATEBUFF)) {
            criticalStrikeDamage += 0.15;
        }

        if (equipment.getTumekensPieces() >= 3 && buff.has(BuffId.SUNSHINE)) {
            criticalStrikeChance += 0.015 * equipment.getTumekensPieces();

            if (equipment.getTumekensPieces() == 5 && buff.has(BuffId.ASPHYXIATEBUFF)) {
                criticalStrikeDamage += 0.2;
            }
        }
        return new CritBonus(criticalStrikeChance, criticalStrikeDamage);
    }
}
