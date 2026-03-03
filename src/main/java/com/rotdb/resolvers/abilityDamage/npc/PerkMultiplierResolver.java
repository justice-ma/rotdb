package com.rotdb.resolvers.abilityDamage.npc;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.context.TargetContext;
import com.rotdb.model.enums.Perks;
import com.rotdb.model.equipment.PerkContext;

public class PerkMultiplierResolver {
    public static double resolve(CalculationContext context) {
        TargetContext target = context.getTarget();
        PerkContext perk = context.getPerks();

        double mod = 1;
        if ((target.isUndead() && perk.has(Perks.UNDEADSLAYER)) || (target.isDragon() && perk.has(Perks.DRAGONSLAYER)) ||
                (target.isDemon() && perk.has(Perks.DEMONSLAYER))) {
            mod *= 1.07;
        }
        return mod;
    }
}
