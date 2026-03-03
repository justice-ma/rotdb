package com.rotdb.modifiers.abilityDamage;

import com.rotdb.model.context.AbilityHitsContext;
import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.Perks;
import com.rotdb.model.equipment.PerkContext;
import com.rotdb.resolvers.Debug;
import com.rotdb.modifiers.Modifier;

public class PreciseModifier implements Modifier {
    public void apply(CalculationContext context) {
        PerkContext perk = context.getPerks();

        int hits = context.getAbility().getHits().size();
        Debug.stageHeader(context, "Precise Modifier");

        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);

            if (!hit.isDot()) {
                if (perk.has(Perks.PRECISE)) {
                    double preciseMod = (perk.rank(Perks.PRECISE) * 0.015);
                    hit.setCurrentMin((int) (hit.getCurrentMin() + hit.getCurrentMax() * preciseMod));
                }
            }
            Debug.stageRow(context, i, hit);
        }
        Debug.stageFooter(context);
    }
}
