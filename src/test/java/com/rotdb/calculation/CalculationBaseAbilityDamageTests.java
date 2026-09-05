package com.rotdb.calculation;

import com.rotdb.calculation.domain.engine.StatPreparation;
import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.calculation.domain.modifiers.baseDamage.BaseAbilityDamageModifier;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.enums.Perks;
import com.rotdb.shared.combat.domain.model.enums.Slots;
import com.rotdb.shared.combat.domain.model.player.PotionContext;
import com.rotdb.shared.combat.domain.model.player.SpellContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculationBaseAbilityDamageTests {
    BaseAbilityDamageModifier baseAbilityDamageModifier = new BaseAbilityDamageModifier();
    StatPreparation statPreparation = new StatPreparation();

    private enum AttackHandedness {
        MAINHAND_ONLY,
        TWO_HANDED,
        DUAL_WIELD
    }

    private record BaseAbilityDamageFixture(
            String provenance,
            LocalDate observedOn,
            int tolerance,
            int expected,
            CombatStyles style,
            AttackHandedness attackHandedness,
            List<PotionContext> potionContext,
            double armourBonus,
            int mainHandTier,
            int offHandTier,
            int ammoTier,
            SpellContext spell,
            boolean shardable,
            int equilibriumRank,
            int eruptiveRank
    ) {
        @Override
        public String toString() {
            return "Observation Date: " + observedOn + " | " + provenance;
        }
    }
    
    private static Stream<BaseAbilityDamageFixture> fixtures() {
        return Stream.of(
                new BaseAbilityDamageFixture(
                        "Dark Shard of Leng, Dark Sliver of Leng, Shard of Genesis, Base Test",
                        LocalDate.of(2026, 9, 3),
                        1,
                        1924,
                        CombatStyles.MELEE,
                        AttackHandedness.DUAL_WIELD,
                        new ArrayList<>(),
                        0,
                        95,
                        95,
                        0,
                        null,
                        true,
                        0,
                        0
                ),
                new BaseAbilityDamageFixture(
                        "Dark Shard of Leng, Dark Sliver of Leng, Shard of Genesis, Eruptive 2, Base Test",
                        LocalDate.of(2026, 9, 3),
                        1,
                        1942,
                        CombatStyles.MELEE,
                        AttackHandedness.DUAL_WIELD,
                        new ArrayList<>(),
                        0,
                        95,
                        95,
                        0,
                        null,
                        true,
                        0,
                        2
                ),
                new BaseAbilityDamageFixture(
                        "Dark Shard of Leng, Dark Sliver of Leng, Shard of Genesis, Elder Overload Test",
                        LocalDate.of(2026, 9, 5),
                        1,
                        2002,
                        CombatStyles.MELEE,
                        AttackHandedness.DUAL_WIELD,
                        PlayerBuffFactory.addElderOverload(new ArrayList<>()),
                        0,
                        95,
                        95,
                        0,
                        null,
                        true,
                        0,
                        0
                ),
                new BaseAbilityDamageFixture(
                        "Ek-Zek-Kil, Shard of Genesis, Eruptive 2, Elder Overload Test",
                        LocalDate.of(2026, 9, 5),
                        1,
                        2022,
                        CombatStyles.MELEE,
                        AttackHandedness.TWO_HANDED,
                        PlayerBuffFactory.addElderOverload(new ArrayList<>()),
                        0,
                        95,
                        0,
                        0,
                        null,
                        true,
                        0,
                        2
                ),
                new BaseAbilityDamageFixture(
                        "Ek-Zek-Kil, Shard of Genesis, Eruptive 2, Base Test",
                        LocalDate.of(2026, 9, 5),
                        1,
                        1943,
                        CombatStyles.MELEE,
                        AttackHandedness.TWO_HANDED,
                        new ArrayList<>(),
                        0,
                        95,
                        0,
                        0,
                        null,
                        true,
                        0,
                        2
                ),
                new BaseAbilityDamageFixture(
                        "Masterwork 2h sword, Equilibrium 4, Base Test",
                        LocalDate.of(2026, 9, 5),
                        1,
                        2193,
                        CombatStyles.MELEE,
                        AttackHandedness.TWO_HANDED,
                        new ArrayList<>(),
                        0,
                        100,
                        0,
                        0,
                        null,
                        false,
                        4,
                        0
                ),
                new BaseAbilityDamageFixture(
                        "Dark Shard of Leng, No offhand, Base Test",
                        LocalDate.of(2026, 9, 5),
                        1,
                        1283,
                        CombatStyles.MELEE,
                        AttackHandedness.MAINHAND_ONLY,
                        new ArrayList<>(),
                        0,
                        95,
                        0,
                        0,
                        null,
                        true,
                        0,
                        0
                ),
                new BaseAbilityDamageFixture(
                        "Dark Shard of Leng, Dark Sliver of Leng, Vestments Robe Set, Enhanced Gloves of Passage, Nodon's Spike Harness, Am-Hej, Igneous-Kal-Zuk, Reaver's Ring, Scripture of Ful, Base Test",
                        LocalDate.of(2026, 9, 5),
                        1,
                        2359,
                        CombatStyles.MELEE,
                        AttackHandedness.DUAL_WIELD,
                        new ArrayList<>(),
                        290.9,
                        95,
                        95,
                        0,
                        null,
                        true,
                        0,
                        0
                ),
                new BaseAbilityDamageFixture(
                        "Laniakea's Spear, Eruptive 4, Base Test",
                        LocalDate.of(2026, 9, 5),
                        1,
                        1815,
                        CombatStyles.MELEE,
                        AttackHandedness.TWO_HANDED,
                        new ArrayList<>(),
                        0,
                        90,
                        0,
                        0,
                        null,
                        false,
                        0,
                        4
                )
        );
    }

    @ParameterizedTest
    @MethodSource("fixtures")
    void baseAbilityDamageTests(BaseAbilityDamageFixture fixture) {
        AggregatedCalculationContext context = BaseCombatState.baseState();

        // Setting mainhand
        context.getSnapshotContext().getEquipment().getMainhand().setDamageTier(fixture.mainHandTier);
        context.getSnapshotContext().getEquipment().getMainhand().setClazz(fixture.style);
        if (fixture.shardable) context.getSnapshotContext().getEquipment().getMainhand().getEffect().add(Effect.SHARDABLE);

        // Setting offhand
        if (fixture.attackHandedness == AttackHandedness.DUAL_WIELD) {
            context.getSnapshotContext().getEquipment().getOffhand().setId(1L);
            context.getSnapshotContext().getEquipment().getOffhand().setDamageTier(fixture.offHandTier);
            context.getSnapshotContext().getEquipment().getOffhand().setClazz(fixture.style);
            if (fixture.shardable) context.getSnapshotContext().getEquipment().getOffhand().getEffect().add(Effect.SHARDABLE);
        }

        // Setting mainhand to two-handed if fixture is two-handed
        if (fixture.attackHandedness == AttackHandedness.TWO_HANDED) {
            context.getSnapshotContext().getEquipment().getMainhand().setSlot(Slots.TWOHANDED);
        }

        // Placeholder ability
        switch (fixture.style) {
            case MELEE -> context.getSnapshotContext().setAbility(AbilityProvider.get(AbilityId.MELEEAUTO, context.getSnapshotContext().getEquipment()));
            case RANGED -> context.getSnapshotContext().setAbility(AbilityProvider.get(AbilityId.RANGEDAUTO, context.getSnapshotContext().getEquipment()));
            case NECROMANCY -> context.getSnapshotContext().setAbility(AbilityProvider.get(AbilityId.NECROMANCYAUTO, context.getSnapshotContext().getEquipment()));
            case MAGIC -> context.getSnapshotContext().setAbility(AbilityProvider.get(AbilityId.MAGICAUTO, context.getSnapshotContext().getEquipment()));
        }

        // Applying armour bonus, reduced just to a total applied to the top for simplicity - net equal results
        switch (fixture.style) {
            case MELEE -> context.getSnapshotContext().getEquipment().getBody().setStrength(fixture.armourBonus);
            case RANGED -> context.getSnapshotContext().getEquipment().getBody().setRanged(fixture.armourBonus);
            case NECROMANCY -> context.getSnapshotContext().getEquipment().getBody().setNecromancy(fixture.armourBonus);
            case MAGIC -> context.getSnapshotContext().getEquipment().getBody().setMagic(fixture.armourBonus);
        }

        // Setting ability damage relevant perks
        Map<Perks, Integer> perks = new HashMap<>();
        if (fixture.equilibriumRank > 0) perks.put(Perks.EQUILIBRIUM, fixture.equilibriumRank);
        if (fixture.eruptiveRank > 0) perks.put(Perks.ERUPTIVE, fixture.eruptiveRank);
        context.getSnapshotContext().getPerks().setPerk(perks);

        // Setting spell
        if (fixture.spell != null) context.getSnapshotContext().setSpellContext(fixture.spell);

        // Setting ammo tier
        context.getSnapshotContext().getEquipment().getAmmo().setDamageTier(fixture.ammoTier);

        // Applying potion buffs
        if (fixture.potionContext != null) {
            context.getSnapshotContext().getBuffs().setPotionBuffs(fixture.potionContext);
        }

        statPreparation.run(context.getSnapshotContext().getSkills(), context.getSnapshotContext().getBuffs());
        baseAbilityDamageModifier.apply(context);
        int baseDamage = context.getSnapshotContext().getDamage().getBaseDamage();

        System.out.println(fixture.provenance + " | Expected: " + fixture.expected + " | Result: " + baseDamage );
        assertEquals(fixture.expected, baseDamage, fixture.tolerance, fixture.provenance);
    }
}
