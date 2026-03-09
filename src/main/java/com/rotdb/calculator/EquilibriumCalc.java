package com.rotdb.calculator;

public class EquilibriumCalc {
    public static double Calc(double base, int eq) {
        if (eq > 0) {
            return base * (1.1 + eq / 100.0);
        }
        return base;
    }
}
