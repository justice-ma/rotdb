package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Perks;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.AdrenalineContext;
import com.rotdb.simulation.domain.model.context.RotationSnapshot;

import java.util.ArrayList;
import java.util.List;

public class PerkAdrenalineResolver {
    public static double resolve(RotationSnapshot rc, PerkContext perks, BuffContext buff) {
        AdrenalineContext ac = rc.getAdrenalineContext();
        AbilityContext ability = rc.getAbilityContext();

        double adrenalineDelta = 0;

        if (perks.has(Perks.IMPATIENT) && ability.getId().getTier() == AbilityTier.BASIC) {
            if (buff.has(BuffId.IMPATIENTPROC)) {
                adrenalineDelta += 3;
            } else {
                adrenalineDelta += (perks.rank(Perks.IMPATIENT) * 0.09) * 3;
            }
        }

        if (perks.has(Perks.RELENTLESS) && ability.getAdrenaline() < 0 && buff.has(BuffId.RELENTLESSPROC)) {
            adrenalineDelta += ability.getAdrenaline();
        }

        List<AbilityId> invigoratingApplicable = new ArrayList<>(
                List.of(AbilityId.MELEEAUTO, AbilityId.RANGEDAUTO, AbilityId.MAGICAUTO, AbilityId.NECROMANCYAUTO));

        if (perks.has(Perks.INVIGORATING) && invigoratingApplicable.contains(ability.getId())) {
            double temp = ability.getAdrenaline();
            temp += adrenalineDelta;
            temp += buff.has(BuffId.FURYOFTHESMALL) ? 1 : 0;
            adrenalineDelta += temp * (perks.rank(Perks.INVIGORATING) * 0.05);
        }

        System.out.println("PERK: " + adrenalineDelta);
        return adrenalineDelta;
    }
}
