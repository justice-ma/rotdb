package com.rotdb.calculation.domain.modifiers;

import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;

public interface StatModifier {
    void apply(SkillsContext skillsContext, BuffContext buffContext);
}
