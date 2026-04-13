package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Perks;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.AdrenalineContext;
import com.rotdb.simulation.domain.model.context.RotationContext;

public class PerkAdrenalineResolver {
    public static double resolve(RotationContext rc, PerkContext perks, BuffContext buff) {
        AdrenalineContext ac = rc.getAdrenalineContext();
        AbilityContext ability = rc.getAbilityContext();

        if (perks.has(Perks.IMPATIENT)) {
            if (buff.has(BuffId.IMPATIENTPROC)) {
                ac.addAdrenaline(3);
            } else {
                ac.addAdrenaline((perks.rank(Perks.IMPATIENT) * 0.09) * 3);
            }
        }

        return ac.getAdrenaline();
    }
}
