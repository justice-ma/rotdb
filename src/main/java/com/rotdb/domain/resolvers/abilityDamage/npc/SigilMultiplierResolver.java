package com.rotdb.domain.resolvers.abilityDamage.npc;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.context.TargetContext;
import com.rotdb.domain.model.enums.BuffId;
import com.rotdb.domain.model.player.BuffContext;

public class SigilMultiplierResolver {
    public static double resolve(CalculationContext context) {
        TargetContext target = context.getTarget();
        BuffContext buff = context.getBuffs();

        double mod = 1;
        if ((target.isUndead() && buff.has(BuffId.UNDEADSLAYERSIGIL)) || (target.isDragon() && buff.has(BuffId.DRAGONSLAYERSIGIL)) ||
                (target.isDemon() && buff.has(BuffId.DEMONSLAYERSIGIL))) {
            mod *= 1.15;
        }
        return mod;
    }
}
