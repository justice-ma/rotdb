package com.rotdb.calculation.domain.modifiers.injectors;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike.CritDamageRangeResolver;
import com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike.CritRange;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.HitType;

import java.util.ArrayList;
import java.util.List;

public class TearingThornsInjector implements Modifier {
    @Override
    public void apply(CalculationContext context) {
        if (context.getBuffs().has(BuffId.TEARING_THORNS)) {
            List<AbilityHitsContext> hits = context.getAbility().getHits();

            List<AbilityHitsContext> originalhits = new ArrayList<>(hits);
            List<AbilityHitsContext> orderedHits = new ArrayList<>();

            for (int i = 0; i < originalhits.size(); i++) {
                AbilityHitsContext parent = originalhits.get(i);
                orderedHits.add(parent);

                if (!parent.isDot()) continue;
                if (context.getAbility().getId() == AbilityId.CORRUPTIONBLAST || context.getAbility().getId() == AbilityId.CORRUPTIONSHOT) continue;

                AbilityHitsContext injectedHit = new AbilityHitsContext(
                        parent.getMin(),
                        parent.getMax(),
                        true,
                        parent.getTier(),
                        parent.getHitTiming() + 1,
                        parent.getType(),
                        i
                );

                injectedHit.setCritDamageModifier(0);
                injectedHit.setCritChanceModifier(0);
                CritRange injectedHitCritRange = CritDamageRangeResolver.resolve(context, injectedHit);
                injectedHit.setCritDamages(injectedHitCritRange.getMinMod(), injectedHitCritRange.getMaxMod());

                orderedHits.add(injectedHit);
            }

            hits.clear();
            hits.addAll(orderedHits);
        }
    }
}
