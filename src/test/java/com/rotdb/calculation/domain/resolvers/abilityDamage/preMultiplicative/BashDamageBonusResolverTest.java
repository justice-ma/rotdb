package com.rotdb.calculation.domain.resolvers.abilityDamage.preMultiplicative;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.context.DamageContext;
import com.rotdb.calculation.domain.modifiers.abilityDamage.BashDamageModifier;
import com.rotdb.calculation.domain.modifiers.abilityDamage.MultiplicativeModifier;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.context.TargetContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.EquipmentType;
import com.rotdb.shared.combat.domain.model.enums.HitType;
import com.rotdb.shared.combat.domain.model.enums.Perks;
import com.rotdb.shared.combat.domain.model.equipment.FamiliarContext;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.PrayerContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BashDamageBonusResolverTest {

    @Test
    void bashAddsApplyToBaseHitsBeforeMultiplicativeDamage() {
        CalculationContext context = bashContext(Set.of(BuffId.STEADFAST_WILL, BuffId.BERSERK));
        AbilityHitsContext hit = context.getAbility().getHits().getFirst();
        hit.setCurrentMin(600);
        hit.setCurrentMax(3000);

        new BashDamageModifier().apply(context);

        assertThat(hit.getCurrentMin()).isEqualTo(4321);
        assertThat(hit.getCurrentMax()).isEqualTo(7721);

        new MultiplicativeModifier().apply(context);

        assertThat(hit.getCurrentMin()).isEqualTo(7561);
        assertThat(hit.getCurrentMax()).isEqualTo(13511);
    }

    @Test
    void bashAddsDoNotApplyToInfernoOfZamorakHits() {
        CalculationContext context = bashContext(Set.of(BuffId.STEADFAST_WILL));
        AbilityHitsContext hit = context.getAbility().getHits().getFirst();
        hit.setType(HitType.INFERNO_OF_ZAMORAK);
        hit.setCurrentMin(600);
        hit.setCurrentMax(3000);

        assertThat(BashDamageBonusResolver.resolve(context, hit)).containsExactly(0, 0);
    }

    @Test
    void bashAddsNormalShieldDamageWithoutSteadfastWill() {
        CalculationContext context = bashContext(Set.of());
        AbilityHitsContext hit = context.getAbility().getHits().getFirst();

        assertThat(BashDamageBonusResolver.resolve(context, hit)).containsExactly(221, 221);
    }

    private CalculationContext bashContext(Set<BuffId> buffSet) {
        CalculationContext context = new CalculationContext();
        EquipmentModel equipment = new EquipmentModel();
        EquipmentSlot offhand = EquipmentSlot.emptySlot();
        offhand.setType(EquipmentType.SHIELD);
        offhand.setArmour(1000);

        EquipmentSlot mainhand = EquipmentSlot.emptySlot();
        mainhand.setClazz(CombatStyles.MELEE);

        equipment.setMainhand(mainhand);
        equipment.setOffhand(offhand);
        equipment.setCombatStyle(CombatStyles.MELEE);
        equipment.fillMissingWithEmpty();

        SkillsContext skills = new SkillsContext();
        skills.setBoostedDefence(109);

        BuffContext buffs = new BuffContext();
        buffs.setBuffSet(buffSet);
        buffs.setBuffStacks(Map.of());

        DamageContext damage = new DamageContext();
        damage.setBaseDamage(3000);

        PerkContext perks = new PerkContext();
        perks.setPerk(Map.<Perks, Integer>of());

        TargetContext target = new TargetContext();
        target.setCurrentHp(100000);
        target.setMaxHp(100000);

        context.setEquipment(equipment);
        context.setAbility(AbilityProvider.get(AbilityId.BASH_MELEE, equipment));
        context.setSkills(skills);
        context.setBuffs(buffs);
        context.setDamage(damage);
        context.setPerks(perks);
        context.setFamiliar(new FamiliarContext());
        context.setTarget(target);
        context.setPrayer(new PrayerContext());

        return context;
    }
}
