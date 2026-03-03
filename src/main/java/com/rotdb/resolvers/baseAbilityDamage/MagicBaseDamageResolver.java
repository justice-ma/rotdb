package com.rotdb.resolvers.baseAbilityDamage;

import com.rotdb.calculator.EruptiveCalc;

public class MagicBaseDamageResolver {
    public static int twoHand(int m, double b, int mt, int er, int sp) {
        double d = 145 * 2.5 * (Math.log(1 + (0.6 * (m / 145.0))) / Math.log(1.6));
        double base = (d * 1.5 + (14.4 * Math.min(mt, sp) + b) + 0.5 * b);
        return (int) EruptiveCalc.Calc(base, er);
    }

    public static int dualWield(int m, double b, int mt, int ot, int er, int sp) {
        double d = 145 * 2.5 * (Math.log(1 + (0.6 * (m / 145.0))) / Math.log(1.6));
        double mh = (d + Math.floor(9.6 * Math.min(mt, sp) + b));
        double oh = (0.5 * Math.floor(d + Math.floor(9.6 * Math.min(ot, sp) + b)));
        return (int) EruptiveCalc.Calc(mh + oh, er);
    }
}
