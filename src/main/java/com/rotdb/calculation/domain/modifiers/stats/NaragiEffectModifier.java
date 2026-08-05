package com.rotdb.calculation.domain.modifiers.stats;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;

public class NaragiEffectModifier implements Modifier {

    @Override
    public void apply(CalculationContext context) {
        if (context.getBuffs().has(BuffId.NARAGI_EFFECT)) {
            context.getSkills().setBoostedAttack(255);
            context.getSkills().setBoostedRanged(255);
            context.getSkills().setBoostedMagic(255);
            context.getSkills().setBoostedNecromancy(255);
            context.getSkills().setBoostedStrength(255);
            context.getSkills().setBoostedDefence(255);
        }
    }
}
