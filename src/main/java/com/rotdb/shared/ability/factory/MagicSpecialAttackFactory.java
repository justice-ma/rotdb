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
import static com.rotdb.shared.combat.domain.model.enums.AbilityTier.ENHANCED;
import static com.rotdb.shared.combat.domain.model.enums.CombatStyles.MAGIC;
import static com.rotdb.shared.combat.domain.model.enums.Targetting.MULTI_TARGET;
import static com.rotdb.shared.combat.domain.model.enums.Targetting.SINGLE_TARGET;

public class MagicSpecialAttackFactory {
    public static AbilityContext fromTheShadows() {
        return new AbilityContext(5,
                List.of(new AbilityHitsContext(0.55, 0.65, false, ENHANCED, 4),
                        new AbilityHitsContext(0.55, 0.65, false, ENHANCED, 8),
                        new AbilityHitsContext(0.55, 0.65, false, ENHANCED, 12),
                        new AbilityHitsContext(0.55, 0.65, false, ENHANCED, 16),
                        new AbilityHitsContext(0.55, 0.65, false, ENHANCED, 20)),
                "From the Shadows",
                -50,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                FROMTHESHADOWS);
    }

    public static AbilityContext instability() {
        GeneratedBuffEffect generatedBuffEffect = new GeneratedBuffEffect(
                BuffId.INSTABILITY,
                GeneratedBuffTiming.ON_RELEASE
        );
        List<GeneratedBuffEffect> generatedBuffEffects = List.of(generatedBuffEffect);
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.2, 1.4, false, ENHANCED, 2)),
                "Instability",
                -50,
                100,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                INSTABILITY,
                generatedBuffEffects,
                AbilityCooldownTiming.ON_RELEASE,
                true);
    }

    public static AbilityContext runeFlame() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.2, 1.4, false, ENHANCED, 2)),
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
        GeneratedBuffEffect generatedBuffEffect = new GeneratedBuffEffect(
                BuffId.CLAWSOFGUTHIX,
                GeneratedBuffTiming.ON_HIT
        );
        List<GeneratedBuffEffect> generatedBuffEffects = List.of(generatedBuffEffect);
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2, 2.4, false, ENHANCED, 1)),
                "Claws of Guthix",
                -25,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                CLAWSOFGUTHIX,
                generatedBuffEffects);
    }

    public static AbilityContext devour() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2, 2.4, false, ENHANCED, 2)),
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
                List.of(new AbilityHitsContext(2, 2.4, false, ENHANCED, 1)),
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
                List.of(new AbilityHitsContext(2, 2.4, false, ENHANCED, 1)),
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
                List.of(new AbilityHitsContext(2, 2.4, false, ENHANCED, 2)),
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
                List.of(new AbilityHitsContext(2.4, 2.8, false, ENHANCED, 3)),
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
                List.of(new AbilityHitsContext(2.7, 3.1, false, ENHANCED, 2)),
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
                List.of(new AbilityHitsContext(0.45, 0.55, false, ENHANCED, 1),
                        new AbilityHitsContext(0.45, 0.55, false, ENHANCED, 2),
                        new AbilityHitsContext(0.45, 0.55, false, ENHANCED, 3),
                        new AbilityHitsContext(0.45, 0.55, false, ENHANCED, 4),
                        new AbilityHitsContext(0.45, 0.55, false, ENHANCED, 5)),
                "Tempest of Armadyl",
                -50,
                0,
                true,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                TEMPESTOFARMADYL,
                DamageCalculationTiming.ON_HIT);
    }

    public static AbilityContext ibanBlast() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(3.4, 3.9, false, ENHANCED, 2)),
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
        GeneratedBuffEffect generatedBuffEffect = new GeneratedBuffEffect(
                BuffId.CONFLAGRATE,
                GeneratedBuffTiming.ON_CAST
        );
        List<GeneratedBuffEffect> generatedBuffEffects = List.of(generatedBuffEffect);
        return new AbilityContext(7,
                List.of(new AbilityHitsContext(1.3, 1.6, false, ENHANCED, 3),
                        new AbilityHitsContext(1.7, 2, true, ENHANCED, 3),
                        new AbilityHitsContext(1.7, 2, true, ENHANCED, 6),
                        new AbilityHitsContext(1.7, 2, true, ENHANCED, 9),
                        new AbilityHitsContext(1.7, 2, true, ENHANCED, 12),
                        new AbilityHitsContext(1.7, 2, true, ENHANCED, 15),
                        new AbilityHitsContext(1.7, 2, true, ENHANCED, 18)),
                "Soulfire",
                -35,
                75,
                false,
                BOTH,
                SINGLE_TARGET,
                MAGIC,
                SOULFIRE,
                generatedBuffEffects,
                AbilityCooldownTiming.ON_RELEASE,
                true);
    }
}
