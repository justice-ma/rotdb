package com.rotdb.simulation.application.processors;

import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.StackEffect;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.ActiveBuffState;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.model.context.TimelineHit;
import com.rotdb.simulation.domain.provider.BuffProvider;
import com.rotdb.simulation.domain.resolvers.buff.StackResolver;

import java.util.ArrayList;
import java.util.List;

public class StackProcessor {
    public static List<BuffDefinition> applyOnReleaseStacks(AbilityPlacement abilityPlacement, SimulationState state) {
        List<BuffDefinition> buffs = new ArrayList<>();
        AbilityContext ability = AbilityProvider.get(abilityPlacement.getPlacedAbility(), state.getState().getEquipment());
        for (StackEffect stackEffect : StackResolver.resolveOnRelease(state, ability)) {
            if (stackEffect.getProcChance() == null || ProcProcessor.determineProc(state.getSimulationConfig().getProcMode(), stackEffect.getProcChance(), state, stackEffect.getBuffId())) {
                buffs.add(processProc(stackEffect, state));
            }
        }
        return buffs;
    }

    public static List<BuffDefinition> applyOnHitStacks(AbilityPlacement abilityPlacement, SimulationState state, TimelineHit hit) {
        List<BuffDefinition> buffs = new ArrayList<>();
        AbilityContext ability = AbilityProvider.get(abilityPlacement.getPlacedAbility(), state.getState().getEquipment());
        for (StackEffect stackEffect : StackResolver.resolveOnHit(state, ability, hit)) {
            if (stackEffect.getProcChance() == null || ProcProcessor.determineProc(state.getSimulationConfig().getProcMode(), stackEffect.getProcChance(), state, stackEffect.getBuffId())) {
                buffs.add(processProc(stackEffect, state));
            }
        }
        return buffs;
    }

    private static BuffDefinition processProc(StackEffect stackEffect, SimulationState state) {
        BuffDefinition buff = BuffProvider.get(stackEffect.getBuffId(), stackEffect.getBuffSource(), state);
        applyStackDelta(stackEffect.getBuffId(), state, stackEffect.getStackDelta());
        initializeDuration(stackEffect.getBuffId(), state, buff, stackEffect.getDurationOverride());
        return buff;
    }

    private static void initializeDuration(BuffId buff, SimulationState state, BuffDefinition buffDefinition, Integer durationOverrideTicks) {
        Integer durationTicks = durationOverrideTicks == null ? buffDefinition.getDurationTicks() : durationOverrideTicks;
        ActiveBuffState activeBuffState = new ActiveBuffState(
                buff,
                buffDefinition.getSource(),
                durationTicks == null ? 0 : durationTicks
        );
        if (durationTicks != null && durationTicks > 0) {
            state.getActiveBuffDurationMap().put(buff, activeBuffState);
        }
    }

    private static void applyStackDelta(BuffId buff, SimulationState state, Integer stackDelta) {
        if (state.getState().getBuffs().has(buff)) {
            state.getState().getBuffs().getBuffStacks().put(buff, Math.min(stackDelta + state.getState().getBuffs().getBuffStacks().get(buff), buff.getMaximumStacks()));
        } else {
            state.getState().getBuffs().getBuffStacks().put(buff, Math.min(stackDelta, buff.getMaximumStacks()));
        }
    }
}
