package com.rotdb.calculation.domain.resolvers.abilityDamage.multiplicative;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.enums.EquipmentType;
import com.rotdb.shared.combat.domain.model.enums.Perks;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;

public class PerkMultiplicativeResolver {
    public static double resolve(CalculationContext context) {
        BuffContext buff = context.getBuffs();
        PerkContext perk = context.getPerks();

        double mod = 1;
        // TODO: Check that offhand == shield some day!
        if (buff.has(BuffId.REVENGESTACKS) && buff.stacks(BuffId.REVENGESTACKS) > 0 &&
                (context.getEquipment().getOffhand().getType() == EquipmentType.SHIELD ||
                        context.getEquipment().getOffhand().getEffect().contains(Effect.DEFENDER))) {
            int maxStacks = context.getBuffs().has(BuffId.STEADFAST_WILL) ? 20 : 10;
            mod *= 1 + Math.min(maxStacks, buff.stacks(BuffId.REVENGESTACKS) ) * 0.05;
        }

        if (perk.has(Perks.SPENDTHRIFT)) {
            mod *= 1 + Math.min(perk.rank(Perks.SPENDTHRIFT) / 100.0, 0.06) * Math.min(perk.rank(Perks.SPENDTHRIFT) / 100.0, 0.06);
        }

        if (buff.has(BuffId.RUTHELESSSTACKS) && buff.stacks(BuffId.RUTHELESSSTACKS) > 0 && perk.has(Perks.RUTHLESS)) {
            mod *= 1 + Math.min(buff.stacks(BuffId.RUTHELESSSTACKS), 5) * Math.min(perk.rank(Perks.RUTHLESS), 3) * 0.005;
        }
        return mod;
    }
}
