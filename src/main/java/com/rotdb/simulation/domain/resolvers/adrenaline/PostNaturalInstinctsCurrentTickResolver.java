package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.provider.BuffProvider;

public class PostNaturalInstinctsCurrentTickResolver {
    public static double resolve(SimulationState simulationState) {
        BuffContext buff = simulationState.getState().getBuffs();
        EquipmentModel eq = simulationState.getState().getEquipment();
        double adrenalineDelta = 0;
        if (buff.has(BuffId.METEORSTRIKE) && eq.getMainhand().getClazz() == CombatStyles.MELEE) {
            adrenalineDelta += 4.5;
        }

        if (buff.has(BuffId.ADRENALINERENEWAL)) {
            adrenalineDelta += BuffProvider.get(BuffId.ADRENALINERENEWAL, BuffSource.USER_PLACED, simulationState).getTickAdrenalineDelta();
        }

        if (buff.has(BuffId.VESTMENTSBLEED)) {
            adrenalineDelta += BuffProvider.get(BuffId.VESTMENTSBLEED, BuffSource.ABILITY_GENERATED, simulationState).getTickAdrenalineDelta();
        }
        return adrenalineDelta;
    }
}
