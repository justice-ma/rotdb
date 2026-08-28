package com.rotdb.shared.ability.factory;

import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;

import java.util.List;

import static com.rotdb.shared.ability.AbilityId.*;
import static com.rotdb.shared.ability.Handedness.BOTH;
import static com.rotdb.shared.combat.domain.model.enums.AbilityTier.BASIC;
import static com.rotdb.shared.combat.domain.model.enums.AbilityTier.CONJURE;
import static com.rotdb.shared.combat.domain.model.enums.CombatStyles.MAGIC;
import static com.rotdb.shared.combat.domain.model.enums.Targetting.MULTI_TARGET;
import static com.rotdb.shared.combat.domain.model.enums.Targetting.SINGLE_TARGET;

public class InternalAbilityFactory {
    public static AbilityContext inciteFearProc() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.1, 0.5, false, BASIC, 3)),
                "Incite Fear Proc",
                0,
                21,
                false,
                BOTH,
                MULTI_TARGET,
                MAGIC,
                INCITEFEARPROC);
    }

    public static AbilityContext commandSkeletonWarriorHit() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.22, 0.28, false, CONJURE, 1)),
                "Command Skeleton Warrior",
                0,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.NECROMANCY,
                COMMANDSKELETONWARRIORHIT);
    }

    public static AbilityContext putridZombieHit() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.18, 0.22, false, CONJURE, 6)),
                "Conjure Putrid Zombie",
                0,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.NECROMANCY,
                PUTRIDZOMBIEHIT,
                false);
    }

    public static AbilityContext putridZombiePoison() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.08, 0.12, false, CONJURE, 3)),
                "Conjure Putrid Zombie",
                0,
                0,
                false,
                BOTH,
                SINGLE_TARGET,
                CombatStyles.NECROMANCY,
                PUTRIDZOMBIEPOISON,
                false);
    }
}
