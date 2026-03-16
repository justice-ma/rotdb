package com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.Perks;
import com.rotdb.calculation.domain.model.equipment.PerkContext;

public class PerkCritResolver {
    public static double resolve(CalculationContext context) {
        PerkContext perk = context.getPerks();

        double criticalStrikeChance = 0;
        if (perk.has(Perks.BITING)) {
            if (perk.isEquipmentLevel20()) {
                criticalStrikeChance += perk.rank(Perks.BITING) * 0.022;
            } else {
                criticalStrikeChance += perk.rank(Perks.BITING) * 0.02;
            }
        }
        return criticalStrikeChance;
    }
}
