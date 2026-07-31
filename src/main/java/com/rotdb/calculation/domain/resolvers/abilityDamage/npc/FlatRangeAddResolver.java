package com.rotdb.calculation.domain.resolvers.abilityDamage.npc;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.enums.EquipmentType;

import java.util.ArrayList;
import java.util.List;

public class FlatRangeAddResolver {
    public static List<Integer> resolve(CalculationContext context) {
        int min = 0;
        int max = 0;

        if (context.getBuffs().has(BuffId.STEADFAST_WILL)
                && (context.getEquipment().getOffhand().getType() == EquipmentType.SHIELD
                || context.getEquipment().getOffhand().getEffect().contains(Effect.DEFENDER))
                && (context.getAbility().getId() == AbilityId.BASH_MAGIC
                || context.getAbility().getId() == AbilityId.BASH_MELEE
                || context.getAbility().getId() == AbilityId.BASH_RANGED
                || context.getAbility().getId() == AbilityId.BASH_NECROMANCY)) {
            min += (int) (context.getEquipment().getTotalArmour() * 3.5);
            max += (int) (context.getEquipment().getTotalArmour() * 4.5);
        }

        return new ArrayList<>(List.of(min, max));
    }
}
