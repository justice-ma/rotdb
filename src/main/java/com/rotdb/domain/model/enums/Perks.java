package com.rotdb.domain.model.enums;

public enum Perks {
    SPENDTHRIFT(1, 6, true, "Spendthrift"),
    RUTHLESS(1, 3, false, "Ruthless"),
    PRECISE(1, 6, false, "Precise"),
    ERUPTIVE(1, 4, false, "Eruptive"),
    ULTIMATUMS(1, 4, false, "Ultimatums"),
    BITING(1, 4, true, "Biting"),
    FLANKING(1, 4, false, "Flanking"),
    CAROMING(1, 4, false, "Caroming"),
    UNDEADSLAYER(1, 1, false, "Undead Slayer"),
    DRAGONSLAYER(1, 1, false, "Dragon Slayer"),
    DEMONSLAYER(1, 1, false, "Demon Slayer"),
    GENOCIDAL(1, 1, false, "Genocidal");

    private final int minTier;
    private final int maxTier;
    private final boolean isAffectedByLevel20;
    private final String name;

    Perks(int minTier, int maxTier, boolean affectedByLevel20, String name) {
        this.minTier = minTier;
        this.maxTier = maxTier;
        this.isAffectedByLevel20 = affectedByLevel20;
        this.name = name;
    }

    public int getMinTier() {
        return minTier;
    }

    public int getMaxTier() {
        return maxTier;
    }

    public boolean isAffectedByLevel20() {
        return isAffectedByLevel20;
    }

    public String getName() {
        return name;
    }
}
