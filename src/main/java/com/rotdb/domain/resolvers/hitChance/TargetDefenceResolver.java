package com.rotdb.domain.resolvers.hitChance;

import com.rotdb.domain.model.context.CalculationContext;

public class TargetDefenceResolver {
        public static int resolve(CalculationContext context) {
            int def = context.getTarget().getDefence();
            int arm = context.getTarget().getArmour();
            int armBonus = (int) (1.0 / 1_250 * Math.pow(def, 3) + 4 * def + 40);
            int bsaDrain = 0;
            int lobDrain = 0;
            if (context.getTarget().getBlackStoneArrowStacks() > 0) {
                int armReductionPerStack = Math.min(22, (int) (0.075 * arm));
                int maxStacks = (int) (Math.ceil(0.15 * arm / armReductionPerStack));
                int stacks = Math.min(maxStacks, context.getTarget().getBlackStoneArrowStacks());
                int currentBsaMod = (int) (Math.floor(stacks * armReductionPerStack / 5.0));
                bsaDrain = currentBsaMod * 5;
            }

            if (context.getTarget().getLordOfBonesStacks() > 0) {
                double lobDrainPerStack = 0.002 * arm;
                int stacks = Math.min(200, context.getTarget().getLordOfBonesStacks());
                lobDrain = (int) (lobDrainPerStack * stacks);
            }
            return arm + armBonus - bsaDrain - lobDrain;
        }
}
