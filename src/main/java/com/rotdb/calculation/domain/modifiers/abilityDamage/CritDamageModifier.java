package com.rotdb.calculation.domain.modifiers.abilityDamage;

import com.rotdb.calculation.ability.AbilityId;
import com.rotdb.calculation.domain.model.context.AbilityContext;
import com.rotdb.calculation.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.resolvers.Debug;
import com.rotdb.calculation.domain.modifiers.Modifier;

public class CritDamageModifier implements Modifier {
    public void apply(CalculationContext context) {
        if (context.debug) Debug.stageHeader(context, "Crit Damage Modifier");
        int hits = context.getAbility().getHits().size();
        AbilityContext ability = context.getAbility();
        for (int i = 0; i < hits; i++) {
            AbilityHitsContext hit = context.getAbility().getHits().get(i);

            if (!hit.isDot() && ability.getId() != AbilityId.MAGMATEMPEST) {
                hit.setCritAndNonDamages(hit.getCritChanceModifier(), hit.getMinCritDamage(), hit.getMaxCritDamage(), hit.getAverageCritDamage());
            } else {
                hit.setCritAndNonDamages(0, 0, 0, 0);
            }

            if (ability.getId() == AbilityId.SHADOWTENDRILS || ability.getId() == AbilityId.SMOKETENDRILS) {
                hit.setCurrentMin(hit.getCritMin());
                hit.setCurrentMax(hit.getCritMax());
                hit.setCurrentDamage((hit.getCurrentMin() + hit.getCurrentMax()) / 2);
            }

            if (context.debug) Debug.stageRow(context, i, hit);
        }
        if (context.debug) Debug.stageFooter(context);
    }
}
