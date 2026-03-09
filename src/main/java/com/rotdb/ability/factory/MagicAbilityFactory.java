package com.rotdb.ability.factory;

import com.rotdb.domain.model.context.AbilityContext;
import com.rotdb.domain.model.context.AbilityHitsContext;
import java.util.List;

import static com.rotdb.ability.AbilityId.*;
import static com.rotdb.ability.Handedness.*;
import static com.rotdb.domain.model.enums.AbilityTier.*;
import static com.rotdb.domain.model.enums.CombatStyles.MAGIC;
import static com.rotdb.domain.model.enums.Targetting.*;

public class MagicAbilityFactory {
    public static AbilityContext magic() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.9, 1.1, false, BASIC, 2)),
                "Magic",
                9,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                MAGICAUTO);
    }

    public static AbilityContext wildMagic() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(1.25, 1.55, false, THRESHOLD, 2),
                        new AbilityHitsContext(1.25, 1.55, false, THRESHOLD, 3)),
                "Wild Magic",
                -25,
                9,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                WILDMAGIC);
    }

    public static AbilityContext soniceWave() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.9, 1.1, false, BASIC, 2)),
                "Sonic Wave",
                9,
                25,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                SONICWAVE);
    }

    public static AbilityContext greaterSonicWave() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.15, 1.35, false, BASIC, 2)),
                "Greater Sonic Wave",
                9,
                25,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                GREATERSONICWAVE);
    }

    public static AbilityContext omnipower() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(4.2, 5, false, ULTIMATE, 3)),
                "Omnipower",
                -60,
                50,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                OMNIPOWER);
    }

    public static AbilityContext omnipowerIgneous() {
        return new AbilityContext(4,
                List.of(new AbilityHitsContext(1.2, 1.5, false, ULTIMATE, 3),
                        new AbilityHitsContext(1.2, 1.5, false, ULTIMATE, 3),
                        new AbilityHitsContext(1.2, 1.5, false, ULTIMATE, 3),
                        new AbilityHitsContext(1.2, 1.5, false, ULTIMATE, 3)),
                "Omnipower",
                -60,
                50,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                OMNIPOWERIGNEOUS);
    }

    public static AbilityContext dragonBreath() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.1, 1.3, false, BASIC, 1)),
                "Dragon Breath",
                9,
                12,
                false,
                BOTH,
                MULTI_TARGET,
                MAGIC,
                DRAGONBREATH);
    }

    public static AbilityContext impact() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.65, 0.75, false, BASIC, 2)),
                "Impact",
                9,
                25,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                IMPACT);
    }

    public static AbilityContext combust() {
        return new AbilityContext(10,
                List.of(new AbilityHitsContext(0.27, 0.33, true, BASIC, 3),
                        new AbilityHitsContext(0.27, 0.33, true, BASIC, 6),
                        new AbilityHitsContext(0.27, 0.33, true, BASIC, 9),
                        new AbilityHitsContext(0.27, 0.33, true, BASIC, 12),
                        new AbilityHitsContext(0.27, 0.33, true, BASIC, 15),
                        new AbilityHitsContext(0.27, 0.33, true, BASIC, 18),
                        new AbilityHitsContext(0.27, 0.33, true, BASIC, 21),
                        new AbilityHitsContext(0.27, 0.33, true, BASIC, 24),
                        new AbilityHitsContext(0.27, 0.33, true, BASIC, 27),
                        new AbilityHitsContext(0.27, 0.33, true, BASIC, 30)),
                "Combust",
                9,
                30,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                COMBUST);
    }

    public static AbilityContext chain() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.7, 0.9, false, BASIC, 2)),
                "Chain",
                9,
                17,
                false,
                BOTH,
                MULTI_TARGET,
                MAGIC,
                CHAIN);
    }

    public static AbilityContext greaterChain() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.8, 1, false, BASIC, 2)),
                "Greater Chain",
                9,
                17,
                false,
                BOTH,
                MULTI_TARGET,
                MAGIC,
                GREATERCHAIN);
    }

    public static AbilityContext asphyxiate() {
        return new AbilityContext(4,
                List.of(new AbilityHitsContext(1.1, 1.3, false, THRESHOLD, 1),
                        new AbilityHitsContext(1.1, 1.3, false, THRESHOLD, 3),
                        new AbilityHitsContext(1.1, 1.3, false, THRESHOLD, 5),
                        new AbilityHitsContext(1.1, 1.3, false, THRESHOLD, 7)),
                "Asphyxiate",
                -25,
                34,
                true,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                ASPHYXIATE);
    }

    public static AbilityContext asphyxiateTumekens() {
        return new AbilityContext(8,
                List.of(new AbilityHitsContext(0.715, 0.845, false, THRESHOLD, 1),
                        new AbilityHitsContext(0.715, 0.845, false, THRESHOLD, 2),
                        new AbilityHitsContext(0.715, 0.845, false, THRESHOLD, 3),
                        new AbilityHitsContext(0.715, 0.845, false, THRESHOLD, 4),
                        new AbilityHitsContext(0.715, 0.845, false, THRESHOLD, 5),
                        new AbilityHitsContext(0.715, 0.845, false, THRESHOLD, 6),
                        new AbilityHitsContext(0.715, 0.845, false, THRESHOLD, 7),
                        new AbilityHitsContext(0.715, 0.845, false, THRESHOLD, 8)),
                "Asphyxiate",
                -25,
                35,
                true,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                ASPHYXIATETUMEKENS);
    }

    public static AbilityContext concentratedBlast() {
        return new AbilityContext(3,
                List.of(new AbilityHitsContext(0.3, 0.4, false, BASIC, 1),
                        new AbilityHitsContext(0.3, 0.4, false, BASIC, 2),
                        new AbilityHitsContext(0.3, 0.4, false, BASIC, 3)),
                "Concentrated Blast",
                9,
                9,
                true,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                CONCENTRATEDBLAST);
    }

    public static AbilityContext greaterConcentratedBlast() {
        return new AbilityContext(3,
                List.of(new AbilityHitsContext(0.4, 0.5, false, BASIC, 1),
                        new AbilityHitsContext(0.4, 0.5, false, BASIC, 2),
                        new AbilityHitsContext(0.4, 0.5, false, BASIC, 3)),
                "Greater Concentrated Blast",
                9,
                9,
                true,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                GREATERCONCENTRATEDBLAST);
    }

    public static AbilityContext magmaTempest() {
        return new AbilityContext(8,
                List.of(new AbilityHitsContext(0.35, 0.45, false, THRESHOLD, 4),
                        new AbilityHitsContext(0.35, 0.45, false, THRESHOLD, 6),
                        new AbilityHitsContext(0.35, 0.45, false, THRESHOLD, 8),
                        new AbilityHitsContext(0.35, 0.45, false, THRESHOLD, 10),
                        new AbilityHitsContext(0.35, 0.45, false, THRESHOLD, 12),
                        new AbilityHitsContext(0.35, 0.45, false, THRESHOLD, 14),
                        new AbilityHitsContext(0.35, 0.45, false, THRESHOLD, 16),
                        new AbilityHitsContext(0.35, 0.45, false, THRESHOLD, 18)),
                "Magma Tempest",
                -20,
                35,
                false,
                BOTH,
                AREA_TARGET,
                MAGIC,
                MAGMATEMPEST);
    }

    public static AbilityContext corruptionBlast() {
        return new AbilityContext(5,
                List.of(new AbilityHitsContext(0.9, 1.1, true, THRESHOLD, 2),
                        new AbilityHitsContext(0.9, 1.1, true, THRESHOLD, 4),
                        new AbilityHitsContext(0.9, 1.1, true, THRESHOLD, 6),
                        new AbilityHitsContext(0.9, 1.1, true, THRESHOLD, 8),
                        new AbilityHitsContext(0.9, 1.1, true, THRESHOLD, 10)),
                "Corruption Blast",
                -20,
                25,
                false,
                BOTH,
                MULTI_TARGET,
                MAGIC,
                CORRUPTIONBLAST);
    }

    public static AbilityContext smokeTendrils() {
        return new AbilityContext(4,
                List.of(new AbilityHitsContext(0.55, 0.65, false, THRESHOLD, 3),
                        new AbilityHitsContext(0.65, 0.8, false, THRESHOLD, 5),
                        new AbilityHitsContext(0.75, 0.95, false, THRESHOLD, 7),
                        new AbilityHitsContext(0.85, 1.1, false, THRESHOLD, 9)),
                "Smoke Tendrils",
                0,
                75,
                true,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                SMOKETENDRILS);
    }

    public static AbilityContext tsunami() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.25, 2.75, false, ULTIMATE, 3)),
                "Tsunami",
                -100,
                100,
                false,
                BOTH,
                MULTI_TARGET,
                MAGIC,
                TSUNAMI);
    }
}
