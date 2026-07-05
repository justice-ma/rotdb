package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.provider.BuffProvider;

public class PostNaturalInstinctsAbilityPlacementResolver {
    public static double resolve(AbilityPlacement abilityPlacement, SimulationState simulationState) {
        AbilityContext ability = AbilityProvider.get(abilityPlacement.getPlacedAbility(), simulationState.getState().getEquipment());
        BuffContext buff = simulationState.getState().getBuffs();
        EquipmentModel eq = simulationState.getState().getEquipment();
        double adrenalineDelta = 0;

        if (buff.has(BuffId.CONSERVATIONOFENERGY) && ability.getId().getTier() == AbilityTier.ULTIMATE) {
            adrenalineDelta += 10;
        }

        if ((buff.has(BuffId.RINGOFVIGOUR) || eq.getRing().getEffect().contains(Effect.RINGOFVIGOUR))) {
            if (ability.getId().getTier() == AbilityTier.ULTIMATE) {
                adrenalineDelta += 10;
            } else if (ability.getId().getTier() == AbilityTier.SPECIAL) {
                double initialAdrenaline = ability.getAdrenaline();
                adrenalineDelta -= initialAdrenaline * 0.1;
            }
        }

        if (buff.has(BuffId.ASYLUMSURGEONSRINGPROC) && ability.getAdrenaline() < 0
                && eq.getRing().getEffect().contains(Effect.ASYLUMSURGEONSRING)) {
            adrenalineDelta += BuffProvider.get(BuffId.ASYLUMSURGEONSRINGPROC, BuffSource.PROC, simulationState).getActivationAdrenalineDelta();
        }

        if (buff.has(BuffId.PRIMORDIALICESTACKS) && buff.stacks(BuffId.PRIMORDIALICESTACKS) > 0 && abilityPlacement.getPlacedAbility() == AbilityId.ICYTEMPEST) {
            adrenalineDelta += Math.min(30, buff.stacks(BuffId.PRIMORDIALICESTACKS) * 12);
        }

        return adrenalineDelta;
    }
}
