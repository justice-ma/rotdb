package com.rotdb.resolvers.abilityDamage.npc;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.context.TargetContext;
import com.rotdb.model.enums.BuffId;
import com.rotdb.model.player.BuffContext;

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
