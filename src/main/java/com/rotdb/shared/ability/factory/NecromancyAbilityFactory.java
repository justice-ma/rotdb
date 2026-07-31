package com.rotdb.shared.ability.factory;

import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import java.util.List;
import static com.rotdb.shared.ability.AbilityId.*;
import static com.rotdb.shared.ability.Handedness.*;
import static com.rotdb.shared.combat.domain.model.enums.AbilityTier.*;
import static com.rotdb.shared.combat.domain.model.enums.Targetting.*;

public class NecromancyAbilityFactory {
    public static AbilityContext necromancy() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.9, 1.1, false, BASIC, 1)),
                "Necromancy",
                9,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.NECROMANCY,
                NECROMANCYAUTO);
    }

    public static AbilityContext conjureSkeletonWarrior() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.22, 0.28, false, CONJURE, 4)),
                "Conjure Skeleton Warrior",
                0,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.NECROMANCY,
                CONJURESKELETONWARRIOR);
    }

    public static AbilityContext commandSkeletonWarrior() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(0.22, 0.28, false, CONJURE, 1),
                        new AbilityHitsContext(0.22, 0.28, false, CONJURE, 2)),
                "Command Skeleton Warrior",
                0,
                25,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.NECROMANCY,
                COMMANDSKELETONWARRIOR);
    }

    public static AbilityContext fingerOfDeath() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.7, 3.3, false, ENHANCED, 2)),
                "Finger of Death",
                -60,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.NECROMANCY,
                FINGEROFDEATH);
    }

    public static AbilityContext touchOfDeath() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.9, 1.1, false, BASIC, 1)),
                "Touch of Death",
                9,
                24,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.NECROMANCY,
                TOUCHOFDEATH);
    }

    public static AbilityContext deathSkulls() {
        return new AbilityContext(3,
                List.of(new AbilityHitsContext(2.25, 2.75, false, ULTIMATE, 3),
                        new AbilityHitsContext(2.25, 2.75, false, ULTIMATE, 9),
                        new AbilityHitsContext(2.25, 2.75, false, ULTIMATE, 15)),
                "Death Skulls",
                -60,
                100,
                false,
                BOTH,
                MULTI_TARGET,
                CombatStyles.NECROMANCY,
                DEATHSKULLS);
    }

    public static AbilityContext deathSkullsIgneous() {
        return new AbilityContext(5,
                List.of(new AbilityHitsContext(2.25, 2.75, false, ULTIMATE, 3),
                        new AbilityHitsContext(2.25, 2.75, false, ULTIMATE, 9),
                        new AbilityHitsContext(2.25, 2.75, false, ULTIMATE, 15),
                        new AbilityHitsContext(2.25, 2.75, false, ULTIMATE, 21)),
                "Death Skulls",
                -60,
                100,
                false,
                BOTH,
                MULTI_TARGET,
                CombatStyles.NECROMANCY,
                DEATHSKULLSIGNEOUS);
    }

    public static AbilityContext bloodSiphon() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.17, 1.43, false, ENHANCED, 10)),
                "Blood Siphon",
                0,
                75,
                true,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.NECROMANCY,
                BLOODSIPHON);
    }

    public static AbilityContext bloodSiphonHeal() {
        return new AbilityContext(4,
                List.of(new AbilityHitsContext(0.22, 0.28, false, ENHANCED, 1),
                        new AbilityHitsContext(0.22, 0.28, false, ENHANCED, 3),
                        new AbilityHitsContext(0.22, 0.28, false, ENHANCED, 5),
                        new AbilityHitsContext(0.22, 0.28, false, ENHANCED, 7)),
                "Blood Siphon AOE",
                0,
                75,
                true,
                BOTH,
                MULTI_TARGET,
                CombatStyles.NECROMANCY,
                BLOODSIPHONHEAL);
    }

    public static AbilityContext conjurePutridZombie() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(0.18, 0.22, false, CONJURE, 6),
                        new AbilityHitsContext(0.08, 0.12, false, CONJURE, 3)),
                "Conjure Putrid Zombie",
                0,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.NECROMANCY,
                CONJUREPUTRIDZOMBIE);
    }

    public static AbilityContext commandPutridZombie() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(3.6, 4.4, false, CONJURE, 4)),
                "Command Putrid Zombie",
                0,
                0,
                false,
                BOTH,
                MULTI_TARGET,
                CombatStyles.NECROMANCY,
                COMMANDPUTRIDZOMBIE);
    }

    public static AbilityContext conjureVengefulGhost() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.18, 0.22, false, CONJURE, 7)),
                "Conjure Vengeful Ghost",
                0,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.NECROMANCY,
                CONJUREVENGEFULGHOST);
    }

    public static AbilityContext bloat() {
        return new AbilityContext(11,
                List.of(new AbilityHitsContext(1.35, 1.65, false, ENHANCED, 2)),
                "Bloat",
                20,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.NECROMANCY,
                BLOAT);
    }

    public static AbilityContext soulSap() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.9, 1.1, false, BASIC, 1)),
                "Soul Sap",
                9,
                9,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.NECROMANCY,
                SOULSAP);
    }

    public static AbilityContext soulStrike() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.35, 1.65, false, ENHANCED, 2)),
                "Soul Strike",
                0,
                0,
                false,
                BOTH,
                MULTI_TARGET,
                CombatStyles.NECROMANCY,
                SOULSTRIKE);
    }

    public static AbilityContext spectralScythe() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.72, 0.88, false, ENHANCED, 1)),
                "Spectral Scythe",
                10,
                25,
                false,
                BOTH,
                MULTI_TARGET,
                CombatStyles.NECROMANCY,
                SPECTRALSCYTHE);
    }

    public static AbilityContext spectralHurricane() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.8, 2.2, false, ENHANCED, 3)),
                "Spectral Scythe",
                20,
                0,
                false,
                BOTH,
                MULTI_TARGET,
                CombatStyles.NECROMANCY,
                SPECTRALHURRICANE);
    }

    public static AbilityContext spectralMeteorStrike() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.25, 2.75, false, ENHANCED, 2)),
                "Spectral Scythe",
                30,
                0,
                false,
                BOTH,
                MULTI_TARGET,
                CombatStyles.NECROMANCY,
                SPECTRALMETEORSTRIKE);
    }

    public static AbilityContext volleyOfSouls() {
        return new AbilityContext(5,
                List.of(new AbilityHitsContext(1.35, 1.65, false, ENHANCED, 2),
                        new AbilityHitsContext(1.35, 1.65, false, ENHANCED, 2)),
                "Volley of Souls",
                0,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.NECROMANCY,
                VOLLEYOFSOULS);
    }

    public static AbilityContext commandPhantomGuardian() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.45, 0.55, false, CONJURE, 4)),
                "Command Phantom Guardian",
                0,
                15,
                false,
                BOTH,
                MULTI_TARGET,
                CombatStyles.NECROMANCY,
                COMMANDPHANTOMGUARDIAN);
    }
}
