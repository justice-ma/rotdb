package com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike;

import com.rotdb.shared.combat.domain.model.enums.*;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;
import com.rotdb.shared.combat.domain.model.equipment.FamiliarContext;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;

import static com.rotdb.shared.combat.domain.model.enums.CombatStyles.MAGIC;

public class GlobalCritResolver {
    public static CritBonus resolve (BuffContext buff, FamiliarContext familiar, EquipmentModel equipment, PerkContext perk) {
        EquipmentSlot pocket = equipment.getPocket();
        EquipmentSlot ring = equipment.getRing();
        EquipmentSlot ammo = equipment.getAmmo();
        EquipmentSlot mainhand = equipment.getMainhand();
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

        double criticalStrikeChance = 0;
        double criticalStrikeDamage = 0;

        if (familiar.getName() == Familiars.KALGERIONDEMON) {
            criticalStrikeChance += 0.01;
        }

        if (buff.has(BuffId.KALG)) {
            criticalStrikeChance += 0.05;
        }

        if (buff.has(BuffId.ECLIPSEDSOUL)) {
            criticalStrikeChance += 0.04;
        }

        if (pocket.getEffect().contains(Effect.GRIMOIRE)) {
            criticalStrikeChance += 0.12;
        }

        if (pocket.getEffect().contains(Effect.CHAOTICGRIMOIRE)) {
            criticalStrikeChance += 0.07;
        }

        if (ring.getEffect().contains(Effect.REAVERSRING)) {
            criticalStrikeChance += 0.05;
        }

        if (ring.getEffect().contains(Effect.STALKERSRING) && mainhand.getClazz() == CombatStyles.RANGED
            && mainhand.getStyle() == WeaponStyle.ARROW) {
            if (buff.has(BuffId.ENCHANTMENTOFSHADOWS)) {
                criticalStrikeChance += 0.04;
                criticalStrikeDamage += 0.03;
            } else {
                criticalStrikeChance += 0.03;
            }
        }

        if (ring.getEffect().contains(Effect.CHAMPIONSRING) && buff.has(BuffId.BLEEDS) &&
                buff.stacks(BuffId.BLEEDS) > 0 && mainhand.getClazz() == CombatStyles.MELEE) {
            if (buff.has(BuffId.ENCHANTMENTOFHEROISM)) {
                criticalStrikeChance += 0.04;
                criticalStrikeDamage += 0.015 * buff.stacks(BuffId.BLEEDS);
            } else {
                criticalStrikeChance += 0.03;
            }
        }

        if (ammo.getEffect().contains(Effect.DEATHSPOREARROWS) && mainhand.getClazz() == CombatStyles.RANGED) {
            criticalStrikeChance += 0.03;
        }

        if (perk.has(Perks.BITING)) {
            if (perk.isEquipmentLevel20()) {
                criticalStrikeChance += perk.rank(Perks.BITING) * 0.022;
            } else {
                criticalStrikeChance += perk.rank(Perks.BITING) * 0.02;
            }
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

        if (equipment.getDracolichPieces() >= 3 && buff.has(BuffId.RAPIDFIREBUFF)  && mainhand.getClazz() == CombatStyles.RANGED) {
            criticalStrikeChance += 0.2;
        }

        if (equipment.getEliteDracolichPieces() >= 3 && buff.has(BuffId.RAPIDFIREBUFF) && mainhand.getClazz() == CombatStyles.RANGED) {
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

        if (buff.has(BuffId.CONCENTRATEDBLASTBUFF)  && mainhand.getClazz() == CombatStyles.MAGIC) {
            if (buff.has(BuffId.RUNICCHARGE)) {
                criticalStrikeChance += 0.45;
            } else {
                criticalStrikeChance += 0.15;
            }
        }

        if (buff.has(BuffId.GREATERCONCENTRATEDBLASTBUFF)  && mainhand.getClazz() == CombatStyles.MAGIC) {
            if (buff.has(BuffId.RUNICCHARGE)) {
                criticalStrikeChance += 0.51;
            } else {
                criticalStrikeChance += 0.21;
            }
        }

        if (buff.has(BuffId.FURYBUFF) && mainhand.getClazz() == CombatStyles.MELEE) criticalStrikeChance += 0.25;

        if (buff.has(BuffId.SMOKECLOUDED)) {
            if (mainhand.getClazz() == MAGIC) {
                criticalStrikeDamage += 0.15;
            } else {
                criticalStrikeDamage += 0.15 * 0.4;
            }
        }

        return new CritBonus(criticalStrikeChance, criticalStrikeDamage);
    }
}
