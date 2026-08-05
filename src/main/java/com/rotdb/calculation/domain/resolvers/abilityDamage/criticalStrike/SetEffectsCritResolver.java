package com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike;

import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;

public class SetEffectsCritResolver {
    public static CritBonus resolve(CalculationContext context) {
        double criticalStrikeChance = 0;
        double criticalStrikeDamage = 0;

        BuffContext buff = context.getBuffs();
        AbilityContext ability = context.getAbility();
        EquipmentModel equipment = context.getEquipment();
        equipment.setTuskaPieces(equipment.countSetPieces(Effect.WARPRIESTOFTUSKA, buff,
                equipment.getHead(), equipment.getBody(), equipment.getLegs(), equipment.getBoots(), equipment.getGloves(), equipment.getCape()));
        equipment.setSliskePieces(equipment.countSetPieces(Effect.ANIMACOREOFSLISKE, buff,
                equipment.getHead(), equipment.getBody(), equipment.getLegs(), equipment.getBoots(), equipment.getGloves(), equipment.getCape()));
        equipment.setTectonicPieces(equipment.countSetPieces(Effect.TECTONIC, buff,
                equipment.getHead(), equipment.getBody(), equipment.getLegs(), equipment.getBoots(), equipment.getGloves(), equipment.getCape()));
        equipment.setEliteTectonicPieces(equipment.countSetPieces(Effect.ELITETECTONIC, buff,
                equipment.getHead(), equipment.getBody(), equipment.getLegs(), equipment.getBoots(), equipment.getGloves(), equipment.getCape()));
        equipment.setDracolichPieces(equipment.countSetPieces(Effect.DRACOLICH, buff,
                equipment.getHead(), equipment.getBody(), equipment.getLegs(), equipment.getBoots(), equipment.getGloves(), equipment.getCape()));
        equipment.setEliteDracolichPieces(equipment.countSetPieces(Effect.ELITEDRACOLICH, buff,
                equipment.getHead(), equipment.getBody(), equipment.getLegs(), equipment.getBoots(), equipment.getGloves(), equipment.getCape()));
        equipment.setTumekensPieces(equipment.countSetPieces(Effect.TUMEKENS, buff,
                equipment.getHead(), equipment.getBody(), equipment.getLegs(), equipment.getBoots(), equipment.getGloves(), equipment.getCape()));

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

        if (equipment.getDracolichPieces() >= 3 && buff.has(BuffId.RAPIDFIREBUFF)  && ability.getCombatStyle() == CombatStyles.RANGED) {
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

            if (equipment.getTumekensPieces() == 5 && buff.has(BuffId.ASPHYXIATEBUFF) ) {
                criticalStrikeDamage += 0.2;
            }
        }
        return new CritBonus(criticalStrikeChance, criticalStrikeDamage);
    }
}
