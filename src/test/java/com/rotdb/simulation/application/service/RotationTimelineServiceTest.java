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
        RotationCombatState state = sampleRangedState();
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
        RotationCombatState state = sampleRangedState();
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
        RotationCombatState state = sampleRangedState();
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
        RotationCombatState state = sampleRangedState();
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
        RotationCombatState state = sampleRangedState();
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
    void procProcessor_seededRandom_correct_outcom() {
        RotationCombatState rotationCombatState = sampleRangedState();
        rotationCombatState.getEquipment().getMainhand().setEffect(EnumSet.of(Effect.PRIMORDIALICESTACKS));
        rotationCombatState.getEquipment().getMainhand().setTitle("dark shard of leng");
        rotationCombatState.getEquipment().getOffhand().setEffect(EnumSet.of(Effect.PRIMORDIALICESTACKS));
        rotationCombatState.getEquipment().getOffhand().setTitle("dark sliver of leng");

        RotationCombatState rotationCombatState2 = sampleRangedState();
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
                .build(rotationCombatState2, abilities, new ArrayList<>(), config2);

        assertEquals(timeline.getTimeline().getLast().getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS),
                timeline2.getTimeline().getLast().getEndingCombatState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS));
    }

    private static RotationCombatState sampleRangedState() {
        EquipmentSlot mainhand = new EquipmentSlot();
        mainhand.setTitle("Sandbox bow");
        mainhand.setClazz(CombatStyles.RANGED);
        mainhand.setSlot(Slots.TWOHANDED);
        mainhand.setTier(90);
        mainhand.setDamageTier(90);
        mainhand.setAccuracyTier(90);
        mainhand.setRanged(0);
        mainhand.setEffect(EnumSet.noneOf(Effect.class));

        EquipmentSlot ammo = EquipmentSlot.emptySlot();
        ammo.setDamageTier(90);

        EquipmentModel equipment = new EquipmentModel();
        equipment.setMainhand(mainhand);
        equipment.setAmmo(ammo);
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
