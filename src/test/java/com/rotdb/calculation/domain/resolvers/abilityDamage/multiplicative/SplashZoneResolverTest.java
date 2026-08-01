package com.rotdb.calculation.domain.resolvers.abilityDamage.multiplicative;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.Handedness;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.context.TargetContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.HitType;
import com.rotdb.shared.combat.domain.model.enums.Targetting;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SplashZoneResolverTest {

    @Test
    void splashZoneAppliesToBaseAoEHits() {
        double multiplier = SplashZoneResolver.resolve(context(Targetting.AREA_TARGET, 5), hit(HitType.BASE));

        assertThat(multiplier).isEqualTo(1.55);
    }

    @Test
    void splashZoneDoesNotApplyToInjectedHits() {
        CalculationContext context = context(Targetting.AREA_TARGET, 5);

        assertThat(SplashZoneResolver.resolve(context, hit(HitType.INFERNO_OF_ZAMORAK))).isEqualTo(1);
        assertThat(SplashZoneResolver.resolve(context, hit(HitType.PERFECTEQUILIBRIUM))).isEqualTo(1);
        assertThat(SplashZoneResolver.resolve(context, hit(HitType.SPLITSOUL))).isEqualTo(1);
    }

    @Test
    void splashZoneDoesNotApplyToSingleTargetHits() {
        double multiplier = SplashZoneResolver.resolve(context(Targetting.SINGLE_TARGET, 5), hit(HitType.BASE));

        assertThat(multiplier).isEqualTo(1);
    }

    private CalculationContext context(Targetting targetting, int targetSize) {
        BuffContext buffs = new BuffContext();
        buffs.setBuffSet(Set.of(BuffId.SPLASH_ZONE));
        buffs.setBuffStacks(Map.of());

        TargetContext target = new TargetContext();
        target.setSize(targetSize);

        AbilityContext ability = new AbilityContext(
                1,
                List.of(hit(HitType.BASE)),
                "Test Ability",
                0,
                0,
                false,
                Handedness.BOTH,
                targetting,
                CombatStyles.MAGIC,
                AbilityId.MAGICAUTO
        );

        CalculationContext context = new CalculationContext();
        context.setBuffs(buffs);
        context.setTarget(target);
        context.setAbility(ability);
        return context;
    }

    private AbilityHitsContext hit(HitType type) {
        return new AbilityHitsContext(0, 0, false, AbilityTier.BASIC, 0, type, -1);
    }
}
