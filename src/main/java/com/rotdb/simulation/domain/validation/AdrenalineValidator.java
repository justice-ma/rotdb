package com.rotdb.simulation.domain.validation;

import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.RotationSnapshot;
import com.rotdb.simulation.domain.resolvers.adrenaline.*;

public class AdrenalineValidator implements Validator {
    public boolean validate(RotationSnapshot snapshot, PerkContext perks, EquipmentModel eq, BuffContext buff) {
        System.out.println("Before Ability Processing: " + snapshot.getAdrenalineContext().getAdrenaline() + " Upper: " + snapshot.getAdrenalineContext().getMaximumBound());
        AbilityContext ability = snapshot.getAbilityContext();
        AdrenalineBoundsResolver.resolve(snapshot, eq, buff);

        double adrenalineDelta =
                ((ability.getAdrenaline() +
                        AdrenalineBuffUltimateResolver.resolve(ability, buff, eq) +
                        EquipmentAdrenalineResolver.resolve(snapshot, eq, buff) +
                        PerkAdrenalineResolver.resolve(snapshot, perks, buff)) *
                        MultiplicativeAdrenalineResolver.resolve(buff, ability)) +
                        PostNaturalInstinctsResolver.resolve(ability, buff, eq);

        System.out.println("TOT: " + adrenalineDelta);

        snapshot.getAdrenalineContext().addAdrenaline(adrenalineDelta);

        AdrenalineDeltaResolver.resolve(snapshot);

        return true;
    }
}