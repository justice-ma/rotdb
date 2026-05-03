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
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.RotationCombatState;
import com.rotdb.simulation.domain.model.context.RotationTimeline;
import com.rotdb.simulation.domain.model.context.TickSnapshot;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RotationTimelineServiceTest {
    CalculationEngine engine = new CalculationEngine();

    @Test
    void deadshotIgneous_placesHitsOnExpectedTicks() {
        // Arrange
        RotationCombatState state = sampleRangedState();

        AbilityPlacement deadshot = new AbilityPlacement();
        deadshot.setPlacementTick(0);
        deadshot.setPlacedAbility(AbilityId.DEADSHOTIGNEOUS);

        // Act
        RotationTimeline timeline = new RotationTimelineService(engine)
                .build(state, List.of(deadshot));

        // Assert
        assertEquals(5, timeline.getTimeline().size());

        TickSnapshot tick0 = timeline.getTimeline().get(0);
        TickSnapshot tick3 = timeline.getTimeline().get(3);
        TickSnapshot tick4 = timeline.getTimeline().get(4);

        assertEquals(0, tick0.getTick());
        assertEquals(1, tick0.getPlacedAbilities().size());
        assertEquals(AbilityId.DEADSHOTIGNEOUS, tick0.getPlacedAbilities().getFirst().getPlacedAbility());

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
        deadshot.setPlacementTick(0);
        deadshot.setPlacedAbility(AbilityId.DEADSHOTIGNEOUS);

        AbilityPlacement greaterRicochet = new AbilityPlacement();
        greaterRicochet.setPlacementTick(3);
        greaterRicochet.setPlacedAbility(AbilityId.GREATERRICOCHET);

        List<AbilityPlacement> abilities = new ArrayList<>();
        abilities.add(deadshot);
        abilities.add(greaterRicochet);

        // Act
        RotationTimeline timeline = new RotationTimelineService(engine)
                .build(state, abilities);

        // Assert
        assertEquals(7, timeline.getTimeline().size());

        TickSnapshot tick0 = timeline.getTimeline().get(0);
        TickSnapshot tick3 = timeline.getTimeline().get(3);
        TickSnapshot tick4 = timeline.getTimeline().get(4);
        TickSnapshot tick5 = timeline.getTimeline().get(5);
        TickSnapshot tick6 = timeline.getTimeline().get(6);

        assertEquals(0, tick0.getTick());
        assertEquals(1, tick0.getPlacedAbilities().size());
        assertEquals(AbilityId.DEADSHOTIGNEOUS, tick0.getPlacedAbilities().getFirst().getPlacedAbility());

        assertEquals(1, tick3.getPlacedAbilities().size());
        assertEquals(AbilityId.GREATERRICOCHET, tick3.getPlacedAbilities().getFirst().getPlacedAbility());

        assertEquals(100.0, tick0.getStartingAdrenaline());
        assertEquals(40.0, tick0.getEndingAdrenaline());
        assertEquals(40.0, tick3.getStartingAdrenaline());
        assertEquals(49.0, tick3.getEndingAdrenaline());

        assertEquals(4, tick3.getLandedHits().size());
        assertEquals(4, tick4.getLandedHits().size());
        assertEquals(1, tick5.getLandedHits().size());
        assertEquals(6, tick6.getLandedHits().size());
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
