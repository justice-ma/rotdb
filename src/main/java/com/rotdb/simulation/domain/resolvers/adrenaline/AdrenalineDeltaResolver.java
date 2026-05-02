package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.simulation.domain.model.context.AdrenalineContext;
import com.rotdb.simulation.domain.model.context.RotationSnapshot;

public class AdrenalineDeltaResolver {
    public static double resolve(RotationSnapshot rc) {
        AdrenalineContext ac = rc.getAdrenalineContext();
        AbilityContext ability = rc.getAbilityContext();

        if (ac.getAdrenaline() < ac.getMinimumBound()) {
            rc.getAdrenalineContext().setMessage("Warning: May not have adrenaline required for " + ability.getName());
        }

        if (ac.getAdrenaline() >= ac.getMaximumBound()) {
            ac.setAdrenaline(ac.getMaximumBound());
            rc.getAdrenalineContext().setMessage("Warning: Adrenaline is capped after " + ability.getName());
        }

        return ac.getAdrenaline();
    }
}