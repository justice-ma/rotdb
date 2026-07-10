package com.rotdb.simulation.domain.resolvers.buff;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.enums.BuffDamageEvaluationTiming;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
import com.rotdb.simulation.domain.model.context.RotationCombatState;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.provider.BuffProvider;

import java.util.HashMap;
import java.util.Map;

// TODO: Must eventually refactor the existing CalculationContext to support base damage on release plus buff
//  modifiers based on current combat state. E.g., combust with essence corruption stacks: Additive damage should be
//  based on current combat state, base damage should be based on release combat state.

public class HitRecalculationPolicyResolver {
    public static boolean requiresRecalculation(AbilityContext abilityContext, SimulationState simulationState) {
        for (Map.Entry<BuffId, Integer> entry : simulationState.getState().getBuffs().getBuffStacks().entrySet()) {
            BuffDefinition buffDefinition = BuffProvider.get(entry.getKey(), BuffSource.STACK, simulationState);

            if (buffDefinition.getBuffDamageEvaluationTiming() == BuffDamageEvaluationTiming.ON_HIT) {
                if (abilityContext.getId() == AbilityId.SOULFIRE || abilityContext.getId() == AbilityId.COMBUST || abilityContext.getId() == AbilityId.CORRUPTIONBLAST) {
                    return true;
                }
            }
        }

        if ((simulationState.getState().getEquipment().getMainhand().getEffect().contains(Effect.SONGOFDESTRUCTION) ||
            simulationState.getState().getEquipment().getOffhand().getEffect().contains(Effect.SONGOFDESTRUCTION)) &&
            (abilityContext.getId() == AbilityId.SOULFIRE || abilityContext.getId() == AbilityId.COMBUST || abilityContext.getId() == AbilityId.CORRUPTIONBLAST)) {
            return true;
        }

        return false;
    }

    public static Map<BuffId, Integer> resolveRecalculatedStacks(AbilityId abilityId, RotationCombatState rotationCombatState) {
        Map<BuffId, Integer> stacks = new HashMap<>();
        if ((rotationCombatState.getEquipment().getMainhand().getEffect().contains(Effect.SONGOFDESTRUCTION) ||
                rotationCombatState.getEquipment().getOffhand().getEffect().contains(Effect.SONGOFDESTRUCTION)) &&
                (abilityId == AbilityId.SOULFIRE || abilityId == AbilityId.COMBUST || abilityId == AbilityId.CORRUPTIONBLAST)) {
            stacks.put(BuffId.ESSENCECORRUPTIONSTACKS, rotationCombatState.getBuffs().stacks(BuffId.ESSENCECORRUPTIONSTACKS));
        }
        return stacks;
    }
}
