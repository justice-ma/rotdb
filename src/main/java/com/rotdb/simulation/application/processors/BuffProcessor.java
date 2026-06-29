package com.rotdb.simulation.application.processors;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.BuffCooldownKey;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.provider.BuffProvider;
import com.rotdb.simulation.domain.resolvers.buff.BuffCooldownKeyResolver;

import java.util.Iterator;
import java.util.Map;

public class BuffProcessor {
    public static void initializeCooldown(BuffId buff, SimulationState state) {
        BuffCooldownKey buffKey = BuffCooldownKeyResolver.resolve(buff);
        BuffDefinition buffDefinition = BuffProvider.get(buff, BuffSource.USER_PLACED, state);
        if (buffDefinition.getCooldownTicks() != null && buffDefinition.getCooldownTicks() > 0) {
            state.getBuffCooldownMap().put(buffKey, buffDefinition.getCooldownTicks());
        }
    }

    public static void initializeBuffDuration(BuffId buff, SimulationState state) {
        BuffDefinition buffDefinition = BuffProvider.get(buff, BuffSource.USER_PLACED, state);
        if (buffDefinition.getDurationTicks() != null && buffDefinition.getDurationTicks() > 0) {
            state.getActiveBuffDurationMap().put(buff, buffDefinition.getDurationTicks());
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
        for (Map.Entry<BuffId, Integer> entry : state.getActiveBuffDurationMap().entrySet()) {
            entry.setValue(entry.getValue() - 1);
        }

        Iterator<Map.Entry<BuffId, Integer>> iterator = state.getActiveBuffDurationMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<BuffId, Integer> entry = iterator.next();
            if (entry.getValue() <= 0) {
                iterator.remove();
                state.getState().getBuffs().getBuffSet().remove(entry.getKey());
            }
        }
    }
}
