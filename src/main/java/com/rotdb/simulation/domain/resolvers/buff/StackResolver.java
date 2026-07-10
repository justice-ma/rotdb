package com.rotdb.simulation.domain.resolvers.buff;

import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.calculation.domain.model.HitResult;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.*;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.simulation.domain.model.buff.AppliedBuffResult;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.ConsumableStackResult;
import com.rotdb.simulation.domain.model.buff.StackEffect;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
import com.rotdb.simulation.domain.model.buff.enums.StackClampingBehaviour;
import com.rotdb.simulation.domain.model.buff.enums.StackConsumptionTiming;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.model.context.TimelineHit;
import com.rotdb.simulation.domain.model.context.TriggeredHitResult;
import com.rotdb.simulation.domain.provider.BuffProvider;
import com.rotdb.simulation.domain.resolvers.cooldown.AbilityCooldownKeyResolver;

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
                    BuffSource.STACK,
                    null,
                    null,
                    null,
                    StackClampingBehaviour.CLAMP
            ));
        }

        if (eq.getMainhand().getTitle() != null && eq.getMainhand().getTitle().equalsIgnoreCase("bow of the last guardian") &&
                abilityContext.getCombatStyle() == CombatStyles.RANGED && abilityContext.getDamageCalculationTiming() == DamageCalculationTiming.ON_RELEASE) {
            if (abilityContext.getId() != AbilityId.BALANCEBYFORCE || state.getState().getBuffs().stacks(BuffId.PERFECTEQUILIBRIUMSTACKS) < 3) {
                int hits = 0;
                for (AbilityHitsContext hit : abilityContext.getHits()) {
                    if (abilityContext.getId() == AbilityId.CRYSTALRAIN) {
                        hits = 1;
                        break;
                    }
                    if (!hit.isDot()) {
                        hits++;
                    }
                }
                stackEffects.add(new StackEffect(
                        BuffId.PERFECTEQUILIBRIUMSTACKS,
                        hits,
                        BuffSource.STACK,
                        null,
                        null,
                        state.getState().getBuffs().has(BuffId.BALANCEBYFORCE) ? 3 : null,
                        StackClampingBehaviour.ROLL_OVER
                ));
            }
        }

        if (abilityContext.getCombatStyle() == CombatStyles.MAGIC &&
                state.getState().getSpell().getSpell() == Spells.EXSANGUINATE &&
                abilityContext.getDamageCalculationTiming() == DamageCalculationTiming.ON_RELEASE) {
            stackEffects.add(new StackEffect(
                    BuffId.TITHESTACKS,
                    1,
                    BuffSource.STACK,
                    null,
                    null,
                    null,
                    StackClampingBehaviour.CLAMP
            ));
        }

        if (abilityContext.getCombatStyle() == CombatStyles.MAGIC &&
                state.getState().getSpell().getSpell() == Spells.INCITEFEAR &&
                abilityContext.getDamageCalculationTiming() == DamageCalculationTiming.ON_RELEASE) {
            stackEffects.add(new StackEffect(
                    BuffId.INCITEFEARSTACKS,
                    1,
                    BuffSource.STACK,
                    null,
                    null,
                    null,
                    StackClampingBehaviour.CLAMP
            ));
        }

        if (abilityContext.getId() == AbilityId.SOULSAP) {
            stackEffects.add(new StackEffect(
                    BuffId.SOULSTACKS,
                    1,
                    BuffSource.STACK,
                    null,
                    null,
                    state.getState().getEquipment().getOffhand().getEffect().contains(Effect.SOULBOUNDLANTERN) ? 5 :
                            null,
                    StackClampingBehaviour.CLAMP
            ));
        }

        if (state.getState().getEquipment().getMainhand().getEffect().contains(Effect.DEVOURERSGUARD) && (
                abilityContext.getId() == AbilityId.NECROMANCYAUTO ||
                (abilityContext.getId() == AbilityId.SOULCRUSH ||
                (state.getState().getBuffs().has(BuffId.SOULCRUSH) &&
                (abilityContext.getId() == AbilityId.SOULSAP ||
                abilityContext.getId() == AbilityId.SOULSTRIKE ||
                abilityContext.getId() == AbilityId.VOLLEYOFSOULS ||
                abilityContext.getId() == AbilityId.SPECTRALSCYTHE ||
                abilityContext.getId() == AbilityId.SPECTRALHURRICANE ||
                abilityContext.getId() == AbilityId.SPECTRALMETEORSTRIKE))))) {
            int stackDelta = abilityContext.getId() == AbilityId.NECROMANCYAUTO ? 1 : BuffId.SOULREAVE.getMaximumStacks();
            stackEffects.add(new StackEffect(
                BuffId.SOULREAVE,
                    stackDelta,
                    BuffSource.STACK,
                    null,
                    null,
                    null,
                    StackClampingBehaviour.CLAMP
            ));
        }

        if (state.getState().getEquipment().getMainhand().getEffect().contains(Effect.OMNIGUARD) && (
                abilityContext.getId() == AbilityId.NECROMANCYAUTO ||
                (abilityContext.getId() == AbilityId.DEATHESSENCE ||
                (state.getState().getBuffs().has(BuffId.DEATHESSENCE) &&
                (abilityContext.getId() == AbilityId.TOUCHOFDEATH ||
                abilityContext.getId() == AbilityId.DEATHSKULLS ||
                abilityContext.getId() == AbilityId.DEATHSKULLSIGNEOUS ||
                abilityContext.getId() == AbilityId.FINGEROFDEATH))))) {
            int stackDelta = abilityContext.getId() == AbilityId.NECROMANCYAUTO ? 1 : BuffId.DEATHSPARK.getMaximumStacks();
            stackEffects.add(new StackEffect(
                    BuffId.DEATHSPARK,
                    stackDelta,
                    BuffSource.STACK,
                    null,
                    null,
                    null,
                    StackClampingBehaviour.CLAMP
            ));
        }

        if (abilityContext.getId() == AbilityId.TOUCHOFDEATH || (abilityContext.getId() == AbilityId.NECROMANCYAUTO && state.getState().getBuffs().has(BuffId.LIVINGDEATH))) {
            int stackDelta = abilityContext.getId() == AbilityId.TOUCHOFDEATH ? 4 : 2;
            stackEffects.add(new StackEffect(
                    BuffId.NECROSIS,
                    stackDelta,
                    BuffSource.STACK,
                    null,
                    null,
                    null,
                    StackClampingBehaviour.CLAMP
            ));
        }

        if (abilityContext.getId() == AbilityId.COMMANDSKELETONWARRIOR || abilityContext.getId() == AbilityId.CONJURESKELETONWARRIOR) {
            stackEffects.add(new StackEffect(
                    BuffId.RAGE,
                    1,
                    BuffSource.STACK,
                    null,
                    null,
                    null,
                    StackClampingBehaviour.CLAMP
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
                    BuffSource.STACK,
                    procChance,
                    null,
                    null,
                    StackClampingBehaviour.CLAMP
            ));
        }

        if (eq.getCombatStyle() == CombatStyles.MELEE && state.getState().getBuffs().has(BuffId.GRAVITATEBUFF) && abilityContext.getCombatStyle() == CombatStyles.MELEE) {
            stackEffects.add(new StackEffect(
                    BuffId.GRAVITATESTACKS,
                    1,
                    BuffSource.STACK,
                    null,
                    null,
                    null,
                    StackClampingBehaviour.CLAMP
            ));
        }

        if (eq.getMainhand().getTitle() != null && eq.getMainhand().getTitle().equalsIgnoreCase("bow of the last guardian") &&
                abilityContext.getCombatStyle() == CombatStyles.RANGED && abilityContext.getDamageCalculationTiming() == DamageCalculationTiming.ON_HIT &&
                !hit.isDot() && hit.getHitType() != HitType.PERFECTEQUILIBRIUM) {
            stackEffects.add(new StackEffect(
                    BuffId.PERFECTEQUILIBRIUMSTACKS,
                    1,
                    BuffSource.STACK,
                    null,
                    null,
                    state.getState().getBuffs().has(BuffId.BALANCEBYFORCE) ? 3 : null,
                    StackClampingBehaviour.ROLL_OVER
            ));
        }

        if (eq.getAmmo().getEffect().contains(Effect.DEATHSPOREARROWS) && abilityContext.getId().getStyle() == CombatStyles.RANGED &&
                !state.getBuffCooldownMap().containsKey(BuffCooldownKeyResolver.resolve(BuffId.DEATHSPORESTACKS))) {
            stackEffects.add(new StackEffect(
                    BuffId.DEATHSPORESTACKS,
                    1,
                    BuffSource.STACK,
                    null,
                    null,
                    null,
                    StackClampingBehaviour.CLAMP
            ));
        }

        if (abilityContext.getCombatStyle() == CombatStyles.MAGIC &&
                state.getState().getSpell().getSpell() == Spells.EXSANGUINATE &&
                abilityContext.getDamageCalculationTiming() == DamageCalculationTiming.ON_HIT) {
            stackEffects.add(new StackEffect(
                    BuffId.TITHESTACKS,
                    1,
                    BuffSource.STACK,
                    null,
                    null,
                    null,
                    StackClampingBehaviour.CLAMP
            ));
        }

        if (abilityContext.getCombatStyle() == CombatStyles.MAGIC &&
                state.getState().getSpell().getSpell() == Spells.INCITEFEAR &&
                abilityContext.getDamageCalculationTiming() == DamageCalculationTiming.ON_HIT) {
            stackEffects.add(new StackEffect(
                    BuffId.INCITEFEARSTACKS,
                    1,
                    BuffSource.STACK,
                    null,
                    null,
                    null,
                    StackClampingBehaviour.CLAMP
            ));
        }

        if ((eq.getMainhand().getEffect().contains(Effect.SONGOFDESTRUCTION) || eq.getOffhand().getEffect().contains(Effect.SONGOFDESTRUCTION)) && hit.isDot()) {
            stackEffects.add(new StackEffect(
                    BuffId.ESSENCECORRUPTIONSTACKS,
                    1,
                    BuffSource.STACK,
                    null,
                    null,
                    null,
                    StackClampingBehaviour.CLAMP
            ));
        }

        return stackEffects;
    }

    public static List<StackEffect> resolveOnHit(SimulationState state, AbilityContext abilityContext,
                                                 AbilityHitsContext hit) {
        List<StackEffect> stackEffects = new ArrayList<>();
        EquipmentModel eq = state.getState().getEquipment();
        if ((eq.getMainhand().getEffect().contains(Effect.PRIMORDIALICESTACKS) || eq.getOffhand().getEffect().contains(Effect.PRIMORDIALICESTACKS)) &&
                abilityContext.getId().getStyle() == CombatStyles.MELEE && !hit.isDot()) {
            double procChance = 0.0;
            procChance += eq.getMainhand().getTitle() != null && eq.getMainhand().getTitle().equalsIgnoreCase("dark shard of leng") &&
                    eq.getMainhand().getEffect().contains(Effect.PRIMORDIALICESTACKS) ? 0.1 :
                    eq.getMainhand().getTitle() != null && eq.getMainhand().getEffect().contains(Effect.PRIMORDIALICESTACKS) ? 0.05 : 0;
            procChance += eq.getOffhand().getTitle() != null && eq.getOffhand().getTitle().equalsIgnoreCase("dark sliver of leng") &&
                    eq.getOffhand().getEffect().contains(Effect.PRIMORDIALICESTACKS) ? 0.02 :
                    eq.getOffhand().getTitle() != null && eq.getMainhand().getEffect().contains(Effect.PRIMORDIALICESTACKS) ? 0.01 : 0;
            stackEffects.add(new StackEffect(
                    BuffId.PRIMORDIALICESTACKS,
                    1,
                    BuffSource.STACK,
                    procChance,
                    null,
                    null,
                    StackClampingBehaviour.CLAMP
            ));
        }

        return stackEffects;
    }

    public static List<StackEffect> resolveOnReleaseResolvedDamage(SimulationState state, DamageResult damageResult) {
        List<StackEffect> stackEffects = new ArrayList<>();
        EquipmentModel eq = state.getState().getEquipment();
        AbilityContext abilityContext = AbilityProvider.get(damageResult.getHit().getFirst().getParentAbility(), eq);
        if (eq.getAmmo().getEffect().contains(Effect.DEATHSPOREARROWS) && abilityContext.getId().getStyle() == CombatStyles.RANGED &&
                !state.getBuffCooldownMap().containsKey(BuffCooldownKeyResolver.resolve(BuffId.DEATHSPORESTACKS))) {
            int hits = 0;
            for (HitResult hit : damageResult.getHit()) {
                if (!hit.isDot()) {
                    hits++;
                }
            }
            stackEffects.add(new StackEffect(
                    BuffId.DEATHSPORESTACKS,
                    hits,
                    BuffSource.STACK,
                    null,
                    null,
                    null,
                    StackClampingBehaviour.CLAMP
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
                    BuffProvider.get(BuffId.WENARROWPROC, BuffSource.PROC, state).getDurationTicks(),
                    null
            );
            buffResults.add(new ConsumableStackResult(
                    appliedBuffResult,
                    BuffId.WENARROWSTACKS,
                    BuffId.WENARROWSTACKS.getMaximumStacks(),
                    state.getState().getBuffs().stacks(BuffId.WENARROWSTACKS),
                    StackConsumptionTiming.PRE_DAMAGE
            ));
        }

        if (abilityPlacement.getPlacedAbility() == AbilityId.ICYTEMPEST) {
            buffResults.add(new ConsumableStackResult(
                    null,
                    BuffId.PRIMORDIALICESTACKS,
                    BuffId.PRIMORDIALICESTACKS.getMaximumStacks(),
                    state.getState().getBuffs().stacks(BuffId.PRIMORDIALICESTACKS),
                    StackConsumptionTiming.POST_DAMAGE
            ));
        }

        if (abilityPlacement.getPlacedAbility() == AbilityId.BALANCEBYFORCE &&
                state.getState().getBuffs().stacks(BuffId.PERFECTEQUILIBRIUMSTACKS) >= 3 &&
                state.getState().getEquipment().getMainhand().getTitle() != null &&
                state.getState().getEquipment().getMainhand().getTitle().equalsIgnoreCase("bow of the last guardian")) {
            buffResults.add(new ConsumableStackResult(
                    null,
                    BuffId.PERFECTEQUILIBRIUMSTACKS,
                    state.getState().getBuffs().stacks(BuffId.PERFECTEQUILIBRIUMSTACKS),
                    state.getState().getBuffs().stacks(BuffId.PERFECTEQUILIBRIUMSTACKS),
                    StackConsumptionTiming.POST_DAMAGE
            ));
        }

        if ((abilityPlacement.getPlacedAbility() == AbilityId.VOLLEYOFSOULS ||
                abilityPlacement.getPlacedAbility() == AbilityId.SOULCRUSH ||
                abilityPlacement.getPlacedAbility() == AbilityId.SOULSTRIKE) &&
                state.getState().getBuffs().has(BuffId.SOULSTACKS)) {
            int consumedAmount = abilityPlacement.getPlacedAbility() == AbilityId.SOULSTRIKE ? 1 :
                    state.getState().getBuffs().stacks(BuffId.SOULSTACKS);
            buffResults.add(new ConsumableStackResult(
                    null,
                    BuffId.SOULSTACKS,
                    consumedAmount,
                    consumedAmount,
                    StackConsumptionTiming.POST_DAMAGE
            ));
        }

        if (abilityPlacement.getPlacedAbility() == AbilityId.NECROMANCYAUTO &&
            state.getState().getBuffs().has(BuffId.SOULREAVE) && state.getState().getBuffs().stacks(BuffId.SOULREAVE) == 4) {
            AppliedBuffResult appliedBuffResult = new AppliedBuffResult(
                    BuffProvider.get(BuffId.SOULSTACKS, BuffSource.STACK, state),
                    null,
                    1
            );
            buffResults.add(new ConsumableStackResult(
                    appliedBuffResult,
                    BuffId.SOULREAVE,
                    state.getState().getBuffs().stacks(BuffId.SOULREAVE),
                    state.getState().getBuffs().stacks(BuffId.SOULREAVE),
                    StackConsumptionTiming.POST_DAMAGE
            ));
        }

        if (abilityPlacement.getPlacedAbility() == AbilityId.NECROMANCYAUTO &&
            state.getState().getBuffs().has(BuffId.DEATHSPARK) && state.getState().getBuffs().stacks(BuffId.DEATHSPARK) == 5) {
            buffResults.add(new ConsumableStackResult(
                    null,
                    BuffId.DEATHSPARK,
                    state.getState().getBuffs().stacks(BuffId.DEATHSPARK),
                    state.getState().getBuffs().stacks(BuffId.DEATHSPARK),
                    StackConsumptionTiming.POST_DAMAGE
            ));
        }

        if ((abilityPlacement.getPlacedAbility() == AbilityId.FINGEROFDEATH ||
                abilityPlacement.getPlacedAbility() == AbilityId.DEATHGRASP) &&
                state.getState().getBuffs().has(BuffId.NECROSIS)) {
            int consumedStacks = abilityPlacement.getPlacedAbility() == AbilityId.DEATHGRASP ?
                    state.getState().getBuffs().stacks(BuffId.NECROSIS) :
                    Math.min(6, state.getState().getBuffs().stacks(BuffId.NECROSIS));
            buffResults.add(new ConsumableStackResult(
                    null,
                    BuffId.NECROSIS,
                    consumedStacks,
                    consumedStacks,
                    StackConsumptionTiming.POST_DAMAGE
            ));
        }

        return buffResults;
    }

    public static List<ConsumableStackResult> resolveEndOfTickStackTriggers(SimulationState state) {
        List<ConsumableStackResult> buffResults = new ArrayList<>();
        if (state.getState().getBuffs().has(BuffId.DEATHSPORESTACKS) && state.getState().getBuffs().stacks(BuffId.DEATHSPORESTACKS) >= 12) {
            AppliedBuffResult appliedBuffResult = new AppliedBuffResult(
                    BuffProvider.get(BuffId.FEASTINGSPORES, BuffSource.PROC, state),
                    BuffProvider.get(BuffId.FEASTINGSPORES, BuffSource.PROC, state).getDurationTicks(),
                    null
            );
            buffResults.add(new ConsumableStackResult(
                    appliedBuffResult,
                    BuffId.DEATHSPORESTACKS,
                    BuffId.DEATHSPORESTACKS.getMaximumStacks(),
                    state.getState().getBuffs().stacks(BuffId.DEATHSPORESTACKS),
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
                            15 : buffDefinition.getDurationTicks(),
                    null
            );
            buffIds.add(buffResult);
        }
        return buffIds;
    }

    public static List<TriggeredHitResult> resolveStackGeneratedAbilities(SimulationState state,
                                                                          AbilityPlacement parentAbility,
                                                                          Integer triggerTick) {
        List<TriggeredHitResult> triggeredHitResults = new ArrayList<>();
        if (triggerTick == null) {
            triggerTick = parentAbility.getReleaseTick();
        }
        if (state.getState().getBuffs().has(BuffId.INCITEFEARSTACKS) && state.getState().getBuffs().stacks(BuffId.INCITEFEARSTACKS) >= 5 &&
                !state.getAbilityCooldownMap().containsKey(AbilityCooldownKeyResolver.resolve(AbilityId.INCITEFEARPROC))) {
            triggeredHitResults.add(new TriggeredHitResult(
                    AbilityId.INCITEFEARPROC,
                    triggerTick,
                    parentAbility.getPlacementId(),
                    parentAbility.getPlacedAbility(),
                    3
            ));
        }
        return triggeredHitResults;
    }
}
