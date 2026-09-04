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
import java.util.*;
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
            return "Observation Date: " + observedOn + " - " + provenance;
        }
    }

    private static Stream<BaseAbilityDamageFixture> fixtures() {
        return Stream.of(
                new BaseAbilityDamageFixture(
                        "Dark Shard of Leng, Dark Sliver of Leng, Shard of Genesis, Maxed Stats, Base Test",
                        LocalDate.of(2026, 9, 3),
                        5,
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

        assertEquals(fixture.expected, baseDamage, fixture.tolerance, fixture.provenance);
    }
}
