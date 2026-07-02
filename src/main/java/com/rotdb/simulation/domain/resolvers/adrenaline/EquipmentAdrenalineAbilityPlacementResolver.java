package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.SimulationState;

public class EquipmentAdrenalineAbilityPlacementResolver {
    public static double resolve(SimulationState simulationState, AbilityPlacement abilityPlacement) {
        EquipmentModel eq = simulationState.getState().getEquipment();
        AbilityContext ability = AbilityProvider.get(abilityPlacement.getPlacedAbility(), eq);
        BuffContext buff = simulationState.getState().getBuffs();
        double adrenalineDelta = 0;

        if (buff.has(BuffId.VESTMENTSBLEED) && eq.getTotalVestmentsOfHavoc() >= 2 &&
                ability.getId().getTier() == AbilityTier.ULTIMATE && ability.getId().getStyle() == CombatStyles.MELEE) {
            adrenalineDelta += 20;
            simulationState.getState().getBuffs().getBuffSet().remove(BuffId.VESTMENTSBLEED);
            simulationState.getActiveBuffDurationMap().remove(BuffId.VESTMENTSBLEED);
        }

        if (buff.has(BuffId.BLEEDS) && ability.getId().getStyle() == CombatStyles.MELEE &&
                eq.getHead().getEffect().contains(Effect.JAWSOFTHEABYSS)) {
            adrenalineDelta += (2 * buff.stacks(BuffId.BLEEDS));
        }

        if (buff.has(BuffId.FURYOFTHESMALL) && ability.getId().getTier() == AbilityTier.BASIC) {
            adrenalineDelta += 1;
        }
        return adrenalineDelta;
    }
}
