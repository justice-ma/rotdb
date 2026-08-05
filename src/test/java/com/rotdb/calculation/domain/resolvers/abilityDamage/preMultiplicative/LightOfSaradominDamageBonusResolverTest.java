package com.rotdb.calculation.domain.resolvers.abilityDamage.preMultiplicative;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.abilityDamage.BlessingFlatDamageModifier;
import com.rotdb.calculation.domain.modifiers.abilityDamage.MultiplicativeModifier;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.context.TargetContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.HitType;
import com.rotdb.shared.combat.domain.model.enums.Perks;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;
import com.rotdb.shared.combat.domain.model.equipment.FamiliarContext;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.PrayerContext;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class LightOfSaradominDamageBonusResolverTest {

    @Test
    void lightOfSaradominArmourAddAppliesBeforeMultiplicativeDamage() {
        CalculationContext context = lightContext(Set.of(BuffId.STRIKING_LIGHT, BuffId.SUNSHINE));
        AbilityHitsContext hit = context.getAbility().getHits().getFirst();
        hit.setCurrentMin(400);
        hit.setCurrentMax(600);

        new BlessingFlatDamageModifier().apply(context);

        assertThat(hit.getCurrentMin()).isEqualTo(2900);
        assertThat(hit.getCurrentMax()).isEqualTo(3100);

        new MultiplicativeModifier().apply(context);

        assertThat(hit.getCurrentMin()).isEqualTo(4350);
        assertThat(hit.getCurrentMax()).isEqualTo(4650);
    }

    @Test
    void lightOfSaradominArmourAddDoesNotApplyToInjectedHits() {
        CalculationContext context = lightContext(Set.of(BuffId.STRIKING_LIGHT));
        AbilityHitsContext hit = context.getAbility().getHits().getFirst();
        hit.setType(HitType.INFERNO_OF_ZAMORAK);

        assertThat(LightOfSaradominDamageBonusResolver.resolve(context, hit)).isZero();
    }

    @Test
    void lightOfSaradominArmourAddRequiresStrikingLight() {
        CalculationContext context = lightContext(Set.of());
        AbilityHitsContext hit = context.getAbility().getHits().getFirst();

        assertThat(LightOfSaradominDamageBonusResolver.resolve(context, hit)).isZero();
    }

    private CalculationContext lightContext(Set<BuffId> buffSet) {
        EquipmentSlot mainhand = EquipmentSlot.emptySlot();
        mainhand.setClazz(CombatStyles.MAGIC);

        EquipmentSlot body = EquipmentSlot.emptySlot();
        body.setArmour(1000);

        EquipmentModel equipment = new EquipmentModel();
        equipment.setMainhand(mainhand);
        equipment.setBody(body);
        equipment.setCombatStyle(CombatStyles.MAGIC);
        equipment.fillMissingWithEmpty();

        BuffContext buffs = new BuffContext();
        buffs.setBuffSet(buffSet);
        buffs.setBuffStacks(Map.of());

        PerkContext perks = new PerkContext();
        perks.setPerk(Map.<Perks, Integer>of());

        TargetContext target = new TargetContext();
        target.setCurrentHp(100000);
        target.setMaxHp(100000);

        CalculationContext context = new CalculationContext();
        context.setEquipment(equipment);
        context.setAbility(AbilityProvider.get(AbilityId.LIGHT_OF_SARADOMIN_MAGIC, equipment));
        context.setBuffs(buffs);
        context.setPerks(perks);
        context.setFamiliar(new FamiliarContext());
        context.setTarget(target);
        context.setPrayer(new PrayerContext());
        return context;
    }
}
