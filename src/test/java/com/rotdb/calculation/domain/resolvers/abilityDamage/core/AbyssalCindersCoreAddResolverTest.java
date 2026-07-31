package com.rotdb.calculation.domain.resolvers.abilityDamage.core;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.context.DamageContext;
import com.rotdb.calculation.domain.modifiers.abilityDamage.CoreModifier;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.Handedness;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.HitType;
import com.rotdb.shared.combat.domain.model.enums.Perks;
import com.rotdb.shared.combat.domain.model.enums.Targetting;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AbyssalCindersCoreAddResolverTest {

    @Test
    void cindersAddsBaseDamageToBaseHits() {
        assertThat(AbyssalCindersCoreAddResolver.resolve(contextWithCinders(), hit(HitType.BASE))).isEqualTo(450);
    }

    @Test
    void cindersDoesNotAddBaseDamageToInfernoHits() {
        assertThat(AbyssalCindersCoreAddResolver.resolve(contextWithCinders(), hit(HitType.INFERNO_OF_ZAMORAK))).isZero();
    }

    @Test
    void coreModifierDoesNotApplyCindersToInfernoHits() {
        AbilityHitsContext baseHit = hit(HitType.BASE);
        baseHit.setCurrentMin(100);
        baseHit.setCurrentMax(200);

        AbilityHitsContext infernoHit = hit(HitType.INFERNO_OF_ZAMORAK);
        infernoHit.setCurrentMin(10);
        infernoHit.setCurrentMax(20);

        CalculationContext context = contextWithCinders();
        context.setAbility(new AbilityContext(
                2,
                List.of(baseHit, infernoHit),
                "Test",
                0,
                0,
                false,
                Handedness.BOTH,
                Targetting.SINGLE_TARGET,
                CombatStyles.MAGIC,
                AbilityId.MAGICAUTO
        ));
        context.setEquipment(equipment());
        context.setPerks(perks());
        context.setSkills(skills());

        new CoreModifier().apply(context);

        assertThat(baseHit.getCurrentMin()).isEqualTo(550);
        assertThat(baseHit.getCurrentMax()).isEqualTo(650);
        assertThat(infernoHit.getCurrentMin()).isEqualTo(10);
        assertThat(infernoHit.getCurrentMax()).isEqualTo(20);
    }

    private CalculationContext contextWithCinders() {
        BuffContext buffs = new BuffContext();
        buffs.setBuffSet(Set.of(BuffId.ABYSSAL_CINDERS));
        buffs.setBuffStacks(Map.of());

        DamageContext damage = new DamageContext();
        damage.setBaseDamage(3000);

        CalculationContext context = new CalculationContext();
        context.setBuffs(buffs);
        context.setDamage(damage);
        return context;
    }

    private AbilityHitsContext hit(HitType hitType) {
        AbilityHitsContext hit = new AbilityHitsContext();
        hit.setType(hitType);
        hit.setTier(AbilityTier.BLESSING);
        return hit;
    }

    private EquipmentModel equipment() {
        EquipmentModel equipment = new EquipmentModel();
        equipment.fillMissingWithEmpty();
        return equipment;
    }

    private PerkContext perks() {
        PerkContext perks = new PerkContext();
        perks.setPerk(Map.of());
        return perks;
    }

    private SkillsContext skills() {
        SkillsContext skills = new SkillsContext();
        skills.setCurrentHp(1000);
        skills.setMaxHp(1000);
        return skills;
    }
}
