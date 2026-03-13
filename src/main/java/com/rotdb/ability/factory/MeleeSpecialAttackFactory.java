package com.rotdb.ability.factory;

import com.rotdb.domain.model.context.AbilityContext;
import com.rotdb.domain.model.context.AbilityHitsContext;

import java.util.List;

import static com.rotdb.ability.AbilityId.*;
import static com.rotdb.ability.Handedness.*;
import static com.rotdb.domain.model.enums.AbilityTier.*;
import static com.rotdb.domain.model.enums.CombatStyles.*;
import static com.rotdb.domain.model.enums.Targetting.*;

public class MeleeSpecialAttackFactory {
    public static AbilityContext energyDrain() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.75, 0.85, false, THRESHOLD, 2)),
                "Energy Drain",
                -50,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                ENERGYDRAIN);
    }

    public static AbilityContext weaken() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.75, 0.85, false, THRESHOLD, 2)),
                "Weaken",
                -60,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                WEAKEN);
    }

    public static AbilityContext theFinalFlurry() {
        return new AbilityContext(3,
                List.of(new AbilityHitsContext(0.8, 1, false, THRESHOLD, 1),
                        new AbilityHitsContext(0.8, 1, false, THRESHOLD, 2),
                        new AbilityHitsContext(1.5, 1.8, false, THRESHOLD, 3)),
                "The Final Flurry",
                -50,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                THEFINALFLURRY);
    }

    public static AbilityContext spearWall() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.05, 1.25, false, THRESHOLD, 2)),
                "Spear Wall",
                -100,
                0,
                false,
                BOTH,
                MULTI_TARGET,
                MELEE,
                SPEARWALL);
    }

    public static AbilityContext clobber() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.9, 1.1, false, THRESHOLD, 2)),
                "Clobber",
                -100,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                CLOBBER);
    }

    public static AbilityContext quickSmash() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.15, 1.35, false, THRESHOLD, 1)),
                "Quick Smash",
                -50,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                QUICKSMASH);
    }

    public static AbilityContext sweep() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(1.2, 1.5, false, THRESHOLD, 2),
                        new AbilityHitsContext(1.2, 1.5, false, THRESHOLD, 2)),
                "Sweep",
                -30,
                0,
                false,
                BOTH,
                MULTI_TARGET,
                MELEE,
                SWEEP);
    }

    public static AbilityContext impale() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.3, 1.5, false, THRESHOLD, 2)),
                "Impale",
                -25,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                IMPALE);
    }

    public static AbilityContext liquefy() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.25, 1.45, false, THRESHOLD, 2)),
                "Liquefy",
                -50,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                LIQUEFY);
    }

    public static AbilityContext favourOfTheWarGod() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.25, 1.45, false, THRESHOLD, 2)),
                "Favour of the War God",
                -100,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                FAVOUROFTHEWARGOD);
    }

    public static AbilityContext sunder() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.25, 1.45, false, THRESHOLD, 2)),
                "Sunder",
                -50,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                SUNDER);
    }

    public static AbilityContext draconicPuncture() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(1.25, 1.55, false, THRESHOLD, 1),
                        new AbilityHitsContext(1.25, 1.55, false, THRESHOLD, 2)),
                "Draconic Puncture",
                -25,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                DRACONICPUNCTURE);
    }

    public static AbilityContext backstab() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.5, 1.7, false, THRESHOLD, 2)),
                "Backstab",
                -75,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                BACKSTAB);
    }

    public static AbilityContext aimedStrike() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.5, 1.7, false, THRESHOLD, 2)),
                "Aimed Strike",
                -35,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                AIMEDSTRIKE);
    }

    public static AbilityContext obliterate() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.6, 1.8, false, THRESHOLD, 2)),
                "Obliterate",
                -35,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                OBLITERATE);
    }

    public static AbilityContext healingBlade() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.85, 2.15, false, THRESHOLD, 3)),
                "Healing Blade",
                -50,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                HEALINGBLADE);
    }

    public static AbilityContext iceCleave() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.85, 2.15, false, THRESHOLD, 3)),
                "Ice Cleave",
                -60,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                ICECLEAVE);
    }

    public static AbilityContext disrupt() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.3, 2.7, false, THRESHOLD, 2)),
                "Disrupt",
                -60,
                0,
                false,
                BOTH,
                MULTI_TARGET,
                MELEE,
                DISRUPT);
    }

    public static AbilityContext warstrike() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.25, 2.65, false, THRESHOLD, 3)),
                "Warstrike",
                -100,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                WARSTRIKE);
    }

    public static AbilityContext draconicBlow() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.4, 2.8, false, THRESHOLD, 2)),
                "Draconic Blow",
                -20,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                DRACONICBLOW);
    }

    public static AbilityContext draconicSlash() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.4, 2.8, false, THRESHOLD, 2)),
                "Draconic Slash",
                -25,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                DRACONICSLASH);
    }

    public static AbilityContext feint() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.55, 2.95, false, THRESHOLD, 2)),
                "Feint",
                -25,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                FEINT);
    }

    public static AbilityContext igneousShowdown() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.6, 3, false, THRESHOLD, 2)),
                "Igneous Showdown",
                -50,
                100,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                IGNEOUSSHOWDOWN);
    }

    public static AbilityContext igneousShowdownRecast() {
        return new AbilityContext(4,
                List.of(new AbilityHitsContext(2.6, 3, false, THRESHOLD, 2),
                        new AbilityHitsContext(2.45, 2.65, false, THRESHOLD, 2),
                        new AbilityHitsContext(2.45, 2.65, false, THRESHOLD, 2),
                        new AbilityHitsContext(2.45, 2.65, false, THRESHOLD, 2)),
                "Igneous Showdown Recast",
                -50,
                100,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                IGNEOUSSHOWDOWNRECAST);
    }

    public static AbilityContext draconicCleave() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.75, 3.15, false, THRESHOLD, 2)),
                "Draconic Cleave",
                -25,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                DRACONICCLEAVE);
    }

    public static AbilityContext saradominsLightning() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(2.85, 3.25, false, THRESHOLD, 2),
                        new AbilityHitsContext(2.85, 3.25, false, THRESHOLD, 2)),
                "Saradomin's Light",
                -100,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                SARADOMINSLIGHTNING);
    }

    public static AbilityContext sunfallSlam() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.9, 3, false, THRESHOLD, 3)),
                "Sunfall Slam",
                -40,
                100,
                false,
                BOTH,
                MULTI_TARGET,
                MELEE,
                SUNFALLSLAM);
    }

    public static AbilityContext powerstab() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.6, 3.2, false, THRESHOLD, 2)),
                "Powerstab",
                -50,
                0,
                false,
                BOTH,
                MULTI_TARGET,
                MELEE,
                POWERSTAB);
    }

    public static AbilityContext armadylsJudgement() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(4, 4.8, false, THRESHOLD, 3)),
                "Armadyl's Judgement",
                -50,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                ARMADYLSJUDGEMENT);
    }

    public static AbilityContext blackhole() {
        return new AbilityContext(11,
                List.of(new AbilityHitsContext(0.35, 0.45, true, THRESHOLD, 3),
                        new AbilityHitsContext(0.35, 0.45, true, THRESHOLD, 6),
                        new AbilityHitsContext(0.35, 0.45, true, THRESHOLD, 9),
                        new AbilityHitsContext(0.35, 0.45, true, THRESHOLD, 12),
                        new AbilityHitsContext(0.35, 0.45, true, THRESHOLD, 15),
                        new AbilityHitsContext(0.35, 0.45, true, THRESHOLD, 18),
                        new AbilityHitsContext(0.35, 0.45, true, THRESHOLD, 21),
                        new AbilityHitsContext(0.35, 0.45, true, THRESHOLD, 24),
                        new AbilityHitsContext(0.35, 0.45, true, THRESHOLD, 27),
                        new AbilityHitsContext(0.35, 0.45, true, THRESHOLD, 30),
                        new AbilityHitsContext(0.35, 0.45, true, THRESHOLD, 33)),
                "Blackhole",
                -50,
                100,
                false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                BLACKHOLE);
    }

    public static AbilityContext vineCall() {
        return new AbilityContext(11,
                List.of(new AbilityHitsContext(1, 1.2, false, THRESHOLD, 2),
                        new AbilityHitsContext(0.2, 0.25, false, THRESHOLD, 5),
                        new AbilityHitsContext(0.2, 0.25, false, THRESHOLD, 8),
                        new AbilityHitsContext(0.2, 0.25, false, THRESHOLD, 11),
                        new AbilityHitsContext(0.2, 0.25, false, THRESHOLD, 14),
                        new AbilityHitsContext(0.2, 0.25, false, THRESHOLD, 17),
                        new AbilityHitsContext(0.2, 0.25, false, THRESHOLD, 20),
                        new AbilityHitsContext(0.2, 0.25, false, THRESHOLD, 23),
                        new AbilityHitsContext(0.2, 0.25, false, THRESHOLD, 26),
                        new AbilityHitsContext(0.2, 0.25, false, THRESHOLD, 29),
                        new AbilityHitsContext(0.2, 0.25, false, THRESHOLD, 32)),
                "Vine Call",
                -60,
                33,
                false,
                BOTH,
                MULTI_TARGET,
                MELEE,
                VINECALL);
    }

    public static AbilityContext icyTempest() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(1.15, 1.35, false, THRESHOLD, 3),
                        new AbilityHitsContext(1.75, 2.05, false, THRESHOLD, 3)),
                "Icy Tempest",
                -30,
                25,
                false,
                BOTH,
                MULTI_TARGET,
                MELEE,
                ICYTEMPEST);
    }

    public static AbilityContext sliceAndDice() {
        return new AbilityContext(4,
                List.of(new AbilityHitsContext(1.8, 2.2, false, THRESHOLD, 2),
                        new AbilityHitsContext(0.9, 1.1, false, THRESHOLD, 2),
                        new AbilityHitsContext(0.45, 0.55, false, THRESHOLD, 4),
                        new AbilityHitsContext(0.45, 0.55, false, THRESHOLD, 4)),
                "Slice & Dice",
                -50,
                 0,
                 false,
                BOTH,
                SINGLE_TARGET,
                MELEE,
                SLICEANDDICE);
    }
}
