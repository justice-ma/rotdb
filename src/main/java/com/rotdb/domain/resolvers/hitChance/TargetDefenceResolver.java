package com.rotdb.domain.resolvers.hitChance;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.BuffId;
import com.rotdb.domain.model.player.BuffContext;

public class TargetDefenceResolver {
        public static int resolve(CalculationContext context) {
            BuffContext buff = context.getBuffs();
            int def = context.getTarget().getDefence();
            int arm = context.getTarget().getArmour();
            int armBonus = (int) (1.0 / 1_250 * Math.pow(def, 3) + 4 * def + 40);
            int bsaDrain = 0;
            int lobDrain = 0;
            if (buff.has(BuffId.BLACKSTONEARROWSTACKS)) {
                int armReductionPerStack = Math.min(22, (int) (0.075 * arm));
                int maxStacks = (int) (Math.ceil(0.15 * arm / armReductionPerStack));
                int stacks = Math.min(maxStacks, buff.stacks(BuffId.BLACKSTONEARROWSTACKS));
                int currentBsaMod = (int) (Math.floor(stacks * armReductionPerStack / 5.0));
                bsaDrain = currentBsaMod * 5;
            }

            if (buff.has(BuffId.LORDOFBONESSTACKS)) {
                double lobDrainPerStack = 0.002 * arm;
                int stacks = Math.min(200, buff.stacks(BuffId.LORDOFBONESSTACKS));
                lobDrain = (int) (lobDrainPerStack * stacks);
            }
            return arm + armBonus - bsaDrain - lobDrain;
        }
}
