package com.rotdb.domain.resolvers.abilityDamage.npc;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.context.TargetContext;
import com.rotdb.domain.model.enums.Perks;
import com.rotdb.domain.model.enums.TargetTags;
import com.rotdb.domain.model.equipment.PerkContext;

public class PerkMultiplierResolver {
    public static double resolve(CalculationContext context) {
        TargetContext target = context.getTarget();
        PerkContext perk = context.getPerks();

        double mod = 1;
        if ((target.has(TargetTags.UNDEAD) && perk.has(Perks.UNDEADSLAYER)) || (target.has(TargetTags.DRAGON) && perk.has(Perks.DRAGONSLAYER)) ||
                (target.has(TargetTags.DEMON) && perk.has(Perks.DEMONSLAYER))) {
            mod *= 1.07;
        }
        return mod;
    }
}
