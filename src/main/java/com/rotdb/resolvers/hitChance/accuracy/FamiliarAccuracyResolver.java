package com.rotdb.resolvers.hitChance.accuracy;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.Familiars;
import com.rotdb.model.equipment.FamiliarContext;

import static com.rotdb.model.enums.CombatStyles.*;
import static com.rotdb.model.enums.CombatStyles.NECROMANCY;

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
