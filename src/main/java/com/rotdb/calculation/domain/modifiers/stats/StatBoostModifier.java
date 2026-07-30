package com.rotdb.calculation.domain.modifiers.stats;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.combat.domain.model.enums.Stats;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.PotionContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;
import com.rotdb.calculation.domain.modifiers.Modifier;

import java.util.HashSet;
import java.util.Set;

public class StatBoostModifier implements Modifier {
    public void apply(AggregatedCalculationContext aggregatedCalculationContext) {
        CalculationContext context = aggregatedCalculationContext.getSnapshotContext();

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
            if (pot.getStat() == Stats.ALL) {
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
                                boosted.add(Stats.ATTACK);
                                boosted.add(Stats.STRENGTH);
                                boosted.add(Stats.DEFENCE);
                                boosted.add(Stats.MAGIC);
                                boosted.add(Stats.RANGED);
                                boosted.add(Stats.NECROMANCY);
                        } else if (pot.getStat() == Stats.ATTACK && !boosted.contains(Stats.ATTACK)) {
                skill.setBoostedAttack(skill.getBaseAttack() +
                        (int) (skill.getBaseAttack() * multiplicativeBonus) + flatBonus);
                                boosted.add(Stats.ATTACK);
                        } else if (pot.getStat() == Stats.STRENGTH && !boosted.contains(Stats.STRENGTH)) {
                skill.setBoostedStrength(skill.getBaseStrength() +
                        (int) (skill.getBaseStrength() * multiplicativeBonus) + flatBonus);
                                boosted.add(Stats.STRENGTH);
                        } else if (pot.getStat() == Stats.DEFENCE && !boosted.contains(Stats.DEFENCE)) {
                skill.setBoostedDefence(skill.getBaseDefence() +
                        (int) (skill.getBaseDefence() * multiplicativeBonus) + flatBonus);
                                boosted.add(Stats.DEFENCE);
                        } else if (pot.getStat() == Stats.MAGIC && !boosted.contains(Stats.MAGIC)) {
                skill.setBoostedMagic(skill.getBaseMagic() +
                        (int) (skill.getBaseMagic() * multiplicativeBonus) + flatBonus);
                                boosted.add(Stats.MAGIC);
                        } else if (pot.getStat() == Stats.RANGED && !boosted.contains(Stats.RANGED)) {
                skill.setBoostedRanged(skill.getBaseRanged() +
                        (int) (skill.getBaseRanged() * multiplicativeBonus) + flatBonus);
                                boosted.add(Stats.RANGED);
                        } else if (pot.getStat() == Stats.NECROMANCY && !boosted.contains(Stats.NECROMANCY)) {
                skill.setBoostedNecromancy(skill.getBaseNecromancy() +
                        (int) (skill.getBaseNecromancy() * multiplicativeBonus) + flatBonus);
                                boosted.add(Stats.NECROMANCY);
            }
        }
    }
}
