package com.rotdb.calculation.domain.modifiers.injectors;

import com.rotdb.calculation.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.enums.AbilityTier;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.shared.ability.AbilityId;

import java.util.List;

import static com.rotdb.shared.ability.AbilityId.*;

public class MasterworkSpearOfAnnihilationInjector implements Modifier {
    public void apply(CalculationContext context) {
        if (context.getEquipment().getMainhand().getEffect().contains(Effect.MASTERWORKSPEAROFANNIHILATION)
            && (context.getAbility().getId() == DISMEMBER ||
                context.getAbility().getId() == SLAUGHTER ||
                context.getAbility().getId() == MASSACRE)) {
            double min = context.getAbility().getHits().get(1).getMin();
            double max = context.getAbility().getHits().get(1).getMax();
            int hitTiming = hitTiming(context.getAbility().getId());

            List<AbilityHitsContext> hits = context.getAbility().getHits();
            int additionalHits = (int) (context.getAbility().getNumberOfHits() * 0.5);

            for (int i = 0; i < additionalHits; i++) {
                hits.add(new AbilityHitsContext(min, max, true, AbilityTier.THRESHOLD,
                        context.getAbility().getHits()
                                .get(context.getAbility().getNumberOfHits() - 1 + i).getHitTiming() + hitTiming));
            }
        }

        if (context.getBuffs().has(BuffId.STRENGTHCAPE) && context.getAbility().getId() == DISMEMBER) {
            for (int i = 0; i < 3; i++) {
                double min = context.getAbility().getHits().get(1).getMin();
                double max = context.getAbility().getHits().get(1).getMax();
                int hitTiming = hitTiming(context.getAbility().getId());
                List<AbilityHitsContext> hits = context.getAbility().getHits();
                hits.add(new AbilityHitsContext(min, max, true, AbilityTier.THRESHOLD, context.getAbility().getHits()
                        .get(context.getAbility().getNumberOfHits() - 1 + i).getHitTiming() + hitTiming));
            }
        }
    }

    private static int hitTiming(AbilityId ability) {
        return switch (ability) {
            case DISMEMBER -> 2;
            case SLAUGHTER -> 3;
            case MASSACRE -> 4;
            default -> 0;
        };
    }
}
