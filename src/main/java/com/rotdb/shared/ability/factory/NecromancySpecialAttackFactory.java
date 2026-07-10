package com.rotdb.shared.ability.factory;

import com.rotdb.shared.ability.model.AbilityCooldownTiming;
import com.rotdb.shared.ability.model.GeneratedBuffEffect;
import com.rotdb.shared.ability.model.GeneratedBuffTiming;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;

import java.util.List;

import static com.rotdb.shared.ability.AbilityId.*;
import static com.rotdb.shared.ability.Handedness.BOTH;
import static com.rotdb.shared.combat.domain.model.enums.AbilityTier.ENHANCED;
import static com.rotdb.shared.combat.domain.model.enums.CombatStyles.NECROMANCY;
import static com.rotdb.shared.combat.domain.model.enums.Targetting.SINGLE_TARGET;

public class NecromancySpecialAttackFactory {
    public static AbilityContext deathGrasp() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(4.05, 4.95, false, ENHANCED, 1)),
                "Death Grasp",
                -25,
                50,
                false,
                BOTH,
                SINGLE_TARGET,
                NECROMANCY,
                DEATHGRASP,
                AbilityCooldownTiming.ON_RELEASE);
    }

    public static AbilityContext soulCrush() {
        GeneratedBuffEffect generatedBuffEffect = new GeneratedBuffEffect(
                BuffId.SOULCRUSH,
                GeneratedBuffTiming.ON_RELEASE
        );
        List<GeneratedBuffEffect> generatedBuffEffects = List.of(generatedBuffEffect);
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.35, 1.65, false, ENHANCED, 2)),
                "Soul Crush",
                -25,
                100,
                false,
                BOTH,
                SINGLE_TARGET,
                NECROMANCY,
                SOULCRUSH,
                generatedBuffEffects,
                AbilityCooldownTiming.ON_RELEASE,
                true);
    }

    public static AbilityContext deathEssence() {
        GeneratedBuffEffect generatedBuffEffect = new GeneratedBuffEffect(
                BuffId.DEATHESSENCE,
                GeneratedBuffTiming.ON_RELEASE
        );
        List<GeneratedBuffEffect> generatedBuffEffects = List.of(generatedBuffEffect);
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(3.6, 4.4, false, ENHANCED, 2)),
                "Death Essence",
                -30,
                100,
                false,
                BOTH,
                SINGLE_TARGET,
                NECROMANCY,
                DEATHESSENCE,
                generatedBuffEffects,
                AbilityCooldownTiming.ON_RELEASE,
                true);
    }
}
