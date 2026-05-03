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
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.RotationCombatState;
import com.rotdb.simulation.domain.model.context.RotationTimeline;
import com.rotdb.simulation.domain.model.context.TickSnapshot;

import java.util.*;

public class Sandbox {
    public static void main(String[] args) {
        RotationCombatState state = sampleRangedState();
        List<AbilityPlacement> abilities = new ArrayList<>();
        AbilityPlacement placement = new AbilityPlacement();
        placement.setPlacementTick(0);
        placement.setPlacedAbility(AbilityId.DEATHSKULLS);
        abilities.add(placement);
        AbilityPlacement placement2 = new AbilityPlacement();
        placement2.setPlacementTick(13);
        placement2.setPlacedAbility(AbilityId.DEATHSKULLS);
        abilities.add(placement2);
        CalculationEngine engine = new CalculationEngine();
        RotationTimeline timeline = new RotationTimelineService(engine)
                .build(state, abilities);

        for (TickSnapshot tick : timeline.getTimeline()) {
            System.out.print("T" + tick.getTick() + " ability = ");
            for (int i = 0; i < tick.getPlacedAbilities().size(); i++) {
                System.out.print((tick.getPlacedAbilities().get(i).getPlacedAbility() == null ? " -- " : tick.getPlacedAbilities().get(i).getPlacedAbility()));
                if (i != tick.getPlacedAbilities().size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.print(" adrenaline=" + tick.getStartingAdrenaline() + " -> " + tick.getEndingAdrenaline()
                    + " hits=" + tick.getLandedHits().size()
                    + " cooldowns=" + tick.getEndingCooldownMap());
            if (!tick.getWarnings().isEmpty()) {
                System.out.print(", warnings=" + tick.getWarnings().get(0) + "\n");
            } else {
                System.out.println("\n");
            }
        }
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

        EquipmentSlot boots = EquipmentSlot.emptySlot();
        boots.setEffect(EnumSet.of(Effect.FLEETINGBOOTS));

        EquipmentModel equipment = new EquipmentModel();
        equipment.setMainhand(mainhand);
        equipment.setAmmo(ammo);
        equipment.setBoots(boots);
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
        buffs.getBuffSet().add(BuffId.HEIGHTENEDSENSES);
        buffs.getBuffSet().add(BuffId.RINGOFVIGOUR);
        buffs.getBuffSet().add(BuffId.LIVINGDEATH);

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
