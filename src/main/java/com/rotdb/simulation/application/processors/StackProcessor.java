package com.rotdb.simulation.application.processors;

import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.*;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
import com.rotdb.simulation.domain.model.context.*;
import com.rotdb.simulation.domain.provider.BuffProvider;
import com.rotdb.simulation.domain.resolvers.buff.BuffCooldownKeyResolver;
import com.rotdb.simulation.domain.resolvers.buff.StackResolver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    public static List<BuffDefinition> applyOnReleaseResolvedHitStacks(AbilityPlacement abilityPlacement,
                                                                       SimulationState state) {
        List<BuffDefinition> buffs = new ArrayList<>();
        AbilityContext ability = AbilityProvider.get(abilityPlacement.getPlacedAbility(), state.getState().getEquipment());
        for (AbilityHitsContext hit : ability.getHits()) {
            for (StackEffect stackEffect : StackResolver.resolveOnHit(state, ability, hit)) {
                if (ProcProcessor.determineProc(state.getSimulationConfig().getProcMode(), stackEffect.getProcChance(),
                        state, stackEffect.getBuffId())) {
                    buffs.add(processProc(stackEffect, state));
                }
            }
        }
        return buffs;
    }

    public static List<BuffDefinition> applyOnReleaseResolvedDamageStacks(DamageResult damageResult,
                                                                          SimulationState state) {
        List<BuffDefinition> buffs = new ArrayList<>();
        for (StackEffect stackEffect : StackResolver.resolveOnReleaseResolvedDamage(state, damageResult)) {
            if (ProcProcessor.determineProc(state.getSimulationConfig().getProcMode(), stackEffect.getProcChance(),
                    state, stackEffect.getBuffId())) {
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
                if (stackResult.appliedBuffResult().stackDelta() == null) {
                    initializeBuffDuration(appliedBuff, state, BuffProvider.get(appliedBuff,
                            BuffSource.PROC,
                            state), null);
                    initializeBuffCooldown(appliedBuff, state, BuffProvider.get(appliedBuff, BuffSource.PROC, state));
                    addToBuffSet(state, BuffProvider.get(appliedBuff, BuffSource.PROC, state));
                } else if (stackResult.appliedBuffResult().stackDelta() > 0) {
                    if (stackResult.appliedBuffResult().buffDefinition().getDurationTicks() != null) {
                        initializeBuffDuration(appliedBuff, state, BuffProvider.get(appliedBuff,
                                BuffSource.STACK,
                                state), null);
                    }
                    initializeBuffCooldown(appliedBuff, state, BuffProvider.get(appliedBuff, BuffSource.STACK, state));
                    addToBuffStacks(state, BuffProvider.get(appliedBuff, BuffSource.STACK, state), stackResult.appliedBuffResult());
                }
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

        BuffDefinition buffDefinition = BuffProvider.get(consumableStackResult.consumedStackId(), BuffSource.STACK,
                state);
        if (buffDefinition.getCooldownTicks() != null) {
            initializeBuffCooldown(consumableStackResult.consumedStackId(), state, buffDefinition);
        }
    }

    public static List<ConsumableStackResult> applyEndOfTickStackTriggers(SimulationState state) {
        List<ConsumableStackResult> buffResults = new ArrayList<>();
        for (ConsumableStackResult consumableStackResult : StackResolver.resolveEndOfTickStackTriggers(state)) {
            if (consumableStackResult.appliedBuffResult() != null) {
                initializeBuffDuration(consumableStackResult.appliedBuffResult().buffDefinition().getBuffId(), state,
                        consumableStackResult.appliedBuffResult().buffDefinition(),
                        consumableStackResult.appliedBuffResult().resolvedDurationTicks());
                initializeBuffCooldown(consumableStackResult.appliedBuffResult().buffDefinition().getBuffId(), state,
                        consumableStackResult.appliedBuffResult().buffDefinition());
                addToBuffSet(state, consumableStackResult.appliedBuffResult().buffDefinition());
            }
            buffResults.add(consumableStackResult);
        }
        return buffResults;
    }

    public static void removeStaleStacks(SimulationState state) {
        Iterator<Map.Entry<BuffId, Integer>> iterator = state.getState().getBuffs().getBuffStacks().entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getValue() <= 0) {
                iterator.remove();
            }
        }
    }

    public static List<TriggeredHitResult> prepareStackGeneratedAbilities(SimulationState state, AbilityPlacement parentAbility) {
        List<TriggeredHitResult> triggeredHitResults = new ArrayList<>();
        triggeredHitResults.addAll(StackResolver.resolveStackGeneratedAbilities(state, parentAbility, null));
        return triggeredHitResults;
    }

    public static List<TriggeredHitResult> prepareStackGeneratedAbilities(SimulationState state,
                                                                          AbilityPlacement parentAbility,
                                                                          Integer triggerTick) {
        List<TriggeredHitResult> triggeredHitResults = new ArrayList<>();
        triggeredHitResults.addAll(StackResolver.resolveStackGeneratedAbilities(state, parentAbility, triggerTick));
        return triggeredHitResults;
    }

    private static BuffDefinition processProc(StackEffect stackEffect, SimulationState state) {
        BuffDefinition buff = BuffProvider.get(stackEffect.getBuffId(), stackEffect.getBuffSource(), state);
        applyStackDelta(stackEffect.getBuffId(), state, stackEffect.getStackDelta(), stackEffect);
        initializeStackDuration(stackEffect.getBuffId(), state, buff, stackEffect.getDurationOverride());
        for (AppliedBuffResult appliedBuffResult : StackResolver.resolveStackTriggeredBuffs(stackEffect, state)) {
            initializeBuffDuration(appliedBuffResult.buffDefinition().getBuffId(), state,
                    appliedBuffResult.buffDefinition(), appliedBuffResult.resolvedDurationTicks());
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

    private static void addToBuffStacks(SimulationState state, BuffDefinition buffDefinition, AppliedBuffResult appliedBuffResult) {
        switch (buffDefinition.getApplication()) {
            case PLAYER_STACKS -> state.getState().getBuffs().getBuffStacks().merge(buffDefinition.getBuffId(), +appliedBuffResult.stackDelta(), Integer::sum);
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

    private static void applyStackDelta(BuffId buff, SimulationState state, Integer stackDelta,
                                        StackEffect stackEffect) {
        if (stackEffect.getMaximumStacksOverride() == null) {
            stackEffect.setMaximumStacksOverride(buff.getMaximumStacks());
        }
        switch (stackEffect.getStackClampingBehaviour()) {
            case CLAMP -> {
                if (state.getState().getBuffs().has(buff)) {
                    state.getState().getBuffs().getBuffStacks().put(buff, Math.min(stackDelta + state.getState().getBuffs().getBuffStacks().get(buff), stackEffect.getMaximumStacksOverride()));
                } else {
                    state.getState().getBuffs().getBuffStacks().put(buff, Math.min(stackDelta, stackEffect.getMaximumStacksOverride()));
                }
            }
            case ROLL_OVER -> {
                if (state.getState().getBuffs().has(buff)) {
                    state.getState().getBuffs().getBuffStacks().put(
                            buff,
                            (state.getState().getBuffs().stacks(buff) + stackDelta) % (stackEffect.getMaximumStacksOverride() + 1));
                } else {
                    state.getState().getBuffs().getBuffStacks().put(buff, stackDelta % (stackEffect.getMaximumStacksOverride() + 1));
                }
            }
        }
    }
}
