package com.rotdb.calculation.calculator;

public class EruptiveCalc {
    public static double Calc(double base, int er) {
        for (int i = 0; i < er; i++) {
            base = Math.floor(base * 1.005);
        }
        return base;
    }
}
