package com.rotdb.calculation;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.context.TargetContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Prayer;
import com.rotdb.shared.combat.domain.model.enums.TargetTags;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.FamiliarContext;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.PrayerContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;

import java.util.*;

public class BaseCombatState {
    public static AggregatedCalculationContext baseState() {
        CalculationContext context = new CalculationContext();

        context.setSkills(baseStats());
        context.setEquipment(baseEquipment());
        context.setBuffs(baseBuffs());
        context.setTarget(baseTarget());
        context.setPerks(basePerks());
        context.setFamiliar(baseFamiliar());
        context.setPrayer(basePrayers());

        return new AggregatedCalculationContext(context, context);
    }

    private static SkillsContext baseStats() {
        SkillsContext skillsContext = new SkillsContext();
        skillsContext.setBaseAttack(120);
        skillsContext.setBaseStrength(120);
        skillsContext.setBaseMagic(120);
        skillsContext.setBaseRanged(120);
        skillsContext.setBaseNecromancy(120);
        skillsContext.setBaseDefence(99);
        skillsContext.setCurrentHp(10100);
        skillsContext.setMaxHp(10100);

        return skillsContext;
    }

    private static EquipmentModel baseEquipment() {
        EquipmentModel equipmentModel = new EquipmentModel();
        equipmentModel.fillMissingWithEmpty();

        return equipmentModel;
    }

    private static BuffContext baseBuffs() {
        BuffContext buffContext = new BuffContext();
        Set<BuffId> buffSet = new HashSet<>();
        buffSet.addAll(EnumSet.of(
                BuffId.SHARDOFGENESIS,
                BuffId.ENCHANTMENTOFAGONY,
                BuffId.ENCHANTMENTOFAFFLICTION,
                BuffId.ENCHANTMENTOFDISPELLING,
                BuffId.ENCHANTMENTOFDREAD,
                BuffId.ENCHANTMENTOFFLAMES,
                BuffId.ENCHANTMENTOFHEROISM,
                BuffId.ENCHANTMENTOFMETAPHYSICS,
                BuffId.ENCHANTMENTOFSAVAGERY,
                BuffId.ENCHANTMENTOFSHADOWS,
                BuffId.REAPERSCREW));
        buffContext.setBuffSet(buffSet);
        buffContext.setBuffStacks(new HashMap<>());
        buffContext.setPotionBuffs(new ArrayList<>());

        return buffContext;
    }

    private static TargetContext baseTarget() {
        TargetContext target = new TargetContext();
        target.setName("Test target");
        target.setMaxHp(100000);
        target.setCurrentHp(100000);
        target.setSize(1);
        target.setArmour(1);
        target.setDefence(1);
        target.setAffinity(100);
        target.setTags(EnumSet.noneOf(TargetTags.class));

        return target;
    }

    private static PerkContext basePerks() {
        PerkContext perkContext = new PerkContext();
        perkContext.setPerk(new HashMap<>());
        perkContext.setGenocidalRank(0.0);
        perkContext.setEquipmentLevel20(true);

        return perkContext;
    }

    private static FamiliarContext baseFamiliar() {
        return new FamiliarContext();
    }

    private static PrayerContext basePrayers() {
        PrayerContext prayerContext = new PrayerContext();
        prayerContext.setSelected(EnumSet.noneOf(Prayer.class));

        return prayerContext;
    }
}
