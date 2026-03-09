package com.rotdb.ability.factory;

import com.rotdb.domain.model.context.AbilityContext;
import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.enums.*;

import java.util.List;

import static com.rotdb.ability.AbilityId.*;
import static com.rotdb.ability.Handedness.*;
import static com.rotdb.domain.model.enums.AbilityTier.*;
import static com.rotdb.domain.model.enums.Targetting.*;

public class MeleeAbilityFactory {
    private MeleeAbilityFactory() {
    }

    public static AbilityContext attack() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.1, 1.3, false, BASIC, 1)),
                "Attack",
                9,
                0,
                true,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                MELEEAUTO);
    }

    public static AbilityContext assault() {
        return new AbilityContext(4,
                List.of(new AbilityHitsContext(1.45, 1.75, false, THRESHOLD, 1),
                        new AbilityHitsContext(1.45, 1.75, false, THRESHOLD, 3),
                        new AbilityHitsContext(1.45, 1.75, false, THRESHOLD, 5),
                        new AbilityHitsContext(1.45, 1.75, false, THRESHOLD, 7)),
                "Assault",
                -25,
                10,
                true,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                ASSAULT);
    }

    public static AbilityContext bloodlustAssault() {
        return new AbilityContext(4,
                List.of(new AbilityHitsContext(1.85, 2.15, false, THRESHOLD, 1),
                        new AbilityHitsContext(1.85, 2.15, false, THRESHOLD, 3),
                        new AbilityHitsContext(1.85, 2.15, false, THRESHOLD, 5),
                        new AbilityHitsContext(1.85, 2.15, false, THRESHOLD, 7)),
                "Bloodlust Assault",
                -25,
                10,
                true,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                BLOODLUSTASSAULT);
    }

    public static AbilityContext sever2h() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.2, 1.4, false, BASIC, 1)),
                "Sever",
                12,
                9,
                false,
                TWO_HANDED,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                SEVER2H);
    }

    public static AbilityContext severDw() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(0.6, 0.75, false, BASIC, 1),
                        new AbilityHitsContext(0.6, 0.75, false, BASIC, 1)),
                "Sever",
                12,
                9,
                false,
                DUAL_WIELD,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                SEVERDW);
    }

    public static AbilityContext overpower() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(5.5, 6, false, ULTIMATE, 3)),
                "Overpower",
                -60,
                50,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                OVERPOWER);
    }

    public static AbilityContext overpowerIgneous() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(3.1, 3.7, false, ULTIMATE, 3),
                        new AbilityHitsContext(3.1, 3.7, false, ULTIMATE, 3)),
                "Overpower",
                -60,
                50,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                OVERPOWERIGNEOUS);
    }

    public static AbilityContext rend() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.35, 1.65, false, BASIC, 1)),
                "Rend",
                9,
                17,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                REND);
    }

    public static AbilityContext fury() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.1, 1.3, false, BASIC, 1)),
                "Fury",
                9,
                25,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                FURY);
    }

    public static AbilityContext greaterFury() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.2, 1.4, false, BASIC, 1)),
                "Greater Fury",
                9,
                25,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                GREATERFURY);
    }

    public static AbilityContext backhand() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.95, 1.05, false, BASIC, 1)),
                "Backhand",
                9,
                25,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                BACKHAND);
    }

    public static AbilityContext hurricane() {
        return new AbilityContext(2,
                List.of(new AbilityHitsContext(1.35, 1.65, false, THRESHOLD, 1),
                        new AbilityHitsContext(1.55, 1.85, false, THRESHOLD, 1)),
                "Hurricane",
                -25,
                34,
                false,
                TWO_HANDED,
                MULTI_TARGET,
                CombatStyles.MELEE,
                HURRICANE);
    }

    public static AbilityContext bloodlustHurricane() {
        return new AbilityContext(3,
                List.of(new AbilityHitsContext(1.35, 1.65, false, THRESHOLD, 1),
                        new AbilityHitsContext(1.55, 1.85, false, THRESHOLD, 1),
                        new AbilityHitsContext(0.75, 0.95, false, THRESHOLD, 1)),
                "Bloodlust Hurricane",
                -25,
                34,
                false,
                TWO_HANDED,
                MULTI_TARGET,
                CombatStyles.MELEE,
                BLOODLUSTHURRICANE);
    }

    public static AbilityContext flurry() {
        return new AbilityContext(8,
                List.of(new AbilityHitsContext(0.65, 0.75, false, THRESHOLD, 1),
                        new AbilityHitsContext(0.65, 0.75, false, THRESHOLD, 2),
                        new AbilityHitsContext(0.65, 0.75, false, THRESHOLD, 3),
                        new AbilityHitsContext(0.65, 0.75, false, THRESHOLD, 4),
                        new AbilityHitsContext(0.65, 0.75, false, THRESHOLD, 5),
                        new AbilityHitsContext(0.65, 0.75, false, THRESHOLD, 6),
                        new AbilityHitsContext(0.65, 0.75, false, THRESHOLD, 7),
                        new AbilityHitsContext(0.65, 0.75, false, THRESHOLD, 8)),
                "Flurry",
                -25,
                34,
                true,
                DUAL_WIELD,
                AREA_TARGET,
                CombatStyles.MELEE,
                FLURRY);
    }

    public static AbilityContext greaterFlurry() {
        return new AbilityContext(8,
                List.of(new AbilityHitsContext(0.65, 0.75, false, THRESHOLD, 1),
                        new AbilityHitsContext(0.65, 0.75, false, THRESHOLD, 2),
                        new AbilityHitsContext(0.65, 0.75, false, THRESHOLD, 3),
                        new AbilityHitsContext(0.65, 0.75, false, THRESHOLD, 4),
                        new AbilityHitsContext(0.65, 0.75, false, THRESHOLD, 5),
                        new AbilityHitsContext(0.65, 0.75, false, THRESHOLD, 6),
                        new AbilityHitsContext(0.65, 0.75, false, THRESHOLD, 7),
                        new AbilityHitsContext(0.65, 0.75, false, THRESHOLD, 8)),
                "Greater Flurry",
                -25,
                34,
                true,
                DUAL_WIELD,
                AREA_TARGET,
                CombatStyles.MELEE,
                GREATERFLURRY);
    }

    public static AbilityContext dismember() {
        return new AbilityContext(8,
                List.of(new AbilityHitsContext(0.25, 0.35,true, BASIC, 2),
                        new AbilityHitsContext(0.25, 0.35,true, BASIC, 4),
                        new AbilityHitsContext(0.25, 0.35,true, BASIC, 6),
                        new AbilityHitsContext(0.25, 0.35,true, BASIC, 8),
                        new AbilityHitsContext(0.25, 0.35,true, BASIC, 10),
                        new AbilityHitsContext(0.25, 0.35,true, BASIC, 12),
                        new AbilityHitsContext(0.25, 0.35,true, BASIC, 14),
                        new AbilityHitsContext(0.25, 0.35,true, BASIC, 16)),
                "Dismember",
                0,
                40,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                DISMEMBER);
    }

    public static AbilityContext slaughter() {
        return new AbilityContext(6,
                List.of(new AbilityHitsContext(0.9, 1.1, true, THRESHOLD, 3),
                        new AbilityHitsContext(0.9, 1.1, true, THRESHOLD, 6),
                        new AbilityHitsContext(0.9, 1.1, true, THRESHOLD, 9),
                        new AbilityHitsContext(0.9, 1.1, true, THRESHOLD, 12),
                        new AbilityHitsContext(0.9, 1.1, true, THRESHOLD, 15),
                        new AbilityHitsContext(0.9, 1.1, true, THRESHOLD, 18)),
                "Slaughter",
                -25,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                SLAUGHTER);
    }

    public static AbilityContext massacre() {
        return new AbilityContext(7,
                List.of(new AbilityHitsContext(1.1, 1.3, false, THRESHOLD, 1),
                        new AbilityHitsContext(1.2, 1.2, true, THRESHOLD, 5),
                        new AbilityHitsContext(1.2, 1.2, true, THRESHOLD, 9),
                        new AbilityHitsContext(1.2, 1.2, true, THRESHOLD, 13),
                        new AbilityHitsContext(1.2, 1.2, true, THRESHOLD, 17),
                        new AbilityHitsContext(1.2, 1.2, true, THRESHOLD, 21),
                        new AbilityHitsContext(1.2, 1.2, true, THRESHOLD, 25)),
                "Massacre",
                -25,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                MASSACRE);
    }

    public static AbilityContext punish() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.1, 1.3, false, BASIC, 1)),
                "Punish",
                9,
                40,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                PUNISH);
    }

    public static AbilityContext barge() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.75, 0.95, false, BASIC, 1)),
                "Barge",
                9,
                34,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                BARGE);
    }

    public static AbilityContext greaterBarge() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.75, 0.95, false, BASIC, 1)),
                "Greater Barge",
                9,
                34,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                GREATERBARGE);
    }

    public static AbilityContext pulverise() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(3, 3.4, false, ULTIMATE, 3)),
                "Pulverise",
                -60,
                100,
                false,
                TWO_HANDED,
                SINGLE_TARGET,
                CombatStyles.MELEE,
                PULVERISE);
    }

    public static AbilityContext meteorStrike() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(2.2, 2.5, false, ULTIMATE, 2)),
                "Meteor Strike",
                -60,
                100,
                false,
                BOTH,
                MULTI_TARGET,
                CombatStyles.MELEE,
                METEORSTRIKE);
    }

    public static AbilityContext chaosRoar() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1, 1.2, false, BASIC, 1)),
                "Chaos Roar",
                9,
                100,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.MELEE,CHAOSROAR);
    }
}
