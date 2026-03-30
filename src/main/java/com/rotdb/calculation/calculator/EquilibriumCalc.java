package com.rotdb.calculation.calculator;

public class EquilibriumCalc {
    public static double Calc(double base, int eq) {
        if (eq > 0) {
            return base * (1.06 + eq / 50.0);
        }
        return base;
    }
}
