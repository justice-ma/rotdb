package com.rotdb.resolvers.abilityDamage.abilitySpecific;

import com.rotdb.ability.AbilityId;
import com.rotdb.model.context.AbilityContext;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.Perks;
import com.rotdb.model.equipment.PerkContext;

public class FlankingAbilityResolver {
    public static double resolve(CalculationContext context) {
        PerkContext perk = context.getPerks();
        AbilityContext ability = context.getAbility();
        if ((ability.getId() == AbilityId.IMPACT || ability.getId() == AbilityId.BINDINGSHOT ||
                ability.getId() == AbilityId.BACKHAND) && perk.has(Perks.FLANKING)) {
            return (1 + (0.4 * perk.rank(Perks.FLANKING)));
        }
        return 1;
    }
}
