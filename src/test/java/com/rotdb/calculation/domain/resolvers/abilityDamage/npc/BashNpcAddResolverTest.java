package com.rotdb.calculation.domain.resolvers.abilityDamage.npc;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.context.DamageContext;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.EquipmentType;
import com.rotdb.shared.combat.domain.model.enums.HitType;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BashNpcAddResolverTest {

    @Test
    void bashAddsApplyToBaseHits() {
        CalculationContext context = bashContext();
        AbilityHitsContext hit = hit(HitType.BASE);

        assertThat(FlatAddResolver.resolve(context, hit)).isEqualTo(209);
        assertThat(FlatRangeAddResolver.resolve(context, hit)).containsExactly(3500, 4500);
    }

    @Test
    void bashAddsDoNotApplyToInfernoOfZamorakHits() {
        CalculationContext context = bashContext();
        AbilityHitsContext hit = hit(HitType.INFERNO_OF_ZAMORAK);

        assertThat(FlatAddResolver.resolve(context, hit)).isZero();
        assertThat(FlatRangeAddResolver.resolve(context, hit)).containsExactly(0, 0);
    }

    private CalculationContext bashContext() {
        CalculationContext context = new CalculationContext();
        EquipmentModel equipment = new EquipmentModel();
        EquipmentSlot offhand = EquipmentSlot.emptySlot();
        offhand.setType(EquipmentType.SHIELD);
        offhand.setArmour(1000);

        equipment.setOffhand(offhand);
        equipment.setCombatStyle(CombatStyles.MELEE);
        equipment.fillMissingWithEmpty();

        SkillsContext skills = new SkillsContext();
        skills.setBoostedDefence(109);

        BuffContext buffs = new BuffContext();
        buffs.setBuffSet(Set.of(BuffId.STEADFAST_WILL));
        buffs.setBuffStacks(Map.of());

        DamageContext damage = new DamageContext();
        damage.setBaseDamage(3000);

        context.setEquipment(equipment);
        context.setAbility(AbilityProvider.get(AbilityId.BASH_MELEE, equipment));
        context.setSkills(skills);
        context.setBuffs(buffs);
        context.setDamage(damage);

        return context;
    }

    private AbilityHitsContext hit(HitType hitType) {
        AbilityHitsContext hit = new AbilityHitsContext();
        hit.setType(hitType);
        hit.setDot(false);
        return hit;
    }
}
