package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.SimulationState;

public class MultiplicativeAdrenalinePreHitTickResolver {
    public static double resolve(SimulationState simulationState) {
        BuffContext buff = simulationState.getState().getBuffs();
        double multiplier = 1;
        if (buff.has(BuffId.NATURALINSTINCT)) {
            multiplier *= 2;
        }
        return multiplier;
    }
}
