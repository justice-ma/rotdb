package com.rotdb.simulation.domain.resolvers.buff;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.BuffCooldownKey;

import static com.rotdb.simulation.domain.model.buff.BuffCooldownKeyType.BUFF;
import static com.rotdb.simulation.domain.model.buff.BuffCooldownKeyType.GROUP;

public class BuffCooldownKeyResolver {
    public static BuffCooldownKey resolve(BuffId buffId) {
        if (buffId.hasCooldownGroup()) {
            return new BuffCooldownKey(GROUP, buffId.getCooldownGroup().toString());
        } else {
            return new BuffCooldownKey(BUFF, buffId.toString());
        }
    }
}
