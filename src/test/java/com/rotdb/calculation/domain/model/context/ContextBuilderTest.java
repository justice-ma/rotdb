package com.rotdb.calculation.domain.model.context;

import com.rotdb.calculation.domain.model.DamageRequest;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.Prayer;
import com.rotdb.shared.combat.domain.model.enums.Spells;
import com.rotdb.shared.combat.domain.model.enums.Targetting;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.PrayerContext;
import com.rotdb.shared.combat.domain.model.player.SpellContext;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;

class ContextBuilderTest {

    @Test
    void magicAutoUsesSelectedSpellTargetting() {
        DamageRequest request = request(AbilityId.MAGICAUTO, Spells.BLOODBARRAGE);

        CalculationContext context = ContextBuilder.build(request);

        assertThat(context.getAbility().getTargetting()).isEqualTo(Targetting.MULTI_TARGET);
    }

    @Test
    void magicAutoKeepsSingleTargetForSingleTargetSpell() {
        DamageRequest request = request(AbilityId.MAGICAUTO, Spells.AIRSURGE);

        CalculationContext context = ContextBuilder.build(request);

        assertThat(context.getAbility().getTargetting()).isEqualTo(Targetting.SINGLE_TARGET);
    }

    @Test
    void selectedSpellDoesNotOverrideNonAutoAbilityTargetting() {
        DamageRequest request = request(AbilityId.WILDMAGIC, Spells.BLOODBARRAGE);

        CalculationContext context = ContextBuilder.build(request);

        assertThat(context.getAbility().getTargetting()).isEqualTo(Targetting.SINGLE_TARGET);
    }

    private DamageRequest request(AbilityId abilityId, Spells selectedSpell) {
        DamageRequest request = new DamageRequest();
        EquipmentModel equipment = new EquipmentModel();
        equipment.setCombatStyle(CombatStyles.MAGIC);

        PrayerContext prayers = new PrayerContext();
        prayers.setSelected(EnumSet.noneOf(Prayer.class));

        SpellContext spell = new SpellContext();
        spell.setSpell(selectedSpell);

        request.setEquipment(equipment);
        request.setAbilityId(abilityId);
        request.setSelectedPrayers(prayers);
        request.setSpell(spell);

        return request;
    }
}
