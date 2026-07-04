package com.rotdb.simulation.domain.resolvers.buff;

import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.simulation.domain.model.buff.StackEffect;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.model.context.TimelineHit;

import java.util.ArrayList;
import java.util.List;

public class StackResolver {
    public static List<StackEffect> resolveOnRelease(SimulationState state, AbilityContext abilityContext) {
        List<StackEffect> stackEffects = new ArrayList<>();
        EquipmentModel eq = state.getState().getEquipment();
        if (eq.getAmmo().getEffect().contains(Effect.WENARROWS) && abilityContext.getId().getStyle() == CombatStyles.RANGED
                && abilityContext.getId().getTier() == AbilityTier.BASIC) {
            stackEffects.add(new StackEffect(
                    BuffId.WENARROWSTACKS,
                    abilityContext.getNumberOfHits(),
                    BuffSource.PROC,
                    null,
                    null
            ));
        }
        return stackEffects;
    }

    public static List<StackEffect> resolveOnHit(SimulationState state, AbilityContext abilityContext, TimelineHit hit) {
        List<StackEffect> stackEffects = new ArrayList<>();
        EquipmentModel eq = state.getState().getEquipment();
        double procChance = 0.0;
        if ((eq.getMainhand().getEffect().contains(Effect.PRIMORDIALICESTACKS) || eq.getOffhand().getEffect().contains(Effect.PRIMORDIALICESTACKS)) &&
                abilityContext.getId().getStyle() == CombatStyles.MELEE && !hit.isDot()) {
            procChance += eq.getMainhand().getTitle() != null && eq.getMainhand().getTitle().equalsIgnoreCase("dark shard of leng") &&
                    eq.getMainhand().getEffect().contains(Effect.PRIMORDIALICESTACKS) ? 0.1 :
                    eq.getMainhand().getTitle() != null && eq.getMainhand().getEffect().contains(Effect.PRIMORDIALICESTACKS) ? 0.05 : 0;
            procChance += eq.getOffhand().getTitle() != null && eq.getOffhand().getTitle().equalsIgnoreCase("dark sliver of leng") &&
                    eq.getOffhand().getEffect().contains(Effect.PRIMORDIALICESTACKS) ? 0.02 :
                    eq.getOffhand().getTitle() != null && eq.getMainhand().getEffect().contains(Effect.PRIMORDIALICESTACKS) ? 0.01 : 0;
            stackEffects.add(new StackEffect(
                    BuffId.PRIMORDIALICESTACKS,
                    1,
                    BuffSource.PROC,
                    procChance,
                    null
            ));
        }
        return stackEffects;
    }
}
