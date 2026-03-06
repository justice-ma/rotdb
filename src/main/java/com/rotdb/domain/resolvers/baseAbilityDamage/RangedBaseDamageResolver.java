package com.rotdb.domain.resolvers.baseAbilityDamage;

import com.rotdb.calculator.EruptiveCalc;

public class RangedBaseDamageResolver {
    // TODO: Upon implementation : Shard of Genesis
    public static int twoHand(int r, double b, int mt, int er, int a) {
        double d = 145 * 2.5 * (Math.log(1 + (0.6 * (r / 145.0))) / Math.log(1.6));
        double base = (d * 1.5 + (9.6 * Math.min(mt, a) + b)
                + (4.8 * Math.min(mt, a) + 0.5 * b));
        return (int) EruptiveCalc.Calc(base, er);
    }

    public static int dualWield(int r, double b, int mt, int ot, int er, int a) {
        double d = 145 * 2.5 * (Math.log(1 + (0.6 * (r / 145.0))) / Math.log(1.6));
        double mh = d + (9.6 * Math.min(mt, a) + b);
        double oh = 0.5 * (d + (9.6 * Math.min(ot, a) + b));
        return (int) EruptiveCalc.Calc(mh + oh, er);
    }
}
