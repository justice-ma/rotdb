package com.rotdb.modifiers.abilityDamage;

import com.rotdb.ability.AbilityId;
import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.HitType;
import com.rotdb.modifiers.Modifier;

public class CrystalRainArrowsModifier implements Modifier {
    public void apply(CalculationContext context) {
        if (context.getAbility().getId() == AbilityId.CRYSTALRAIN) {
            double expectedNumberOfArrows = 0;
            int size = context.getTarget().getSize();
            switch (size) {
                case 1:
                    expectedNumberOfArrows = 1;
                    break;
                case 2:
                    expectedNumberOfArrows = 1.5;
                    break;
                case 3:
                    expectedNumberOfArrows = 2.33;
                    break;
                case 4:
                    expectedNumberOfArrows = 3.5;
                    break;
                default:
                    expectedNumberOfArrows = 5;
            }
            while (expectedNumberOfArrows > 0) {
                for (int i = 0; i < context.getAbility().getHits().size(); i++) {
                    AbilityHitsContext hit = context.getAbility().getHits().get(i);
                    if (hit.getType() == HitType.BASE) {
                        hit.calculateDamages(Math.min(1, expectedNumberOfArrows));
                        expectedNumberOfArrows = Math.max(0, expectedNumberOfArrows - 1);
                    }
                }
            }
        }
    }
}
