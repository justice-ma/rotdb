package com.rotdb.shared.ability.factory;

import com.rotdb.shared.ability.model.AbilityCooldownTiming;
import com.rotdb.shared.ability.model.GeneratedBuffEffect;
import com.rotdb.shared.ability.model.GeneratedBuffTiming;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.DamageCalculationTiming;

import java.util.List;

import static com.rotdb.shared.ability.AbilityId.*;
import static com.rotdb.shared.ability.Handedness.BOTH;
import static com.rotdb.shared.combat.domain.model.enums.AbilityTier.*;
import static com.rotdb.shared.combat.domain.model.enums.CombatStyles.MAGIC;
import static com.rotdb.shared.combat.domain.model.enums.Targetting.*;

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
                List.of(new AbilityHitsContext(1.25, 1.55, false, ENHANCED, 2),
                        new AbilityHitsContext(1.25, 1.55, false, ENHANCED, 2)),
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
        GeneratedBuffEffect generatedBuffEffect = new GeneratedBuffEffect(
                BuffId.SONICWAVE,
                GeneratedBuffTiming.ON_RELEASE
        );
        List<GeneratedBuffEffect> generatedBuffEffects = List.of(generatedBuffEffect);
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.9, 1.1, false, BASIC, 2)),
                "Sonic Wave",
                9,
                25,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                SONICWAVE,
                generatedBuffEffects);
    }

    public static AbilityContext greaterSonicWave() {
        GeneratedBuffEffect generatedBuffEffect = new GeneratedBuffEffect(
                BuffId.GREATERSONICWAVE,
                GeneratedBuffTiming.ON_RELEASE
        );
        List<GeneratedBuffEffect> generatedBuffEffects = List.of(generatedBuffEffect);
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.15, 1.35, false, BASIC, 2)),
                "Greater Sonic Wave",
                9,
                25,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                GREATERSONICWAVE,
                generatedBuffEffects);
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
                List.of(new AbilityHitsContext(1.2, 1.4, false, ENHANCED, 1),
                        new AbilityHitsContext(1.2, 1.4, false, ENHANCED, 3),
                        new AbilityHitsContext(1.2, 1.4, false, ENHANCED, 5),
                        new AbilityHitsContext(1.2, 1.4, false, ENHANCED, 7)),
                "Asphyxiate",
                -25,
                34,
                true,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                ASPHYXIATE,
                DamageCalculationTiming.ON_HIT);
    }

    public static AbilityContext asphyxiateTumekens() {
        return new AbilityContext(8,
                List.of(new AbilityHitsContext(0.72, 0.84, false, ENHANCED, 1),
                        new AbilityHitsContext(0.72, 0.84, false, ENHANCED, 2),
                        new AbilityHitsContext(0.72, 0.84, false, ENHANCED, 3),
                        new AbilityHitsContext(0.72, 0.84, false, ENHANCED, 4),
                        new AbilityHitsContext(0.72, 0.84, false, ENHANCED, 5),
                        new AbilityHitsContext(0.72, 0.84, false, ENHANCED, 6),
                        new AbilityHitsContext(0.72, 0.84, false, ENHANCED, 7),
                        new AbilityHitsContext(0.72, 0.84, false, ENHANCED, 8)),
                "Asphyxiate",
                -25,
                35,
                true,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                ASPHYXIATE,
                DamageCalculationTiming.ON_HIT);
    }

    public static AbilityContext concentratedBlast() {
        GeneratedBuffEffect generatedBuffEffect = new GeneratedBuffEffect(
                BuffId.CONCENTRATEDBLASTBUFF,
                GeneratedBuffTiming.ON_COMPLETION
        );
        List<GeneratedBuffEffect> generatedBuffEffects = List.of(generatedBuffEffect);
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
                CONCENTRATEDBLAST,
                generatedBuffEffects,
                AbilityCooldownTiming.ON_CAST,
                true,
                DamageCalculationTiming.ON_HIT);
    }

    public static AbilityContext greaterConcentratedBlast() {
        GeneratedBuffEffect generatedBuffEffect = new GeneratedBuffEffect(
                BuffId.GREATERCONCENTRATEDBLASTBUFF,
                GeneratedBuffTiming.ON_COMPLETION
        );
        List<GeneratedBuffEffect> generatedBuffEffects = List.of(generatedBuffEffect);
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
                GREATERCONCENTRATEDBLAST,
                generatedBuffEffects,
                AbilityCooldownTiming.ON_CAST,
                true,
                DamageCalculationTiming.ON_HIT);
    }

    public static AbilityContext magmaTempest() {
        return new AbilityContext(8,
                List.of(new AbilityHitsContext(0.35, 0.45, false, ENHANCED, 4),
                        new AbilityHitsContext(0.35, 0.45, false, ENHANCED, 6),
                        new AbilityHitsContext(0.35, 0.45, false, ENHANCED, 8),
                        new AbilityHitsContext(0.35, 0.45, false, ENHANCED, 10),
                        new AbilityHitsContext(0.35, 0.45, false, ENHANCED, 12),
                        new AbilityHitsContext(0.35, 0.45, false, ENHANCED, 14),
                        new AbilityHitsContext(0.35, 0.45, false, ENHANCED, 16),
                        new AbilityHitsContext(0.35, 0.45, false, ENHANCED, 18)),
                "Magma Tempest",
                -20,
                35,
                false,
                BOTH,
                AREA_TARGET,
                MAGIC,
                MAGMATEMPEST,
                DamageCalculationTiming.ON_HIT);
    }

    public static AbilityContext corruptionBlast() {
        return new AbilityContext(5,
                List.of(new AbilityHitsContext(0.9, 1.1, true, ENHANCED, 2),
                        new AbilityHitsContext(0.9, 1.1, true, ENHANCED, 4),
                        new AbilityHitsContext(0.9, 1.1, true, ENHANCED, 6),
                        new AbilityHitsContext(0.9, 1.1, true, ENHANCED, 8),
                        new AbilityHitsContext(0.9, 1.1, true, ENHANCED, 10)),
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
                List.of(new AbilityHitsContext(0.55, 0.65, false, ENHANCED, 3),
                        new AbilityHitsContext(0.65, 0.8, false, ENHANCED, 5),
                        new AbilityHitsContext(0.75, 0.95, false, ENHANCED, 7),
                        new AbilityHitsContext(0.85, 1.1, false, ENHANCED, 9)),
                "Smoke Tendrils",
                0,
                75,
                true,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                SMOKETENDRILS,
                DamageCalculationTiming.ON_HIT);
    }

    public static AbilityContext tsunami() {
        GeneratedBuffEffect generatedBuffEffect = new GeneratedBuffEffect(
                BuffId.TSUNAMI,
                GeneratedBuffTiming.ON_RELEASE
        );
        List<GeneratedBuffEffect> generatedBuffEffects = List.of(generatedBuffEffect);
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.25, 2.75, false, ULTIMATE, 3)),
                "Tsunami",
                -100,
                100,
                false,
                BOTH,
                MULTI_TARGET,
                MAGIC,
                TSUNAMI,
                generatedBuffEffects);
    }
}
