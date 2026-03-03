package com.rotdb.resolvers.abilityDamage.npc;

import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.context.TargetContext;
import com.rotdb.model.enums.Effect;
import com.rotdb.model.equipment.EquipmentSlot;

public class HauntedBonusResolver {
    // TODO: NEXUS GOES IN QUIVER SLOT - CREATE QUIVER SLOT
    public static HauntedBonus resolve(CalculationContext context, AbilityHitsContext hit) {
        TargetContext target = context.getTarget();
        EquipmentSlot pocket = context.getEquipment().getPocket();
        int minCrit = 0, maxCrit = 0, minNonCrit = 0, maxNonCrit = 0, minAvg = 0, maxAvg = 0;
        if (target.isHaunted()) {
            double mod = pocket.getEffect().contains(Effect.DEVOURERSNEXUS) ? 0.15 : 0.1;
            minCrit = Math.min((int) (hit.getCritMin() * mod), (int) (context.getDamage().getBaseDamage() * (mod * 2)));
            maxCrit = Math.min((int) (hit.getCritMax() * mod), (int) (context.getDamage().getBaseDamage() * (mod * 2)));
            minNonCrit = Math.min((int) (hit.getNonCritMin() * mod), (int) (context.getDamage().getBaseDamage() * (mod * 2)));
            maxNonCrit = Math.min((int) (hit.getNonCritMax() * mod), (int) (context.getDamage().getBaseDamage() * (mod * 2)));
            minAvg = Math.min((int) (hit.getCurrentMin() * mod), (int) (context.getDamage().getBaseDamage() * (mod * 2)));
            maxAvg = Math.min((int) (hit.getCurrentMax() * mod), (int) (context.getDamage().getBaseDamage() * (mod * 2)));
        }
        return new HauntedBonus(minCrit, maxCrit, minNonCrit, maxNonCrit, minAvg, maxAvg);
    }
}
