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
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RotationTimelineServiceTest {
    CalculationEngine engine = new CalculationEngine();
    SimulationStateSnapshotCopier copier = new SimulationStateSnapshotCopier();

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
        TickSnapshot tick10 = timeline.getTimeline().get(10);
        TickSnapshot tick11 = timeline.getTimeline().get(11);
        TickSnapshot tick20 = timeline.getTimeline().get(20);
        TickSnapshot tick21 = timeline.getTimeline().get(21);

        assertEquals(0, tick0.getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
        assertEquals(0, tick10.getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
        assertEquals(1, tick11.getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
        assertEquals(1, tick20.getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
        assertEquals(2, tick21.getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
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

        assertEquals(8,
                timeline.getTimeline().get(9).getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
        assertFalse(timeline.getTimeline().get(10).getEndingCombatState().getBuffs().has(BuffId.PRIMORDIALICESTACKS));
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
        icyTempest2.setCastTick(15);
        icyTempest2.setPlacedAbility(AbilityId.ICYTEMPEST);
        abilities.add(icyTempest2);

        SimulationConfig config = new SimulationConfig();
        config.setProcMode(ProcMode.FORCED);

        RotationTimeline timeline = new RotationTimelineService(engine, copier)
                .build(rotationCombatState, abilities, new ArrayList<>(), config);

        assertEquals(75, timeline.getTimeline().get(9).getEndingAdrenaline());
        assertEquals(75, timeline.getTimeline().get(10).getEndingAdrenaline());
        assertEquals(69, timeline.getTimeline().get(15).getEndingAdrenaline());
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
}
