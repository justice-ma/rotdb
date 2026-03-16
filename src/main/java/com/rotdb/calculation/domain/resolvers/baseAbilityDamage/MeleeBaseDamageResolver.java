package com.rotdb.calculation.domain.resolvers.baseAbilityDamage;


import com.rotdb.calculation.calculator.EquilibriumCalc;
import com.rotdb.calculation.calculator.EruptiveCalc;

public class MeleeBaseDamageResolver {
    public static int twoHand(int s, double b, int mt, int er, int eq) {
        double d = 145 * 2.5 * (Math.log(1 + (0.6 * (s / 145.0))) / Math.log(1.6));
        double base = (d * 1.5 + (9.6 * Math.min(mt, s) + b)
                + (4.8 * mt + 0.5 * b));
        base = EquilibriumCalc.Calc(base, eq);
        return (int) EruptiveCalc.Calc(base, er);
    }

    public static int dualWield(int s, double b, int mt, int ot, int er, int eq) {
        double d = 145 * 2.5 * (Math.log(1 + (0.6 * (s / 145.0))) / Math.log(1.6));
        double mh = (d + 9.6 * Math.min(mt, s) + b);
        double oh = (0.5 * (d + 9.6 * Math.min(ot, s) + b));
        double base = EquilibriumCalc.Calc(oh + mh, eq);
        return (int) EruptiveCalc.Calc(base, er);
    }
    // Damage output is low when tier is greater than strength ** ASSUME TIER IS ALWAYS LESS THAN OR EQUAL TO STR **
    // TODO: Try to find a fix, contact Akritia if required
}