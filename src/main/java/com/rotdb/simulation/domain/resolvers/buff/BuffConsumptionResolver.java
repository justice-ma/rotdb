package com.rotdb.simulation.domain.resolvers.buff;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.simulation.domain.model.context.SimulationState;

public class BuffConsumptionResolver {
    public static boolean isFeastingSporesConsumedByBuffPlacement(BuffId buffId, SimulationState state) {
        return (buffId == BuffId.DEATHSWIFTNESS || buffId == BuffId.SPLITSOUL || buffId == BuffId.IMBUESHADOWS) &&
                state.getState().getBuffs().has(BuffId.FEASTINGSPORES) &&
                state.getState().getEquipment().getCombatStyle() == CombatStyles.RANGED;
    }
}
