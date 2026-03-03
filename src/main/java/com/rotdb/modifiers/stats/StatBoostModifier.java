package com.rotdb.modifiers.stats;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.Stats;
import com.rotdb.model.player.BuffContext;
import com.rotdb.model.player.PotionContext;
import com.rotdb.model.player.SkillsContext;
import com.rotdb.modifiers.Modifier;

import java.util.HashSet;
import java.util.Set;

import static com.rotdb.model.enums.Stats.*;

public class StatBoostModifier implements Modifier {
    public void apply(CalculationContext context) {
        SkillsContext skill = context.getSkills();
        BuffContext buffs = context.getBuffs();

        skill.setBoostedAttack(skill.getBaseAttack());
        skill.setBoostedStrength(skill.getBaseStrength());
        skill.setBoostedDefence(skill.getBaseDefence());
        skill.setBoostedMagic(skill.getBaseMagic());
        skill.setBoostedRanged(skill.getBaseRanged());
        skill.setBoostedNecromancy(skill.getBaseNecromancy());
        if (buffs.getPotionBuffs() == null) {
            return;
        }


        Set<Stats> boosted = new HashSet<>();
        for (PotionContext pot : buffs.getPotionBuffs()) {
            int flatBonus = pot.getPotion().getFlatBonus();
            double multiplicativeBonus = pot.getPotion().getMultiplicativeBonus();
            if (pot.getStat() == ALL) {
                skill.setBoostedAttack(Math.max(skill.getBaseAttack() +
                        (int) (skill.getBaseAttack() * multiplicativeBonus) + flatBonus, skill.getBoostedAttack()));
                skill.setBoostedStrength(Math.max(skill.getBaseStrength() +
                        (int) (skill.getBaseStrength() * multiplicativeBonus) + flatBonus, skill.getBoostedStrength()));
                skill.setBoostedDefence(Math.max(skill.getBaseDefence() +
                        (int) (skill.getBaseDefence() * multiplicativeBonus) + flatBonus, skill.getBoostedDefence()));
                skill.setBoostedMagic(Math.max(skill.getBaseMagic() +
                        (int) (skill.getBaseMagic() * multiplicativeBonus) + flatBonus, skill.getBoostedMagic()));
                skill.setBoostedRanged(Math.max(skill.getBaseRanged() +
                        (int) (skill.getBaseRanged() * multiplicativeBonus) + flatBonus, skill.getBoostedRanged()));
                skill.setBoostedNecromancy(Math.max(skill.getBaseNecromancy() +
                        (int) (skill.getBaseNecromancy() * multiplicativeBonus) + flatBonus, skill.getBoostedNecromancy()));
                boosted.add(ATTACK);
                boosted.add(STRENGTH);
                boosted.add(DEFENCE);
                boosted.add(MAGIC);
                boosted.add(RANGED);
                boosted.add(NECROMANCY);
            } else if (pot.getStat() == ATTACK && !boosted.contains(ATTACK)) {
                skill.setBoostedAttack(skill.getBaseAttack() +
                        (int) (skill.getBaseAttack() * multiplicativeBonus) + flatBonus);
                boosted.add(ATTACK);
            } else if (pot.getStat() == STRENGTH && !boosted.contains(STRENGTH)) {
                skill.setBoostedStrength(skill.getBaseStrength() +
                        (int) (skill.getBaseStrength() * multiplicativeBonus) + flatBonus);
                boosted.add(STRENGTH);
            } else if (pot.getStat() == DEFENCE && !boosted.contains(DEFENCE)) {
                skill.setBoostedDefence(skill.getBaseDefence() +
                        (int) (skill.getBaseDefence() * multiplicativeBonus) + flatBonus);
                boosted.add(DEFENCE);
            } else if (pot.getStat() == MAGIC && !boosted.contains(MAGIC)) {
                skill.setBoostedMagic(skill.getBaseMagic() +
                        (int) (skill.getBaseMagic() * multiplicativeBonus) + flatBonus);
                boosted.add(MAGIC);
            } else if (pot.getStat() == RANGED && !boosted.contains(RANGED)) {
                skill.setBoostedRanged(skill.getBaseRanged() +
                        (int) (skill.getBaseRanged() * multiplicativeBonus) + flatBonus);
                boosted.add(RANGED);
            } else if (pot.getStat() == NECROMANCY && !boosted.contains(NECROMANCY)) {
                skill.setBoostedNecromancy(skill.getBaseNecromancy() +
                        (int) (skill.getBaseNecromancy() * multiplicativeBonus) + flatBonus);
                boosted.add(NECROMANCY);
            }
        }
    }
}
