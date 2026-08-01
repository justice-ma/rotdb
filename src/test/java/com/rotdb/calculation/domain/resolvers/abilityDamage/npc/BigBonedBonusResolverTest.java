package com.rotdb.calculation.domain.resolvers.abilityDamage.npc;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.context.DamageContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.HitType;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BigBonedBonusResolverTest {

    @Test
    void bigBonedAddsFivePercentEffectiveMaxHpToBaseHits() {
        HauntedBonus bonus = BigBonedBonusResolver.resolve(context(3000, 1000, 0, true), hit(HitType.BASE));

        assertBonus(bonus, 75);
    }

    @Test
    void bigBonedIsNotCappedByBaseDamage() {
        HauntedBonus bonus = BigBonedBonusResolver.resolve(context(3000, 32000, 0, true), hit(HitType.BASE));

        assertBonus(bonus, 2400);
    }

    @Test
    void bigBonedReturnsZeroWhenBuffIsMissing() {
        HauntedBonus bonus = BigBonedBonusResolver.resolve(context(3000, 1000, 0, false), hit(HitType.BASE));

        assertThat(bonus.isZero()).isTrue();
    }

    @Test
    void bigBonedReturnsZeroForInjectedHits() {
        HauntedBonus bonus = BigBonedBonusResolver.resolve(context(3000, 32000, 0, true), hit(HitType.INFERNO_OF_ZAMORAK));

        assertThat(bonus.isZero()).isTrue();
    }

    private CalculationContext context(int baseDamage, int maxHp, double equipmentLife, boolean bigBoned) {
        BuffContext buffs = new BuffContext();
        buffs.setBuffSet(bigBoned ? Set.of(BuffId.BIG_BONED) : Set.of());
        buffs.setBuffStacks(Map.of());

        DamageContext damage = new DamageContext();
        damage.setBaseDamage(baseDamage);

        SkillsContext skills = new SkillsContext();
        skills.setMaxHp(maxHp);

        EquipmentSlot body = EquipmentSlot.emptySlot();
        body.setLife(equipmentLife);

        EquipmentModel equipment = new EquipmentModel();
        equipment.setBody(body);
        equipment.fillMissingWithEmpty();

        CalculationContext context = new CalculationContext();
        context.setBuffs(buffs);
        context.setDamage(damage);
        context.setSkills(skills);
        context.setEquipment(equipment);
        return context;
    }

    private AbilityHitsContext hit(HitType type) {
        return new AbilityHitsContext(0, 0, false, AbilityTier.BASIC, 0, type, -1);
    }

    private void assertBonus(HauntedBonus bonus, int expected) {
        assertThat(bonus.getMinCrit()).isEqualTo(expected);
        assertThat(bonus.getMaxCrit()).isEqualTo(expected);
        assertThat(bonus.getMinNonCrit()).isEqualTo(expected);
        assertThat(bonus.getMaxNonCrit()).isEqualTo(expected);
        assertThat(bonus.getMinAvg()).isEqualTo(expected);
        assertThat(bonus.getMaxAvg()).isEqualTo(expected);
    }
}
