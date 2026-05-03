package com.rotdb.simulation.application.service;

import com.rotdb.calculation.domain.model.DamageRequest;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.simulation.domain.model.context.RotationCombatState;

public class DamageRequestFactory {
    public static DamageRequest getDamageRequest(RotationCombatState state, AbilityId abilityId) {
        return new DamageRequest(
                state.getEquipment(),
                abilityId,
                state.getBuffs(),
                state.getTarget(),
                state.getSkills(),
                state.getPerk(),
                state.getFamiliar(),
                state.getPrayer(),
                state.getSpell()
        );
    }
}
