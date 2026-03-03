package com.rotdb.ability.factory;

import com.rotdb.model.context.AbilityContext;
import com.rotdb.model.context.AbilityHitsContext;
import java.util.List;

import static com.rotdb.ability.AbilityId.*;
import static com.rotdb.ability.Handedness.*;
import static com.rotdb.model.enums.AbilityTier.*;
import static com.rotdb.model.enums.CombatStyles.*;
import static com.rotdb.model.enums.Targetting.*;

public class MagicSpecialAttackFactory {
    public static AbilityContext fromTheShadows() {
        return new AbilityContext(5,
                List.of(new AbilityHitsContext(0.55, 0.65, false, THRESHOLD, 4),
                        new AbilityHitsContext(0.55, 0.65, false, THRESHOLD, 8),
                        new AbilityHitsContext(0.55, 0.65, false, THRESHOLD, 12),
                        new AbilityHitsContext(0.55, 0.65, false, THRESHOLD, 16),
                        new AbilityHitsContext(0.55, 0.65, false, THRESHOLD, 20)),
                "Fromt the Shadows",
                -50,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                FROMTHESHADOWS);
    }

    public static AbilityContext instability() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.2, 1.4, false, THRESHOLD, 2)),
                "Instability",
                -50,
                100,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                INSTABILITY);
    }

    public static AbilityContext runeFlame() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.2, 1.4, false, THRESHOLD, 2)),
                "Rune Flame",
                -35,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                RUNEFLAME);
    }

    public static AbilityContext clawsOfGuthix() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2, 2.4, false, THRESHOLD, 1)),
                "Claws of Guthix",
                -25,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                CLAWSOFGUTHIX);
    }

    public static AbilityContext devour() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2, 2.4, false, THRESHOLD, 2)),
                "Devour",
                -50,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                DEVOUR);
    }

    public static AbilityContext saradominStrike() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2, 2.4, false, THRESHOLD, 1)),
                "Saradomin Strike",
                -25,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                SARADOMINSTRIKE);
    }

    public static AbilityContext flamesOfZamorak() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2, 2.4, false, THRESHOLD, 1)),
                "Flames of Zamorak",
                -25,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                FLAMESOFZAMORAK);
    }

    public static AbilityContext miasmicBarrage() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2, 2.4, false, THRESHOLD, 2)),
                "Miasmic Barrage",
                -50,
                0,
                false,
                BOTH,
                MULTI_TARGET,
                MAGIC,
                MIASMICBARRAGE);
    }

    public static AbilityContext theLastCommand() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.4, 2.8, false, THRESHOLD, 3)),
                "The Last Command",
                -35,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                THELASTCOMMAND);
    }

    public static AbilityContext reap() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.7, 3.1, false, THRESHOLD, 2)),
                "Reap",
                -45,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                REAP);
    }

    public static AbilityContext tempestOfArmadyl() {
        return new AbilityContext(5,
                List.of(new AbilityHitsContext(0.45, 0.55, false, THRESHOLD, 1),
                        new AbilityHitsContext(0.45, 0.55, false, THRESHOLD, 2),
                        new AbilityHitsContext(0.45, 0.55, false, THRESHOLD, 3),
                        new AbilityHitsContext(0.45, 0.55, false, THRESHOLD, 4),
                        new AbilityHitsContext(0.45, 0.55, false, THRESHOLD, 5)),
                "Tempest of Armadyl",
                -50,
                0,
                true,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                TEMPESTOFARMADYL);
    }

    public static AbilityContext ibanBlast() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(3.4, 3.9, false, THRESHOLD, 2)),
                "Iban Blast",
                -50,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                IBANBLAST);
    }

    public static AbilityContext soulfire() {
        return new AbilityContext(7,
                List.of(new AbilityHitsContext(1.3, 1.6, false, THRESHOLD, 3),
                        new AbilityHitsContext(1.7, 2, true, THRESHOLD, 3),
                        new AbilityHitsContext(1.7, 2, true, THRESHOLD, 6),
                        new AbilityHitsContext(1.7, 2, true, THRESHOLD, 9),
                        new AbilityHitsContext(1.7, 2, true, THRESHOLD, 12),
                        new AbilityHitsContext(1.7, 2, true, THRESHOLD, 15),
                        new AbilityHitsContext(1.7, 2, true, THRESHOLD, 18)),
                "Soulfire",
                -35,
                75,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                SOULFIRE);
    }
}
