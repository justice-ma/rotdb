package com.rotdb.simulation.application.processors;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.ability.model.GeneratedBuffEffect;
import com.rotdb.shared.ability.model.GeneratedBuffTiming;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.simulation.domain.model.buff.AppliedBuffResult;
import com.rotdb.simulation.domain.model.buff.BuffCooldownKey;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
import com.rotdb.simulation.domain.model.context.*;
import com.rotdb.simulation.domain.provider.BuffProvider;
import com.rotdb.simulation.domain.resolvers.buff.*;
import com.rotdb.simulation.domain.resolvers.cooldown.AbilityCooldownKeyResolver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BuffProcessor {
    public static List<AppliedBuffResult> applyUserPlacedBuff(BuffPlacement buffPlacement, SimulationState state, TickSnapshot snapshot) {
        BuffDefinition buffDefinition = BuffProvider.get(buffPlacement.getBuffId(), BuffSource.USER_PLACED, state);
        List<AppliedBuffResult> appliedBuffResults = new ArrayList<>();
        appliedBuffResults.add(new AppliedBuffResult(
                buffDefinition,
                buffDefinition.getDurationTicks()
        ));

        processAdrenalineDelta(buffDefinition, state);
        generateWarnings(state, snapshot, buffDefinition);
        initializeCooldown(buffDefinition.getBuffId(), state, buffDefinition);
        processGlobalCooldown(buffDefinition, state, snapshot);
        switch (buffDefinition.getLifecycle()) {
            case TIMED -> {
                initializeBuffDuration(buffDefinition.getBuffId(), state, buffDefinition, null);
                addToBuffSet(state, buffDefinition);
            }
            case UNTIL_CONSUMED -> {
                addToBuffSet(state, buffDefinition);
            }
        }

        boolean vestmentsAlreadyApplied = state.getState().getBuffs().has(BuffId.VESTMENTSBLEED);

        for (GeneratedBuffEffect buff : BuffPlacementTriggeredEffectResolver.resolve(buffPlacement, state)) {
            appliedBuffResults.add(applyGeneratedBuff(buff, state));
        }

        if (vestmentsAlreadyApplied && buffDefinition.getBuffId() == BuffId.BERSERK) {
            state.setAdrenaline(state.getAdrenaline() + 20);
            state.getActiveBuffDurationMap().remove(BuffId.VESTMENTSBLEED);
            state.getState().getBuffs().getBuffSet().remove(BuffId.VESTMENTSBLEED);
        }

        return appliedBuffResults;
    }

    private static AppliedBuffResult applyGeneratedBuff(GeneratedBuffEffect buffEffect, SimulationState state) {
        BuffDefinition buffDefinition = BuffProvider.get(buffEffect.buffId(), BuffSource.ABILITY_GENERATED, state);
        AppliedBuffResult appliedBuffResult = new AppliedBuffResult(
                buffDefinition,
                buffEffect.durationOverrideTicks() == null ? buffDefinition.getDurationTicks() : buffEffect.durationOverrideTicks()
        );
        processAdrenalineDelta(buffDefinition, state);
        initializeCooldown(buffDefinition.getBuffId(), state, buffDefinition);
        switch (buffDefinition.getLifecycle()) {
            case TIMED -> {
                initializeBuffDuration(buffDefinition.getBuffId(), state, buffDefinition, buffEffect.durationOverrideTicks());
                addToBuffSet(state, buffDefinition);
            }
            case UNTIL_CONSUMED -> {
                addToBuffSet(state, buffDefinition);
            }
        }
        return appliedBuffResult;
    }

    public static List<AppliedBuffResult> applyAbilityGeneratedBuffsWithTiming(AbilityPlacement abilityPlacement, SimulationState state,
                                                                               GeneratedBuffTiming timing, boolean vestmentsBleedActiveAtTickStart) {
        AbilityContext ability = AbilityProvider.get(abilityPlacement.getPlacedAbility(), state.getState().getEquipment());
        List<AppliedBuffResult> buffs = new ArrayList<>();
        if (ability.getId() == AbilityId.BALANCEBYFORCE && state.getState().getBuffs().has(BuffId.BALANCEBYFORCE)) {
            return buffs;
        }
        for (GeneratedBuffEffect buff : ability.getGeneratedBuffEffects()) {
            if (buff.buffTiming() == timing) {
                buffs.add(applyGeneratedBuff(buff, state));
            }
        }

        for (GeneratedBuffEffect buff : AbilityGeneratedBuffEffectResolver.resolve(abilityPlacement, state, ability, timing, vestmentsBleedActiveAtTickStart)) {
            if (buff.buffTiming() == timing) {
                buffs.add(applyGeneratedBuff(buff, state));
            }
        }
        return buffs;
    }

    public static List<AppliedBuffResult> applyPreDamageReleaseBuffs(AbilityPlacement abilityPlacement, SimulationState state) {
        AbilityContext ability = AbilityProvider.get(abilityPlacement.getPlacedAbility(), state.getState().getEquipment());
        List<AppliedBuffResult> buffs = new ArrayList<>();
        BuffDefinition buffDefinition = BuffProvider.get(BuffId.BALANCEBYFORCE, BuffSource.ABILITY_GENERATED, state);
        if (ability.getId() == AbilityId.BALANCEBYFORCE) {
            buffs.add(new AppliedBuffResult(
                    buffDefinition,
                    null
            ));
            processAdrenalineDelta(buffDefinition, state);
            initializeCooldown(buffDefinition.getBuffId(), state, buffDefinition);
            switch (buffDefinition.getLifecycle()) {
                case TIMED -> {
                    initializeBuffDuration(buffDefinition.getBuffId(), state, buffDefinition, null);
                    addToBuffSet(state, buffDefinition);
                }
                case UNTIL_CONSUMED -> {
                    addToBuffSet(state, buffDefinition);
                }
            }
        }
        return buffs;
    }

    private static void initializeCooldown(BuffId buff, SimulationState state, BuffDefinition buffDefinition) {
        if (buffDefinition.getCooldownTicks() != null) {
            BuffCooldownKey buffKey = BuffCooldownKeyResolver.resolve(buff);
            if (buffDefinition.getCooldownTicks() != null && buffDefinition.getCooldownTicks() > 0) {
                state.getBuffCooldownMap().put(buffKey, buffDefinition.getCooldownTicks());
            }
        }
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

    public static void decayCooldown(SimulationState state) {
        for (Map.Entry<BuffCooldownKey, Integer> entry : state.getBuffCooldownMap().entrySet()) {
            entry.setValue(entry.getValue() - 1);
        }

        Iterator<Map.Entry<BuffCooldownKey, Integer>> iterator = state.getBuffCooldownMap().entrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getValue() <= 0) {
                iterator.remove();
            }
        }
    }

    public static void decayBuffDuration(SimulationState state) {
        for (Map.Entry<BuffId, ActiveBuffState> entry : state.getActiveBuffDurationMap().entrySet()) {
            entry.getValue().setDuration(entry.getValue().getDuration() - 1);
        }

        Iterator<Map.Entry<BuffId, ActiveBuffState>> iterator = state.getActiveBuffDurationMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<BuffId, ActiveBuffState> entry = iterator.next();
            if (entry.getValue().getDuration() <= 0) {
                iterator.remove();
                BuffDefinition buffDefinition = BuffProvider.get(entry.getKey(), entry.getValue().getSource(), state);
                clearStaleBuffState(state, buffDefinition, entry.getKey());
                for (BuffId expiredStack : StackResolver.resolveStacksRemovedWithExpiredBuff(entry.getKey())) {
                    if (expiredStack != null) {
                        state.getState().getBuffs().getBuffStacks().remove(expiredStack);
                        state.getActiveBuffDurationMap().remove(expiredStack);
                    }
                }
            }
        }
    }

    public static void removeBuffsConsumedByAbilityPlacement(AbilityPlacement abilityPlacement,
                                                             SimulationState simulationState) {
        if (abilityPlacement != null && abilityPlacement.getPlacedAbility().getTier() != AbilityTier.BASIC &&
                simulationState.getState().getBuffs().has(BuffId.FEASTINGSPORES) &&
                abilityPlacement.getPlacedAbility().getStyle() == CombatStyles.RANGED) {
            clearStaleBuffState(simulationState, BuffProvider.get(BuffId.FEASTINGSPORES, BuffSource.PROC,
                    simulationState), BuffId.FEASTINGSPORES);
        }
    }

    public static void removeBuffsConsumedByBuffPlacement(BuffPlacement buffPlacement,
                                                          SimulationState simulationState) {
        if (BuffConsumptionResolver.isFeastingSporesConsumedByBuffPlacement(buffPlacement.getBuffId(), simulationState)) {
            clearStaleBuffState(simulationState, BuffProvider.get(BuffId.FEASTINGSPORES, BuffSource.PROC,
                    simulationState), BuffId.FEASTINGSPORES);
        }
    }

    private static void addToBuffSet(SimulationState state, BuffDefinition buffDefinition) {
        switch (buffDefinition.getApplication()) {
            case PLAYER_BUFF_SET -> state.getState().getBuffs().getBuffSet().add(buffDefinition.getBuffId());
            case TARGET_BUFF_SET -> state.getState().getTarget().getDebuffs().add(buffDefinition.getBuffId());
        }
    }

    private static void clearStaleBuffState(SimulationState state, BuffDefinition buffDefinition, BuffId buffId) {
        switch (buffDefinition.getApplication()) {
            case PLAYER_BUFF_SET -> state.getState().getBuffs().getBuffSet().remove(buffId);
            case TARGET_BUFF_SET -> state.getState().getTarget().getDebuffs().remove(buffId);
            case PLAYER_STACKS -> state.getState().getBuffs().getBuffStacks().remove(buffId);
        }
    }

    private static void generateWarnings(SimulationState state, TickSnapshot tickSnapshot, BuffDefinition buffDefinition) {
        BuffId buffId = buffDefinition.getBuffId();
        BuffCooldownKey buffKey = BuffCooldownKeyResolver.resolve(buffId);
        if (state.getBuffCooldownMap().containsKey(buffKey)) {
            tickSnapshot.getWarnings().add(buffId.getLabel() + " may be on cooldown.");
        }

        if (state.getActiveBuffDurationMap().containsKey(buffId)) {
            tickSnapshot.getWarnings().add(buffId.getLabel() + " may still be active.");
        }
    }

    private static void processAdrenalineDelta(BuffDefinition buffDefinition, SimulationState state) {
        if (buffDefinition.getActivationAdrenalineDelta() != null) {
            if (!BuffConsumptionResolver.isFeastingSporesConsumedByBuffPlacement(buffDefinition.getBuffId(), state)) {
                state.setAdrenaline(state.getAdrenaline() + buffDefinition.getActivationAdrenalineDelta());
            }
        }
    }

    private static void processGlobalCooldown(BuffDefinition buffDefinition, SimulationState state, TickSnapshot snapshot) {
        if (buffDefinition.isGcdConsuming()) {
            if (state.getAbilityCooldownMap().containsKey(AbilityCooldownKeyResolver.resolveGlobalCooldown())) {
                snapshot.getWarnings().add("Global cooldown may not be ready.");
            }
            state.getAbilityCooldownMap().put(AbilityCooldownKeyResolver.resolveGlobalCooldown(), 3);
        }
    }
}
