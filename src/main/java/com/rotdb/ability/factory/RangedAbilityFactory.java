package com.rotdb.ability.factory;

import com.rotdb.model.context.AbilityContext;
import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.enums.CombatStyles;

import java.util.List;

import static com.rotdb.ability.AbilityId.*;
import static com.rotdb.ability.Handedness.*;
import static com.rotdb.model.enums.AbilityTier.*;
import static com.rotdb.model.enums.Targetting.*;

public class RangedAbilityFactory {
    public static AbilityContext ranged() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.9, 1.1, false, BASIC, 2)),
                "Ranged",
                9,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.RANGED,
                RANGEDAUTO);
    }

    public static AbilityContext snapShot() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(1.35, 1.55, false, THRESHOLD, 2),
                        new AbilityHitsContext(1.35, 1.55, false, THRESHOLD, 2)),
                "Snap Shot",
                -25,
                9,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.RANGED,
                SNAPSHOT);
    }

    public static AbilityContext snipe() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(3, 3.6, false, THRESHOLD, 3)),
                "Snipe",
                0,
                100,
                true,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.RANGED,
                SNIPE);
    }

    public static AbilityContext piercingShot() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(0.45, 0.55, false, BASIC, 2),
                        new AbilityHitsContext(0.45, 0.55, false, BASIC, 2)),
                "Piercing Shot",
                9,
                5,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.RANGED,
                PIERCINGSHOT);
    }

    public static AbilityContext deadshot() {
        return new AbilityContext(4,
                List.of(new AbilityHitsContext(1.05, 1.25, false, ULTIMATE, 3),
                        new AbilityHitsContext(1.05, 1.25, false, ULTIMATE, 3),
                        new AbilityHitsContext(1.05, 1.25, false, ULTIMATE, 3),
                        new AbilityHitsContext(1.05, 1.25, false, ULTIMATE, 3)),
                "Deadshot",
                -60,
                50,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.RANGED,
                DEADSHOT);
    }

    public static AbilityContext deadshotIgneous() {
        return new AbilityContext(8,
                List.of(new AbilityHitsContext(0.55, 0.75, false, ULTIMATE, 3),
                        new AbilityHitsContext(0.55, 0.75, false, ULTIMATE, 3),
                        new AbilityHitsContext(0.55, 0.75, false, ULTIMATE, 3),
                        new AbilityHitsContext(0.55, 0.75, false, ULTIMATE, 3),
                        new AbilityHitsContext(0.55, 0.75, false, ULTIMATE, 4),
                        new AbilityHitsContext(0.55, 0.75, false, ULTIMATE, 4),
                        new AbilityHitsContext(0.55, 0.75, false, ULTIMATE, 4),
                        new AbilityHitsContext(0.55, 0.75, false, ULTIMATE, 4)),
                "Deadshot",
                -60,
                50,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.RANGED,
                DEADSHOTIGNEOUS);
    }

    public static AbilityContext bindingShot() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.65, 0.75, false, BASIC, 2)),
                "Binding Shot",
                9,
                25,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.RANGED,
                BINDINGSHOT);
    }

    public static AbilityContext bombardment() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.2, 2.6, false, THRESHOLD, 3)),
                "Bombardment",
                -25,
                9,
                false,
                BOTH,
                MULTI_TARGET,
                CombatStyles.RANGED,
                BOMBARDMENT);
    }

    public static AbilityContext galeshot() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.9, 1.1, false, BASIC, 2)),
                "Galeshot",
                9,
                34,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.RANGED,
                GALESHOT);
    }

    public static AbilityContext rapidFire() {
        return new AbilityContext(8,
                List.of(new AbilityHitsContext(0.75, 0.85, false, THRESHOLD, 2),
                        new AbilityHitsContext(0.75, 0.85, false, THRESHOLD, 3),
                        new AbilityHitsContext(0.75, 0.85, false, THRESHOLD, 4),
                        new AbilityHitsContext(0.75, 0.85, false, THRESHOLD, 5),
                        new AbilityHitsContext(0.75, 0.85, false, THRESHOLD, 6),
                        new AbilityHitsContext(0.75, 0.85, false, THRESHOLD, 7),
                        new AbilityHitsContext(0.75, 0.85, false, THRESHOLD, 8),
                        new AbilityHitsContext(0.75, 0.85, false, THRESHOLD, 9)),
                "Rapid Fire",
                -25,
                34,
                true,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.RANGED,
                RAPIDFIRE);
    }

    public static AbilityContext ricochet() {
        return new AbilityContext(3,
                List.of(new AbilityHitsContext(0.75, 0.85, false, BASIC, 2),
                        new AbilityHitsContext(0.15, 0.2, false, BASIC, 3),
                        new AbilityHitsContext(0.15, 0.2, false, BASIC, 3)),
                "Ricochet",
                9,
                17,
                false,
                BOTH,
                MULTI_TARGET,
                CombatStyles.RANGED,
                RICOCHET);
    }

    public static AbilityContext greaterRicochet() {
        return new AbilityContext(7,
                List.of(new AbilityHitsContext(0.75, 0.85, false, BASIC, 2),
                        new AbilityHitsContext(0.15, 0.2, false, BASIC, 3),
                        new AbilityHitsContext(0.15, 0.2, false, BASIC, 3),
                        new AbilityHitsContext(0.04, 0.06, false, BASIC, 3),
                        new AbilityHitsContext(0.04, 0.06, false, BASIC, 3),
                        new AbilityHitsContext(0.04, 0.06, false, BASIC, 3),
                        new AbilityHitsContext(0.04, 0.06, false, BASIC, 3)),
                "Greater Ricochet",
                9,
                17,
                false,
                BOTH,
                MULTI_TARGET,
                CombatStyles.RANGED,
                GREATERRICOCHET);
    }

    public static AbilityContext corruptionShot() {
        return new AbilityContext(5,
                List.of(new AbilityHitsContext(0.9, 1.1, true, THRESHOLD, 2),
                        new AbilityHitsContext(0.9, 1.1, true, THRESHOLD, 4),
                        new AbilityHitsContext(0.9, 1.1, true, THRESHOLD, 6),
                        new AbilityHitsContext(0.9, 1.1, true, THRESHOLD, 8),
                        new AbilityHitsContext(0.9, 1.1, true, THRESHOLD, 10)),
                "Corruption Shot",
                -20,
                25,
                false,
                BOTH,
                MULTI_TARGET,
                CombatStyles.RANGED,
                CORRUPTIONSHOT);
    }

    // TODO: Force-crit
    public static AbilityContext shadowTendrils() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2, 2.4, false, THRESHOLD, 2)),
                "Shadow Tendrils",
                0,
                75,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.RANGED,
                SHADOWTENDRILS);
    }
}
