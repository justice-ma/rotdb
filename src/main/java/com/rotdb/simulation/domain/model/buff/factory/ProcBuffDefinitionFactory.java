package com.rotdb.simulation.domain.model.buff.factory;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.enums.BuffApplication;
import com.rotdb.simulation.domain.model.buff.enums.BuffLifecycle;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;

public class ProcBuffDefinitionFactory {
    public static BuffDefinition frostblades() {
        return create(BuffId.FROSTBLADES, BuffLifecycle.TIMED, BuffApplication.PLAYER_BUFF_SET, 15, 0, null);
    }

    public static BuffDefinition rubyAurora() {
        return create(BuffId.RUBYAURORA, BuffLifecycle.TIMED, BuffApplication.PLAYER_BUFF_SET, 40, null, null);
    }

    public static BuffDefinition wenStacks() {
        return create(BuffId.WENSTACKS, BuffLifecycle.TIMED, BuffApplication.PLAYER_BUFF_SET, 15, null, null);
    }

    public static BuffDefinition impatientProc() {
        return create(BuffId.IMPATIENTPROC, BuffLifecycle.INSTANT, BuffApplication.NONE, null, null, null);
    }

    public static BuffDefinition relentlessProc() {
        return create(BuffId.RELENTLESSPROC, BuffLifecycle.INSTANT, BuffApplication.NONE, null, 100, null);
    }

    public static BuffDefinition asylumSurgeonsRingProc() {
        return create(BuffId.ASYLUMSURGEONSRINGPROC, BuffLifecycle.INSTANT, BuffApplication.NONE, null, 50, 15.0);
    }

    public static BuffDefinition ringOfDeathProc() {
        return create(BuffId.RINGOFDEATHPROC, BuffLifecycle.INSTANT, BuffApplication.NONE, null, null, 5.0);
    }

    public static BuffDefinition warpriestOfArmadylProc() {
        return create(BuffId.WARPRIESTOFARMADYLPROC, BuffLifecycle.INSTANT, BuffApplication.NONE, null, null, null);
    }

    private static BuffDefinition create(BuffId id, BuffLifecycle lifecycle, BuffApplication application, Integer duration, Integer cooldown, Double adrenalineDelta) {
        return new BuffDefinition(
                id,
                BuffSource.PROC,
                lifecycle,
                application,
                duration,
                cooldown,
                false,
                true,
                adrenalineDelta,
                null
        );
    }
}
