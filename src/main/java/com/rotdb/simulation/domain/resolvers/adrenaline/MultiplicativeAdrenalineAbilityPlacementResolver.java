package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.SimulationState;

public class MultiplicativeAdrenalineAbilityPlacementResolver {
    public static double resolve(AbilityPlacement abilityPlacement, SimulationState simulationState) {
        BuffContext buff = simulationState.getState().getBuffs();
        AbilityContext ability = AbilityProvider.get(abilityPlacement.getPlacedAbility(), simulationState.getState().getEquipment());
        double multiplier = 1;
        if (buff.has(BuffId.METEORSTRIKE) && ability.getId().getStyle() == CombatStyles.MELEE &&
                ability.getId().getTier() == AbilityTier.BASIC) {
            multiplier *= 1.5;
        }

        if (buff.has(BuffId.NATURALINSTINCT) && ability.getAdrenaline() > 0) {
            multiplier *= 2;
        }

        return multiplier;
    }
}