package com.rotdb.domain.resolvers.abilityDamage.criticalStrike;

public class CritBonus {
    private double chanceDelta;
    private double damageDelta;

    public CritBonus (double chanceDelta, double damageDelta) {
        this.chanceDelta = chanceDelta;
        this.damageDelta = damageDelta;
    }

    public double getChanceDelta() {
        return chanceDelta;
    }

    public void setChanceDelta(double chanceDelta) {
        this.chanceDelta = chanceDelta;
    }

    public double getDamageDelta() {
        return damageDelta;
    }

    public void setDamageDelta(double damageDelta) {
        this.damageDelta = damageDelta;
    }
}
