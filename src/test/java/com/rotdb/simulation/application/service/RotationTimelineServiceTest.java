package com.rotdb.simulation.application.service;

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
import com.rotdb.simulation.application.processors.ProcProcessor;
import com.rotdb.simulation.application.snapshot.SimulationStateSnapshotCopier;
import com.rotdb.simulation.domain.model.config.ProcMode;
import com.rotdb.simulation.domain.model.config.SimulationConfig;
import com.rotdb.simulation.domain.model.context.*;
import com.rotdb.simulation.domain.resolvers.cooldown.AbilityCooldownKeyResolver;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RotationTimelineServiceTest {
    CalculationEngine engine = new CalculationEngine();
    SimulationStateSnapshotCopier copier = new SimulationStateSnapshotCopier();

    private static RotationCombatState sampleRangedState() {
        return sampleState(CombatStyles.RANGED, "Sandbox bow");
    }

    private static RotationCombatState sampleMeleeState() {
        return sampleState(CombatStyles.MELEE, "Sandbox sword");
    }

    private static RotationCombatState sampleMagicState() {
        return sampleState(CombatStyles.MAGIC, "Sandbox staff");
    }

    private static RotationCombatState sampleNecromancyState() {
        return sampleState(CombatStyles.NECROMANCY, "Sandbox lantern");
    }

    private static RotationCombatState sampleState(CombatStyles style, String mainhandTitle) {
        RotationCombatState state = new RotationCombatState();
        state.setEquipment(sampleEquipment(style, mainhandTitle));
        state.setSkills(sampleSkills());
        state.setBuffs(sampleBuffs());
        state.setPerk(samplePerks());
        state.setPrayer(samplePrayer());
        state.setSpell(sampleSpell());
        state.setTarget(sampleTarget());
        state.setFamiliar(new FamiliarContext());

        return state;
    }

    private static EquipmentModel sampleEquipment(CombatStyles style, String mainhandTitle) {
        EquipmentSlot mainhand = new EquipmentSlot();
        mainhand.setTitle(mainhandTitle);
        mainhand.setClazz(style);
        mainhand.setSlot(Slots.MAINHAND);
        mainhand.setTier(90);
        mainhand.setDamageTier(90);
        mainhand.setAccuracyTier(90);
        applyStyleBonus(mainhand, style);
        mainhand.setEffect(EnumSet.noneOf(Effect.class));

        EquipmentSlot ammo = EquipmentSlot.emptySlot();
        ammo.setDamageTier(90);

        EquipmentModel equipment = new EquipmentModel();
        equipment.setCombatStyle(style);
        equipment.setMainhand(mainhand);
        equipment.setAmmo(ammo);
        equipment.fillMissingWithEmpty();

        return equipment;
    }

    private static void applyStyleBonus(EquipmentSlot slot, CombatStyles style) {
        switch (style) {
            case MELEE -> slot.setStrength(0);
            case RANGED -> slot.setRanged(0);
            case MAGIC -> slot.setMagic(0);
            case NECROMANCY -> slot.setNecromancy(0);
        }
    }

    private static SkillsContext sampleSkills() {
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

        return skills;
    }

    private static BuffContext sampleBuffs() {
        BuffContext buffs = new BuffContext();
        buffs.setBuffSet(new HashSet<>());
        buffs.setBuffStacks(new HashMap<>());
        buffs.setPotionBuffs(new ArrayList<>());

        return buffs;
    }

    private static PerkContext samplePerks() {
        PerkContext perks = new PerkContext();
        perks.setPerk(new HashMap<>());
        perks.setGenocidalRank(0.0);
        perks.setEquipmentLevel20(false);

        return perks;
    }

    private static PrayerContext samplePrayer() {
        PrayerContext prayer = new PrayerContext();
        prayer.setSelected(EnumSet.noneOf(Prayer.class));

        return prayer;
    }

    private static SpellContext sampleSpell() {
        SpellContext spell = new SpellContext();
        spell.setSpell(Spells.WINDRUSH);

        return spell;
    }

    private static TargetContext sampleTarget() {
        TargetContext target = new TargetContext();
        target.setName("Sandbox target");
        target.setMaxHp(100000);
        target.setCurrentHp(100000);
        target.setSize(1);
        target.setArmour(1);
        target.setDefence(1);
        target.setAffinity(100);
        target.setTags(EnumSet.noneOf(TargetTags.class));

        return target;
    }

    @Test
    void deadshotIgneous_placesHitsOnExpectedTicks() {
        // Arrange
        RotationCombatState state = sampleRangedState();

        AbilityPlacement deadshot = new AbilityPlacement();
        deadshot.setCastTick(0);
        deadshot.setPlacedAbility(AbilityId.DEADSHOTIGNEOUS);

        // Act
        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(state, List.of(deadshot), List.of());

        // Assert
        assertEquals(5, timeline.getTimeline().size());

        TickSnapshot tick0 = timeline.getTimeline().get(0);
        TickSnapshot tick3 = timeline.getTimeline().get(3);
        TickSnapshot tick4 = timeline.getTimeline().get(4);

        assertEquals(0, tick0.getTick());
        assertEquals(1, tick0.getCastAbilities().size());
        assertEquals(AbilityId.DEADSHOTIGNEOUS, tick0.getCastAbilities().getFirst().getPlacedAbility());

        assertEquals(100.0, tick0.getStartingAdrenaline());
        assertEquals(40.0, tick0.getEndingAdrenaline());

        assertEquals(4, tick3.getLandedHits().size());
        assertEquals(4, tick4.getLandedHits().size());
    }

    @Test
    void deadshotIgneous_and_greaterRicochet_placesHitsOnExpectedTicks() {
        // Arrange
        RotationCombatState state = sampleRangedState();

        AbilityPlacement deadshot = new AbilityPlacement();
        deadshot.setCastTick(0);
        deadshot.setPlacedAbility(AbilityId.DEADSHOTIGNEOUS);

        AbilityPlacement greaterRicochet = new AbilityPlacement();
        greaterRicochet.setCastTick(3);
        greaterRicochet.setPlacedAbility(AbilityId.GREATERRICOCHET);

        List<AbilityPlacement> abilities = new ArrayList<>();
        abilities.add(deadshot);
        abilities.add(greaterRicochet);

        List<BuffPlacement> buffs = new ArrayList<>();

        // Act
        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(state, abilities, buffs);

        // Assert

        assertEquals(7, timeline.getTimeline().size());

        TickSnapshot tick0 = timeline.getTimeline().get(0);
        TickSnapshot tick3 = timeline.getTimeline().get(3);
        TickSnapshot tick4 = timeline.getTimeline().get(4);
        TickSnapshot tick5 = timeline.getTimeline().get(5);
        TickSnapshot tick6 = timeline.getTimeline().get(6);

        assertEquals(0, tick0.getTick());
        assertEquals(1, tick0.getCastAbilities().size());
        assertEquals(AbilityId.DEADSHOTIGNEOUS, tick0.getCastAbilities().getFirst().getPlacedAbility());

        assertEquals(1, tick3.getCastAbilities().size());
        assertEquals(AbilityId.GREATERRICOCHET, tick3.getCastAbilities().getFirst().getPlacedAbility());

        assertEquals(100.0, tick0.getStartingAdrenaline());
        assertEquals(40.0, tick0.getEndingAdrenaline());
        assertEquals(40.0, tick3.getStartingAdrenaline());
        assertEquals(49.0, tick3.getEndingAdrenaline());

        assertEquals(4, tick3.getLandedHits().size());
        assertEquals(4, tick4.getLandedHits().size());
        assertEquals(1, tick5.getLandedHits().size());
        assertEquals(6, tick6.getLandedHits().size());
    }

    @Test
    void rapidFire_applies_rapidFireBuff_on_completion() {
        RotationCombatState state = sampleRangedState();
        EquipmentSlot head = new EquipmentSlot();
        EquipmentSlot body = new EquipmentSlot();
        EquipmentSlot legs = new EquipmentSlot();
        EquipmentSlot boots = new EquipmentSlot();
        EquipmentSlot gloves = new EquipmentSlot();

        head.setEffect(EnumSet.of(Effect.ELITEDRACOLICH));
        body.setEffect(EnumSet.of(Effect.ELITEDRACOLICH));
        legs.setEffect(EnumSet.of(Effect.ELITEDRACOLICH));
        boots.setEffect(EnumSet.of(Effect.ELITEDRACOLICH));
        gloves.setEffect(EnumSet.of(Effect.ELITEDRACOLICH));

        state.getEquipment().setHead(head);
        state.getEquipment().setBody(body);
        state.getEquipment().setLegs(legs);
        state.getEquipment().setBoots(boots);
        state.getEquipment().setGloves(gloves);

        List<BuffPlacement> buffs = new ArrayList<>();

        List<AbilityPlacement> abilities = new ArrayList<>();
        AbilityPlacement rapidFire = new AbilityPlacement();
        rapidFire.setCastTick(0);
        rapidFire.setReleaseTick(0);
        rapidFire.setPlacedAbility(AbilityId.RAPIDFIRE);
        abilities.add(rapidFire);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(state, abilities, buffs);

        TickSnapshot tick9 = timeline.getTimeline().get(9);
        TickSnapshot tick8 = timeline.getTimeline().get(8);


        assertTrue(tick9.getEndingCombatState().getBuffs().getBuffSet().contains(BuffId.RAPIDFIREBUFF));
        assertFalse(tick8.getEndingCombatState().getBuffs().getBuffSet().contains(BuffId.RAPIDFIREBUFF));
    }

    @Test
    void vestmentsBleed_active_after_berserk_and_cleared_after_melee_ultimate() {
        RotationCombatState state = sampleMeleeState();
        EquipmentSlot head = new EquipmentSlot();
        EquipmentSlot body = new EquipmentSlot();
        EquipmentSlot legs = new EquipmentSlot();
        EquipmentSlot boots = new EquipmentSlot();

        head.setEffect(EnumSet.of(Effect.VESTMENTSOFHAVOC));
        body.setEffect(EnumSet.of(Effect.VESTMENTSOFHAVOC));
        legs.setEffect(EnumSet.of(Effect.VESTMENTSOFHAVOC));
        boots.setEffect(EnumSet.of(Effect.VESTMENTSOFHAVOC));

        state.getEquipment().setHead(head);
        state.getEquipment().setBody(body);
        state.getEquipment().setLegs(legs);
        state.getEquipment().setBoots(boots);

        List<BuffPlacement> buffs = new ArrayList<>();
        BuffPlacement berserk = new BuffPlacement();
        berserk.setBuffId(BuffId.BERSERK);
        berserk.setPlacementTick(0);
        buffs.add(berserk);

        List<AbilityPlacement> abilities = new ArrayList<>();
        AbilityPlacement overpower = new AbilityPlacement();
        overpower.setCastTick(3);
        overpower.setPlacedAbility(AbilityId.OVERPOWERIGNEOUS);
        abilities.add(overpower);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(state, abilities, buffs);

        TickSnapshot tick2 = timeline.getTimeline().get(2);
        TickSnapshot tick3 = timeline.getTimeline().get(3);

        assertTrue(tick2.getEndingCombatState().getBuffs().has(BuffId.VESTMENTSBLEED));
        assertFalse(tick3.getEndingCombatState().getBuffs().has(BuffId.VESTMENTSBLEED));
    }

    @Test
    void vestmentsBleed_active_after_melee_ultimate_and_cleared_after_berserk() {
        RotationCombatState state = sampleMeleeState();
        EquipmentSlot head = new EquipmentSlot();
        EquipmentSlot body = new EquipmentSlot();
        EquipmentSlot legs = new EquipmentSlot();
        EquipmentSlot boots = new EquipmentSlot();

        head.setEffect(EnumSet.of(Effect.VESTMENTSOFHAVOC));
        body.setEffect(EnumSet.of(Effect.VESTMENTSOFHAVOC));
        legs.setEffect(EnumSet.of(Effect.VESTMENTSOFHAVOC));
        boots.setEffect(EnumSet.of(Effect.VESTMENTSOFHAVOC));

        state.getEquipment().setHead(head);
        state.getEquipment().setBody(body);
        state.getEquipment().setLegs(legs);
        state.getEquipment().setBoots(boots);

        List<BuffPlacement> buffs = new ArrayList<>();
        BuffPlacement berserk = new BuffPlacement();
        berserk.setBuffId(BuffId.BERSERK);
        berserk.setPlacementTick(3);
        buffs.add(berserk);

        List<AbilityPlacement> abilities = new ArrayList<>();
        AbilityPlacement overpower = new AbilityPlacement();
        overpower.setCastTick(0);
        overpower.setPlacedAbility(AbilityId.OVERPOWERIGNEOUS);
        abilities.add(overpower);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(state, abilities, buffs);

        TickSnapshot tick2 = timeline.getTimeline().get(2);
        TickSnapshot tick3 = timeline.getTimeline().get(3);

        assertTrue(tick2.getEndingCombatState().getBuffs().has(BuffId.VESTMENTSBLEED));
        assertFalse(tick3.getEndingCombatState().getBuffs().has(BuffId.VESTMENTSBLEED));
    }

    @Test
    void wenArrowStacks_stack_decay_and_remove() {
        RotationCombatState state = sampleRangedState();
        EquipmentSlot arrows = new EquipmentSlot();
        arrows.setEffect(EnumSet.of(Effect.WENARROWS));
        state.getEquipment().setAmmo(arrows);

        List<BuffPlacement> buffs = new ArrayList<>();

        List<AbilityPlacement> abilities = new ArrayList<>();
        AbilityPlacement greaterRicochet = new AbilityPlacement();
        greaterRicochet.setCastTick(0);
        greaterRicochet.setPlacedAbility(AbilityId.GREATERRICOCHET);
        abilities.add(greaterRicochet);

        AbilityPlacement greaterRicochet2 = new AbilityPlacement();
        greaterRicochet2.setCastTick(6);
        greaterRicochet2.setPlacedAbility(AbilityId.GREATERRICOCHET);
        abilities.add(greaterRicochet2);

        AbilityPlacement piercingShot = new AbilityPlacement();
        piercingShot.setCastTick(12);
        piercingShot.setPlacedAbility(AbilityId.PIERCINGSHOT);
        abilities.add(piercingShot);

        AbilityPlacement backhand = new AbilityPlacement();
        backhand.setCastTick(70);
        backhand.setPlacedAbility(AbilityId.BACKHAND);
        abilities.add(backhand);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(state, abilities, buffs);

        TickSnapshot tick1 = timeline.getTimeline().get(1);
        TickSnapshot tick6 = timeline.getTimeline().get(6);
        TickSnapshot tick10 = timeline.getTimeline().get(10);

        assertEquals(7, tick1.getEndingCombatState().getBuffs().stacks(BuffId.WENARROWSTACKS));
        assertEquals(10, tick6.getEndingCombatState().getBuffs().stacks(BuffId.WENARROWSTACKS));
        assertTrue(tick10.getEndingActiveBuffDurationMap().containsKey(BuffId.WENARROWSTACKS));
        assertEquals(46, tick10.getEndingActiveBuffDurationMap().get(BuffId.WENARROWSTACKS).getDuration());
        assertFalse(timeline.getTimeline().get(62).getEndingActiveBuffDurationMap().containsKey(BuffId.WENARROWSTACKS));
        assertFalse(timeline.getTimeline().get(62).getEndingCombatState().getBuffs().has(BuffId.WENARROWSTACKS));
    }

    @Test
    void leng_stacks_generation_with_procMode_force() {
        RotationCombatState state = sampleMeleeState();
        state.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.PRIMORDIALICESTACKS));
        state.getEquipment().getMainhand().setTitle("dark shard of leng");
        state.getEquipment().getOffhand().setEffect(EnumSet.of(Effect.PRIMORDIALICESTACKS));
        state.getEquipment().getOffhand().setTitle("dark sliver of leng");

        List<BuffPlacement> buffs = new ArrayList<>();

        List<AbilityPlacement> abilities = new ArrayList<>();
        AbilityPlacement greaterFlurry = new AbilityPlacement();
        greaterFlurry.setCastTick(0);
        greaterFlurry.setPlacedAbility(AbilityId.GREATERFLURRY);
        abilities.add(greaterFlurry);

        SimulationConfig config = new SimulationConfig();
        config.setProcMode(ProcMode.FORCED);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(state, abilities, buffs, config);

        TickSnapshot tick1 = timeline.getTimeline().get(1);
        TickSnapshot tick8 = timeline.getTimeline().get(8);

        assertEquals(1, tick1.getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
        assertEquals(8, tick8.getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
    }

    @Test
    void leng_stacks_generation_with_procMode_disabled() {
        RotationCombatState state = sampleMeleeState();
        state.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.PRIMORDIALICESTACKS));
        state.getEquipment().getMainhand().setTitle("dark shard of leng");
        state.getEquipment().getOffhand().setEffect(EnumSet.of(Effect.PRIMORDIALICESTACKS));
        state.getEquipment().getOffhand().setTitle("dark sliver of leng");

        List<BuffPlacement> buffs = new ArrayList<>();

        List<AbilityPlacement> abilities = new ArrayList<>();
        AbilityPlacement greaterFlurry = new AbilityPlacement();
        greaterFlurry.setCastTick(0);
        greaterFlurry.setPlacedAbility(AbilityId.GREATERFLURRY);
        abilities.add(greaterFlurry);

        SimulationConfig config = new SimulationConfig();
        config.setProcMode(ProcMode.DISABLED);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(state, abilities, buffs, config);

        TickSnapshot tick1 = timeline.getTimeline().get(1);
        TickSnapshot tick8 = timeline.getTimeline().get(8);

        assertEquals(0, tick1.getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
        assertEquals(0, tick8.getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
    }

    @Test
    void leng_stacks_generation_with_procMode_expectedAccumulation() {
        RotationCombatState state = sampleMeleeState();
        state.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.PRIMORDIALICESTACKS));
        state.getEquipment().getMainhand().setTitle("dark shard of leng");
        state.getEquipment().getOffhand().setEffect(EnumSet.of(Effect.PRIMORDIALICESTACKS));
        state.getEquipment().getOffhand().setTitle("dark sliver of leng");

        List<BuffPlacement> buffs = new ArrayList<>();

        List<AbilityPlacement> abilities = new ArrayList<>();
        AbilityPlacement greaterFlurry = new AbilityPlacement();
        greaterFlurry.setCastTick(0);
        greaterFlurry.setPlacedAbility(AbilityId.GREATERFLURRY);
        abilities.add(greaterFlurry);
        AbilityPlacement greaterFlurry2 = new AbilityPlacement();
        greaterFlurry2.setCastTick(9);
        greaterFlurry2.setPlacedAbility(AbilityId.GREATERFLURRY);
        abilities.add(greaterFlurry2);
        AbilityPlacement greaterFlurry3 = new AbilityPlacement();
        greaterFlurry3.setCastTick(18);
        greaterFlurry3.setPlacedAbility(AbilityId.GREATERFLURRY);
        abilities.add(greaterFlurry3);

        SimulationConfig config = new SimulationConfig();
        config.setProcMode(ProcMode.EXPECTED_ACCUMULATED);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(state, abilities, buffs, config);

        TickSnapshot tick0 = timeline.getTimeline().getFirst();
        TickSnapshot tick9 = timeline.getTimeline().get(9);
        TickSnapshot tick10 = timeline.getTimeline().get(10);
        TickSnapshot tick18 = timeline.getTimeline().get(18);
        TickSnapshot tick19 = timeline.getTimeline().get(19);

        assertEquals(0, tick0.getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
        assertEquals(0, tick9.getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
        assertEquals(1, tick10.getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
        assertEquals(1, tick18.getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
        assertEquals(2, tick19.getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
    }

    @Test
    void procProcessor_correct_outcome() {
        SimulationState state = new SimulationState();
        state.setProcAccumulators(new HashMap<>());

        boolean forced = ProcProcessor.determineProc(ProcMode.FORCED, 0.12, state, BuffId.PRIMORDIALICESTACKS);
        boolean disabled = ProcProcessor.determineProc(ProcMode.DISABLED, 0.12, state, BuffId.PRIMORDIALICESTACKS);
        boolean expectedAccumulated = ProcProcessor.determineProc(ProcMode.EXPECTED_ACCUMULATED, 0.12, state, BuffId.PRIMORDIALICESTACKS);

        assertTrue(forced);
        assertFalse(disabled);
        assertFalse(expectedAccumulated);

        state.getProcAccumulators().put(BuffId.PRIMORDIALICESTACKS, 0.98);
        expectedAccumulated = ProcProcessor.determineProc(ProcMode.EXPECTED_ACCUMULATED, 0.12, state, BuffId.PRIMORDIALICESTACKS);

        assertTrue(expectedAccumulated);
        assertEquals(0.1, state.getProcAccumulators().get(BuffId.PRIMORDIALICESTACKS), 1e-9);
    }

    @Test
    void procProcessor_seededRandom_correct_outcome() {
        RotationCombatState rotationCombatState = sampleMeleeState();
        rotationCombatState.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.PRIMORDIALICESTACKS));
        rotationCombatState.getEquipment().getMainhand().setTitle("dark shard of leng");
        rotationCombatState.getEquipment().getOffhand().setEffect(EnumSet.of(Effect.PRIMORDIALICESTACKS));
        rotationCombatState.getEquipment().getOffhand().setTitle("dark sliver of leng");

        RotationCombatState rotationCombatState2 = sampleMeleeState();
        rotationCombatState2.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.PRIMORDIALICESTACKS));
        rotationCombatState2.getEquipment().getMainhand().setTitle("dark shard of leng");
        rotationCombatState2.getEquipment().getOffhand().setEffect(EnumSet.of(Effect.PRIMORDIALICESTACKS));
        rotationCombatState2.getEquipment().getOffhand().setTitle("dark sliver of leng");

        List<AbilityPlacement> abilities = new ArrayList<>();
        AbilityPlacement greaterFlurry = new AbilityPlacement();
        greaterFlurry.setCastTick(0);
        greaterFlurry.setPlacedAbility(AbilityId.GREATERFLURRY);
        abilities.add(greaterFlurry);

        List<AbilityPlacement> abilities2 = new ArrayList<>();
        AbilityPlacement greaterFlurry2 = new AbilityPlacement();
        greaterFlurry2.setCastTick(0);
        greaterFlurry2.setPlacedAbility(AbilityId.GREATERFLURRY);
        abilities2.add(greaterFlurry2);

        SimulationConfig config = new SimulationConfig();
        config.setProcMode(ProcMode.SEEDED_RANDOM);
        config.setRandomSeed(67L);

        SimulationConfig config2 = new SimulationConfig();
        config2.setProcMode(ProcMode.SEEDED_RANDOM);
        config2.setRandomSeed(67L);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, new ArrayList<>(), config);

        RotationTimeline timeline2 = new RotationTimelineService(engine, copier)
                .build(rotationCombatState2, abilities2, new ArrayList<>(), config2);

        assertEquals(timeline.getTimeline().getLast().getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS),
                timeline2.getTimeline().getLast().getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
    }

    @Test
    void gravitate_expiry_removes_gravitate_stacks() {
        RotationCombatState rotationCombatState = sampleMeleeState();
        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement greaterFlurry = new AbilityPlacement();
        greaterFlurry.setCastTick(3);
        greaterFlurry.setPlacedAbility(AbilityId.GREATERFLURRY);
        abilities.add(greaterFlurry);

        BuffPlacement gravitate = new BuffPlacement();
        gravitate.setBuffId(BuffId.GRAVITATEBUFF);
        gravitate.setPlacementTick(0);
        buffs.add(gravitate);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(8, timeline.getTimeline().get(11).getEndingCombatState().getBuffs().stacks(BuffId.GRAVITATESTACKS));
        assertTrue(timeline.getTimeline().get(49).getEndingCombatState().getBuffs().has(BuffId.GRAVITATESTACKS));
        assertFalse(timeline.getTimeline().get(50).getEndingCombatState().getBuffs().has(BuffId.GRAVITATESTACKS));
    }

    @Test
    void gravitate_stacks_do_not_exist_without_parent_buff() {
        RotationCombatState rotationCombatState = sampleMeleeState();
        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement greaterFlurry = new AbilityPlacement();
        greaterFlurry.setCastTick(3);
        greaterFlurry.setPlacedAbility(AbilityId.GREATERFLURRY);
        abilities.add(greaterFlurry);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertFalse(timeline.getTimeline().get(5).getEndingCombatState().getBuffs().has(BuffId.GRAVITATESTACKS));
    }

    @Test
    void gravitate_stack_generation_caps_at_20() {
        RotationCombatState rotationCombatState = sampleMeleeState();
        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement greaterFlurry = new AbilityPlacement();
        greaterFlurry.setCastTick(3);
        greaterFlurry.setPlacedAbility(AbilityId.GREATERFLURRY);
        for (int i = 0; i < 5; i++) {
            abilities.add(greaterFlurry);
        }

        BuffPlacement gravitate = new BuffPlacement();
        gravitate.setBuffId(BuffId.GRAVITATEBUFF);
        gravitate.setPlacementTick(0);
        buffs.add(gravitate);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(20, timeline.getTimeline().get(10).getEndingCombatState().getBuffs().stacks(BuffId.GRAVITATESTACKS));
    }

    @Test
    void wen_consumption_removes_stacks_but_keeps_proc() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getAmmo().setEffect(EnumSet.of(Effect.WENARROWS));
        List<AbilityPlacement> abilities = new ArrayList<>();

        AbilityPlacement greaterRicochet = new AbilityPlacement();
        greaterRicochet.setCastTick(0);
        greaterRicochet.setPlacedAbility(AbilityId.GREATERRICOCHET);
        abilities.add(greaterRicochet);
        abilities.add(greaterRicochet);

        AbilityPlacement snapshot = new AbilityPlacement();
        snapshot.setCastTick(3);
        snapshot.setPlacedAbility(AbilityId.SNAPSHOT);
        abilities.add(snapshot);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, new ArrayList<>());

        TickSnapshot tick2 = timeline.getTimeline().get(2);
        TickSnapshot tick3 = timeline.getTimeline().get(3);

        assertTrue(tick2.getEndingCombatState().getBuffs().has(BuffId.WENARROWSTACKS));
        assertFalse(tick2.getEndingCombatState().getBuffs().has(BuffId.WENARROWPROC));
        assertFalse(tick3.getEndingCombatState().getBuffs().has(BuffId.WENARROWSTACKS));
        assertTrue(tick3.getEndingCombatState().getBuffs().has(BuffId.WENARROWPROC));

    }

    @Test
    void wen_does_not_reconsume_while_proc_active() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getAmmo().setEffect(EnumSet.of(Effect.WENARROWS));
        List<AbilityPlacement> abilities = new ArrayList<>();

        AbilityPlacement greaterRicochet = new AbilityPlacement();
        greaterRicochet.setCastTick(0);
        greaterRicochet.setPlacedAbility(AbilityId.GREATERRICOCHET);
        abilities.add(greaterRicochet);
        abilities.add(greaterRicochet);

        AbilityPlacement snapshot = new AbilityPlacement();
        snapshot.setCastTick(3);
        snapshot.setPlacedAbility(AbilityId.SNAPSHOT);
        abilities.add(snapshot);

        AbilityPlacement greaterRicochet2 = new AbilityPlacement();
        greaterRicochet2.setCastTick(6);
        greaterRicochet2.setPlacedAbility(AbilityId.GREATERRICOCHET);
        abilities.add(greaterRicochet2);
        abilities.add(greaterRicochet2);

        AbilityPlacement snapshot2 = new AbilityPlacement();
        snapshot2.setCastTick(9);
        snapshot2.setPlacedAbility(AbilityId.SNAPSHOT);
        abilities.add(snapshot2);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, new ArrayList<>());

        assertTrue(timeline.getTimeline().get(8).getEndingCombatState().getBuffs().has(BuffId.WENARROWSTACKS) &&
                timeline.getTimeline().get(9).getEndingCombatState().getBuffs().has(BuffId.WENARROWSTACKS));

    }

    @Test
    void icyTempest_consumes_primordialIce_stacks() {
        RotationCombatState rotationCombatState = sampleMeleeState();
        List<AbilityPlacement> abilities = new ArrayList<>();
        rotationCombatState.getEquipment().getMainhand().getEffect().add(Effect.PRIMORDIALICESTACKS);

        AbilityPlacement greaterFlurry = new AbilityPlacement();
        greaterFlurry.setCastTick(0);
        greaterFlurry.setPlacedAbility(AbilityId.GREATERFLURRY);
        abilities.add(greaterFlurry);

        AbilityPlacement icyTempest = new AbilityPlacement();
        icyTempest.setCastTick(10);
        icyTempest.setPlacedAbility(AbilityId.ICYTEMPEST);
        abilities.add(icyTempest);

        SimulationConfig config = new SimulationConfig();
        config.setProcMode(ProcMode.FORCED);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, new ArrayList<>(), config);

        assertEquals(8, timeline.getTimeline().get(9).getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
        assertFalse(timeline.getTimeline().get(11).getEndingCombatState().getBuffs().has(BuffId.PRIMORDIALICESTACKS));
    }

    @Test
    void primordialIce_stacks_reduce_icyTempest_adrenaline_cost() {
        RotationCombatState rotationCombatState = sampleMeleeState();
        List<AbilityPlacement> abilities = new ArrayList<>();
        rotationCombatState.getEquipment().getMainhand().getEffect().add(Effect.PRIMORDIALICESTACKS);

        AbilityPlacement greaterFlurry = new AbilityPlacement();
        greaterFlurry.setCastTick(0);
        greaterFlurry.setPlacedAbility(AbilityId.GREATERFLURRY);
        abilities.add(greaterFlurry);

        AbilityPlacement icyTempest = new AbilityPlacement();
        icyTempest.setCastTick(10);
        icyTempest.setPlacedAbility(AbilityId.ICYTEMPEST);
        abilities.add(icyTempest);

        AbilityPlacement icyTempest2 = new AbilityPlacement();
        icyTempest2.setCastTick(16);
        icyTempest2.setPlacedAbility(AbilityId.ICYTEMPEST);
        abilities.add(icyTempest2);

        SimulationConfig config = new SimulationConfig();
        config.setProcMode(ProcMode.FORCED);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, new ArrayList<>(), config);

        assertEquals(75, timeline.getTimeline().get(9).getEndingAdrenaline());
        assertEquals(75, timeline.getTimeline().get(10).getEndingAdrenaline());
        assertEquals(45, timeline.getTimeline().get(16).getEndingAdrenaline());
    }

    @Test
    void gravitate_increases_damage_of_each_greaterFlurry_hit() {
        RotationCombatState rotationCombatState = sampleMeleeState();
        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        BuffPlacement gravitate = new BuffPlacement();
        gravitate.setPlacementTick(0);
        gravitate.setBuffId(BuffId.GRAVITATEBUFF);
        buffs.add(gravitate);

        AbilityPlacement greaterFlurry = new AbilityPlacement();
        greaterFlurry.setCastTick(3);
        greaterFlurry.setPlacedAbility(AbilityId.GREATERFLURRY);
        abilities.add(greaterFlurry);

        SimulationConfig config = new SimulationConfig();
        config.setProcMode(ProcMode.FORCED);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs, config);

        int damage = 0;
        for (TimelineHit timelineHit : timeline.getTimeline().get(5).getLandedHits()) {
            damage += timelineHit.getHitAvgDamage();
        }

        for (TimelineHit timelineHit : timeline.getTimeline().get(6).getLandedHits()) {
            damage -= timelineHit.getHitAvgDamage();
        }

        assertTrue(damage < 0);
    }

    @Test
    void gravitate_does_not_boost_on_release_abilities() {
        RotationCombatState rotationCombatState = sampleMeleeState();
        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement hurricane = new AbilityPlacement();
        hurricane.setCastTick(0);
        hurricane.setPlacedAbility(AbilityId.HURRICANE);
        abilities.add(hurricane);

        BuffPlacement gravitate = new BuffPlacement();
        gravitate.setPlacementTick(3);
        gravitate.setBuffId(BuffId.GRAVITATEBUFF);
        buffs.add(gravitate);

        AbilityPlacement hurricane2 = new AbilityPlacement();
        hurricane2.setCastTick(6);
        hurricane2.setPlacedAbility(AbilityId.HURRICANE);
        abilities.add(hurricane2);


        SimulationConfig config = new SimulationConfig();
        config.setProcMode(ProcMode.FORCED);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs, config);

        int damage = 0;
        for (TimelineHit timelineHit : timeline.getTimeline().get(5).getLandedHits()) {
            damage += timelineHit.getHitAvgDamage();
        }

        for (TimelineHit timelineHit : timeline.getTimeline().get(6).getLandedHits()) {
            damage -= timelineHit.getHitAvgDamage();
        }

        assertEquals(0, damage);
    }

    @Test
    void primordialIce_procs_add_frostblades_with_offhand_leng() {
        RotationCombatState rotationCombatState = sampleMeleeState();
        rotationCombatState.getEquipment().getOffhand().setEffect(EnumSet.of(Effect.OFFHANDLENG, Effect.PRIMORDIALICESTACKS));
        rotationCombatState.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.PRIMORDIALICESTACKS));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement greaterFlurry = new AbilityPlacement();
        greaterFlurry.setCastTick(0);
        greaterFlurry.setPlacedAbility(AbilityId.GREATERFLURRY);
        abilities.add(greaterFlurry);

        AbilityPlacement meleeAuto = new AbilityPlacement();
        meleeAuto.setCastTick(30);
        meleeAuto.setPlacedAbility(AbilityId.MELEEAUTO);
        abilities.add(meleeAuto);

        SimulationConfig config = new SimulationConfig();
        config.setProcMode(ProcMode.FORCED);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs, config);

        assertTrue(timeline.getTimeline().get(3).getEndingCombatState().getBuffs().has(BuffId.FROSTBLADES));
        assertFalse(timeline.getTimeline().get(23).getEndingCombatState().getBuffs().has(BuffId.FROSTBLADES));
    }

    @Test
    void primordialIce_procs_dont_add_frostblades_without_offhand_leng() {
        RotationCombatState rotationCombatState = sampleMeleeState();
        rotationCombatState.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.PRIMORDIALICESTACKS));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement greaterFlurry = new AbilityPlacement();
        greaterFlurry.setCastTick(0);
        greaterFlurry.setPlacedAbility(AbilityId.GREATERFLURRY);
        abilities.add(greaterFlurry);

        AbilityPlacement meleeAuto = new AbilityPlacement();
        meleeAuto.setCastTick(30);
        meleeAuto.setPlacedAbility(AbilityId.MELEEAUTO);
        abilities.add(meleeAuto);

        SimulationConfig config = new SimulationConfig();
        config.setProcMode(ProcMode.FORCED);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs, config);

        assertFalse(timeline.getTimeline().get(3).getEndingCombatState().getBuffs().has(BuffId.FROSTBLADES));
    }

    @Test
    void piercingShot_rolls_from_7_to_1_perfectEquilibrium_and_proc_appears() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getMainhand().setTitle("bow of the last guardian");

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement greaterRicochet = new AbilityPlacement();
        greaterRicochet.setCastTick(0);
        greaterRicochet.setPlacedAbility(AbilityId.GREATERRICOCHET);
        abilities.add(greaterRicochet);

        AbilityPlacement piercingShot = new AbilityPlacement();
        piercingShot.setCastTick(3);
        piercingShot.setPlacedAbility(AbilityId.PIERCINGSHOT);
        abilities.add(piercingShot);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(7, timeline.getTimeline().get(2).getEndingCombatState().getBuffs().stacks(BuffId.PERFECTEQUILIBRIUMSTACKS));
        assertEquals(1, timeline.getTimeline().get(3).getEndingCombatState().getBuffs().stacks(BuffId.PERFECTEQUILIBRIUMSTACKS));
        assertEquals(3, timeline.getTimeline().get(5).getLandedHits().size());
    }

    @Test
    void snipe_at_7_perfectEquilibrium_stacks_produces_tick_after_proc() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getMainhand().setTitle("bow of the last guardian");

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement greaterRicochet = new AbilityPlacement();
        greaterRicochet.setCastTick(0);
        greaterRicochet.setPlacedAbility(AbilityId.GREATERRICOCHET);
        abilities.add(greaterRicochet);

        AbilityPlacement snipe = new AbilityPlacement();
        snipe.setCastTick(3);
        snipe.setPlacedAbility(AbilityId.SNIPE);
        abilities.add(snipe);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(1, timeline.getTimeline().get(6).getLandedHits().size());
        assertEquals(1, timeline.getTimeline().get(7).getLandedHits().size());
        assertTrue(timeline.getTimeline().get(6).getLandedHits().getFirst().getHitAvgDamage() > timeline.getTimeline().get(7).getLandedHits().getFirst().getHitAvgDamage());
    }

    @Test
    void rapidFire_at_7_perfectEquilibrium_stacks_produces_proc_same_tick_as_proccing_hit() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getMainhand().setTitle("bow of the last guardian");

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement greaterRicochet = new AbilityPlacement();
        greaterRicochet.setCastTick(0);
        greaterRicochet.setPlacedAbility(AbilityId.GREATERRICOCHET);
        abilities.add(greaterRicochet);

        AbilityPlacement rapidFire = new AbilityPlacement();
        rapidFire.setCastTick(3);
        rapidFire.setPlacedAbility(AbilityId.RAPIDFIRE);
        abilities.add(rapidFire);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(2, timeline.getTimeline().get(5).getLandedHits().size());
        assertEquals(1, timeline.getTimeline().get(6).getLandedHits().size());
    }

    @Test
    void rapidFire_PE_proc_does_not_advance_PE_stacks_for_same_scheduled_hit() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getMainhand().setTitle("bow of the last guardian");

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement balanceByForce = new AbilityPlacement();
        balanceByForce.setCastTick(0);
        balanceByForce.setPlacedAbility(AbilityId.BALANCEBYFORCE);
        abilities.add(balanceByForce);

        AbilityPlacement rapidFire = new AbilityPlacement();
        rapidFire.setCastTick(3);
        rapidFire.setPlacedAbility(AbilityId.RAPIDFIRE);
        abilities.add(rapidFire);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(1, timeline.getTimeline().get(12).getEndingCombatState().getBuffs().stacks(BuffId.PERFECTEQUILIBRIUMSTACKS));
    }

    @Test
    void balanceByForce_generates_perfectEquilibrium_stacks_under_3_stacks() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getMainhand().setTitle("bow of the last guardian");

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement piercingShot = new AbilityPlacement();
        piercingShot.setCastTick(0);
        piercingShot.setPlacedAbility(AbilityId.PIERCINGSHOT);
        abilities.add(piercingShot);

        AbilityPlacement balanceByForce = new AbilityPlacement();
        balanceByForce.setCastTick(3);
        balanceByForce.setPlacedAbility(AbilityId.BALANCEBYFORCE);
        abilities.add(balanceByForce);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(3, timeline.getTimeline().get(3).getEndingCombatState().getBuffs().stacks(BuffId.PERFECTEQUILIBRIUMSTACKS));
    }

    @Test
    void balanceByForce_at_3_PE_stacks_correctly_rolls_stacks_over_and_applies_proc() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getMainhand().setTitle("bow of the last guardian");

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement piercingShot = new AbilityPlacement();
        piercingShot.setCastTick(0);
        piercingShot.setPlacedAbility(AbilityId.PIERCINGSHOT);
        abilities.add(piercingShot);

        AbilityPlacement rangedAuto = new AbilityPlacement();
        rangedAuto.setCastTick(3);
        rangedAuto.setPlacedAbility(AbilityId.RANGEDAUTO);
        abilities.add(rangedAuto);

        AbilityPlacement balanceByForce = new AbilityPlacement();
        balanceByForce.setCastTick(6);
        balanceByForce.setPlacedAbility(AbilityId.BALANCEBYFORCE);
        abilities.add(balanceByForce);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertFalse(timeline.getTimeline().get(6).getEndingCombatState().getBuffs().has(BuffId.PERFECTEQUILIBRIUMSTACKS));
        assertEquals(2, timeline.getTimeline().get(8).getLandedHits().size());
    }

    @Test
    void percingShot_at_3_PE_with_BBF_active_rolls_over_to_1_stack_with_PE_proc() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getMainhand().setTitle("bow of the last guardian");

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement piercingShot = new AbilityPlacement();
        piercingShot.setCastTick(0);
        piercingShot.setPlacedAbility(AbilityId.PIERCINGSHOT);
        abilities.add(piercingShot);

        AbilityPlacement balanceByForce = new AbilityPlacement();
        balanceByForce.setCastTick(3);
        balanceByForce.setPlacedAbility(AbilityId.BALANCEBYFORCE);
        abilities.add(balanceByForce);

        AbilityPlacement piercingShot2 = new AbilityPlacement();
        piercingShot2.setCastTick(6);
        piercingShot2.setPlacedAbility(AbilityId.PIERCINGSHOT);
        abilities.add(piercingShot2);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(3, timeline.getTimeline().get(8).getLandedHits().size());
        assertEquals(1, timeline.getTimeline().get(6).getEndingCombatState().getBuffs().stacks(BuffId.PERFECTEQUILIBRIUMSTACKS));
    }

    @Test
    void phantomStrike_generates_stacks_only_for_initial_and_not_for_bleed() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getMainhand().setTitle("bow of the last guardian");

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement phantomStrike = new AbilityPlacement();
        phantomStrike.setCastTick(0);
        phantomStrike.setPlacedAbility(AbilityId.PHANTOMSTRIKE);
        abilities.add(phantomStrike);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(1, timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.PERFECTEQUILIBRIUMSTACKS));
        assertEquals(1, timeline.getTimeline().getLast().getEndingCombatState().getBuffs().stacks(BuffId.PERFECTEQUILIBRIUMSTACKS));
    }

    @Test
    void deathspore_stacks_generate_from_resolved_ON_RELEASE_damage() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getAmmo().setEffect(EnumSet.of(Effect.DEATHSPOREARROWS));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement greaterRicochet = new AbilityPlacement();
        greaterRicochet.setCastTick(0);
        greaterRicochet.setPlacedAbility(AbilityId.GREATERRICOCHET);
        abilities.add(greaterRicochet);

        AbilityPlacement corruptionShot = new AbilityPlacement();
        corruptionShot.setCastTick(4);
        corruptionShot.setPlacedAbility(AbilityId.CORRUPTIONSHOT);
        abilities.add(corruptionShot);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(7, timeline.getTimeline().get(3).getEndingCombatState().getBuffs().stacks(BuffId.DEATHSPORESTACKS));
        assertEquals(7, timeline.getTimeline().get(9).getEndingCombatState().getBuffs().stacks(BuffId.DEATHSPORESTACKS));
    }

    @Test
    void deathspore_stacks_include_PE_proc_hit() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getAmmo().setEffect(EnumSet.of(Effect.DEATHSPOREARROWS));
        rotationCombatState.getEquipment().getMainhand().setTitle("bow of the last guardian");

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement greaterRicochet = new AbilityPlacement();
        greaterRicochet.setCastTick(0);
        greaterRicochet.setPlacedAbility(AbilityId.GREATERRICOCHET);
        abilities.add(greaterRicochet);

        AbilityPlacement piercingShot = new AbilityPlacement();
        piercingShot.setCastTick(3);
        piercingShot.setPlacedAbility(AbilityId.PIERCINGSHOT);
        abilities.add(piercingShot);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(7, timeline.getTimeline().get(0).getEndingCombatState().getBuffs().stacks(BuffId.DEATHSPORESTACKS));
        assertEquals(10, timeline.getTimeline().get(3).getEndingCombatState().getBuffs().stacks(BuffId.DEATHSPORESTACKS));
    }

    @Test
    void deathspore_stacks_ignore_DoT_hits() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getAmmo().setEffect(EnumSet.of(Effect.DEATHSPOREARROWS));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement phantomStrike = new AbilityPlacement();
        phantomStrike.setCastTick(0);
        phantomStrike.setPlacedAbility(AbilityId.PHANTOMSTRIKE);
        abilities.add(phantomStrike);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(1, timeline.getTimeline().get(3).getEndingCombatState().getBuffs().stacks(BuffId.DEATHSPORESTACKS));
        assertEquals(1, timeline.getTimeline().get(9).getEndingCombatState().getBuffs().stacks(BuffId.DEATHSPORESTACKS));
    }

    @Test
    void rapidFire_generates_spore_stacks_per_hit() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getAmmo().setEffect(EnumSet.of(Effect.DEATHSPOREARROWS));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement rapidFire = new AbilityPlacement();
        rapidFire.setCastTick(0);
        rapidFire.setPlacedAbility(AbilityId.RAPIDFIRE);
        abilities.add(rapidFire);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(1, timeline.getTimeline().get(2).getEndingCombatState().getBuffs().stacks(BuffId.DEATHSPORESTACKS));
        assertEquals(2, timeline.getTimeline().get(3).getEndingCombatState().getBuffs().stacks(BuffId.DEATHSPORESTACKS));
        assertEquals(8, timeline.getTimeline().get(9).getEndingCombatState().getBuffs().stacks(BuffId.DEATHSPORESTACKS));
    }

    @Test
    void deathspore_triggers_feastingspores_at_12_stacks() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getAmmo().setEffect(EnumSet.of(Effect.DEATHSPOREARROWS));
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.DEATHSPORESTACKS, 11);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement rangedAuto = new AbilityPlacement();
        rangedAuto.setCastTick(0);
        rangedAuto.setReleaseTick(1);
        rangedAuto.setPlacedAbility(AbilityId.RANGEDAUTO);
        abilities.add(rangedAuto);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(11, timeline.getTimeline().get(0).getEndingCombatState().getBuffs().stacks(BuffId.DEATHSPORESTACKS));
        assertFalse(timeline.getTimeline().get(1).getEndingCombatState().getBuffs().has(BuffId.DEATHSPORESTACKS));
        assertTrue(timeline.getTimeline().get(1).getEndingCombatState().getBuffs().has(BuffId.FEASTINGSPORES));
    }

    @Test
    void deathspore_cooldown_prevents_restacking() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getAmmo().setEffect(EnumSet.of(Effect.DEATHSPOREARROWS));
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.DEATHSPORESTACKS, 11);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<AbilityPlacement> abilities2 = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement rangedAuto = new AbilityPlacement();
        rangedAuto.setCastTick(0);
        rangedAuto.setPlacedAbility(AbilityId.RANGEDAUTO);
        abilities.add(rangedAuto);
        abilities2.add(rangedAuto);

        AbilityPlacement rangedAuto2 = new AbilityPlacement();
        rangedAuto2.setCastTick(3);
        rangedAuto2.setPlacedAbility(AbilityId.RANGEDAUTO);
        abilities.add(rangedAuto2);

        AbilityPlacement rangedAuto3 = new AbilityPlacement();
        rangedAuto3.setCastTick(0);
        rangedAuto3.setPlacedAbility(AbilityId.RANGEDAUTO);
        abilities2.add(rangedAuto3);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        RotationTimeline timeline2 = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities2, buffs);

        assertFalse(timeline.getTimeline().get(3).getEndingCombatState().getBuffs().has(BuffId.DEATHSPORESTACKS));
        assertFalse(timeline2.getTimeline().getFirst().getEndingCombatState().getBuffs().has(BuffId.DEATHSPORESTACKS));
    }

    @Test
    void feastingspores_refunds_adrenaline_cost_of_non_basic_ranged_ability_and_is_consumed() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getAmmo().setEffect(EnumSet.of(Effect.DEATHSPOREARROWS));
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.DEATHSPORESTACKS, 11);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement rangedAuto = new AbilityPlacement();
        rangedAuto.setCastTick(0);
        rangedAuto.setReleaseTick(1);
        rangedAuto.setPlacedAbility(AbilityId.RANGEDAUTO);
        abilities.add(rangedAuto);

        AbilityPlacement deadshot = new AbilityPlacement();
        deadshot.setCastTick(3);
        deadshot.setPlacedAbility(AbilityId.DEADSHOTIGNEOUS);
        abilities.add(deadshot);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(timeline.getTimeline().getFirst().getEndingAdrenaline(), timeline.getTimeline().get(3).getEndingAdrenaline());
        assertFalse(timeline.getTimeline().get(3).getEndingCombatState().getBuffs().has(BuffId.FEASTINGSPORES));
    }

    @Test
    void feastingspores_does_not_apply_to_basic_abilities() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getAmmo().setEffect(EnumSet.of(Effect.DEATHSPOREARROWS));
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.DEATHSPORESTACKS, 11);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement rangedAuto = new AbilityPlacement();
        rangedAuto.setCastTick(0);
        rangedAuto.setReleaseTick(1);
        rangedAuto.setPlacedAbility(AbilityId.RANGEDAUTO);
        abilities.add(rangedAuto);

        AbilityPlacement rangedAuto2 = new AbilityPlacement();
        rangedAuto2.setCastTick(3);
        rangedAuto2.setPlacedAbility(AbilityId.RANGEDAUTO);
        abilities.add(rangedAuto2);

        AbilityPlacement meleeAuto = new AbilityPlacement();
        meleeAuto.setCastTick(6);
        meleeAuto.setPlacedAbility(AbilityId.MELEEAUTO);
        abilities.add(meleeAuto);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertTrue(timeline.getTimeline().get(7).getEndingCombatState().getBuffs().has(BuffId.FEASTINGSPORES));
    }

    @Test
    void feasting_spores_applies_to_deathSwiftness_and_consumes_buff() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getAmmo().setEffect(EnumSet.of(Effect.DEATHSPOREARROWS));
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.DEATHSPORESTACKS, 11);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement rangedAuto = new AbilityPlacement();
        rangedAuto.setCastTick(0);
        rangedAuto.setReleaseTick(1);
        rangedAuto.setPlacedAbility(AbilityId.RANGEDAUTO);
        abilities.add(rangedAuto);

        BuffPlacement deathSwiftness = new BuffPlacement();
        deathSwiftness.setPlacementTick(3);
        deathSwiftness.setBuffId(BuffId.DEATHSWIFTNESS);
        buffs.add(deathSwiftness);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(100, timeline.getTimeline().get(3).getEndingAdrenaline());
        assertFalse(timeline.getTimeline().get(3).getEndingCombatState().getBuffs().has(BuffId.FEASTINGSPORES));
    }

    @Test
    void feasting_spores_applies_to_imbueShadows_and_consumes_buff() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getAmmo().setEffect(EnumSet.of(Effect.DEATHSPOREARROWS));
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.DEATHSPORESTACKS, 11);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement rangedAuto = new AbilityPlacement();
        rangedAuto.setCastTick(0);
        rangedAuto.setReleaseTick(1);
        rangedAuto.setPlacedAbility(AbilityId.RANGEDAUTO);
        abilities.add(rangedAuto);

        BuffPlacement deathSwiftness = new BuffPlacement();
        deathSwiftness.setPlacementTick(3);
        deathSwiftness.setBuffId(BuffId.IMBUESHADOWS);
        buffs.add(deathSwiftness);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(100, timeline.getTimeline().get(3).getEndingAdrenaline());
        assertFalse(timeline.getTimeline().get(3).getEndingCombatState().getBuffs().has(BuffId.FEASTINGSPORES));
    }

    @Test
    void feasting_spores_applies_to_splitsoul_and_consumes_buff() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getAmmo().setEffect(EnumSet.of(Effect.DEATHSPOREARROWS));
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.DEATHSPORESTACKS, 11);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement rangedAuto = new AbilityPlacement();
        rangedAuto.setCastTick(0);
        rangedAuto.setReleaseTick(1);
        rangedAuto.setPlacedAbility(AbilityId.RANGEDAUTO);
        abilities.add(rangedAuto);

        BuffPlacement deathSwiftness = new BuffPlacement();
        deathSwiftness.setPlacementTick(3);
        deathSwiftness.setBuffId(BuffId.SPLITSOUL);
        buffs.add(deathSwiftness);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(100, timeline.getTimeline().get(3).getEndingAdrenaline());
        assertFalse(timeline.getTimeline().get(4).getEndingCombatState().getBuffs().has(BuffId.FEASTINGSPORES));
    }

    @Test
    void ON_RELEASE_magic_ability_generates_one_tithe_stack() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.EXSANGUINATE);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement magicAuto = new AbilityPlacement();
        magicAuto.setCastTick(0);
        magicAuto.setPlacedAbility(AbilityId.MAGICAUTO);
        abilities.add(magicAuto);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(1, timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.TITHESTACKS));
    }

    @Test
    void ON_HIT_magic_ability_generates_tithe_stacks_when_hit_lands() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.EXSANGUINATE);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement gConc = new AbilityPlacement();
        gConc.setCastTick(0);
        gConc.setPlacedAbility(AbilityId.GREATERCONCENTRATEDBLAST);
        abilities.add(gConc);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(0, timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.TITHESTACKS));
        assertEquals(1, timeline.getTimeline().get(1).getEndingCombatState().getBuffs().stacks(BuffId.TITHESTACKS));
        assertEquals(2, timeline.getTimeline().get(2).getEndingCombatState().getBuffs().stacks(BuffId.TITHESTACKS));
        assertEquals(3, timeline.getTimeline().get(3).getEndingCombatState().getBuffs().stacks(BuffId.TITHESTACKS));
    }

    @Test
    void multi_hit_magic_abilities_generate_1_tithe_stack_if_ON_RELEASE() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.EXSANGUINATE);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement omnipower = new AbilityPlacement();
        omnipower.setCastTick(0);
        omnipower.setPlacedAbility(AbilityId.OMNIPOWER);
        abilities.add(omnipower);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(1, timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.TITHESTACKS));
    }

    @Test
    void tithe_stacks_only_generated_with_exsanguinate() {
        RotationCombatState rotationCombatState = sampleMagicState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement magicAuto = new AbilityPlacement();
        magicAuto.setCastTick(0);
        magicAuto.setPlacedAbility(AbilityId.MAGICAUTO);
        abilities.add(magicAuto);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertFalse(timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().has(BuffId.TITHESTACKS));
    }

    @Test
    void tithe_stacks_clamp_at_12_stacks() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.EXSANGUINATE);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement magicAuto = new AbilityPlacement();
        magicAuto.setCastTick(0);
        magicAuto.setPlacedAbility(AbilityId.MAGICAUTO);

        for (int i = 0; i < 15; i++) {
            abilities.add(magicAuto);
        }

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(12, timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.TITHESTACKS));
    }

    @Test
    void tithe_stacks_deplete_after_33_ticks() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.EXSANGUINATE);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement magicAuto = new AbilityPlacement();
        magicAuto.setCastTick(0);
        magicAuto.setPlacedAbility(AbilityId.MAGICAUTO);
        abilities.add(magicAuto);

        AbilityPlacement meleeAuto = new AbilityPlacement();
        meleeAuto.setCastTick(50);
        meleeAuto.setPlacedAbility(AbilityId.MELEEAUTO);
        abilities.add(meleeAuto);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertTrue(timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().has(BuffId.TITHESTACKS));
        assertFalse(timeline.getTimeline().getLast().getEndingCombatState().getBuffs().has(BuffId.TITHESTACKS));
    }

    @Test
    void new_magic_abilities_renew_tithe_stack_duration() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.EXSANGUINATE);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement magicAuto = new AbilityPlacement();
        magicAuto.setCastTick(0);
        magicAuto.setPlacedAbility(AbilityId.MAGICAUTO);
        abilities.add(magicAuto);

        AbilityPlacement magicAuto2 = new AbilityPlacement();
        magicAuto2.setCastTick(15);
        magicAuto2.setPlacedAbility(AbilityId.MAGICAUTO);
        abilities.add(magicAuto2);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(timeline.getTimeline().getFirst().getEndingActiveBuffDurationMap().get(BuffId.TITHESTACKS).getDuration(),
                timeline.getTimeline().get(15).getEndingActiveBuffDurationMap().get(BuffId.TITHESTACKS).getDuration());
    }

    @Test
    void magic_basic_damage_increases_with_tithe_stacks() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.EXSANGUINATE);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement gConc = new AbilityPlacement();
        gConc.setCastTick(0);
        gConc.setPlacedAbility(AbilityId.GREATERCONCENTRATEDBLAST);
        abilities.add(gConc);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertTrue(timeline.getTimeline().get(1).getLandedHits().getFirst().getHitAvgDamage() <
                timeline.getTimeline().get(2).getLandedHits().getFirst().getHitAvgDamage());
    }

    @Test
    void tithe_stacks_do_not_increase_non_basic_magic_ability_damage() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.EXSANGUINATE);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement wildMagic = new AbilityPlacement();
        wildMagic.setCastTick(0);
        wildMagic.setPlacedAbility(AbilityId.WILDMAGIC);
        abilities.add(wildMagic);

        AbilityPlacement wildMagic2 = new AbilityPlacement();
        wildMagic2.setCastTick(3);
        wildMagic2.setPlacedAbility(AbilityId.WILDMAGIC);
        abilities.add(wildMagic2);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(timeline.getTimeline().get(2).getLandedHits().getFirst().getHitAvgDamage(),
                timeline.getTimeline().get(5).getLandedHits().getFirst().getHitAvgDamage());
        assertTrue(timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.TITHESTACKS) <
                timeline.getTimeline().get(3).getEndingCombatState().getBuffs().stacks(BuffId.TITHESTACKS));
    }

    @Test
    void bleeds_only_generate_one_tithe_stack() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.EXSANGUINATE);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement combust = new AbilityPlacement();
        combust.setCastTick(0);
        combust.setPlacedAbility(AbilityId.COMBUST);
        abilities.add(combust);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(1, timeline.getTimeline().get(8).getEndingCombatState().getBuffs().stacks(BuffId.TITHESTACKS));
    }

    @Test
    void instability_procs_are_not_boosted_by_tithe_stacks() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.EXSANGUINATE);

        RotationCombatState rotationCombatState2 = sampleMagicState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement instability = new AbilityPlacement();
        instability.setCastTick(0);
        instability.setPlacedAbility(AbilityId.INSTABILITY);
        abilities.add(instability);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        RotationTimeline timeline2 = new RotationTimelineService(engine, copier)
                .build(rotationCombatState2, abilities, buffs);

        int damage = 0;
        for (TickSnapshot snapshot : timeline.getTimeline()) {
            for (TimelineHit hit : snapshot.getLandedHits()) {
                if (hit.getHitType() == HitType.INSTABILITY) {
                    damage += hit.getHitAvgDamage();
                }
            }
        }

        for (TickSnapshot snapshot : timeline2.getTimeline()) {
            for (TimelineHit hit : snapshot.getLandedHits()) {
                if (hit.getHitType() == HitType.INSTABILITY) {
                    damage -= hit.getHitAvgDamage();
                }
            }
        }
        assertEquals(0, damage);
    }

    @Test
    void inciteFearProc_is_scheduled_and_lands_on_appropriate_tick_at_5_stacks_with_ON_RELEASE_ability() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.INCITEFEAR);
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.INCITEFEARSTACKS, 4);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement dragonBreath = new AbilityPlacement();
        dragonBreath.setCastTick(0);
        dragonBreath.setPlacedAbility(AbilityId.DRAGONBREATH);
        abilities.add(dragonBreath);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(1, timeline.getTimeline().get(3).getLandedHits().size());
        assertEquals(AbilityId.INCITEFEARPROC, timeline.getTimeline().get(3).getLandedHits().getFirst().getParentAbility());
    }

    @Test
    void inciteFearProc_is_scheduled_and_lands_on_appropriate_tick_at_5_stacks_with_ON_HIT_ability() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.INCITEFEAR);
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.INCITEFEARSTACKS, 1);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement asphyxiate = new AbilityPlacement();
        asphyxiate.setCastTick(0);
        asphyxiate.setPlacedAbility(AbilityId.ASPHYXIATE);
        abilities.add(asphyxiate);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(1, timeline.getTimeline().get(10).getLandedHits().size());
        assertEquals(AbilityId.INCITEFEARPROC, timeline.getTimeline().get(10).getLandedHits().getFirst().getParentAbility());
    }

    @Test
    void inciteFearProc_does_not_exist_while_on_cooldown() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.INCITEFEAR);
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.INCITEFEARSTACKS, 4);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement dragonBreath = new AbilityPlacement();
        dragonBreath.setCastTick(0);
        dragonBreath.setPlacedAbility(AbilityId.DRAGONBREATH);
        abilities.add(dragonBreath);
        AbilityPlacement dragonBreath2 = new AbilityPlacement();
        dragonBreath2.setCastTick(3);
        dragonBreath2.setPlacedAbility(AbilityId.DRAGONBREATH);
        abilities.add(dragonBreath2);
        AbilityPlacement meleeAuto = new AbilityPlacement();
        meleeAuto.setCastTick(12);
        meleeAuto.setPlacedAbility(AbilityId.MELEEAUTO);
        abilities.add(meleeAuto);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        int landedInciteFearProcs = 0;
        for (TickSnapshot tick : timeline.getTimeline()) {
            for (TimelineHit hit : tick.getLandedHits()) {
                if (hit.getParentAbility() == AbilityId.INCITEFEARPROC) {
                    landedInciteFearProcs++;
                }
            }
        }

        assertEquals(1, landedInciteFearProcs);
    }

    @Test
    void inciteFearStacks_decays_duration_until_removal() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.INCITEFEAR);
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.INCITEFEARSTACKS, 4);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement dragonBreath = new AbilityPlacement();
        dragonBreath.setCastTick(0);
        dragonBreath.setPlacedAbility(AbilityId.DRAGONBREATH);
        abilities.add(dragonBreath);
        AbilityPlacement dragonBreath2 = new AbilityPlacement();
        dragonBreath2.setCastTick(3);
        dragonBreath2.setPlacedAbility(AbilityId.DRAGONBREATH);
        abilities.add(dragonBreath2);
        AbilityPlacement meleeAuto = new AbilityPlacement();
        meleeAuto.setCastTick(40);
        meleeAuto.setPlacedAbility(AbilityId.MELEEAUTO);
        abilities.add(meleeAuto);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertTrue(timeline.getTimeline().get(15).getEndingCombatState().getBuffs().has(BuffId.INCITEFEARSTACKS));
        assertFalse(timeline.getTimeline().get(37).getEndingCombatState().getBuffs().has(BuffId.INCITEFEARSTACKS));
    }

    @Test
    void inciteFearProc_procs_again_after_cooldown() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.INCITEFEAR);
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.INCITEFEARSTACKS, 4);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement dragonBreath = new AbilityPlacement();
        dragonBreath.setCastTick(0);
        dragonBreath.setPlacedAbility(AbilityId.DRAGONBREATH);
        abilities.add(dragonBreath);
        AbilityPlacement dragonBreath2 = new AbilityPlacement();
        dragonBreath2.setCastTick(23);
        dragonBreath2.setPlacedAbility(AbilityId.DRAGONBREATH);
        abilities.add(dragonBreath2);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(1, timeline.getTimeline().get(3).getLandedHits().size());
        assertEquals(1, timeline.getTimeline().get(26).getLandedHits().size());
    }

    @Test
    void inciteFearProc_placed_on_cooldown_with_proccing_ability_for_ON_RELEASE() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.INCITEFEAR);
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.INCITEFEARSTACKS, 4);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement dragonBreath = new AbilityPlacement();
        dragonBreath.setCastTick(0);
        dragonBreath.setPlacedAbility(AbilityId.DRAGONBREATH);
        abilities.add(dragonBreath);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertTrue(timeline.getTimeline().getFirst().getEndingAbilityCooldownMap().containsKey(AbilityCooldownKeyResolver.resolve(AbilityId.INCITEFEARPROC)));
    }

    @Test
    void inciteFearProc_placed_on_cooldown_with_proccing_hit_for_ON_HIT() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.INCITEFEAR);
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.INCITEFEARSTACKS, 3);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement asphyxiate = new AbilityPlacement();
        asphyxiate.setCastTick(0);
        asphyxiate.setPlacedAbility(AbilityId.ASPHYXIATE);
        abilities.add(asphyxiate);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertTrue(timeline.getTimeline().get(3).getEndingAbilityCooldownMap().containsKey(AbilityCooldownKeyResolver.resolve(AbilityId.INCITEFEARPROC)));
    }

    @Test
    void inciteFearStacks_reduce_tsunami_cost_by_12_per_stack() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.INCITEFEAR);
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.INCITEFEARSTACKS, 3);

        RotationCombatState rotationCombatState2 = sampleMagicState();
        rotationCombatState2.getSpell().setSpell(Spells.INCITEFEAR);
        rotationCombatState2.getBuffs().getBuffStacks().put(BuffId.INCITEFEARSTACKS, 2);

        RotationCombatState rotationCombatState3 = sampleMagicState();
        rotationCombatState3.getSpell().setSpell(Spells.INCITEFEAR);
        rotationCombatState3.getBuffs().getBuffStacks().put(BuffId.INCITEFEARSTACKS, 1);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement tsunami = new AbilityPlacement();
        tsunami.setCastTick(0);
        tsunami.setPlacedAbility(AbilityId.TSUNAMI);
        abilities.add(tsunami);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);
        RotationTimeline timeline2 = new RotationTimelineService(engine, copier)
                .build(rotationCombatState2, abilities, buffs);
        RotationTimeline timeline3 = new RotationTimelineService(engine, copier)
                .build(rotationCombatState3, abilities, buffs);

        assertEquals(36, timeline.getTimeline().getFirst().getEndingAdrenaline());
        assertEquals(24, timeline2.getTimeline().getFirst().getEndingAdrenaline());
        assertEquals(12, timeline3.getTimeline().getFirst().getEndingAdrenaline());
    }

    @Test
    void inciteFearProc_does_not_exist_below_threshold() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getSpell().setSpell(Spells.INCITEFEAR);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement asphyxiate = new AbilityPlacement();
        asphyxiate.setCastTick(0);
        asphyxiate.setPlacedAbility(AbilityId.ASPHYXIATE);
        abilities.add(asphyxiate);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        boolean inciteFearProc = false;

        for (TickSnapshot tick : timeline.getTimeline()) {
            for (TimelineHit hit : tick.getLandedHits()) {
                if (hit.getParentAbility() == AbilityId.INCITEFEARPROC) {
                    inciteFearProc = true;
                }
            }
        }

        assertFalse(inciteFearProc);
    }

    @Test
    void essenceCorruption_stacks_generated_per_hit_of_combust() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.SONGOFDESTRUCTION));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement combust = new AbilityPlacement();
        combust.setCastTick(0);
        combust.setPlacedAbility(AbilityId.COMBUST);
        abilities.add(combust);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(0, timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.ESSENCECORRUPTIONSTACKS));
        assertEquals(1, timeline.getTimeline().get(3).getEndingCombatState().getBuffs().stacks(BuffId.ESSENCECORRUPTIONSTACKS));
    }

    @Test
    void combust_damage_increases_with_essenceCorruption_stacks() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.SONGOFDESTRUCTION));
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.ESSENCECORRUPTIONSTACKS, 10);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement combust = new AbilityPlacement();
        combust.setCastTick(0);
        combust.setPlacedAbility(AbilityId.COMBUST);
        abilities.add(combust);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertTrue(timeline.getTimeline().get(3).getLandedHits().getFirst().getHitAvgDamage() < timeline.getTimeline().get(6).getLandedHits().getFirst().getHitAvgDamage());
        assertTrue(timeline.getTimeline().get(3).getEndingCombatState().getBuffs().stacks(BuffId.ESSENCECORRUPTIONSTACKS) < timeline.getTimeline().get(6).getEndingCombatState().getBuffs().stacks(BuffId.ESSENCECORRUPTIONSTACKS));
    }

    @Test
    void essenceCorruption_stacks_do_not_generate_without_songOfDestruction() {
        RotationCombatState rotationCombatState = sampleMagicState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement combust = new AbilityPlacement();
        combust.setCastTick(0);
        combust.setPlacedAbility(AbilityId.COMBUST);
        abilities.add(combust);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertFalse(timeline.getTimeline().get(12).getEndingCombatState().getBuffs().has(BuffId.ESSENCECORRUPTIONSTACKS));
    }

    @Test
    void essenceCorruption_stacks_do_not_add_damage_without_songOfDestruction() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.ESSENCECORRUPTIONSTACKS, 30);

        RotationCombatState rotationCombatState1 = sampleMagicState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement combust = new AbilityPlacement();
        combust.setCastTick(0);
        combust.setPlacedAbility(AbilityId.COMBUST);
        abilities.add(combust);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        RotationTimeline timeline1 = new RotationTimelineService(engine, copier)
                .build(rotationCombatState1, abilities, buffs);

        assertEquals(timeline.getTimeline().get(3).getLandedHits().getFirst().getHitAvgDamage(),
                timeline1.getTimeline().get(3).getLandedHits().getFirst().getHitAvgDamage());
    }

    @Test
    void essenceCorruption_stacks_do_not_add_damage_to_non_dots() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.SONGOFDESTRUCTION));
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.ESSENCECORRUPTIONSTACKS, 30);

        RotationCombatState rotationCombatState1 = sampleMagicState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement dragonbreath = new AbilityPlacement();
        dragonbreath.setCastTick(0);
        dragonbreath.setPlacedAbility(AbilityId.DRAGONBREATH);
        abilities.add(dragonbreath);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        RotationTimeline timeline1 = new RotationTimelineService(engine, copier)
                .build(rotationCombatState1, abilities, buffs);

        assertEquals(timeline.getTimeline().get(1).getLandedHits().getFirst().getHitAvgDamage(),
                timeline1.getTimeline().get(1).getLandedHits().getFirst().getHitAvgDamage());
    }

    @Test
    void corruptionBlast_damage_increases_with_essenceCorruption_stacks() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.SONGOFDESTRUCTION));
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.ESSENCECORRUPTIONSTACKS, 10);
        RotationCombatState rotationCombatState1 = sampleMagicState();


        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement corruptionBlast = new AbilityPlacement();
        corruptionBlast.setCastTick(0);
        corruptionBlast.setPlacedAbility(AbilityId.CORRUPTIONBLAST);
        abilities.add(corruptionBlast);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        RotationTimeline timeline1 = new RotationTimelineService(engine, copier)
                .build(rotationCombatState1, abilities, buffs);

        assertTrue(timeline.getTimeline().get(4).getLandedHits().getFirst().getHitAvgDamage() > timeline1.getTimeline().get(4).getLandedHits().getFirst().getHitAvgDamage());
        assertTrue(timeline.getTimeline().get(2).getEndingCombatState().getBuffs().stacks(BuffId.ESSENCECORRUPTIONSTACKS) < timeline.getTimeline().get(4).getEndingCombatState().getBuffs().stacks(BuffId.ESSENCECORRUPTIONSTACKS));
    }

    @Test
    void multiple_hits_on_same_tick_only_boost_dot_damage_with_essenceCorruption_stacks() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.SONGOFDESTRUCTION));
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.ESSENCECORRUPTIONSTACKS, 30);

        RotationCombatState rotationCombatState1 = sampleMagicState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement dragonbreath = new AbilityPlacement();
        dragonbreath.setCastTick(2);
        dragonbreath.setPlacedAbility(AbilityId.DRAGONBREATH);
        abilities.add(dragonbreath);

        AbilityPlacement combust = new AbilityPlacement();
        combust.setCastTick(0);
        combust.setPlacedAbility(AbilityId.COMBUST);
        abilities.add(combust);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        RotationTimeline timeline1 = new RotationTimelineService(engine, copier)
                .build(rotationCombatState1, abilities, buffs);

        int combustDiff = 0;
        int dbreathDiff = 0;

        for (TimelineHit hit : timeline.getTimeline().get(3).getLandedHits()) {
            if (hit.getParentAbility() == AbilityId.DRAGONBREATH) dbreathDiff += hit.getHitAvgDamage();
            if (hit.getParentAbility() == AbilityId.COMBUST) combustDiff += hit.getHitAvgDamage();
        }

        for (TimelineHit hit : timeline1.getTimeline().get(3).getLandedHits()) {
            if (hit.getParentAbility() == AbilityId.DRAGONBREATH) dbreathDiff -= hit.getHitAvgDamage();
            if (hit.getParentAbility() == AbilityId.COMBUST) combustDiff -= hit.getHitAvgDamage();
        }

        assertEquals(0, dbreathDiff);
        assertNotEquals(0, combustDiff);
    }

    @Test
    void essenceCorruption_does_not_add_damage_below_10_stacks_to_combust() {
        RotationCombatState rotationCombatState = sampleMagicState();
        rotationCombatState.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.SONGOFDESTRUCTION));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement combust = new AbilityPlacement();
        combust.setCastTick(0);
        combust.setPlacedAbility(AbilityId.COMBUST);
        abilities.add(combust);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(timeline.getTimeline().get(3).getLandedHits().getFirst().getHitAvgDamage(),
                timeline.getTimeline().get(6).getLandedHits().getFirst().getHitAvgDamage());
    }

    @Test
    void soulSap_generates_one_soul_stack() {
        RotationCombatState rotationCombatState = sampleNecromancyState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement soulsap = new AbilityPlacement();
        soulsap.setCastTick(0);
        soulsap.setPlacedAbility(AbilityId.SOULSAP);
        abilities.add(soulsap);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(1, timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.SOULSTACKS));
    }

    @Test
    void soulStacks_cap_at_3_without_soulboundLantern() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.SOULSTACKS, 2);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement soulsap = new AbilityPlacement();
        soulsap.setCastTick(0);
        soulsap.setPlacedAbility(AbilityId.SOULSAP);
        abilities.add(soulsap);
        abilities.add(soulsap);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(3, timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.SOULSTACKS));
    }

    @Test
    void soulStacks_cap_at_5_with_soulboundLantern() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.SOULSTACKS, 4);
        rotationCombatState.getEquipment().getOffhand().setEffect(EnumSet.of(Effect.SOULBOUNDLANTERN));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement soulsap = new AbilityPlacement();
        soulsap.setCastTick(0);
        soulsap.setPlacedAbility(AbilityId.SOULSAP);
        abilities.add(soulsap);
        abilities.add(soulsap);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(5, timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.SOULSTACKS));
    }

    @Test
    void soulStrike_consumes_one_soulStack() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.SOULSTACKS, 4);
        rotationCombatState.getEquipment().getOffhand().setEffect(EnumSet.of(Effect.SOULBOUNDLANTERN));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement soulStrike = new AbilityPlacement();
        soulStrike.setCastTick(0);
        soulStrike.setPlacedAbility(AbilityId.SOULSTRIKE);
        abilities.add(soulStrike);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(3, timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.SOULSTACKS));
    }

    @Test
    void volleyOfSouls_consumes_all_soulStacks() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.SOULSTACKS, 4);
        rotationCombatState.getEquipment().getOffhand().setEffect(EnumSet.of(Effect.SOULBOUNDLANTERN));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement volleyOfSouls = new AbilityPlacement();
        volleyOfSouls.setCastTick(0);
        volleyOfSouls.setPlacedAbility(AbilityId.VOLLEYOFSOULS);
        abilities.add(volleyOfSouls);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertFalse(timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().has(BuffId.SOULSTACKS));
    }

    @Test
    void soulCrush_consumes_all_soulStacks() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.SOULSTACKS, 4);
        rotationCombatState.getEquipment().getOffhand().setEffect(EnumSet.of(Effect.SOULBOUNDLANTERN));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement soulCrush = new AbilityPlacement();
        soulCrush.setCastTick(0);
        soulCrush.setPlacedAbility(AbilityId.SOULCRUSH);
        abilities.add(soulCrush);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertFalse(timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().has(BuffId.SOULSTACKS));
    }

    @Test
    void soulCrush_readies_soulReave() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.SOULSTACKS, 4);
        rotationCombatState.getEquipment().getOffhand().setEffect(EnumSet.of(Effect.SOULBOUNDLANTERN));
        rotationCombatState.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.DEVOURERSGUARD));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement soulCrush = new AbilityPlacement();
        soulCrush.setCastTick(0);
        soulCrush.setPlacedAbility(AbilityId.SOULCRUSH);
        abilities.add(soulCrush);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(4, timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.SOULREAVE));
    }

    @Test
    void necromancyAuto_consumes_soulReave_and_generates_one_soulStack() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.SOULSTACKS, 4);
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.SOULREAVE, 4);
        rotationCombatState.getEquipment().getOffhand().setEffect(EnumSet.of(Effect.SOULBOUNDLANTERN));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement necromancyAuto = new AbilityPlacement();
        necromancyAuto.setCastTick(0);
        necromancyAuto.setPlacedAbility(AbilityId.NECROMANCYAUTO);
        abilities.add(necromancyAuto);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(5, timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.SOULSTACKS));
        assertFalse(timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().has(BuffId.SOULREAVE));
    }

    @Test
    void volleyOfSouls_readies_soulReave_while_soulCrush_is_active() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.SOULSTACKS, 4);
        rotationCombatState.getBuffs().getBuffSet().add(BuffId.SOULCRUSH);
        rotationCombatState.getEquipment().getOffhand().setEffect(EnumSet.of(Effect.SOULBOUNDLANTERN));
        rotationCombatState.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.DEVOURERSGUARD));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement volleyOfSouls = new AbilityPlacement();
        volleyOfSouls.setCastTick(0);
        volleyOfSouls.setPlacedAbility(AbilityId.VOLLEYOFSOULS);
        abilities.add(volleyOfSouls);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(4, timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.SOULREAVE));
    }

    @Test
    void soulCrush_damage_scales_with_soulStacks_consumed() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.SOULSTACKS, 4);
        rotationCombatState.getBuffs().getBuffSet().add(BuffId.SOULCRUSH);
        rotationCombatState.getEquipment().getOffhand().setEffect(EnumSet.of(Effect.SOULBOUNDLANTERN));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement soulCrush = new AbilityPlacement();
        soulCrush.setCastTick(0);
        soulCrush.setPlacedAbility(AbilityId.SOULCRUSH);
        abilities.add(soulCrush);

        AbilityPlacement soulCrush1 = new AbilityPlacement();
        soulCrush1.setCastTick(3);
        soulCrush1.setPlacedAbility(AbilityId.SOULCRUSH);
        abilities.add(soulCrush1);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertTrue(timeline.getTimeline().get(2).getLandedHits().getFirst().getHitAvgDamage() >
                timeline.getTimeline().get(5).getLandedHits().getFirst().getHitAvgDamage());
    }

    @Test
    void volleyOfSouls_under_2_soulStacks_generates_warning() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.SOULSTACKS, 1);
        rotationCombatState.getEquipment().getOffhand().setEffect(EnumSet.of(Effect.SOULBOUNDLANTERN));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement volleyOfSouls = new AbilityPlacement();
        volleyOfSouls.setCastTick(0);
        volleyOfSouls.setPlacedAbility(AbilityId.VOLLEYOFSOULS);
        abilities.add(volleyOfSouls);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertFalse(timeline.getTimeline().getFirst().getWarnings().isEmpty());
        assertEquals("Insufficient amount of: Soul Stacks", timeline.getTimeline().getFirst().getWarnings().getFirst());
    }

    @Test
    void volleyOfSouls_at_2_soulStacks_does_not_generate_warning() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.SOULSTACKS, 2);
        rotationCombatState.getEquipment().getOffhand().setEffect(EnumSet.of(Effect.SOULBOUNDLANTERN));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement volleyOfSouls = new AbilityPlacement();
        volleyOfSouls.setCastTick(0);
        volleyOfSouls.setPlacedAbility(AbilityId.VOLLEYOFSOULS);
        abilities.add(volleyOfSouls);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertTrue(timeline.getTimeline().getFirst().getWarnings().isEmpty());
    }

    @Test
    void necromancyAuto_generates_1_deathSpark_stack() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.OMNIGUARD));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement necromancyAuto = new AbilityPlacement();
        necromancyAuto.setCastTick(0);
        necromancyAuto.setPlacedAbility(AbilityId.NECROMANCYAUTO);
        abilities.add(necromancyAuto);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(1, timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.DEATHSPARK));
    }

    @Test
    void deathSpark_stacks_not_generated_without_omniGuard() {
        RotationCombatState rotationCombatState = sampleNecromancyState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement necromancyAuto = new AbilityPlacement();
        necromancyAuto.setCastTick(0);
        necromancyAuto.setPlacedAbility(AbilityId.NECROMANCYAUTO);
        abilities.add(necromancyAuto);

        AbilityPlacement deathEssence = new AbilityPlacement();
        deathEssence.setCastTick(3);
        deathEssence.setPlacedAbility(AbilityId.DEATHESSENCE);
        abilities.add(deathEssence);

        AbilityPlacement touchOfDeath = new AbilityPlacement();
        touchOfDeath.setCastTick(6);
        touchOfDeath.setPlacedAbility(AbilityId.TOUCHOFDEATH);
        abilities.add(touchOfDeath);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertFalse(timeline.getTimeline().get(9).getEndingCombatState().getBuffs().has(BuffId.DEATHSPARK));
    }

    @Test
    void deathEssence_ability_immediately_readies_deathSpark() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.OMNIGUARD));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement deathEssence = new AbilityPlacement();
        deathEssence.setCastTick(0);
        deathEssence.setPlacedAbility(AbilityId.DEATHESSENCE);
        abilities.add(deathEssence);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(5, timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.DEATHSPARK));
    }

    // TODO: Need to adjust hit timing of enhanced necromancy autos to be t2.
    @Test
    void necromancyAuto_consumes_deathSpark_at_5_and_increases_damage() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.OMNIGUARD));
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.DEATHSPARK, 4);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement necromancyAuto = new AbilityPlacement();
        necromancyAuto.setCastTick(0);
        necromancyAuto.setPlacedAbility(AbilityId.NECROMANCYAUTO);
        abilities.add(necromancyAuto);

        AbilityPlacement necromancyAuto1 = new AbilityPlacement();
        necromancyAuto1.setCastTick(3);
        necromancyAuto1.setPlacedAbility(AbilityId.NECROMANCYAUTO);
        abilities.add(necromancyAuto1);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertFalse(timeline.getTimeline().get(3).getEndingCombatState().getBuffs().has(BuffId.DEATHSPARK));
        assertTrue(timeline.getTimeline().get(1).getLandedHits().getFirst().getHitAvgDamage() <
                timeline.getTimeline().get(4).getLandedHits().getFirst().getHitAvgDamage());
    }

    @Test
    void touchOfDeath_immediately_readies_deathSpark_if_deathEssence_buff_is_active() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.OMNIGUARD));

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement deathEssence = new AbilityPlacement();
        deathEssence.setCastTick(0);
        deathEssence.setPlacedAbility(AbilityId.DEATHESSENCE);
        abilities.add(deathEssence);

        AbilityPlacement necromancyAuto = new AbilityPlacement();
        necromancyAuto.setCastTick(3);
        necromancyAuto.setPlacedAbility(AbilityId.NECROMANCYAUTO);
        abilities.add(necromancyAuto);

        AbilityPlacement touchOfDeath = new AbilityPlacement();
        touchOfDeath.setCastTick(6);
        touchOfDeath.setPlacedAbility(AbilityId.TOUCHOFDEATH);
        abilities.add(touchOfDeath);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(5, timeline.getTimeline().get(6).getEndingCombatState().getBuffs().stacks(BuffId.DEATHSPARK));
    }

    @Test
    void touchOfDeath_generates_4_necrosis_stacks() {
        RotationCombatState rotationCombatState = sampleNecromancyState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement touchOfDeath = new AbilityPlacement();
        touchOfDeath.setCastTick(0);
        touchOfDeath.setPlacedAbility(AbilityId.TOUCHOFDEATH);
        abilities.add(touchOfDeath);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(4, timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.NECROSIS));
    }

    @Test
    void necromancyAuto_does_not_generate_necrosis_stacks_outside_of_livingDeath() {
        RotationCombatState rotationCombatState = sampleNecromancyState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement necromancyAuto = new AbilityPlacement();
        necromancyAuto.setCastTick(0);
        necromancyAuto.setPlacedAbility(AbilityId.NECROMANCYAUTO);
        abilities.add(necromancyAuto);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertFalse(timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().has(BuffId.NECROSIS));
    }

    @Test
    void necromancyAuto_generates_2_necrosis_inside_livingDeath() {
        RotationCombatState rotationCombatState = sampleNecromancyState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        BuffPlacement livingDeath = new BuffPlacement();
        livingDeath.setBuffId(BuffId.LIVINGDEATH);
        livingDeath.setPlacementTick(0);
        buffs.add(livingDeath);

        AbilityPlacement necromancyAuto = new AbilityPlacement();
        necromancyAuto.setCastTick(3);
        necromancyAuto.setPlacedAbility(AbilityId.NECROMANCYAUTO);
        abilities.add(necromancyAuto);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(2, timeline.getTimeline().get(3).getEndingCombatState().getBuffs().stacks(BuffId.NECROSIS));
    }

    @Test
    void fingerOfDeath_consumes_up_to_6_necrosis() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.NECROSIS, 8);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement fingerOfDeath = new AbilityPlacement();
        fingerOfDeath.setCastTick(0);
        fingerOfDeath.setPlacedAbility(AbilityId.FINGEROFDEATH);
        abilities.add(fingerOfDeath);

        AbilityPlacement fingerOfDeath2 = new AbilityPlacement();
        fingerOfDeath2.setCastTick(3);
        fingerOfDeath2.setPlacedAbility(AbilityId.FINGEROFDEATH);
        abilities.add(fingerOfDeath2);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(2, timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().stacks(BuffId.NECROSIS));
        assertEquals(0, timeline.getTimeline().get(3).getEndingCombatState().getBuffs().stacks(BuffId.NECROSIS));
    }

    @Test
    void deathGrasp_consumes_all_necrosis_stacks() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.NECROSIS, 12);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement deathGrasp = new AbilityPlacement();
        deathGrasp.setCastTick(0);
        deathGrasp.setPlacedAbility(AbilityId.DEATHGRASP);
        abilities.add(deathGrasp);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertFalse(timeline.getTimeline().getFirst().getEndingCombatState().getBuffs().has(BuffId.NECROSIS));
    }

    @Test
    void necrosis_reduces_adrenaline_cost_of_fingerOfDeath() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.NECROSIS, 8);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement fingerOfDeath = new AbilityPlacement();
        fingerOfDeath.setCastTick(0);
        fingerOfDeath.setPlacedAbility(AbilityId.FINGEROFDEATH);
        abilities.add(fingerOfDeath);

        AbilityPlacement fingerOfDeath2 = new AbilityPlacement();
        fingerOfDeath2.setCastTick(3);
        fingerOfDeath2.setPlacedAbility(AbilityId.FINGEROFDEATH);
        abilities.add(fingerOfDeath2);

        AbilityPlacement fingerOfDeath3 = new AbilityPlacement();
        fingerOfDeath3.setCastTick(6);
        fingerOfDeath3.setPlacedAbility(AbilityId.FINGEROFDEATH);
        abilities.add(fingerOfDeath3);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(100, timeline.getTimeline().getFirst().getEndingAdrenaline());
        assertEquals(60, timeline.getTimeline().get(3).getEndingAdrenaline());
        assertEquals(0, timeline.getTimeline().get(6).getEndingAdrenaline());
    }

    @Test
    void necrosis_stacks_increase_deathGrasp_damage() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.NECROSIS, 12);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement deathGrasp = new AbilityPlacement();
        deathGrasp.setCastTick(0);
        deathGrasp.setPlacedAbility(AbilityId.DEATHGRASP);
        abilities.add(deathGrasp);

        AbilityPlacement deathGrasp2 = new AbilityPlacement();
        deathGrasp2.setCastTick(3);
        deathGrasp2.setPlacedAbility(AbilityId.DEATHGRASP);
        abilities.add(deathGrasp2);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertTrue(timeline.getTimeline().get(1).getLandedHits().getFirst().getHitAvgDamage() >
                timeline.getTimeline().get(4).getLandedHits().getFirst().getHitAvgDamage());
    }

    @Test
    void conjureSkeletonWarrior_does_not_generate_rage_on_release() {
        RotationCombatState rotationCombatState = sampleNecromancyState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement conjureSkeletonWarrior = new AbilityPlacement();
        conjureSkeletonWarrior.setCastTick(0);
        conjureSkeletonWarrior.setPlacedAbility(AbilityId.CONJURESKELETONWARRIOR);
        abilities.add(conjureSkeletonWarrior);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertFalse(timeline.getTimeline().get(4).getEndingCombatState().getBuffs().has(BuffId.RAGE));
    }

    @Test
    void conjureSkeletonWarrior_generates_rage_when_initial_hit_lands() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getEquipment().getOffhand().getEffect().add(Effect.NECROMANCY_CONDUIT);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement conjureSkeletonWarrior = new AbilityPlacement();
        conjureSkeletonWarrior.setCastTick(0);
        conjureSkeletonWarrior.setPlacedAbility(AbilityId.CONJURESKELETONWARRIOR);
        abilities.add(conjureSkeletonWarrior);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(1, timeline.getTimeline().get(5).getEndingCombatState().getBuffs().stacks(BuffId.RAGE));
    }

    @Test
    void conjureSkeletonWarrior_generates_rage_for_followup_hits() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getEquipment().getOffhand().getEffect().add(Effect.NECROMANCY_CONDUIT);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement conjureSkeletonWarrior = new AbilityPlacement();
        conjureSkeletonWarrior.setCastTick(0);
        conjureSkeletonWarrior.setPlacedAbility(AbilityId.CONJURESKELETONWARRIOR);
        abilities.add(conjureSkeletonWarrior);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(2, timeline.getTimeline().get(10).getEndingCombatState().getBuffs().stacks(BuffId.RAGE));
        assertEquals(3, timeline.getTimeline().get(15).getEndingCombatState().getBuffs().stacks(BuffId.RAGE));
        assertEquals(4, timeline.getTimeline().get(20).getEndingCombatState().getBuffs().stacks(BuffId.RAGE));
    }

    @Test
    void conjureSkeletonWarrior_rage_stacks_cap_at_25() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getEquipment().getOffhand().getEffect().add(Effect.NECROMANCY_CONDUIT);
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.RAGE, 22);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement conjureSkeletonWarrior = new AbilityPlacement();
        conjureSkeletonWarrior.setCastTick(0);
        conjureSkeletonWarrior.setPlacedAbility(AbilityId.CONJURESKELETONWARRIOR);
        abilities.add(conjureSkeletonWarrior);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(24, timeline.getTimeline().get(10).getEndingCombatState().getBuffs().stacks(BuffId.RAGE));
        assertEquals(25, timeline.getTimeline().get(15).getEndingCombatState().getBuffs().stacks(BuffId.RAGE));
        assertEquals(25, timeline.getTimeline().get(20).getEndingCombatState().getBuffs().stacks(BuffId.RAGE));
    }

    @Test
    void conjureSkeletonWarrior_followup_hits_stop_after_duration() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.RAGE, 22);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement conjureSkeletonWarrior = new AbilityPlacement();
        conjureSkeletonWarrior.setCastTick(0);
        conjureSkeletonWarrior.setPlacedAbility(AbilityId.CONJURESKELETONWARRIOR);
        abilities.add(conjureSkeletonWarrior);

        AbilityPlacement necromancyAuto = new AbilityPlacement();
        necromancyAuto.setCastTick(75);
        necromancyAuto.setPlacedAbility(AbilityId.NECROMANCYAUTO);
        abilities.add(necromancyAuto);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        boolean success = true;
        for (int i = 71; i < timeline.getTimeline().size(); i++) {
            for (TimelineHit hit : timeline.getTimeline().get(i).getLandedHits()) {
                if (hit.getParentAbility() == AbilityId.CONJURESKELETONWARRIOR) {
                    success = false;
                    break;
                }
            }
        }

        assertTrue(success);
    }

    @Test
    void commandSkeleton_produces_10_landed_commandSkeletonWarriorHit_hits() {
        RotationCombatState rotationCombatState = sampleNecromancyState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement commandSkeletonWarrior = new AbilityPlacement();
        commandSkeletonWarrior.setCastTick(0);
        commandSkeletonWarrior.setPlacedAbility(AbilityId.COMMANDSKELETONWARRIOR);
        abilities.add(commandSkeletonWarrior);

        AbilityPlacement necromancyAuto = new AbilityPlacement();
        necromancyAuto.setCastTick(12);
        necromancyAuto.setPlacedAbility(AbilityId.NECROMANCYAUTO);
        abilities.add(necromancyAuto);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        int hits = 0;

        for (TickSnapshot tick : timeline.getTimeline()) {
            for (TimelineHit hit : tick.getLandedHits()) {
                if (hit.getParentAbility() == AbilityId.COMMANDSKELETONWARRIORHIT) {
                    hits++;
                }
            }
        }

        assertEquals(10, hits);
    }

    @Test
    void commandSkeleton_initial_hit_lands_t1() {
        RotationCombatState rotationCombatState = sampleNecromancyState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement commandSkeletonWarrior = new AbilityPlacement();
        commandSkeletonWarrior.setCastTick(0);
        commandSkeletonWarrior.setPlacedAbility(AbilityId.COMMANDSKELETONWARRIOR);
        abilities.add(commandSkeletonWarrior);
        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(1, timeline.getTimeline().get(1).getLandedHits().size());
        assertEquals(AbilityId.COMMANDSKELETONWARRIORHIT, timeline.getTimeline().get(1).getLandedHits().getFirst().getParentAbility());
    }

    @Test
    void commandSkeleton_generates_rage_each_landing_tick() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getEquipment().getOffhand().getEffect().add(Effect.NECROMANCY_CONDUIT);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement conjureSkeleton = new AbilityPlacement();
        conjureSkeleton.setCastTick(0);
        conjureSkeleton.setPlacedAbility(AbilityId.CONJURESKELETONWARRIOR);
        abilities.add(conjureSkeleton);

        AbilityPlacement commandSkeletonWarrior = new AbilityPlacement();
        commandSkeletonWarrior.setCastTick(5);
        commandSkeletonWarrior.setPlacedAbility(AbilityId.COMMANDSKELETONWARRIOR);
        abilities.add(commandSkeletonWarrior);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(2, timeline.getTimeline().get(6).getEndingCombatState().getBuffs().stacks(BuffId.RAGE));
        assertEquals(3, timeline.getTimeline().get(7).getEndingCombatState().getBuffs().stacks(BuffId.RAGE));
        assertEquals(4, timeline.getTimeline().get(8).getEndingCombatState().getBuffs().stacks(BuffId.RAGE));
        assertEquals(5, timeline.getTimeline().get(9).getEndingCombatState().getBuffs().stacks(BuffId.RAGE));
        assertEquals(6, timeline.getTimeline().get(10).getEndingCombatState().getBuffs().stacks(BuffId.RAGE));
        assertEquals(7, timeline.getTimeline().get(11).getEndingCombatState().getBuffs().stacks(BuffId.RAGE));
        assertEquals(8, timeline.getTimeline().get(12).getEndingCombatState().getBuffs().stacks(BuffId.RAGE));
        assertEquals(9, timeline.getTimeline().get(13).getEndingCombatState().getBuffs().stacks(BuffId.RAGE));
        assertEquals(10, timeline.getTimeline().get(14).getEndingCombatState().getBuffs().stacks(BuffId.RAGE));
    }

    @Test
    void commandSkeleton_suppresses_conjureSkeleton_hits() {
        RotationCombatState rotationCombatState = sampleNecromancyState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement conjureSkeletonWarrior = new AbilityPlacement();
        conjureSkeletonWarrior.setCastTick(0);
        conjureSkeletonWarrior.setPlacedAbility(AbilityId.CONJURESKELETONWARRIOR);
        abilities.add(conjureSkeletonWarrior);

        AbilityPlacement commandSkeletonWarrior = new AbilityPlacement();
        commandSkeletonWarrior.setCastTick(6);
        commandSkeletonWarrior.setPlacedAbility(AbilityId.COMMANDSKELETONWARRIOR);
        abilities.add(commandSkeletonWarrior);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        boolean success = true;
        for (TickSnapshot tick : timeline.getTimeline()) {
            for (TimelineHit hit : tick.getLandedHits()) {
                if (hit.getParentAbility() == AbilityId.CONJURESKELETONWARRIOR && tick.getStartingCombatState().getBuffs().has(BuffId.COMMANDSKELETONWARRIOR)) {
                    success = false;
                }
            }
        }

        assertTrue(success);
    }

    @Test
    void conjureSkeleton_hits_resume_after_commandSkeleton_expiry() {
        RotationCombatState rotationCombatState = sampleNecromancyState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement conjureSkeletonWarrior = new AbilityPlacement();
        conjureSkeletonWarrior.setCastTick(0);
        conjureSkeletonWarrior.setPlacedAbility(AbilityId.CONJURESKELETONWARRIOR);
        abilities.add(conjureSkeletonWarrior);

        AbilityPlacement commandSkeletonWarrior = new AbilityPlacement();
        commandSkeletonWarrior.setCastTick(3);
        commandSkeletonWarrior.setPlacedAbility(AbilityId.COMMANDSKELETONWARRIOR);
        abilities.add(commandSkeletonWarrior);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        int conjureSkeletonWarriorHits = 0;
        for (int i = 0; i < timeline.getTimeline().size(); i++) {
            for(TimelineHit hit : timeline.getTimeline().get(i).getLandedHits()) {
                if (hit.getParentAbility() == AbilityId.CONJURESKELETONWARRIOR) {
                    conjureSkeletonWarriorHits++;
                }
            }
        }

        assertTrue(conjureSkeletonWarriorHits > 0);
    }

    @Test
    void rage_stacks_cap_at_25_from_commandSkeletonHits() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getEquipment().getOffhand().getEffect().add(Effect.NECROMANCY_CONDUIT);
        rotationCombatState.getBuffs().getBuffStacks().put(BuffId.RAGE, 22);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement conjureSkeletonWarrior = new AbilityPlacement();
        conjureSkeletonWarrior.setCastTick(0);
        conjureSkeletonWarrior.setPlacedAbility(AbilityId.CONJURESKELETONWARRIOR);
        abilities.add(conjureSkeletonWarrior);

        AbilityPlacement commandSkeletonWarrior = new AbilityPlacement();
        commandSkeletonWarrior.setCastTick(3);
        commandSkeletonWarrior.setPlacedAbility(AbilityId.COMMANDSKELETONWARRIOR);
        abilities.add(commandSkeletonWarrior);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(25, timeline.getTimeline().get(10).getEndingCombatState().getBuffs().stacks(BuffId.RAGE));
    }

    @Test
    void conjurePutridZombie_schedules_both_internal_hit_sources() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getEquipment().getOffhand().getEffect().add(Effect.NECROMANCY_CONDUIT);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement conjurePutridZombie = new AbilityPlacement();
        conjurePutridZombie.setCastTick(0);
        conjurePutridZombie.setPlacedAbility(AbilityId.CONJUREPUTRIDZOMBIE);
        abilities.add(conjurePutridZombie);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(AbilityId.PUTRIDZOMBIEPOISON, timeline.getTimeline().get(3).getLandedHits().getFirst().getParentAbility());
        assertEquals(AbilityId.PUTRIDZOMBIEPOISON, timeline.getTimeline().get(6).getLandedHits().get(1).getParentAbility());
        assertEquals(AbilityId.PUTRIDZOMBIEHIT, timeline.getTimeline().get(6).getLandedHits().getFirst().getParentAbility());
    }

    @Test
    void putridZombiePoison_starts_at_tick_3() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getEquipment().getOffhand().getEffect().add(Effect.NECROMANCY_CONDUIT);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement conjurePutridZombie = new AbilityPlacement();
        conjurePutridZombie.setCastTick(0);
        conjurePutridZombie.setPlacedAbility(AbilityId.CONJUREPUTRIDZOMBIE);
        abilities.add(conjurePutridZombie);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(AbilityId.PUTRIDZOMBIEPOISON, timeline.getTimeline().get(3).getLandedHits().getFirst().getParentAbility());
    }

    @Test
    void putridZombieHit_starts_at_tick_6() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getEquipment().getOffhand().getEffect().add(Effect.NECROMANCY_CONDUIT);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement conjurePutridZombie = new AbilityPlacement();
        conjurePutridZombie.setCastTick(0);
        conjurePutridZombie.setPlacedAbility(AbilityId.CONJUREPUTRIDZOMBIE);
        abilities.add(conjurePutridZombie);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(AbilityId.PUTRIDZOMBIEHIT, timeline.getTimeline().get(6).getLandedHits().getFirst().getParentAbility());
    }

    @Test
    void putridZombie_damage_recurs_until_expiry() {
        RotationCombatState rotationCombatState = sampleNecromancyState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement conjurePutridZombie = new AbilityPlacement();
        conjurePutridZombie.setCastTick(0);
        conjurePutridZombie.setPlacedAbility(AbilityId.CONJUREPUTRIDZOMBIE);
        abilities.add(conjurePutridZombie);

        AbilityPlacement necromancyAuto = new AbilityPlacement();
        necromancyAuto.setCastTick(100);
        necromancyAuto.setPlacedAbility(AbilityId.NECROMANCYAUTO);
        abilities.add(necromancyAuto);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        boolean success = true;
        for (int i = 71; i < timeline.getTimeline().size(); i++) {
            for (TimelineHit hit : timeline.getTimeline().get(i).getLandedHits()) {
                if (hit.getParentAbility() == AbilityId.PUTRIDZOMBIEHIT || hit.getParentAbility() == AbilityId.PUTRIDZOMBIEPOISON) {
                    success = false;
                    break;
                }
            }
        }

        assertTrue(success);
    }

    @Test
    void putridZombie_release_damage_not_double_counted() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getEquipment().getOffhand().getEffect().add(Effect.NECROMANCY_CONDUIT);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement conjurePutridZombie = new AbilityPlacement();
        conjurePutridZombie.setCastTick(0);
        conjurePutridZombie.setPlacedAbility(AbilityId.CONJUREPUTRIDZOMBIE);
        abilities.add(conjurePutridZombie);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(1, timeline.getTimeline().get(3).getLandedHits().size());
        assertEquals(2, timeline.getTimeline().get(6).getLandedHits().size());
    }

    @Test
    void vengefulGhost_creates_recurring_hits() {
        RotationCombatState rotationCombatState = sampleNecromancyState();
        rotationCombatState.getEquipment().getOffhand().getEffect().add(Effect.NECROMANCY_CONDUIT);

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement conjureVengefulGhost = new AbilityPlacement();
        conjureVengefulGhost.setCastTick(0);
        conjureVengefulGhost.setPlacedAbility(AbilityId.CONJUREVENGEFULGHOST);
        abilities.add(conjureVengefulGhost);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        int vengefulGhostHits = 0;
        for (TickSnapshot tick : timeline.getTimeline()) {
            for (TimelineHit hit : tick.getLandedHits()) {
                if (hit.getParentAbility() == AbilityId.CONJUREVENGEFULGHOST) vengefulGhostHits++;
            }
        }

        assertTrue(vengefulGhostHits > 2);
    }

    @Test
    void vengefulGhost_first_hit_timing_is_t7() {
        RotationCombatState rotationCombatState = sampleNecromancyState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement conjureVengefulGhost = new AbilityPlacement();
        conjureVengefulGhost.setCastTick(0);
        conjureVengefulGhost.setPlacedAbility(AbilityId.CONJUREVENGEFULGHOST);
        abilities.add(conjureVengefulGhost);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        assertEquals(AbilityId.CONJUREVENGEFULGHOST, timeline.getTimeline().get(7).getLandedHits().getFirst().getParentAbility());
    }

    @Test
    void vengefulGhost_hits_stop_after_expiry() {
        RotationCombatState rotationCombatState = sampleNecromancyState();

        List<AbilityPlacement> abilities = new ArrayList<>();
        List<BuffPlacement> buffs = new ArrayList<>();

        AbilityPlacement conjureVengefulGhost = new AbilityPlacement();
        conjureVengefulGhost.setCastTick(0);
        conjureVengefulGhost.setPlacedAbility(AbilityId.CONJUREVENGEFULGHOST);
        abilities.add(conjureVengefulGhost);

        AbilityPlacement necromancyAuto = new AbilityPlacement();
        necromancyAuto.setCastTick(100);
        necromancyAuto.setPlacedAbility(AbilityId.NECROMANCYAUTO);
        abilities.add(necromancyAuto);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, buffs);

        boolean success = true;
        for (int i = 71; i < timeline.getTimeline().size(); i++) {
            for (TimelineHit hit : timeline.getTimeline().get(i).getLandedHits()) {
                if (hit.getParentAbility() == AbilityId.CONJUREVENGEFULGHOST) {
                    success = false;
                    break;
                }
            }
        }

        assertTrue(success);
    }
}
