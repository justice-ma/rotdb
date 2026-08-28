package com.rotdb.simulation.domain.resolvers.buff;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.ConjureHitSource;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.provider.BuffProvider;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ConjureResolver {
    public static boolean isConjureDamage(AbilityPlacement abilityPlacement) {
        AbilityId ability = abilityPlacement.getPlacedAbility();
        return ability == AbilityId.CONJURESKELETONWARRIOR || ability == AbilityId.CONJUREPUTRIDZOMBIE ||
                ability == AbilityId.CONJUREVENGEFULGHOST || ability == AbilityId.COMMANDSKELETONWARRIOR ||
                ability == AbilityId.COMMANDPUTRIDZOMBIE || ability == AbilityId.COMMANDPHANTOMGUARDIAN;
    }

    public static List<ConjureHitSource> resolveHitSources(AbilityPlacement abilityPlacement, SimulationState state) {
        List<ConjureHitSource> conjureHitSources = new ArrayList<>();
        AbilityContext abilityContext = AbilityProvider.get(abilityPlacement.getPlacedAbility(), state.getState().getEquipment());
        if (abilityPlacement.getPlacedAbility() == AbilityId.COMMANDSKELETONWARRIOR) {
            ConjureHitSource skeletonWarrior = new ConjureHitSource(
                    AbilityId.COMMANDSKELETONWARRIORHIT,
                    abilityContext.getHits().getFirst().getHitTiming(),
                    abilityContext.getHits().getFirst().getHitTiming(),
                    BuffId.COMMANDSKELETONWARRIOR,
                    false
            );
            conjureHitSources.add(skeletonWarrior);
        } else if (abilityPlacement.getPlacedAbility() == AbilityId.CONJUREPUTRIDZOMBIE) {
            ConjureHitSource putridZombieHit = new ConjureHitSource(
                    AbilityId.PUTRIDZOMBIEHIT,
                    6,
                    6,
                    BuffId.PUTRIDZOMBIEDURATION,
                    false
            );

            ConjureHitSource putridZombiePoison = new ConjureHitSource(
                    AbilityId.PUTRIDZOMBIEPOISON,
                    3,
                    3,
                    BuffId.PUTRIDZOMBIEDURATION,
                    false
            );

            conjureHitSources.add(putridZombieHit);
            conjureHitSources.add(putridZombiePoison);
        }
        return conjureHitSources;
    }

    public static boolean usesScheduledDamage(AbilityPlacement abilityPlacement) {
        return abilityPlacement.getPlacedAbility() == AbilityId.COMMANDSKELETONWARRIOR || abilityPlacement.getPlacedAbility() == AbilityId.CONJUREPUTRIDZOMBIE;
    }

    public static boolean isConjureDamageHit(AbilityId  abilityId) {
        return abilityId == AbilityId.CONJURESKELETONWARRIOR || abilityId == AbilityId.COMMANDSKELETONWARRIORHIT ||
                abilityId == AbilityId.PUTRIDZOMBIEHIT || abilityId == AbilityId.PUTRIDZOMBIEPOISON;
    }

    public static boolean shouldSkipFirstRecursiveHit(AbilityId abilityId) {
        return abilityId != AbilityId.COMMANDSKELETONWARRIOR;
    }

    public static @Nullable BuffDefinition resolveBuffsRemovedWithExpiredConjure(BuffId buffId, SimulationState state) {
        if (buffId == BuffId.VENGEFULGHOSTDURATION) return BuffProvider.get(BuffId.HAUNTED, BuffSource.USER_PLACED, state);
        if (buffId == BuffId.SKELETONWARRIORDURATION) return BuffProvider.get(BuffId.RAGE, BuffSource.STACK, state);
        return null;
    }

    public static boolean hasNecromancyConduit(EquipmentModel equipmentModel) {
        return equipmentModel.getOffhand().getEffect().contains(Effect.NECROMANCY_CONDUIT);
    }

    public static boolean hasConjureMaintainingOffhand(EquipmentModel equipmentModel) {
        return equipmentModel.getOffhand().getEffect().contains(Effect.NECROMANCY_CONDUIT) ||
                equipmentModel.getOffhand().getEffect().contains(Effect.NECROMANCY_SHIELD);
    }
}
