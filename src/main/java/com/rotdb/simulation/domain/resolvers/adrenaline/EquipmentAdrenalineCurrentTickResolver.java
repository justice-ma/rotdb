package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.SimulationState;

public class EquipmentAdrenalineCurrentTickResolver {
    public static double resolve(SimulationState simulationState) {
        BuffContext buff = simulationState.getState().getBuffs();
        EquipmentModel eq = simulationState.getState().getEquipment();

        double adrenalineDelta = 0;
        if (buff.has(BuffId.RINGOFDEATHPROC) && eq.getRing().getEffect().contains(Effect.RINGOFDEATH)) {
            adrenalineDelta += 5;
        }
        return adrenalineDelta;
    }
}