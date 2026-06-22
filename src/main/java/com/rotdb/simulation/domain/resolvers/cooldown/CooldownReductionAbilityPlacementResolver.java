package com.rotdb.simulation.domain.resolvers.cooldown;

import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.model.cooldown.AbilityCooldownKey;

import java.util.Map;

public class CooldownReductionAbilityPlacementResolver {
    public static void resolve(SimulationState simulationState, AbilityPlacement abilityPlacement, DamageResult damageResult) {
        EquipmentModel equipment = simulationState.getState().getEquipment();
        BuffContext buff = simulationState.getState().getBuffs();
        AbilityContext ability = AbilityProvider.get(abilityPlacement.getPlacedAbility(), equipment);
        Map<AbilityCooldownKey, Integer> cooldownMap = simulationState.getAbilityCooldownMap();
        AbilityCooldownKey key = AbilityCooldownKeyResolver.resolve(abilityPlacement.getPlacedAbility());
        AbilityCooldownKey snipeKey = AbilityCooldownKeyResolver.resolve(AbilityId.SNIPE);

        if (buff.has(BuffId.WARPRIESTOFARMADYLPROC) && ability.getCooldownTicks() > 50) {
            int delta = (int) (ability.getCooldownTicks() * 0.15);
            cooldownMap.put(key, cooldownMap.get(key) - delta);
        }

        if (ability.getId() == AbilityId.PIERCINGSHOT && cooldownMap.containsKey(snipeKey)) {
            int delta = equipment.getBoots().getEffect().contains(Effect.FLEETINGBOOTS) ? 6 : 4;
            cooldownMap.put(snipeKey, cooldownMap.get(snipeKey) - delta * damageResult.getHit().size());
            if (cooldownMap.get(snipeKey) <= 0) {
                cooldownMap.remove(snipeKey);
            }
        }

        if (ability.getId() == AbilityId.RANGEDAUTO && cooldownMap.containsKey(snipeKey)
                && equipment.getBoots().getEffect().contains(Effect.FLEETINGBOOTS)) {
            int delta = 6;
            cooldownMap.put(snipeKey, cooldownMap.get(snipeKey) - delta * damageResult.getHit().size());
            if (cooldownMap.get(snipeKey) <= 0) {
                cooldownMap.remove(snipeKey);
            }
        }

        if (buff.has(BuffId.BERSERK) && key.id().equals("OVERPOWER")) {
            cooldownMap.put(key, 15);
        }

        if (buff.has(BuffId.LIVINGDEATH) && key.id().equals("DEATH_SKULLS")) {
            cooldownMap.put(key, 17);
        }
    }
}
