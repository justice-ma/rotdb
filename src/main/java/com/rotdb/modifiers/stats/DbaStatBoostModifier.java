package com.rotdb.modifiers.stats;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.BuffId;
import com.rotdb.model.player.BuffContext;
import com.rotdb.model.player.SkillsContext;
import com.rotdb.modifiers.Modifier;

public class DbaStatBoostModifier implements Modifier {
    public void apply(CalculationContext context) {
        BuffContext buffs = context.getBuffs();
        SkillsContext skill = context.getSkills();
        if (buffs.has(BuffId.DBA)) {
            int boost = (((int) (skill.getBoostedAttack() * 0.1) + (int) (skill.getBoostedDefence() * 0.1) +
                    (int) (skill.getBoostedMagic() * 0.1) + (int) (skill.getBoostedRanged() * 0.1) +
                    (int) (skill.getBoostedNecromancy() * 0.1)) / 4) + 10;
            skill.setBoostedStrength(Math.max(skill.getBoostedStrength(), skill.getBaseStrength() + boost));
        }
    }
}
