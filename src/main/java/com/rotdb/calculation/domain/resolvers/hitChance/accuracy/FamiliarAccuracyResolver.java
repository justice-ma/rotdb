package com.rotdb.calculation.domain.resolvers.hitChance.accuracy;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.Familiars;
import com.rotdb.calculation.domain.model.equipment.FamiliarContext;

import static com.rotdb.calculation.domain.model.enums.CombatStyles.*;

public class FamiliarAccuracyResolver {
    public static double resolve(CalculationContext context) {
        FamiliarContext familiar = context.getFamiliar();
        double accuracyModifier = 0;
            if (familiar.getName() == Familiars.BLOODNIHIL && context.getEquipment().getCombatStyle() == MELEE) accuracyModifier += 0.05;
            if (familiar.getName() == Familiars.SHADOWNIHIL && context.getEquipment().getCombatStyle() == RANGED) accuracyModifier += 0.05;
            if (familiar.getName() == Familiars.SMOKENIHIL && context.getEquipment().getCombatStyle() == MAGIC) accuracyModifier += 0.05;
            if (familiar.getName() == Familiars.ICENIHIL && context.getEquipment().getCombatStyle() == NECROMANCY) accuracyModifier += 0.05;
        return accuracyModifier;
    }
}
