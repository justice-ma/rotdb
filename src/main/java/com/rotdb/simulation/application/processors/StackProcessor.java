package com.rotdb.simulation.application.processors;

import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.*;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.ActiveBuffState;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.model.context.TimelineHit;
import com.rotdb.simulation.domain.provider.BuffProvider;
import com.rotdb.simulation.domain.resolvers.buff.BuffCooldownKeyResolver;
import com.rotdb.simulation.domain.resolvers.buff.StackResolver;

import java.util.ArrayList;
import java.util.List;

public class StackProcessor {
    public static List<BuffDefinition> applyOnReleaseStacks(AbilityPlacement abilityPlacement, SimulationState state) {
        List<BuffDefinition> buffs = new ArrayList<>();
        AbilityContext ability = AbilityProvider.get(abilityPlacement.getPlacedAbility(), state.getState().getEquipment());
        for (StackEffect stackEffect : StackResolver.resolveOnRelease(state, ability)) {
            if (ProcProcessor.determineProc(state.getSimulationConfig().getProcMode(), stackEffect.getProcChance(), state, stackEffect.getBuffId())) {
                buffs.add(processProc(stackEffect, state));
            }
        }
        return buffs;
    }

    public static List<BuffDefinition> applyOnHitStacks(AbilityPlacement abilityPlacement, SimulationState state,
                                                        TimelineHit hit) {
        List<BuffDefinition> buffs = new ArrayList<>();
        AbilityContext ability = AbilityProvider.get(abilityPlacement.getPlacedAbility(), state.getState().getEquipment());
        for (StackEffect stackEffect : StackResolver.resolveOnHit(state, ability, hit)) {
            if (ProcProcessor.determineProc(state.getSimulationConfig().getProcMode(), stackEffect.getProcChance(), state, stackEffect.getBuffId())) {
                buffs.add(processProc(stackEffect, state));
            }
        }
        return buffs;
    }

    public static List<ConsumableStackResult> prepareConsumableStacksForDamage(AbilityPlacement abilityPlacement,
                                                                               SimulationState state) {
        List<ConsumableStackResult> buffResults = new ArrayList<>();
        for (ConsumableStackResult stackResult : StackResolver.resolveConsumableStackPreparation(abilityPlacement,
                state)) {
            if (stackResult.appliedBuffResult() != null) {
                BuffId appliedBuff = stackResult.appliedBuffResult().buffDefinition().getBuffId();
                initializeBuffDuration(appliedBuff, state, BuffProvider.get(appliedBuff,
                        BuffSource.PROC,
                        state), null);
                initializeBuffCooldown(appliedBuff, state, BuffProvider.get(appliedBuff, BuffSource.PROC, state));
                addToBuffSet(state, BuffProvider.get(appliedBuff, BuffSource.PROC, state));
            }
            buffResults.add(stackResult);
        }
        return buffResults;
    }

    public static void consumeStacks(SimulationState state, ConsumableStackResult consumableStackResult) {
        state.getState().getBuffs().getBuffStacks().merge(consumableStackResult.consumedStackId(),
                -consumableStackResult.consumedAmount(), Integer::sum);
        if (state.getState().getBuffs().stacks(consumableStackResult.consumedStackId()) <= consumableStackResult.consumedStackId().getMinimumStacks()) {
            state.getActiveBuffDurationMap().remove(consumableStackResult.consumedStackId());
            state.getState().getBuffs().getBuffStacks().remove(consumableStackResult.consumedStackId());
        }
    }

    private static BuffDefinition processProc(StackEffect stackEffect, SimulationState state) {
        BuffDefinition buff = BuffProvider.get(stackEffect.getBuffId(), stackEffect.getBuffSource(), state);
        applyStackDelta(stackEffect.getBuffId(), state, stackEffect.getStackDelta());
        initializeStackDuration(stackEffect.getBuffId(), state, buff, stackEffect.getDurationOverride());
        for (AppliedBuffResult appliedBuffResult : StackResolver.resolveStackTriggeredBuffs(stackEffect, state)) {
            initializeBuffDuration(appliedBuffResult.buffDefinition().getBuffId(), state,
                    appliedBuffResult.buffDefinition(), null);
            initializeBuffCooldown(appliedBuffResult.buffDefinition().getBuffId(), state,
                    appliedBuffResult.buffDefinition());
            addToBuffSet(state, appliedBuffResult.buffDefinition());
        }
        return buff;
    }

    private static void initializeBuffDuration(BuffId buff, SimulationState state, BuffDefinition buffDefinition, Integer durationOverrideTicks) {
        Integer durationTicks = durationOverrideTicks == null ? buffDefinition.getDurationTicks() : durationOverrideTicks;
        ActiveBuffState activeBuffState = new ActiveBuffState(
                buff,
                buffDefinition.getSource(),
                durationTicks
        );
        if (durationTicks != null && durationTicks > 0) {
            state.getActiveBuffDurationMap().put(buff, activeBuffState);
        }
    }

    private static void initializeBuffCooldown(BuffId buff, SimulationState state, BuffDefinition buffDefinition) {
        if (buffDefinition.getCooldownTicks() != null) {
            BuffCooldownKey buffKey = BuffCooldownKeyResolver.resolve(buff);
            if (buffDefinition.getCooldownTicks() != null && buffDefinition.getCooldownTicks() > 0) {
                state.getBuffCooldownMap().put(buffKey, buffDefinition.getCooldownTicks());
            }
        }
    }

    private static void addToBuffSet(SimulationState state, BuffDefinition buffDefinition) {
        switch (buffDefinition.getApplication()) {
            case PLAYER_BUFF_SET -> state.getState().getBuffs().getBuffSet().add(buffDefinition.getBuffId());
            case TARGET_BUFF_SET -> state.getState().getTarget().getDebuffs().add(buffDefinition.getBuffId());
        }
    }

    private static void initializeStackDuration(BuffId buff, SimulationState state, BuffDefinition buffDefinition, Integer durationOverrideTicks) {
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
