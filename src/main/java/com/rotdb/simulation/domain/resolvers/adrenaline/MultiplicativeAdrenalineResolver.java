package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.player.BuffContext;

public class MultiplicativeAdrenalineResolver {
    public static double resolve(BuffContext buff, AbilityContext ability) {
        double multiplier = 1;
        if (buff.has(BuffId.METEORSTRIKE) && ability.getId().getStyle() == CombatStyles.MELEE &&
                ability.getId().getTier() == AbilityTier.BASIC ) {
            multiplier *= 1.5;
        }

        if (buff.has(BuffId.NATURALINSTINCT)) {
            multiplier *= 2;
        }

        return multiplier;
    }
}