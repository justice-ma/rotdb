package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.calculation.domain.model.HitResult;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.SimulationState;

public class AdrenalineBuffUltimateAbilityPlacementResolver {
    public static double resolve(AbilityPlacement abilityPlacement, SimulationState simulationState, DamageResult damageResult) {
        double adrenalineDelta = 0;
        BuffContext buff = simulationState.getState().getBuffs();
        AbilityContext ability = AbilityProvider.get(abilityPlacement.getPlacedAbility(), simulationState.getState().getEquipment());

        if (ability.isChannel()) return adrenalineDelta;
        if (buff.has(BuffId.IMBUESHADOWS) && ability.getCombatStyle() == CombatStyles.RANGED) {
            for (HitResult hit : damageResult.getHit()) {
                if (!hit.isDot()) adrenalineDelta += 5;
            }
        }
        if (buff.has(BuffId.TSUNAMI) && ability.getCombatStyle() == CombatStyles.MAGIC) {
            for (HitResult hit : damageResult.getHit()) {
                if (!hit.isDot()) adrenalineDelta += 8 * hit.getCritChance();
            }
        }
        return adrenalineDelta;
    }
}