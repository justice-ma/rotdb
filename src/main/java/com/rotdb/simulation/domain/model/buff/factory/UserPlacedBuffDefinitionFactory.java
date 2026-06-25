package com.rotdb.simulation.domain.model.buff.factory;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.enums.BuffApplication;
import com.rotdb.simulation.domain.model.buff.enums.BuffLifecycle;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;

public class UserPlacedBuffDefinitionFactory {

    public static BuffDefinition sunshine() {
        return create(BuffId.SUNSHINE, BuffLifecycle.TIMED, 50, 100, true);
    }

    public static BuffDefinition deathSwiftness() {
        return create(BuffId.DEATHSWIFTNESS, BuffLifecycle.TIMED, 50, 100, true);
    }

    public static BuffDefinition berserk() {
        return create(BuffId.BERSERK, BuffLifecycle.TIMED, 33, 100, true);
    }

    public static BuffDefinition undeadSlayerSigil() {
        return create(BuffId.UNDEADSLAYERSIGIL, BuffLifecycle.TIMED, 17, 100, false);
    }

    public static BuffDefinition dragonSlayerSigil() {
        return create(BuffId.DRAGONSLAYERSIGIL, BuffLifecycle.TIMED, 17, 100, false);
    }

    public static BuffDefinition demonSlayerSigil() {
        return create(BuffId.DEMONSLAYERSIGIL, BuffLifecycle.TIMED, 17, 100, false);
    }

    public static BuffDefinition runicCharge() {
        return create(BuffId.RUNICCHARGE, BuffLifecycle.TIMED, 25, 50, false);
    }

    public static BuffDefinition dba() {
        return create(BuffId.DBA, BuffLifecycle.TIMED, 100, null, true);
    }

    public static BuffDefinition gravitate() {
        return create(BuffId.GRAVITATE, BuffLifecycle.TIMED, 50, null, true);
    }

    public static BuffDefinition haunted() {
        return create(BuffId.HAUNTED, BuffLifecycle.TIMED, BuffApplication.TARGET_BUFF_SET, 70, null, true);
    }

    public static BuffDefinition vulned() {
        return create(BuffId.VULNED, BuffLifecycle.TIMED, BuffApplication.TARGET_BUFF_SET, 100, null, true);
    }

    public static BuffDefinition cursed() {
        return create(BuffId.CURSED, BuffLifecycle.TIMED, BuffApplication.TARGET_BUFF_SET, 100, null, true);
    }

    public static BuffDefinition smokeClouded() {
        return create(BuffId.SMOKECLOUDED, BuffLifecycle.TIMED, BuffApplication.TARGET_BUFF_SET, 200, null, true);
    }

    public static BuffDefinition livingDeath() {
        return create(BuffId.LIVINGDEATH, BuffLifecycle.TIMED, 50, 150, true);
    }

    public static BuffDefinition adrenalinePotion() {
        return create(BuffId.ADRENALINEPOTION, BuffLifecycle.INSTANT, null, 200, false);
    }

    public static BuffDefinition superAdrenalinePotion() {
        return create(BuffId.SUPERADRENALINEPOTION, BuffLifecycle.INSTANT, null, 200, false);
    }

    public static BuffDefinition adrenalineRenewal() {
        return create(BuffId.ADRENALINERENEWAL, BuffLifecycle.TIMED, 10, 200, false);
    }

    public static BuffDefinition imbueShadows() {
        return create(BuffId.IMBUESHADOWS, BuffLifecycle.TIMED, 50, 100, true);
    }

    public static BuffDefinition naturalInstinct() {
        return create(BuffId.NATURALINSTINCT, BuffLifecycle.TIMED, 34, 200, true);
    }

    public static BuffDefinition splitSoul() {
        return create(BuffId.SPLITSOUL, BuffLifecycle.TIMED, 25, null, true);
    }

    private static BuffDefinition create(BuffId id, BuffLifecycle lifecycle, Integer duration, Integer cooldown, boolean gcdConsuming) {
        return new BuffDefinition(
                id,
                BuffSource.USER_PLACED,
                lifecycle,
                BuffApplication.PLAYER_BUFF_SET,
                duration,
                cooldown,
                gcdConsuming,
                false
        );
    }

    private static BuffDefinition create(BuffId id, BuffLifecycle lifecycle, BuffApplication application, Integer duration, Integer cooldown, boolean gcdConsuming) {
        return new BuffDefinition(
                id,
                BuffSource.USER_PLACED,
                lifecycle,
                application,
                duration,
                cooldown,
                gcdConsuming,
                false
        );
    }
}
