package com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike;

import com.rotdb.calculation.domain.model.ForcedCritResult;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.ForceCritSource;
import com.rotdb.shared.combat.domain.model.enums.HitType;
import com.rotdb.shared.combat.domain.model.player.BuffContext;

import static com.rotdb.shared.ability.AbilityId.*;

public class ForcedCritResolver {
    public static ForcedCritResult resolve (CalculationContext context, AbilityHitsContext hit) {
        BuffContext buff = context.getBuffs();
        AbilityContext ability = context.getAbility();
        if (buff.has(BuffId.GREATERFURYBUFF) && ability.getCombatStyle() == CombatStyles.MELEE && hit.getType() == HitType.BASE) return new ForcedCritResult(true, ForceCritSource.GREATER_FURY);
        if ((ability.getId() == SMOKETENDRILS || ability.getId() == SHADOWTENDRILS) && hit.getType() == HitType.BASE) return new ForcedCritResult(true, ForceCritSource.TENDRILS);
        return new ForcedCritResult(false, ForceCritSource.NONE);
    }
}
