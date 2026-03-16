package com.rotdb.calculation.domain.model.enums;

public enum Familiars {
    ICENIHIL("Ice Nihil"),
    SMOKENIHIL("Smoke Nihil"),
    BLOODNIHIL("Blood Nihil"),
    SHADOWNIHIL("Shadow Nihil"),
    KALGERIONDEMON("Kal'gerion Demon"),
    RIPPERDEMON("Ripper Demon");

    private final String name;

    Familiars(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
