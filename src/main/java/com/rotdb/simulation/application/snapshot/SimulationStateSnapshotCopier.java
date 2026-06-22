package com.rotdb.simulation.application.snapshot;

import com.rotdb.shared.combat.domain.model.context.TargetContext;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;
import com.rotdb.shared.combat.domain.model.equipment.FamiliarContext;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.*;
import com.rotdb.simulation.domain.model.context.RotationCombatState;
import com.rotdb.simulation.domain.model.context.SimulationState;

import java.util.*;

public class SimulationStateSnapshotCopier {
    public SimulationState copySimulationState(SimulationState source) {
        SimulationState copy = new SimulationState();
        copy.setState(copyCombatState(source.getState()));
        copy.setAdrenaline(source.getAdrenaline());
        copy.setMaximumAdrenaline(source.getMaximumAdrenaline());
        copy.setAbilityCooldownMap(new HashMap<>(source.getAbilityCooldownMap()));
        copy.setBuffCooldownMap(new HashMap<>(source.getBuffCooldownMap()));

        return copy;
    }

    private RotationCombatState copyCombatState(RotationCombatState source) {
        RotationCombatState copy = new RotationCombatState();
        copy.setBuffs(copyBuffContext(source));
        copy.setEquipment(copyEquipment(source));
        copy.setTarget(copyTarget(source));
        copy.setSkills(copySkills(source));
        copy.setPerk(copyPerks(source));
        copy.setFamiliar(copyFamiliar(source));
        copy.setPrayer(copyPrayer(source));
        copy.setSpell(copySpell(source));

        return copy;
    }

    private EquipmentModel copyEquipment(RotationCombatState source) {
        EquipmentModel copy = new EquipmentModel();
        copy.setHead(copyEquipmentSlot(source.getEquipment().getHead()));
        copy.setBody(copyEquipmentSlot(source.getEquipment().getBody()));
        copy.setLegs(copyEquipmentSlot(source.getEquipment().getLegs()));
        copy.setBoots(copyEquipmentSlot(source.getEquipment().getBoots()));
        copy.setGloves(copyEquipmentSlot(source.getEquipment().getGloves()));
        copy.setCape(copyEquipmentSlot(source.getEquipment().getCape()));
        copy.setPocket(copyEquipmentSlot(source.getEquipment().getPocket()));
        copy.setNeck(copyEquipmentSlot(source.getEquipment().getNeck()));
        copy.setRing(copyEquipmentSlot(source.getEquipment().getRing()));
        copy.setAmmo(copyEquipmentSlot(source.getEquipment().getAmmo()));
        copy.setMainhand(copyEquipmentSlot(source.getEquipment().getMainhand()));
        copy.setOffhand(copyEquipmentSlot(source.getEquipment().getOffhand()));
        copy.setQuiver(copyEquipmentSlot(source.getEquipment().getQuiver()));

        copy.setCombatStyle(source.getEquipment().getCombatStyle());
        copy.setAbilityDamage(source.getEquipment().getAbilityDamage());
        copy.setTuskaPieces(source.getEquipment().getTuskaPieces());
        copy.setSliskePieces(source.getEquipment().getSliskePieces());
        copy.setTectonicPieces(source.getEquipment().getTectonicPieces());
        copy.setEliteTectonicPieces(source.getEquipment().getEliteTectonicPieces());
        copy.setDracolichPieces(source.getEquipment().getDracolichPieces());
        copy.setEliteDracolichPieces(source.getEquipment().getEliteDracolichPieces());
        copy.setTumekensPieces(source.getEquipment().getTumekensPieces());

        return copy;
    }

    private EquipmentSlot copyEquipmentSlot(EquipmentSlot source) {
        EquipmentSlot copy = new EquipmentSlot();
        copy.setId(source.getId());
        copy.setTier(source.getTier());
        copy.setDamageTier(source.getDamageTier());
        copy.setAccuracyTier(source.getAccuracyTier());
        copy.setArmourTier(source.getArmourTier());
        copy.setAttackRange(source.getAttackRange());
        copy.setStrength(source.getStrength());
        copy.setRanged(source.getRanged());
        copy.setMagic(source.getMagic());
        copy.setNecromancy(source.getNecromancy());
        copy.setTitle(source.getTitle());
        copy.setRequiredSkill(source.getRequiredSkill());
        copy.setRequiredLevel(source.getRequiredLevel());
        copy.setMembers(source.isMembers());
        copy.setClazz(source.getClazz());
        copy.setSlot(source.getSlot());
        copy.setEffect(source.getEffect().isEmpty() || source.getEffect() == null ? EnumSet.noneOf(Effect.class) : EnumSet.copyOf(source.getEffect()));
        copy.setStyle(source.getStyle());
        return copy;
    }

    private BuffContext copyBuffContext(RotationCombatState source) {
        BuffContext copy = new BuffContext();
        copy.setBuffStacks(new HashMap<>(source.getBuffs().getBuffStacks()));
        copy.setBuffSet(new HashSet<>(source.getBuffs().getBuffSet()));

        List<PotionContext> potionListCopy = new ArrayList<>();
        for (PotionContext potion : source.getBuffs().getPotionBuffs()) {
            PotionContext potionCopy = new PotionContext(
                    potion.getPotion(),
                    potion.getStat()
            );
            potionListCopy.add(potionCopy);
        }
        copy.setPotionBuffs(potionListCopy);
        return copy;
    }

    private TargetContext copyTarget(RotationCombatState source) {
        TargetContext copy = new TargetContext();
        copy.setMaxHp(source.getTarget().getMaxHp());
        copy.setCurrentHp(source.getTarget().getCurrentHp());
        copy.setStartingTask(source.getTarget().getStartingTask());
        copy.setCurrentTask(source.getTarget().getCurrentTask());
        copy.setSize(source.getTarget().getSize());
        copy.setDefence(source.getTarget().getDefence());
        copy.setArmour(source.getTarget().getArmour());
        copy.setAffinity(source.getTarget().getAffinity());
        copy.setName(source.getTarget().getName());
        copy.setWeakness(source.getTarget().getWeakness());
        copy.setWeaponWeakness(source.getTarget().getWeaponWeakness());
        copy.setTags(EnumSet.copyOf(source.getTarget().getTags()));
        return copy;
    }

    private SkillsContext copySkills(RotationCombatState source) {
        SkillsContext copy = new SkillsContext();
        copy.setBoostedNecromancy(source.getSkills().getBoostedNecromancy());
        copy.setConstitution(source.getSkills().getConstitution());
        copy.setBoostedStrength(source.getSkills().getBoostedStrength());
        copy.setBoostedRanged(source.getSkills().getBoostedRanged());
        copy.setBoostedMagic(source.getSkills().getBoostedMagic());
        copy.setBoostedAttack(source.getSkills().getBoostedAttack());
        copy.setBoostedDefence(source.getSkills().getBoostedDefence());
        copy.setSummoning(source.getSkills().getSummoning());
        copy.setCurrentHp(source.getSkills().getCurrentHp());
        copy.setMaxHp(source.getSkills().getMaxHp());
        copy.setBaseNecromancy(source.getSkills().getBaseNecromancy());
        copy.setBaseStrength(source.getSkills().getBaseStrength());
        copy.setBaseRanged(source.getSkills().getBaseRanged());
        copy.setBaseMagic(source.getSkills().getBaseMagic());
        copy.setBaseAttack(source.getSkills().getBaseAttack());
        copy.setBaseDefence(source.getSkills().getBaseDefence());
        return copy;
    }

    private PerkContext copyPerks(RotationCombatState source) {
        PerkContext copy = new PerkContext();
        copy.setPerk(new HashMap<>(source.getPerk().getPerk()));
        copy.setEquipmentLevel20(source.getPerk().isEquipmentLevel20());
        copy.setGenocidalRank(source.getPerk().getGenocidalRank());

        return copy;
    }

    private FamiliarContext copyFamiliar(RotationCombatState source) {
        FamiliarContext copy = new FamiliarContext();
        copy.setName(source.getFamiliar().getName());
        return copy;
    }

    private PrayerContext copyPrayer(RotationCombatState source) {
        PrayerContext copy = new PrayerContext();
        copy.setPrayerStrengthBonus(source.getPrayer().getPrayerStrengthBonus());
        copy.setPrayerAccuracyBonus(source.getPrayer().getPrayerAccuracyBonus());
        copy.setEclipsedSoul(source.getPrayer().getEclipsedSoul());
        copy.setSelected(EnumSet.copyOf(source.getPrayer().getSelected()));
        return copy;
    }

    private SpellContext copySpell(RotationCombatState source) {
        SpellContext copy = new SpellContext();
        copy.setSpell(source.getSpell().getSpell());
        return copy;
    }
}
