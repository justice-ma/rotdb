package com.rotdb.calculation.domain.modifiers.injectors;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.Effect;

import java.util.List;

public class NightmareGauntletsInjector implements Modifier {
    public void apply(CalculationContext context) {
        if (context.getEquipment().getMainhand().getClazz() != CombatStyles.RANGED) {
            return;
        }
        if (context.getEquipment().getGloves().getEffect().contains(Effect.NIGHTMAREGAUNTLETS) &&
                context.getEquipment().getGloves().getEffect().contains(Effect.ENHANCED) &&
                context.getBuffs().has(BuffId.ENCHANTMENTOFDREAD) && context.getAbility().getId() == AbilityId.SNIPE) {
            List<AbilityHitsContext> hits = context.getAbility().getHits();
            double min = hits.getFirst().getMin() * 0.5;
            double max = hits.getFirst().getMax() * 0.5;

            hits.add(new AbilityHitsContext(
                    min, max, false, AbilityTier.ENHANCED, 3
            ));
        }
    }
}
