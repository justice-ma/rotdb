package com.rotdb.calculation.domain.resolvers.hitChance.accuracy;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.Familiars;
import com.rotdb.shared.combat.domain.model.equipment.FamiliarContext;

public class FamiliarAccuracyResolver {
    public static double resolve(CalculationContext context) {
        FamiliarContext familiar = context.getFamiliar();
        double accuracyModifier = 0;
            if (familiar.getName() == Familiars.BLOODNIHIL && context.getEquipment().getCombatStyle() == CombatStyles.MELEE) accuracyModifier += 0.05;
            if (familiar.getName() == Familiars.SHADOWNIHIL && context.getEquipment().getCombatStyle() == CombatStyles.RANGED) accuracyModifier += 0.05;
            if (familiar.getName() == Familiars.SMOKENIHIL && context.getEquipment().getCombatStyle() == CombatStyles.MAGIC) accuracyModifier += 0.05;
            if (familiar.getName() == Familiars.ICENIHIL && context.getEquipment().getCombatStyle() == CombatStyles.NECROMANCY) accuracyModifier += 0.05;
        return accuracyModifier;
    }
}
