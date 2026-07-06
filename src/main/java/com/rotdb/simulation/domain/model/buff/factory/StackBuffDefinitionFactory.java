package com.rotdb.simulation.domain.model.buff.factory;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.enums.BuffApplication;
import com.rotdb.simulation.domain.model.buff.enums.BuffLifecycle;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;

public class StackBuffDefinitionFactory {
    public static BuffDefinition wenArrowStacks() {
        return create(BuffId.WENARROWSTACKS, BuffLifecycle.TIMED, BuffApplication.PLAYER_STACKS, 50, null, null);
    }

    public static BuffDefinition primordialIceStacks() {
        return create(BuffId.PRIMORDIALICESTACKS, BuffLifecycle.UNTIL_CONSUMED, BuffApplication.PLAYER_STACKS, null, null, null);
    }


    public static BuffDefinition gravitateStacks() {
        return create(BuffId.GRAVITATESTACKS, BuffLifecycle.STACK, BuffApplication.PLAYER_STACKS, null, null, null);
    }

    public static BuffDefinition perfectEquilibriumStacks() {
        return create(BuffId.PERFECTEQUILIBRIUMSTACKS, BuffLifecycle.STACK, BuffApplication.PLAYER_STACKS, null, null, null);
    }

    public static BuffDefinition deathsporeStacks() {
        return create(BuffId.DEATHSPORESTACKS, BuffLifecycle.STACK, BuffApplication.PLAYER_STACKS, 52, 52, null);
    }

    public static BuffDefinition titheStacks() {
        return create(BuffId.TITHESTACKS, BuffLifecycle.STACK, BuffApplication.PLAYER_STACKS, 33, null, null);
    }

    private static BuffDefinition create(BuffId id, BuffLifecycle lifecycle, BuffApplication application, Integer duration, Integer cooldown, Double adrenalineDelta) {
        return new BuffDefinition(
                id,
                BuffSource.STACK,
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
