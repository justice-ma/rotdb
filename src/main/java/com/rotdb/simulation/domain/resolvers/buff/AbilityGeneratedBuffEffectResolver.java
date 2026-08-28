package com.rotdb.simulation.domain.resolvers.buff;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.model.GeneratedBuffEffect;
import com.rotdb.shared.ability.model.GeneratedBuffTiming;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
import com.rotdb.simulation.domain.model.context.AbilityPlacement;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.provider.BuffProvider;

import java.util.ArrayList;
import java.util.List;

import static com.rotdb.shared.combat.domain.model.enums.CombatStyles.MELEE;

public class AbilityGeneratedBuffEffectResolver {
    public static List<GeneratedBuffEffect> resolve(AbilityPlacement abilityPlacement, SimulationState state, AbilityContext abilityContext, GeneratedBuffTiming timing, boolean vestmentsBleedActiveAtTickStart) {
        EquipmentModel eq = state.getState().getEquipment();
        List<GeneratedBuffEffect> buffs = new ArrayList<>();
        int dracoPieces = eq.getTotalEliteDracolichPieces();
        if (abilityContext.getId() == AbilityId.RAPIDFIRE && dracoPieces >= 3) {
            BuffDefinition buffDefinition = BuffProvider.get(BuffId.RAPIDFIREBUFF, BuffSource.ABILITY_GENERATED, state);
            buffs.add(new GeneratedBuffEffect(
                    BuffId.RAPIDFIREBUFF,
                    GeneratedBuffTiming.ON_COMPLETION,
                    buffDefinition.getDurationTicks() + (dracoPieces == 4 ? 3 : dracoPieces == 5 ? 6 : 0)
            ));
        }

        int tumekensPieces = eq.getTotalTumekensPieces();
        if (abilityContext.getId() == AbilityId.ASPHYXIATE) {
            BuffDefinition buffDefinition = BuffProvider.get(BuffId.ASPHYXIATEBUFF, BuffSource.ABILITY_GENERATED, state);
            buffs.add(new GeneratedBuffEffect(
                    BuffId.ASPHYXIATEBUFF,
                    GeneratedBuffTiming.ON_COMPLETION,
                    buffDefinition.getDurationTicks() + (tumekensPieces == 5 ? 9 : 0)
            ));
        }

        if (abilityContext.getId() == AbilityId.REND && eq.getGloves().getEffect().contains(Effect.GLOVESOFPASSAGE)) {
            BuffDefinition rend = BuffProvider.get(BuffId.REND, BuffSource.ABILITY_GENERATED, state);
            BuffDefinition rendBleed = BuffProvider.get(BuffId.REND_BLEED, BuffSource.ABILITY_GENERATED, state);
            buffs.add(new GeneratedBuffEffect(
                    BuffId.REND,
                    GeneratedBuffTiming.ON_RELEASE,
                    rend.getDurationTicks()
            ));
            buffs.add(new GeneratedBuffEffect(
                    BuffId.REND_BLEED,
                    GeneratedBuffTiming.ON_RELEASE,
                    rendBleed.getDurationTicks()
            ));
        }

        if (abilityContext.getId() == AbilityId.GREATERBARGE && state.getState().getBuffs().stacks(BuffId.TIMESINCELASTATTACK) > 7) {
            BuffDefinition greaterBarge = BuffProvider.get(BuffId.GREATERBARGE, BuffSource.ABILITY_GENERATED, state);
            buffs.add(new GeneratedBuffEffect(
                    BuffId.GREATERBARGE,
                    GeneratedBuffTiming.ON_CAST,
                    greaterBarge.getDurationTicks()
            ));
        }

        if (abilityContext.getCombatStyle() == MELEE && abilityContext.getId().getTier() == AbilityTier.ULTIMATE && eq.getTotalVestmentsOfHavoc() > 1 && !vestmentsBleedActiveAtTickStart) {
            BuffDefinition vestments = BuffProvider.get(BuffId.VESTMENTSBLEED, BuffSource.ABILITY_GENERATED, state);
            buffs.add(new GeneratedBuffEffect(
                    BuffId.VESTMENTSBLEED,
                    GeneratedBuffTiming.ON_CAST,
                    vestments.getDurationTicks()
            ));
        }
        return buffs;
    }
}
