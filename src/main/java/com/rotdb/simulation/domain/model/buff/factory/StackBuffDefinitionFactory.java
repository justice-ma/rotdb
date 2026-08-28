package com.rotdb.simulation.domain.model.buff.factory;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.enums.BuffApplication;
import com.rotdb.simulation.domain.model.buff.enums.BuffDamageEvaluationTiming;
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

    public static BuffDefinition inciteFearStacks() {
        return create(BuffId.INCITEFEARSTACKS, BuffLifecycle.STACK, BuffApplication.PLAYER_STACKS, 33, null, null);
    }

    public static BuffDefinition essenceCorruptionStacks() {
        return create(BuffId.ESSENCECORRUPTIONSTACKS, BuffLifecycle.STACK, BuffApplication.PLAYER_STACKS, 50, null,
                null, BuffDamageEvaluationTiming.ON_HIT);
    }

    public static BuffDefinition soulStacks() {
        return create(BuffId.SOULSTACKS, BuffLifecycle.STACK, BuffApplication.PLAYER_STACKS, null, null, null);
    }

    public static BuffDefinition soulReave() {
        return create(BuffId.SOULREAVE, BuffLifecycle.STACK, BuffApplication.PLAYER_STACKS, null, null, null);
    }

    public static BuffDefinition deathSpark() {
        return create(BuffId.DEATHSPARK, BuffLifecycle.STACK, BuffApplication.PLAYER_STACKS, null, null, null);
    }

    public static BuffDefinition necrosis() {
        return create(BuffId.NECROSIS, BuffLifecycle.STACK, BuffApplication.PLAYER_STACKS, null, null, null);
    }

    public static BuffDefinition rage() {
        return create(BuffId.RAGE, BuffLifecycle.STACK, BuffApplication.PLAYER_STACKS, null, null, null);
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

    private static BuffDefinition create(BuffId id, BuffLifecycle lifecycle, BuffApplication application,
                                         Integer duration, Integer cooldown, Double adrenalineDelta,
                                         BuffDamageEvaluationTiming buffDamageEvaluationTiming) {
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
                null,
                buffDamageEvaluationTiming
        );
    }
}
