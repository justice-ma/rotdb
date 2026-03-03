package com.rotdb.resolvers.abilityDamage.criticalStrike;

import com.rotdb.model.context.CalculationContext;

public class BaseCritResolver {
    public static double resolve(CalculationContext context) {
        int strength = context.getSkills().getRevelvantStregthLevel(context);
        double criticalStrikeDamage = 0;

        if (strength < 20) {
            criticalStrikeDamage = 0.1;
        } else if (strength < 30) {
            criticalStrikeDamage = 0.15;
        } else if (strength < 40) {
            criticalStrikeDamage = 0.2;
        } else if (strength < 50) {
            criticalStrikeDamage = 0.25;
        } else if (strength < 60) {
            criticalStrikeDamage = 0.3;
        } else if (strength < 70) {
            criticalStrikeDamage = 0.35;
        } else if (strength < 80) {
            criticalStrikeDamage = 0.4;
        } else if (strength < 90) {
            criticalStrikeDamage = 0.45;
        } else {
            criticalStrikeDamage = 0.5;
        }
        return criticalStrikeDamage;
    }
}
