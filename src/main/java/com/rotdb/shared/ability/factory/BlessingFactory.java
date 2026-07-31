package com.rotdb.shared.ability.factory;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.Handedness;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.HitType;
import com.rotdb.shared.combat.domain.model.enums.Targetting;

import java.util.List;

public class BlessingFactory {
    public static AbilityContext lightOfSaradomin(AbilityId id) {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.4, 0.6, false, AbilityTier.BLESSING, 1)),
                "Light of Saradomin",
                0,
                15,
                false,
                Handedness.BOTH,
                Targetting.SINGLE_TARGET,
                id.getStyle(),
                id
                );
    }

    public static AbilityContext bash(AbilityId id) {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.2, 1, false, AbilityTier.BLESSING, 1)),
                "Bash",
                0,
                25,
                false,
                Handedness.BOTH,
                Targetting.SINGLE_TARGET,
                id.getStyle(),
                id
        );
    }

    public static AbilityContext barkscales(AbilityId id) {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.8, 1.2, true, AbilityTier.BLESSING, 1, HitType.POISON, -1)),
                "Barkscales",
                0,
                25,
                false,
                Handedness.BOTH,
                Targetting.MULTI_TARGET,
                id.getStyle(),
                id
        );
    }

    public static AbilityContext infernoOfZamorak(AbilityId id) {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(1.0, 1.2, false, AbilityTier.BLESSING, 1)),
                "Inferno of Zamorak",
                0,
                0,
                false,
                Handedness.BOTH,
                Targetting.SINGLE_TARGET,
                id.getStyle(),
                id
        );
    }
}
