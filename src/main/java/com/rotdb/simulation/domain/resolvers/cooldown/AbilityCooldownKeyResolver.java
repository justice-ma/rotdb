package com.rotdb.simulation.domain.resolvers.cooldown;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.simulation.domain.model.cooldown.AbilityCooldownKey;
import com.rotdb.simulation.domain.model.cooldown.AbilityCooldownKeyType;

public class AbilityCooldownKeyResolver {
    public static AbilityCooldownKey resolve(AbilityId abilityId) {
        if (abilityId.getAbilityCooldownGroup() != null) {
            return new AbilityCooldownKey(AbilityCooldownKeyType.GROUP, abilityId.getAbilityCooldownGroup().toString());
        } else {
            return new AbilityCooldownKey(AbilityCooldownKeyType.ABILITY, abilityId.toString());
        }
    }

    public static AbilityCooldownKey resolveGlobalCooldown() {
        return new AbilityCooldownKey(AbilityCooldownKeyType.GLOBAL, "GLOBAL");
    }
}
