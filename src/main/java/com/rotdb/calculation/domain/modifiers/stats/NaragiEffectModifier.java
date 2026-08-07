package com.rotdb.calculation.domain.modifiers.stats;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.modifiers.StatModifier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;

public class NaragiEffectModifier implements StatModifier {

    @Override
    public void apply(SkillsContext skillsContext, BuffContext buffContext) {
        if (buffContext.has(BuffId.NARAGI_EFFECT)) {
            skillsContext.setBoostedAttack(255);
            skillsContext.setBoostedRanged(255);
            skillsContext.setBoostedMagic(255);
            skillsContext.setBoostedNecromancy(255);
            skillsContext.setBoostedStrength(255);
            skillsContext.setBoostedDefence(255);
        }
    }
}
