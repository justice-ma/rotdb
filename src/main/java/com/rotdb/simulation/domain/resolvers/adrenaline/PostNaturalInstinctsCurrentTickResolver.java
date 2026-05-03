package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.SimulationState;

public class PostNaturalInstinctsCurrentTickResolver {
    public static double resolve(SimulationState simulationState) {
        BuffContext buff = simulationState.getState().getBuffs();
        EquipmentModel eq = simulationState.getState().getEquipment();
        double adrenalineDelta = 0;
        if (buff.has(BuffId.METEORSTRIKE) && eq.getMainhand().getClazz() == CombatStyles.MELEE) {
            adrenalineDelta += 4.5;
        }

        if (buff.has(BuffId.ADRENALINEPOTION)) {
            adrenalineDelta += 25;
        }

        if (buff.has(BuffId.SUPERADRENALINEPOTION)) {
            adrenalineDelta += 30;
        }

        if (buff.has(BuffId.ADRENALINERENEWAL)) {
            adrenalineDelta += 4;
        }

        if (buff.has(BuffId.VESTMENTSBLEED)) {
            adrenalineDelta += 0.5;
        }
        return adrenalineDelta;
    }
}
