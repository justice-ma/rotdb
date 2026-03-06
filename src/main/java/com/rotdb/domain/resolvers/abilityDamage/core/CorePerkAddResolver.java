package com.rotdb.domain.resolvers.abilityDamage.core;

import com.rotdb.domain.model.context.AbilityContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.Perks;
import com.rotdb.domain.model.equipment.PerkContext;
import static com.rotdb.ability.AbilityId.*;

public class CorePerkAddResolver {
    public static int resolve(CalculationContext context) {
        PerkContext perk = context.getPerks();
        AbilityContext ability = context.getAbility();
        if (perk.has(Perks.CAROMING) && (ability.getId() == GREATERRICOCHET || ability.getId() == RICOCHET)) {
            return (int) (context.getDamage().getBaseDamage() * Math.min(4, perk.rank(Perks.CAROMING)) * 0.025);
        }
        return 0;
    }
}
