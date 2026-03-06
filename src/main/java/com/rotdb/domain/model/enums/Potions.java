package com.rotdb.domain.model.enums;

public enum Potions {
    ELDER(5, 0.17),
    SUPREME(4, 0.16),
    EXTREME(3, 0.15),
    GRAND(2, 0.14),
    SUPER(2, 0.12),
    BASE(1, 0.08),
    NONE(0, 0);

    private final int flatBonus;
    private final double multiplicativeBonus;

    Potions(int flatBonus, double multiplicativeBonus) {
        this.flatBonus = flatBonus;
        this.multiplicativeBonus = multiplicativeBonus;
    }

    public int getFlatBonus() {
        return flatBonus;
    }

    public double getMultiplicativeBonus() {
        return multiplicativeBonus;
    }
}
