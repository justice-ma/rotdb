package com.rotdb.shared.ability.factory;

import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;

import java.util.List;

import static com.rotdb.shared.ability.AbilityId.*;
import static com.rotdb.shared.ability.Handedness.BOTH;
import static com.rotdb.shared.combat.domain.model.enums.AbilityTier.*;
import static com.rotdb.shared.combat.domain.model.enums.Targetting.MULTI_TARGET;
import static com.rotdb.shared.combat.domain.model.enums.Targetting.SINGLE_TARGET;

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
                List.of(new AbilityHitsContext(1.35, 1.55, false, ENHANCED, 2),
                        new AbilityHitsContext(1.35, 1.55, false, ENHANCED, 2)),
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
                List.of(new AbilityHitsContext(3, 3.6, false, ENHANCED, 3)),
                "Snipe",
                0,
                103,
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
                List.of(new AbilityHitsContext(2.2, 2.6, false, ENHANCED, 3)),
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
                List.of(new AbilityHitsContext(0.75, 0.85, false, ENHANCED, 2),
                        new AbilityHitsContext(0.75, 0.85, false, ENHANCED, 3),
                        new AbilityHitsContext(0.75, 0.85, false, ENHANCED, 4),
                        new AbilityHitsContext(0.75, 0.85, false, ENHANCED, 5),
                        new AbilityHitsContext(0.75, 0.85, false, ENHANCED, 6),
                        new AbilityHitsContext(0.75, 0.85, false, ENHANCED, 7),
                        new AbilityHitsContext(0.75, 0.85, false, ENHANCED, 8),
                        new AbilityHitsContext(0.75, 0.85, false, ENHANCED, 9)),
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
                List.of(new AbilityHitsContext(0.9, 1.1, true, ENHANCED, 2),
                        new AbilityHitsContext(0.9, 1.1, true, ENHANCED, 4),
                        new AbilityHitsContext(0.9, 1.1, true, ENHANCED, 6),
                        new AbilityHitsContext(0.9, 1.1, true, ENHANCED, 8),
                        new AbilityHitsContext(0.9, 1.1, true, ENHANCED, 10)),
                "Corruption Shot",
                -20,
                25,
                false,
                BOTH,
                MULTI_TARGET,
                CombatStyles.RANGED,
                CORRUPTIONSHOT);
    }

    public static AbilityContext shadowTendrils() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2, 2.4, false, ENHANCED, 2)),
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
