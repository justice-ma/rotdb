package com.rotdb.simulation.domain;

import com.rotdb.calculation.domain.engine.CalculationEngine;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.TargetContext;
import com.rotdb.shared.combat.domain.model.enums.*;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;
import com.rotdb.shared.combat.domain.model.equipment.FamiliarContext;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.PrayerContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;
import com.rotdb.shared.combat.domain.model.player.SpellContext;
import com.rotdb.simulation.application.service.RotationTimelineService;
import com.rotdb.simulation.application.snapshot.SimulationStateSnapshotCopier;
import com.rotdb.simulation.domain.model.config.ProcMode;
import com.rotdb.simulation.domain.model.config.SimulationConfig;
import com.rotdb.simulation.domain.model.context.*;

import java.util.*;

public class Sandbox {
    public static void main(String[] args) {
        RotationCombatState state = sampleRangedState();
        List<AbilityPlacement> abilities = new ArrayList<>();
        AbilityPlacement placement = new AbilityPlacement();
        placement.setCastTick(3);
        placement.setPlacedAbility(AbilityId.GREATERFLURRY);
        placement.setReleaseTick(0);
        abilities.add(placement);
        AbilityPlacement placement2 = new AbilityPlacement();
        placement2.setCastTick(13);
        placement2.setReleaseTick(0);
        placement2.setPlacedAbility(AbilityId.ICYTEMPEST);
        abilities.add(placement2);
        AbilityPlacement placement3 = new AbilityPlacement();
        placement3.setCastTick(17);
        placement3.setReleaseTick(0);
        placement3.setPlacedAbility(AbilityId.ICYTEMPEST);
        abilities.add(placement3);
//        AbilityPlacement placement4 = new AbilityPlacement();
//        placement4.setCastTick(12);
//        placement4.setReleaseTick(14);
//        placement4.setPlacedAbility(AbilityId.SNAPSHOT);
//        abilities.add(placement4);
//        AbilityPlacement placement5 = new AbilityPlacement();
//        placement5.setCastTick(15);
//        placement5.setReleaseTick(14);
//        placement5.setPlacedAbility(AbilityId.GREATERRICOCHET);
//        abilities.add(placement5);
//        AbilityPlacement placement6 = new AbilityPlacement();
//        placement6.setCastTick(18);
//        placement6.setReleaseTick(14);
//        placement6.setPlacedAbility(AbilityId.SNAPSHOT);
//        abilities.add(placement6);

        List<BuffPlacement> buffs = new ArrayList<>();
        BuffPlacement buff = new BuffPlacement();
        buff.setPlacementTick(0);
        buff.setBuffId(BuffId.GRAVITATEBUFF);
        buffs.add(buff);
//        BuffPlacement buff2 = new BuffPlacement();
//        buff2.setPlacementTick(0);
//        buff2.setBuffId(BuffId.SUPERADRENALINEPOTION);
//        buffs.add(buff2);
//        BuffPlacement buff3 = new BuffPlacement();
//        buff3.setPlacementTick(4);
//        buff3.setBuffId(BuffId.NATURALINSTINCT);
//        buffs.add(buff3);
//        BuffPlacement buff4 = new BuffPlacement();
//        buff4.setPlacementTick(3);
//        buff4.setBuffId(BuffId.SMOKECLOUDED);
//        buffs.add(buff4);

        state.getBuffs().getBuffSet().add(BuffId.VULNED);

        SimulationConfig config = new SimulationConfig();
        config.setProcMode(ProcMode.FORCED);
        config.setRandomSeed(67L);
        CalculationEngine engine = new CalculationEngine();
        SimulationStateSnapshotCopier stateSnapshotCopier = new SimulationStateSnapshotCopier();
        RotationTimeline timeline = new RotationTimelineService(engine, stateSnapshotCopier)
                .build(state, abilities, buffs, config);

        printTimeline(timeline);
    }

    private static void printTimeline(RotationTimeline timeline) {
        for (TickSnapshot tick : timeline.getTimeline()) {
            System.out.println("T" + tick.getTick());
            System.out.println("  casts:     " + formatAbilities(tick.getCastAbilities()));
            System.out.println("  releases:  " + formatAbilities(tick.getReleasedAbilities()));
            System.out.println("  buffs:     " + formatBuffs(tick.getPlacedBuffs()));
            System.out.println("  state:     buffs  " + tick.getStartingCombatState().getBuffs().getBuffSet()
                    + " -> " + tick.getEndingCombatState().getBuffs().getBuffSet());
            System.out.println("             stacks " + formatStacks(tick.getEndingCombatState().getBuffs().getBuffStacks()));
            System.out.println("             debuffs" + tick.getStartingCombatState().getTarget().getDebuffs()
                    + " -> " + tick.getEndingCombatState().getTarget().getDebuffs());
            System.out.println("  adren:     " + tick.getStartingAdrenaline() + " -> " + tick.getEndingAdrenaline());
            System.out.println("  hits:      " + tick.getLandedHits().size());
            System.out.println("  damage:    " + cumulativeDamageForTick(tick));
            System.out.println("  cooldowns: " + tick.getEndingAbilityCooldownMap());
            System.out.println("             " + tick.getEndingBuffCooldownMap());
            System.out.println("  durations: " + formatDurations(tick.getEndingActiveBuffDurationMap()));
            System.out.println("  warnings:  " + formatWarnings(tick.getWarnings()));
            System.out.println();
        }
    }

    private static String formatAbilities(List<AbilityPlacement> abilities) {
        if (abilities == null || abilities.isEmpty()) {
            return "--";
        }

        List<String> names = new ArrayList<>();
        for (AbilityPlacement ability : abilities) {
            names.add(ability.getPlacedAbility() == null ? "null" : ability.getPlacedAbility().name());
        }
        return String.join(", ", names);
    }

    private static String formatBuffs(List<BuffPlacement> buffs) {
        if (buffs == null || buffs.isEmpty()) {
            return "--";
        }

        List<String> names = new ArrayList<>();
        for (BuffPlacement buff : buffs) {
            names.add(buff.getBuffId() == null ? "null" : buff.getBuffId().name());
        }
        return String.join(", ", names);
    }

    private static String formatStacks(Map<BuffId, Integer> stacks) {
        if (stacks == null || stacks.isEmpty()) {
            return "--";
        }

        List<String> names = new ArrayList<>();
        stacks.forEach((key, value) -> {
            names.add(key.toString() + " -> " + value);
        });
        return String.join(", ", names);
    }

    private static String formatWarnings(List<String> warnings) {
        if (warnings == null || warnings.isEmpty()) {
            return "--";
        }

        return String.join(" | ", warnings);
    }

    private static List<String> formatDurations(Map<BuffId, ActiveBuffState> durations) {
        List<String> names = new ArrayList<>();
        if (durations == null || durations.isEmpty()) {
            names.add("--");
            return names;
        }
        for (Map.Entry<BuffId, ActiveBuffState> entry : durations.entrySet()) {
            names.add(String.join(" | ", entry.getKey() + ": " + entry.getValue().getDuration()));
        }
        return names;
    }

    private static int cumulativeDamageForTick(TickSnapshot tick) {
        int damage = 0;
        for (TimelineHit hit : tick.getLandedHits()) {
            damage += hit.getHitAvgDamage();
        }
        return damage;
    }

    private static RotationCombatState sampleRangedState() {
        EquipmentSlot mainhand = new EquipmentSlot();
        mainhand.setTitle("Snadbox bow");
        mainhand.setClazz(CombatStyles.MELEE);
        mainhand.setSlot(Slots.MAINHAND);
        mainhand.setTier(90);
        mainhand.setDamageTier(90);
        mainhand.setAccuracyTier(90);
        mainhand.setRanged(0);
        mainhand.setEffect(EnumSet.of(Effect.PRIMORDIALICESTACKS));

        EquipmentSlot ammo = EquipmentSlot.emptySlot();
        ammo.setDamageTier(90);
        ammo.setEffect(EnumSet.of(Effect.WENARROWS));

        EquipmentSlot offhand = EquipmentSlot.emptySlot();
        offhand.setDamageTier(95);
        offhand.setEffect(EnumSet.of(Effect.OFFHANDLENG));

        EquipmentSlot head = new EquipmentSlot();
        EquipmentSlot body = new EquipmentSlot();
        EquipmentSlot legs = new EquipmentSlot();
        EquipmentSlot boots = new EquipmentSlot();
        EquipmentSlot gloves = new EquipmentSlot();

        head.setEffect(EnumSet.of(Effect.VESTMENTSOFHAVOC));
        body.setEffect(EnumSet.of(Effect.VESTMENTSOFHAVOC));
        legs.setEffect(EnumSet.of(Effect.TUMEKENS));
        boots.setEffect(EnumSet.of(Effect.TUMEKENS));
        gloves.setEffect(EnumSet.of(Effect.TUMEKENS));

        EquipmentModel equipment = new EquipmentModel();
        equipment.setMainhand(mainhand);
        equipment.setOffhand(offhand);
        equipment.setAmmo(ammo);
        equipment.setHead(head);
        equipment.setBody(body);
        equipment.setLegs(legs);
        equipment.setBoots(boots);
        equipment.setGloves(gloves);
        equipment.fillMissingWithEmpty();

        SkillsContext skills = new SkillsContext();
        skills.setBaseRanged(120);
        skills.setBoostedRanged(120);
        skills.setBaseAttack(120);
        skills.setBoostedAttack(120);
        skills.setBaseStrength(120);
        skills.setBoostedStrength(120);
        skills.setBaseMagic(120);
        skills.setBoostedMagic(120);
        skills.setBaseNecromancy(120);
        skills.setBoostedNecromancy(120);
        skills.setCurrentHp(10000);
        skills.setMaxHp(10000);

        BuffContext buffs = new BuffContext();
        buffs.setBuffSet(new HashSet<>());
        buffs.setBuffStacks(new HashMap<>());
        buffs.setPotionBuffs(new ArrayList<>());

        PerkContext perks = new PerkContext();
        perks.setPerk(new HashMap<>());
        perks.setGenocidalRank(0.0);
        perks.setEquipmentLevel20(false);

        PrayerContext prayer = new PrayerContext();
        prayer.setSelected(EnumSet.noneOf(Prayer.class));

        SpellContext spell = new SpellContext();
        spell.setSpell(Spells.WINDRUSH);

        TargetContext target = new TargetContext();
        target.setName("Sandbox target");
        target.setMaxHp(100000);
        target.setCurrentHp(100000);
        target.setSize(1);
        target.setArmour(1);
        target.setDefence(1);
        target.setAffinity(100);
        target.setTags(EnumSet.noneOf(TargetTags.class));

        RotationCombatState state = new RotationCombatState();
        state.setEquipment(equipment);
        state.setSkills(skills);
        state.setBuffs(buffs);
        state.setPerk(perks);
        state.setPrayer(prayer);
        state.setSpell(spell);
        state.setTarget(target);
        state.setFamiliar(new FamiliarContext());

        return state;
    }
}
