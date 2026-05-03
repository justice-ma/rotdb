package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Perks;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.SimulationState;

import java.util.ArrayList;
import java.util.List;

public class PerkAdrenalineAbilityPlacementResolver {
    public static double resolve(AbilityPlacement abilityPlacement, SimulationState simulationState) {
        AbilityContext ability = AbilityProvider.get(abilityPlacement.getPlacedAbility(), simulationState.getState().getEquipment());
        PerkContext perks = simulationState.getState().getPerk();
        BuffContext buff = simulationState.getState().getBuffs();

        double adrenalineDelta = 0;

        if (perks.has(Perks.IMPATIENT) && ability.getId().getTier() == AbilityTier.BASIC) {
            if (buff.has(BuffId.IMPATIENTPROC)) {
                adrenalineDelta += 3;
            } else {
                adrenalineDelta += (perks.rank(Perks.IMPATIENT) * 0.09) * 3;
            }
        }

        if (perks.has(Perks.RELENTLESS) && ability.getAdrenaline() < 0 && buff.has(BuffId.RELENTLESSPROC)) {
            adrenalineDelta -= ability.getAdrenaline();
        }

        List<AbilityId> invigoratingApplicable = new ArrayList<>(
                List.of(AbilityId.MELEEAUTO, AbilityId.RANGEDAUTO, AbilityId.MAGICAUTO, AbilityId.NECROMANCYAUTO));

        if (perks.has(Perks.INVIGORATING) && invigoratingApplicable.contains(ability.getId())) {
            double temp = ability.getAdrenaline();
            temp += adrenalineDelta;
            temp += buff.has(BuffId.FURYOFTHESMALL) ? 1 : 0;
            adrenalineDelta += temp * (perks.rank(Perks.INVIGORATING) * 0.05);
        }

        return adrenalineDelta;
    }
}
