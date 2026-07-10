package com.rotdb.simulation.application.processors;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.model.context.TickSnapshot;

public class AbilityRequirementProcessor {
    public static void generateAbilityRequirementWarnings(TickSnapshot tickSnapshot, SimulationState simulationState,
                                                          AbilityPlacement abilityPlacement) {
        if ((!simulationState.getState().getBuffs().has(BuffId.SOULSTACKS) ||
                simulationState.getState().getBuffs().stacks(BuffId.SOULSTACKS) < 2)
                && abilityPlacement.getPlacedAbility() == AbilityId.VOLLEYOFSOULS) {
            tickSnapshot.getWarnings().add("Insufficient amount of: " + BuffId.SOULSTACKS.getLabel());
        }
    }
}

