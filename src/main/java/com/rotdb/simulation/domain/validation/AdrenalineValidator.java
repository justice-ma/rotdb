package com.rotdb.simulation.domain.validation;

import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.RotationContext;
import com.rotdb.simulation.domain.resolvers.adrenaline.*;

public class AdrenalineValidator implements Validator {
    public boolean validate(RotationContext rc, EquipmentModel eq, BuffContext buff) {
        System.out.println("Before Ability Processing: " + rc.getAdrenalineContext().getAdrenaline() + " Upper: " + rc.getAdrenalineContext().getMaximumBound());

        AdrenalineBoundsResolver.resolve(rc, eq, buff);
        MeteorStrikeResolver.resolve(rc.getAdrenalineContext(), buff);
        AdrenalinePotionResolver.resolve(rc.getAdrenalineContext(), buff);
        EquipmentAdrenalineResolver.resolve(rc, eq, buff);

        AdrenalineDeltaResolver.resolve(rc);

        return true;
    }
}