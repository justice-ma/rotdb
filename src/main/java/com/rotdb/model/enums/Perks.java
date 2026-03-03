package com.rotdb.model.enums;

public enum Perks {
    SPENDTHRIFT(1, 6, true),
    RUTHLESS(1, 3, false),
    PRECISE(1, 6, false),
    ERUPTIVE(1, 4, false),
    ULTIMATUMS(1, 4, false),
    BITING(1, 4, true),
    FLANKING(1, 4, false),
    CAROMING(1, 4, false),
    UNDEADSLAYER(1, 1, false),
    DRAGONSLAYER(1, 1, false),
    DEMONSLAYER(1, 1, false),
    GENOCIDAL(1, 1, false);

    private final int minTier;
    private final int maxTier;

    Perks(int minTier, int maxTier, boolean affectedByLevel20) {
        this.minTier = minTier;
        this.maxTier = maxTier;
    }

    public int getMinTier() {
        return minTier;
    }

    public int getMaxTier() {
        return maxTier;
    }
}
