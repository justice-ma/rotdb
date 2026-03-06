package com.rotdb.domain.resolvers.hitChance;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.context.TargetContext;

public class AffinityResolver {
    public static int resolve(CalculationContext context) {
        int affinity = context.getTarget().getAffinity();
        TargetContext target = context.getTarget();

        int statius = target.isObliterate() ? 5 : 0;
        int bandos = target.isBandosBook() ? 3 : 0;
        int guthixStaff = target.isClawsOfGuthix() ? 2 : 0;
        int dragonHatchet = target.isClobber() ? 3 : 0;
        int barrelchest = target.isSunder() ? 4 : 0;
        int boneDagger = target.isBackstab() ? 2 : 0;

        return affinity + statius + bandos + guthixStaff + dragonHatchet + barrelchest + boneDagger;
    }
}
