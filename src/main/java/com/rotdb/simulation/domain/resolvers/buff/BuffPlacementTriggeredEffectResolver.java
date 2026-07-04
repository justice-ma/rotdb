package com.rotdb.simulation.domain.resolvers.buff;

import com.rotdb.shared.ability.model.GeneratedBuffEffect;
import com.rotdb.shared.ability.model.GeneratedBuffTiming;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
import com.rotdb.simulation.domain.model.context.BuffPlacement;
import com.rotdb.simulation.domain.model.context.SimulationState;
import com.rotdb.simulation.domain.provider.BuffProvider;

import java.util.ArrayList;
import java.util.List;

public class BuffPlacementTriggeredEffectResolver {
    public static List<GeneratedBuffEffect> resolve(BuffPlacement buffPlacement, SimulationState state) {
        EquipmentModel eq = state.getState().getEquipment();
        List<GeneratedBuffEffect> buffs = new ArrayList<>();
        if (buffPlacement.getBuffId() == BuffId.BERSERK && eq.getTotalVestmentsOfHavoc() > 1) {
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
