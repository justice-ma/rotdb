package com.rotdb.simulation.domain.model;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.simulation.domain.model.context.AbilityCooldownContext;

public class RotationRequest {
    private AbilityId abilityId;
    private AbilityCooldownContext abilityCooldowns;
}
