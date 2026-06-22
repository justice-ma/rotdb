package com.rotdb.simulation.application.processors;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.BuffCooldownKey;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.provider.BuffProvider;
import com.rotdb.simulation.domain.resolvers.buff.BuffCooldownKeyResolver;

import java.util.Iterator;
import java.util.Map;

public class BuffProcessor {
    public static void initializeCooldown(BuffId buff, SimulationState state) {
        BuffCooldownKey buffKey = BuffCooldownKeyResolver.resolve(buff);
        BuffDefinition buffDefinition = BuffProvider.get(buff, state);
        state.getBuffCooldownMap().put(buffKey, buffDefinition.getCooldownTicks());
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
}
