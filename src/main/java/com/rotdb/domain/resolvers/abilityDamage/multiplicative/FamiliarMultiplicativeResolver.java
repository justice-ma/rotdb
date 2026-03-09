package com.rotdb.domain.resolvers.abilityDamage.multiplicative;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.context.TargetContext;
import com.rotdb.domain.model.enums.Familiars;
import com.rotdb.domain.model.equipment.FamiliarContext;

public class FamiliarMultiplicativeResolver {
    public static double resolve(CalculationContext context) {
        FamiliarContext familiar = context.getFamiliar();
        TargetContext target = context.getTarget();
        if (target.getMaxHp() == 0 || target.getCurrentHp() == 0) return 1;
        if (familiar.getName() == Familiars.RIPPERDEMON) {
            return 1 + 0.05 * (1 - ((double) target.getCurrentHp() / target.getMaxHp()));
        }
        return 1;
    }
}
