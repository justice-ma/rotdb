package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.AdrenalineContext;

public class MeteorStrikeResolver {
    public static double resolve(AdrenalineContext ac, BuffContext buff) {
        if (buff.has(BuffId.METEORSTRIKE)) {
            ac.setAdrenaline(ac.getAdrenaline() + 5);
        }
        return ac.getAdrenaline();
    }
}
