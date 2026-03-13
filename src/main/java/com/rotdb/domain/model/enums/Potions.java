package com.rotdb.domain.model.enums;

public enum Potions {
    ELDER(5, 0.17, "Elder"),
    SUPREME(4, 0.16, "Supreme"),
    EXTREME(3, 0.15, "Extreme"),
    GRAND(2, 0.14, "Grand"),
    SUPER(2, 0.12, "Super"),
    BASE(1, 0.08, "Base"),
    NONE(0, 0, "None");

    private final int flatBonus;
    private final double multiplicativeBonus;
    private final String name;

    Potions(int flatBonus, double multiplicativeBonus, String name) {
        this.flatBonus = flatBonus;
        this.multiplicativeBonus = multiplicativeBonus;
        this.name = name;
    }

    public int getFlatBonus() {
        return flatBonus;
    }

    public double getMultiplicativeBonus() {
        return multiplicativeBonus;
    }

    public String getName() {
        return name;
    }
}
