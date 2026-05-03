package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.SimulationState;

public class AdrenalineBoundsResolver {
    public static void resolve(SimulationState simulationState) {
        EquipmentModel eq = simulationState.getState().getEquipment();
        BuffContext buff = simulationState.getState().getBuffs();

        double maximumDelta = 0;

        if (eq.getTotalVestmentsOfHavoc() >= 4 && eq.getMainhand().getClazz() == CombatStyles.MELEE) {
            maximumDelta += 20;
        }

        if (buff.has(BuffId.HEIGHTENEDSENSES)) {
            maximumDelta += 10;
        }

        simulationState.setMaximumAdrenaline(simulationState.getBaseMaximumAdrenaline() + maximumDelta);
    }
}
