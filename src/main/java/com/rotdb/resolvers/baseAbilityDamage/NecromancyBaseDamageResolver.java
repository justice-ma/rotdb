package com.rotdb.resolvers.baseAbilityDamage;

import com.rotdb.calculator.EruptiveCalc;

public class NecromancyBaseDamageResolver {
    public static int dualWield(int n, double b, int mt, int ot, int er) {
        double d = 145 * 2.5 * (Math.log(1 + (0.6 * (n / 145.0))) / Math.log(1.6));
        double mh = (d + 9.6 * mt + b);
        double oh = (0.5 * (d + (9.6 * ot + b)));
        return (int) EruptiveCalc.Calc(oh + mh, er);
    }
}
