package com.rotdb.simulation.domain.resolvers.cooldown;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.simulation.domain.model.cooldown.CooldownKey;
import com.rotdb.simulation.domain.model.cooldown.CooldownKeyType;

public class CooldownKeyResolver {
    public static CooldownKey resolve(AbilityId abilityId) {
        if (abilityId.getAbilityCooldownGroup() != null) {
            return new CooldownKey(CooldownKeyType.GROUP, abilityId.getAbilityCooldownGroup().toString());
        } else {
            return new CooldownKey(CooldownKeyType.ABILITY, abilityId.toString());
        }
    }

    public static CooldownKey resolveGlobalCooldown() {
        return new CooldownKey(CooldownKeyType.GLOBAL, "GLOBAL");
    }
}
