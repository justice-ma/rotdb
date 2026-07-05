package com.rotdb.simulation.domain.resolvers.buff;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.simulation.domain.model.buff.AppliedBuffResult;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.ConsumableStackResult;
import com.rotdb.simulation.domain.model.buff.StackEffect;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
import com.rotdb.simulation.domain.model.buff.enums.StackConsumptionTiming;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.model.context.TimelineHit;
import com.rotdb.simulation.domain.provider.BuffProvider;

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

        if (eq.getCombatStyle() == CombatStyles.MELEE && state.getState().getBuffs().has(BuffId.GRAVITATEBUFF) && abilityContext.getCombatStyle() == CombatStyles.MELEE) {
            stackEffects.add(new StackEffect(
                    BuffId.GRAVITATESTACKS,
                    1,
                    BuffSource.PROC,
                    null,
                    null
            ));
        }
        return stackEffects;
    }

    public static List<ConsumableStackResult> resolveConsumableStackPreparation(AbilityPlacement abilityPlacement,
                                                                                SimulationState state) {
        List<ConsumableStackResult> buffResults = new ArrayList<>();

        if (abilityPlacement.getPlacedAbility().getTier() != AbilityTier.BASIC &&
                abilityPlacement.getPlacedAbility().getStyle() == CombatStyles.RANGED &&
                state.getState().getBuffs().has(BuffId.WENARROWSTACKS) &&
                state.getState().getBuffs().stacks(BuffId.WENARROWSTACKS) >= 10 &&
                !state.getActiveBuffDurationMap().containsKey(BuffId.WENARROWPROC) &&
                !state.getState().getBuffs().has(BuffId.WENARROWPROC) &&
                state.getState().getEquipment().getAmmo().getEffect().contains(Effect.WENARROWS)) {
            AppliedBuffResult appliedBuffResult = new AppliedBuffResult(
                    BuffProvider.get(BuffId.WENARROWPROC, BuffSource.PROC, state),
                    BuffProvider.get(BuffId.WENARROWPROC, BuffSource.PROC, state).getDurationTicks()
            );
            buffResults.add(new ConsumableStackResult(
                    appliedBuffResult,
                    BuffId.WENARROWSTACKS,
                    BuffId.WENARROWSTACKS.getMaximumStacks(),
                    StackConsumptionTiming.PRE_DAMAGE
            ));
        }

        if (abilityPlacement.getPlacedAbility() == AbilityId.ICYTEMPEST) {
            buffResults.add(new ConsumableStackResult(
                    null,
                    BuffId.PRIMORDIALICESTACKS,
                    state.getState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS),
                    StackConsumptionTiming.POST_DAMAGE
            ));
        }
        return buffResults;
    }

    public static List<BuffId> resolveStacksRemovedWithExpiredBuff(BuffId expiredBuffId) {
        List<BuffId> resolvedBuffs = new ArrayList<>();
        if (expiredBuffId == BuffId.GRAVITATEBUFF) {
            resolvedBuffs.add(BuffId.GRAVITATESTACKS);
        }
        return resolvedBuffs;
    }

    public static List<AppliedBuffResult> resolveStackTriggeredBuffs(StackEffect stackEffect, SimulationState state) {
        List<AppliedBuffResult> buffIds = new ArrayList<>();
        if (stackEffect.getBuffId() == BuffId.PRIMORDIALICESTACKS &&
                state.getState().getEquipment().getOffhand().getEffect().contains(Effect.OFFHANDLENG)) {
            BuffDefinition buffDefinition = BuffProvider.get(BuffId.FROSTBLADES, BuffSource.PROC, state);
            AppliedBuffResult buffResult = new AppliedBuffResult(
                    buffDefinition,
                    state.getState().getEquipment().getOffhand().getTitle().equalsIgnoreCase("dark sliver of leng") ?
                            15 : buffDefinition.getDurationTicks());
            buffIds.add(buffResult);
        }
        return buffIds;
    }
}
