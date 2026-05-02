package com.rotdb.simulation.domain.resolvers.adrenaline;

import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;

public class PostNaturalInstinctsResolver {
    public static double resolve(AbilityContext ability, BuffContext buff, EquipmentModel eq) {
        double adrenalineDelta = 0;
        if (buff.has(BuffId.METEORSTRIKE) && eq.getMainhand().getClazz() == CombatStyles.MELEE) {
            adrenalineDelta += 4.5;
        }

        if (buff.has(BuffId.ADRENALINEPOTION)) {
            adrenalineDelta += 25;
        }

        if (buff.has(BuffId.SUPERADRENALINEPOTION)) {
            adrenalineDelta += 30;
        }

        if (buff.has(BuffId.ADRENALINERENEWAL)) {
            adrenalineDelta += 4;
        }

        if (buff.has(BuffId.VESTMENTSBLEED)) {
            adrenalineDelta += 0.5;
        }

        if (buff.has(BuffId.CONSERVATIONOFENERGY) && ability.getId().getTier() == AbilityTier.ULTIMATE) {
            adrenalineDelta += 10;
        }

        if ((buff.has(BuffId.RINGOFVIGOUR) || eq.getRing().getEffect().contains(Effect.RINGOFVIGOUR)) &&
                ability.getId().getTier() == AbilityTier.ULTIMATE) {
            adrenalineDelta += 10;
        }

        if (buff.has(BuffId.ASYLUMSURGEONSRINGPROC) && ability.getAdrenaline() < 0
                && eq.getRing().getEffect().contains(Effect.ASYLUMSURGEONSRING)) {
            adrenalineDelta += 15;
        }

        System.out.println("PNI: " + adrenalineDelta);
        return adrenalineDelta;
    }
}
