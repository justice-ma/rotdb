package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.AdrenalineContext;

public class AdrenalinePotionResolver {
    public static double resolve(AdrenalineContext ac, BuffContext buff) {
        if (buff.has(BuffId.ADRENALINEPOTION)) {
            ac.addAdrenaline(25);
        }

        if (buff.has(BuffId.SUPERADRENALINEPOTION)) {
            ac.addAdrenaline(30);
        }

        if (buff.has(BuffId.ADRENALINERENEWAL)) {
            ac.addAdrenaline(4);
        }

        return ac.getAdrenaline();
    }
}
