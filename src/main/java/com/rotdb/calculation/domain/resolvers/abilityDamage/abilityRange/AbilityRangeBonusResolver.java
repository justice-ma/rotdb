package com.rotdb.calculation.domain.resolvers.abilityDamage.abilityRange;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Perks;

public class AbilityRangeBonusResolver {
    public static AbilityRangeBonus resolve(CalculationContext snapshotContext, CalculationContext liveContext) {
        double min = 0;
        double max = 0;

        int lengStacks = snapshotContext.getBuffs().has(BuffId.PRIMORDIALICESTACKS) ? snapshotContext.getBuffs().stacks(BuffId.PRIMORDIALICESTACKS) : 0;
        int time = snapshotContext.getBuffs().has(BuffId.TIMESINCELASTATTACK) ? snapshotContext.getBuffs().stacks(BuffId.TIMESINCELASTATTACK) : 0;

        if (lengStacks > 0 && snapshotContext.getAbility().getId() == AbilityId.ICYTEMPEST) {
            min += 0.18 * lengStacks;
            max += 0.22 * lengStacks;
        }

        if (time > 0 && snapshotContext.getAbility().getId() == AbilityId.GREATERBARGE) {
            min += 0.05 * time;
            max += 0.07 * time;
        }

        if (snapshotContext.getAbility().getId() == AbilityId.SPECTRALMETEORSTRIKE) {
            double targetHp = 1 - (snapshotContext.getTarget().getCurrentHp() * 1.0 / snapshotContext.getTarget().getMaxHp());
            min += targetHp * snapshotContext.getAbility().getHits().getFirst().getMin();
            max += targetHp * snapshotContext.getAbility().getHits().getFirst().getMax();
        }

        if (snapshotContext.getAbility().getId() == AbilityId.COMMANDPHANTOMGUARDIAN && snapshotContext.getBuffs().has(BuffId.VALOUR)) {
            min += snapshotContext.getAbility().getHits().getFirst().getMin() * (0.2 * snapshotContext.getBuffs().stacks(BuffId.VALOUR)) - snapshotContext.getAbility().getHits().getFirst().getMin();
            max += snapshotContext.getAbility().getHits().getFirst().getMax() * (0.2 * snapshotContext.getBuffs().stacks(BuffId.VALOUR)) - snapshotContext.getAbility().getHits().getFirst().getMax();
        }

        if ((snapshotContext.getAbility().getId() == AbilityId.CONJURESKELETONWARRIOR ||
                snapshotContext.getAbility().getId() == AbilityId.COMMANDSKELETONWARRIOR) && liveContext.getBuffs().has(BuffId.RAGE)) {
            min += snapshotContext.getAbility().getHits().getFirst().getMin() * (0.03 * liveContext.getBuffs().stacks(BuffId.RAGE));
            max += snapshotContext.getAbility().getHits().getFirst().getMax() * (0.03 * liveContext.getBuffs().stacks(BuffId.RAGE));
        }

        if ((snapshotContext.getAbility().getId() == AbilityId.DISMEMBER || snapshotContext.getAbility().getId() == AbilityId.COMBUST)
                && snapshotContext.getPerks().has(Perks.LUNGING)) {
            min += snapshotContext.getAbility().getHits().getFirst().getMin() * (0.1 + 0.03 * snapshotContext.getPerks().rank(Perks.LUNGING));
            max += snapshotContext.getAbility().getHits().getFirst().getMax() * (0.1 + 0.03 * snapshotContext.getPerks().rank(Perks.LUNGING));
        }

        return new AbilityRangeBonus(min, max);
    }
}
