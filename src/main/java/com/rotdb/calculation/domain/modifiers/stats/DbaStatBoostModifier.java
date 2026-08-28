package com.rotdb.calculation.domain.modifiers.stats;

import com.rotdb.calculation.domain.modifiers.StatModifier;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;

public class DbaStatBoostModifier implements StatModifier {
    public void apply(SkillsContext skill, BuffContext buffs) {
        if (buffs.has(BuffId.DBA)) {
            int def = skill.getBoostedDefence() == null ? skill.getBaseDefence() == null ? 1 : skill.getBaseDefence() : skill.getBoostedDefence();
            int att = skill.getBoostedAttack() == null ? skill.getBaseAttack() == null ? 1 : skill.getBaseAttack() : skill.getBoostedAttack();
            int magic = skill.getBoostedMagic() == null ? skill.getBaseMagic() == null ? 1 : skill.getBaseMagic() : skill.getBoostedMagic();
            int ranged = skill.getBoostedRanged() == null ? skill.getBaseRanged() == null ? 1 : skill.getBaseRanged() : skill.getBoostedRanged();
            int necro = skill.getBoostedNecromancy() == null ? skill.getBaseNecromancy() == null ? 1 : skill.getBaseNecromancy() : skill.getBoostedNecromancy();

            int boost = (((int) (att * 0.1) + (int) (def * 0.1) +
                    (int) (magic * 0.1) + (int) (ranged * 0.1) +
                    (int) (necro * 0.1)) / 4) + 10;
            skill.setBoostedStrength(Math.max(skill.getBoostedStrength() == null ? skill.getBaseStrength() :
                    skill.getBoostedStrength(), skill.getBaseStrength() + boost));
        }
    }
}
