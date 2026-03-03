package com.rotdb.resolvers.abilityDamage.multiplicative;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.context.TargetContext;
import com.rotdb.model.enums.Familiars;
import com.rotdb.model.equipment.FamiliarContext;

public class FamiliarMultiplicativeResolver {
    public static double resolve(CalculationContext context) {
        FamiliarContext familiar = context.getFamiliar();
        TargetContext target = context.getTarget();
        if (familiar.getName() == Familiars.RIPPERDEMON) {
            return 1 + 0.05 * (1 - ((double) target.getCurrentHp() / target.getMaxHp()));
        }
        return 1;
    }
}
