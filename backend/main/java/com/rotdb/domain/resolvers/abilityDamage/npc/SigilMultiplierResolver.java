package com.rotdb.domain.resolvers.abilityDamage.npc;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.context.TargetContext;
import com.rotdb.domain.model.enums.BuffId;
import com.rotdb.domain.model.enums.TargetTags;
import com.rotdb.domain.model.player.BuffContext;

public class SigilMultiplierResolver {
    public static double resolve(CalculationContext context) {
        TargetContext target = context.getTarget();
        BuffContext buff = context.getBuffs();

        double mod = 1;
        if ((target.has(TargetTags.UNDEAD) && buff.has(BuffId.UNDEADSLAYERSIGIL)) || (target.has(TargetTags.DRAGON) && buff.has(BuffId.DRAGONSLAYERSIGIL)) ||
                (target.has(TargetTags.DEMON) && buff.has(BuffId.DEMONSLAYERSIGIL))) {
            mod *= 1.15;
        }
        return mod;
    }
}