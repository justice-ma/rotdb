package com.rotdb.calculation.domain.resolvers.abilityDamage.core;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.Perks;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;

import static com.rotdb.shared.ability.AbilityId.GREATERRICOCHET;
import static com.rotdb.shared.ability.AbilityId.RICOCHET;

public class CorePerkAddResolver {
    public static int resolve(CalculationContext context) {
        PerkContext perk = context.getPerks();
        AbilityContext ability = context.getAbility();
        if (perk.has(Perks.CAROMING) && perk.rank(Perks.CAROMING) > 0 && (ability.getId() == GREATERRICOCHET || ability.getId() == RICOCHET)) {
            return (int) (context.getDamage().getBaseDamage() * Math.min(0.16, perk.rank(Perks.CAROMING) * 0.04));
        }
        return 0;
    }
}
