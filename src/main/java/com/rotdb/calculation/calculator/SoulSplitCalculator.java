package com.rotdb.calculation.calculator;

public class SoulSplitCalculator {

    public static int soulSplit(int hit, double amuletOfSouls, double chance) {
        int soulSplit = 0;
        double amuletOfSoulsAdd = amuletOfSouls * chance;

        if (hit <= 2_000) {
            soulSplit = (int) (hit * 0.1);
            soulSplit += (int) (soulSplit * amuletOfSoulsAdd);
        } else if (hit <= 4_000) {
            soulSplit = (int) ((2_000 * 0.1) + ((hit - 2_000) * 0.05));
            soulSplit += (int) (soulSplit * amuletOfSoulsAdd);
        } else {
            soulSplit = (int) ((2_000 * 0.1) + (2_000 * 0.05) + ((hit - 4_000) * 0.0125));
            soulSplit += (int) (soulSplit * amuletOfSoulsAdd);
        }
        return soulSplit;
    }
}
