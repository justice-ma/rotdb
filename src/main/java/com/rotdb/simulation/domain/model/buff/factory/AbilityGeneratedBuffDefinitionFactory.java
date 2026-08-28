package com.rotdb.simulation.domain.model.buff.factory;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.enums.BuffApplication;
import com.rotdb.simulation.domain.model.buff.enums.BuffLifecycle;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;

public class AbilityGeneratedBuffDefinitionFactory {
    public static BuffDefinition rend() {
        return create(BuffId.REND, BuffLifecycle.TIMED, 10, null);
    }

    public static BuffDefinition rendBleed() {
        return create(BuffId.REND_BLEED, BuffLifecycle.TIMED, 25, null);
    }

    public static BuffDefinition chaosRoar() {
        return create(BuffId.CHAOSROAR, BuffLifecycle.TIMED, 12, null);
    }

    public static BuffDefinition balanceByForce() {
        return create(BuffId.BALANCEBYFORCE, BuffLifecycle.TIMED, 50, null);
    }

    public static BuffDefinition dragonScimitar() {
        return create(BuffId.DRAGONSCIMITAR, BuffLifecycle.TIMED, 100, null);
    }

    public static BuffDefinition furyBuff() {
        return create(BuffId.FURYBUFF, BuffLifecycle.TIMED, 25, null);
    }

    public static BuffDefinition greaterFuryBuff() {
        return create(BuffId.GREATERFURYBUFF, BuffLifecycle.TIMED, 25, null);
    }

    public static BuffDefinition concentratedBlastBuff() {
        return create(BuffId.CONCENTRATEDBLASTBUFF, BuffLifecycle.UNTIL_CONSUMED, null, null);
    }

    public static BuffDefinition greaterConcentratedBlastBuff() {
        return create(BuffId.GREATERCONCENTRATEDBLASTBUFF, BuffLifecycle.UNTIL_CONSUMED, null, null);
    }

    public static BuffDefinition rapidFireBuff() {
        return create(BuffId.RAPIDFIREBUFF, BuffLifecycle.TIMED, 5, null);
    }

    public static BuffDefinition asphyxiateBuff() {
        return create(BuffId.ASPHYXIATEBUFF, BuffLifecycle.TIMED, 6, null);
    }

    public static BuffDefinition conflagrate() {
        return create(BuffId.CONFLAGRATE, BuffLifecycle.TIMED, 25, null);
    }

    public static BuffDefinition obliterated() {
        return create(BuffId.OBLITERATED, BuffLifecycle.TIMED, 100, null, BuffApplication.TARGET_BUFF_SET);
    }

    public static BuffDefinition clawsOfGuthix() {
        return create(BuffId.CLAWSOFGUTHIX, BuffLifecycle.TIMED, 100, null, BuffApplication.TARGET_BUFF_SET);
    }

    public static BuffDefinition clobber() {
        return create(BuffId.CLOBBER, BuffLifecycle.TIMED, 100, null, BuffApplication.TARGET_BUFF_SET);
    }

    public static BuffDefinition sunder() {
        return create(BuffId.SUNDER, BuffLifecycle.TIMED, 100, null, BuffApplication.TARGET_BUFF_SET);
    }

    public static BuffDefinition backstab() {
        return create(BuffId.BACKSTAB, BuffLifecycle.TIMED, 100, null, BuffApplication.TARGET_BUFF_SET);
    }

    public static BuffDefinition sonicWave() {
        return create(BuffId.SONICWAVE, BuffLifecycle.TIMED, 15, null, BuffApplication.PLAYER_BUFF_SET);
    }

    public static BuffDefinition greaterSonicWave() {
        return create(BuffId.GREATERSONICWAVE, BuffLifecycle.TIMED, 15, null, BuffApplication.PLAYER_BUFF_SET);
    }

    public static BuffDefinition greaterBarge() {
        return create(BuffId.GREATERBARGE, BuffLifecycle.TIMED, 10, null, BuffApplication.PLAYER_BUFF_SET);
    }

    public static BuffDefinition sunfallSlam() {
        return create(BuffId.SUNFALLSLAM, BuffLifecycle.TIMED, 50, 100, BuffApplication.PLAYER_BUFF_SET);
    }

    public static BuffDefinition soulCrush() {
        return create(BuffId.SOULCRUSH, BuffLifecycle.TIMED, 50, 100, BuffApplication.PLAYER_BUFF_SET);
    }

    public static BuffDefinition deathEssence() {
        return create(BuffId.DEATHESSENCE, BuffLifecycle.TIMED, 50, 100, BuffApplication.PLAYER_BUFF_SET);
    }

    public static BuffDefinition meteorStrike() {
        return new BuffDefinition(
                BuffId.METEORSTRIKE,
                BuffSource.ABILITY_GENERATED,
                BuffLifecycle.TIMED,
                BuffApplication.PLAYER_BUFF_SET,
                50,
                null,
                false,
                false,
                null,
                4.5
        );
    }

    public static BuffDefinition vestmentsBleed() {
        return new BuffDefinition(
                BuffId.VESTMENTSBLEED,
                BuffSource.ABILITY_GENERATED,
                BuffLifecycle.TIMED,
                BuffApplication.PLAYER_BUFF_SET,
                30,
                null,
                false,
                false,
                null,
                0.5
        );
    }

    public static BuffDefinition tsunami() {
        return create(BuffId.TSUNAMI, BuffLifecycle.TIMED, 50, null);
    }

    public static BuffDefinition instability() {
        return create(BuffId.INSTABILITY, BuffLifecycle.TIMED, 50, null);
    }

    public static BuffDefinition combusted() {
        return create(BuffId.COMBUSTED, BuffLifecycle.TIMED, 30, null, BuffApplication.TARGET_BUFF_SET);
    }

    public static BuffDefinition flameboundRival() {
        return create(BuffId.FLAMEBOUNDRIVAL, BuffLifecycle.UNTIL_CONSUMED, null, null, BuffApplication.TARGET_BUFF_SET);
    }

    public static BuffDefinition zgs() {
        return create(BuffId.ZGS, BuffLifecycle.TIMED, 35, 100);
    }

    public static BuffDefinition gales() {
        return create(BuffId.GALES, BuffLifecycle.TIMED, 10, 34);
    }

    public static BuffDefinition skeletonWarriorDuration() {
        return create(BuffId.SKELETONWARRIORDURATION, BuffLifecycle.TIMED, 70, null);
    }

    public static BuffDefinition commandSkeletonWarrior() {
        return create(BuffId.COMMANDSKELETONWARRIOR, BuffLifecycle.TIMED, 10, 25);
    }

    public static BuffDefinition putridZombieDuration() {
        return create(BuffId.PUTRIDZOMBIEDURATION, BuffLifecycle.TIMED, 70, null);
    }

    public static BuffDefinition vengefulGhostDuration() {
        return create(BuffId.VENGEFULGHOSTDURATION, BuffLifecycle.TIMED, 70, null);
    }

    private static BuffDefinition create(BuffId id, BuffLifecycle lifecycle, Integer duration, Integer cooldown) {
        return new BuffDefinition(
                id,
                BuffSource.ABILITY_GENERATED,
                lifecycle,
                BuffApplication.PLAYER_BUFF_SET,
                duration,
                cooldown,
                false,
                false,
                null,
                null
        );
    }

    private static BuffDefinition create(BuffId id, BuffLifecycle lifecycle, Integer duration, Integer cooldown, BuffApplication application) {
        return new BuffDefinition(
                id,
                BuffSource.ABILITY_GENERATED,
                lifecycle,
                application,
                duration,
                cooldown,
                false,
                false,
                null,
                null
        );
    }

    private static BuffDefinition create(BuffId id, BuffLifecycle lifecycle, Integer duration, Integer cooldown, Double adrenalineDelta, Double tickAdrenalineDelta) {
        return new BuffDefinition(
                id,
                BuffSource.ABILITY_GENERATED,
                lifecycle,
                BuffApplication.PLAYER_BUFF_SET,
                duration,
                cooldown,
                false,
                false,
                adrenalineDelta,
                tickAdrenalineDelta
        );
    }

    private static BuffDefinition create(BuffId id, BuffLifecycle lifecycle, Integer duration, Integer cooldown, BuffApplication application, Double adrenalineDelta) {
        return new BuffDefinition(
                id,
                BuffSource.ABILITY_GENERATED,
                lifecycle,
                application,
                duration,
                cooldown,
                false,
                false,
                adrenalineDelta,
                null
        );
    }
}
