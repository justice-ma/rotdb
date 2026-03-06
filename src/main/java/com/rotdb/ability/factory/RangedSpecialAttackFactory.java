package com.rotdb.ability.factory;

import com.rotdb.domain.model.context.AbilityContext;
import com.rotdb.domain.model.context.AbilityHitsContext;

import java.util.List;

import static com.rotdb.ability.AbilityId.*;
import static com.rotdb.ability.Handedness.*;
import static com.rotdb.domain.model.enums.AbilityTier.*;
import static com.rotdb.domain.model.enums.CombatStyles.*;
import static com.rotdb.domain.model.enums.Targetting.*;

public class RangedSpecialAttackFactory {
    public static AbilityContext chainHit() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.55, 0.65, false, THRESHOLD, 3)),
                "Chain Hit",
                -10,
                0,
                false,
                BOTH,
                MULTI_TARGET,
                RANGED,
                CHAINHIT);
    }

    public static AbilityContext twinShot() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(0.55, 0.65, false, THRESHOLD, 2),
                        new AbilityHitsContext(0.55, 0.65, false, THRESHOLD, 2)),
                "Twin Shot",
                -35,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                RANGED,
                TWINSHOT);
    }

    public static AbilityContext shadowfall() {
        return new AbilityContext(3,
                List.of(new AbilityHitsContext(0.85, 1.05, false, THRESHOLD, 2),
                        new AbilityHitsContext(0.85, 1.05, false, THRESHOLD, 2),
                        new AbilityHitsContext(2.55, 2.95, false, THRESHOLD, 3)),
                "Shadowfall",
                -65,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                RANGED,
                SHADOWFALL);
    }

    public static AbilityContext soulshot() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1, 1.2, false, THRESHOLD, 3)),
                "Soulshot",
                -50,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                RANGED,
                SOULSHOT);
    }

    public static AbilityContext twinFang() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(1.15, 1.45, false, THRESHOLD, 1),
                        new AbilityHitsContext(1.15, 1.45, false, THRESHOLD, 1)),
                "Twin Fang",
                -50,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                RANGED,
                TWINFANG);
    }

    public static AbilityContext crystalRain() {
        return new AbilityContext(5,
                List.of(new AbilityHitsContext(1.25, 1.55, false, THRESHOLD, 3),
                        new AbilityHitsContext(1.25, 1.55, false, THRESHOLD, 3),
                        new AbilityHitsContext(1.25, 1.55, false, THRESHOLD, 3),
                        new AbilityHitsContext(1.25, 1.55, false, THRESHOLD, 3),
                        new AbilityHitsContext(1.25, 1.55, false, THRESHOLD, 3)),
                "Crystal Rain",
                -30,
                50,
                false,
                BOTH,
                MULTI_TARGET,
                RANGED,
                CRYSTALRAIN);
    }

    public static AbilityContext hamstring() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.5, 1.7, false, THRESHOLD, 3)),
                "Hamstring",
                -50,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                RANGED,
                HAMSTRING);
    }

    public static AbilityContext descentOfDarkness() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(1.9, 2.3, false, THRESHOLD, 2),
                        new AbilityHitsContext(1.9, 2.3, false, THRESHOLD, 2)),
                "Descent of Darkness",
                -65,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                RANGED,
                DESCENTOFDARKNESS);
    }

    public static AbilityContext powershot() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.1, 2.3, false, THRESHOLD, 3)),
                "Powershot",
                -35,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                RANGED,
                POWERSHOT);
    }

    public static AbilityContext defiance() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.25, 2.75, false, THRESHOLD, 3)),
                "Defiance",
                -40,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                RANGED,
                DEFIANCE);
    }

    public static AbilityContext balanceByForce() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.35, 2.55, false, THRESHOLD, 2)),
                "Balance by Force",
                -30,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                RANGED,
                BALANCEBYFORCE);
    }

    public static AbilityContext phantomStrike() {
        return new AbilityContext(6,
                List.of(new AbilityHitsContext(1.2, 1.4, false, THRESHOLD, 3),
                        new AbilityHitsContext(0.3, 0.4, true, THRESHOLD, 6),
                        new AbilityHitsContext(0.3, 0.4, true, THRESHOLD, 9),
                        new AbilityHitsContext(0.3, 0.4, true, THRESHOLD, 12),
                        new AbilityHitsContext(0.3, 0.4, true, THRESHOLD, 15),
                        new AbilityHitsContext(0.3, 0.4, true, THRESHOLD, 18)),
                "Phantom Strike",
                -50,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                RANGED,
                PHANTOMSTRIKE);
    }

    public static AbilityContext aimedShot() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(3, 3.6, false, THRESHOLD, 4)),
                "Aimed Shot",
                -35,
                0,
                true,
                BOTH,
                SINGLE_TARGET,
                RANGED,
                AIMEDSHOT);
    }

    public static AbilityContext destructiveShot() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(1.6, 1.8, false, THRESHOLD, 2),
                        new AbilityHitsContext(1.6, 1.8, false, THRESHOLD, 2)),
                "Destructive Shot",
                -40,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                RANGED,
                DESTRUCTIVESHOT);
    }

    public static AbilityContext restorativeShot() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(1.35, 1.45, false, THRESHOLD, 2),
                        new AbilityHitsContext(0.55, 0.65, false, THRESHOLD, 2)),
                "Restorative Shot",
                -30,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                RANGED,
                RESTORATIVESHOT);
    }

    public static AbilityContext balancedShot() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(1.7, 1.9, false, THRESHOLD, 2),
                        new AbilityHitsContext(0.55, 0.65, false, THRESHOLD, 2)),
                "Balanced Shot",
                -35,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                RANGED,
                BALANCEDSHOT);
    }
}
