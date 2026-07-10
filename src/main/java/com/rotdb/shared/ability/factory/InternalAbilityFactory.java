package com.rotdb.shared.ability.factory;

import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;

import java.util.List;

import static com.rotdb.shared.ability.AbilityId.INCITEFEARPROC;
import static com.rotdb.shared.ability.Handedness.BOTH;
import static com.rotdb.shared.combat.domain.model.enums.AbilityTier.BASIC;
import static com.rotdb.shared.combat.domain.model.enums.CombatStyles.MAGIC;
import static com.rotdb.shared.combat.domain.model.enums.Targetting.MULTI_TARGET;

public class InternalAbilityFactory {
    public static AbilityContext inciteFearProc() {
        return new AbilityContext(1,
                List.of(new AbilityHitsContext(0.1, 0.5, false, BASIC, 3)),
                "Incite Fear Proc",
                0,
                21,
                false,
                BOTH,
                MULTI_TARGET,
                MAGIC,
                INCITEFEARPROC);
    }
}
