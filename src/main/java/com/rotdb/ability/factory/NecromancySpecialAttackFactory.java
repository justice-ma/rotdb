package com.rotdb.ability.factory;

import com.rotdb.model.context.AbilityContext;
import com.rotdb.model.context.AbilityHitsContext;

import java.util.List;

import static com.rotdb.ability.AbilityId.*;
import static com.rotdb.ability.Handedness.*;
import static com.rotdb.model.enums.AbilityTier.*;
import static com.rotdb.model.enums.CombatStyles.*;
import static com.rotdb.model.enums.Targetting.*;

public class NecromancySpecialAttackFactory {
    public static AbilityContext deathGrasp() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(4.05, 4.95, false, THRESHOLD, 1)),
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
                List.of(new AbilityHitsContext(1.35, 1.65, false, THRESHOLD, 2)),
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
                List.of(new AbilityHitsContext(3.6, 4.4, false, THRESHOLD, 2)),
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
