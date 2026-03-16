package com.rotdb.calculation.domain.resolvers.hitChance;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.model.player.BuffContext;

public class AffinityResolver {
    public static int resolve(CalculationContext context) {
        int affinity = context.getTarget().getAffinity();
        BuffContext buff = context.getBuffs();

        int statius = buff.has(BuffId.OBLITERATED) ? 5 : 0;
        int bandos = buff.has(BuffId.BANDOSBOOK) ? 3 : 0;
        int guthixStaff = buff.has(BuffId.CLAWSOFGUTHIX) ? 2 : 0;
        int dragonHatchet = buff.has(BuffId.CLOBBER) ? 3 : 0;
        int barrelchest = buff.has(BuffId.SUNDER) ? 4 : 0;
        int boneDagger = buff.has(BuffId.BACKSTAB) ? 2 : 0;

        return affinity + statius + bandos + guthixStaff + dragonHatchet + barrelchest + boneDagger;
    }
}
