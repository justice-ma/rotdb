package com.rotdb.calculation.domain.resolvers.abilityDamage.abilityRange;

import com.rotdb.shared.combat.domain.model.enums.Perks;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;

public class AbilityRangeBonusResolver {
    public static AbilityRangeBonus resolve(CalculationContext context) {
        double min = 0;
        double max = 0;

        int lengStacks = context.getBuffs().has(BuffId.PRIMORDIALICESTACKS) ? context.getBuffs().stacks(BuffId.PRIMORDIALICESTACKS): 0;
        int time = context.getBuffs().has(BuffId.TIMESINCELASTATTACK) ? context.getBuffs().stacks(BuffId.TIMESINCELASTATTACK) : 0;

        if (lengStacks > 0 && context.getAbility().getId() == AbilityId.ICYTEMPEST) {
            min += 0.18 * lengStacks;
            max += 0.22 * lengStacks;
        }

        if (time > 0 && context.getAbility().getId() == AbilityId.GREATERBARGE) {
            min += 0.05 * time;
            max += 0.07 * time;
        }

        if (context.getAbility().getId() == AbilityId.SPECTRALMETEORSTRIKE) {
            double targetHp = 1 - (context.getTarget().getCurrentHp() * 1.0 / context.getTarget().getMaxHp());
            min += targetHp * context.getAbility().getHits().getFirst().getMin();
            max += targetHp * context.getAbility().getHits().getFirst().getMax();
        }

        if (context.getAbility().getId() == AbilityId.COMMANDPHANTOMGUARDIAN && context.getBuffs().has(BuffId.VALOUR)) {
            min += context.getAbility().getHits().getFirst().getMin() * (0.2 * context.getBuffs().stacks(BuffId.VALOUR)) - context.getAbility().getHits().getFirst().getMin();
            max += context.getAbility().getHits().getFirst().getMax() * (0.2 * context.getBuffs().stacks(BuffId.VALOUR)) - context.getAbility().getHits().getFirst().getMax();
        }

        if (context.getAbility().getId() == AbilityId.CONJURESKELETONWARRIOR ||
                context.getAbility().getId() == AbilityId.COMMANDSKELETONWARRIOR && context.getBuffs().has(BuffId.RAGE)) {
            min += context.getAbility().getHits().getFirst().getMin() * (0.03 * context.getBuffs().stacks(BuffId.RAGE));
            max += context.getAbility().getHits().getFirst().getMax() * (0.03 * context.getBuffs().stacks(BuffId.RAGE));
        }

        if ((context.getAbility().getId() == AbilityId.DISMEMBER || context.getAbility().getId() == AbilityId.COMBUST)
            && context.getPerks().has(Perks.LUNGING)) {
            min += context.getAbility().getHits().getFirst().getMin() * (0.1 + 0.03 * context.getPerks().rank(Perks.LUNGING));
            max += context.getAbility().getHits().getFirst().getMax() * (0.1 + 0.03 * context.getPerks().rank(Perks.LUNGING));
        }

        return new AbilityRangeBonus(min, max);
    }
}
