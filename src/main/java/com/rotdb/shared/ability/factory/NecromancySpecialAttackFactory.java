package com.rotdb.shared.ability.factory;

import com.rotdb.calculation.domain.model.context.AbilityContext;
import com.rotdb.calculation.domain.model.context.AbilityHitsContext;

import java.util.List;

import static com.rotdb.shared.ability.AbilityId.*;
import static com.rotdb.shared.ability.Handedness.*;
import static com.rotdb.calculation.domain.model.enums.AbilityTier.ENHANCED;
import static com.rotdb.calculation.domain.model.enums.CombatStyles.NECROMANCY;
import static com.rotdb.calculation.domain.model.enums.Targetting.SINGLE_TARGET;

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
                DEATHGRASP);
    }

    public static AbilityContext soulCrush() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.35, 1.65, false, ENHANCED, 2)),
                "Soul Crush",
                -25,
                100,
                false,
                BOTH,
                SINGLE_TARGET,
                NECROMANCY,
                SOULCRUSH);
    }

    public static AbilityContext deathEssence() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(3.6, 4.4, false, ENHANCED, 2)),
                "Death Essence",
                -30,
                100,
                false,
                BOTH,
                SINGLE_TARGET,
                NECROMANCY,
                DEATHESSENCE);
    }
}
