package com.rotdb.model.enums;

public enum BuffId {
    ENCHANTMENTOFSAVAGERY(0, 0, false),
    ENCHANTMENTOFAGONY(0, 0, false),
    ENCHANTMENTOFHEROISM(0, 0, false),
    ENCHANTMENTOFDISPELLING(0, 0, false),
    ENCHANTMENTOFDREAD(0, 0, false),
    ENCHANTMENTOFSHADOWS(0, 0, false),
    ENCHANTMENTOFAFFLICTION(0, 0, false),
    ENCHANTMENTOFFLAMES(0, 0, false),
    ENCHANTMENTOFMETAPHYSICS(0, 0, false),
    SHARDOFGENESIS(0, 0, false),
    DRACONICFRUIT(0, 0, false),
    SMASH(0, 0, false),
    CHAOSROAR(0, 0, false),
    SUNSHINE(0, 0, false),
    DEATHSWIFTNESS(0, 0, false),
    BERSERK(0, 0, false),
    ZGS(0, 0, false),
    UNDEADSLAYERSIGIL(0, 0, false),
    DRAGONSLAYERSIGIL(0, 0, false),
    DEMONSLAYERSIGIL(0, 0, false),
    REAPERSCREW(0, 0, false),
    RUNICCHARGE(0, 0, false),
    KALG(0, 0, false),
    ECLIPSEDSOUL(0, 0, false),
    SPLITSOUL(0, 0, false),
    INSTABILITY(0, 0, false),
    BALANCEBYFORCE(0, 0, false),
    DBA(0, 0, false),
    DRAGONSCIMITAR(0, 0, false),
    GALES(0, 0, false),
    FURYBUFF(0, 0, false),
    GREATERFURYBUFF(0, 0, false),
    CONCENTRATEDBLASTBUFF(0, 0, false),
    GREATERCONCENTRATEDBLASTBUFF(0, 0, false),
    RAPIDFIREBUFF(0, 0, false),
    ASPHYXIATEBUFF(0, 0, false),
    CHILL(0, 0, false),
    BLOODLUST(0, 8, true),
    CONFLAGRATE(0, 0, false),
    STONEOFJAS(0, 6, true),
    RUBYAURORA(0, 3, true),
    GRAVITATE(0, 20, true),
    WENSTACKS(0, 15, true),
    REVENGESTACKS(0, 10, true),
    RUTHELESSSTACKS(0, 5, true),
    GUARDHOUSE(0, 3, true),
    TITHESTACKS(0, 12, true),
    PUZZLEBOX(0, 6, true),
    NOPENOPENOPE(0, 2, true),
    BALANCEOFPOWER(0, 6, true),
    GUARDIANSTRIUMPH(0, 6, true),
    SLAYERLODGE(0, 3, true),
    ESSENCECORRUPTIONSTACKS(0, 100, true),
    NOFEAR(0, 2, true),
    PERFECTEQUILIBRIUMSTACKS(0, 7, true),
    PRIMORDIALICESTACKS(0, 10, true),
    TIMESINCELASTATTACK(0, 10, true),
    REAPERSTACKS(0, 30, true);

    private final int minimumStacks, maximumStacks;
    private final boolean stackable;

    BuffId(int minimumStacks, int maximumStacks, boolean stackable) {
        this.minimumStacks = minimumStacks;
        this.maximumStacks = maximumStacks;
        this.stackable = stackable;
    }

    public int getMinimumStacks() {
        return minimumStacks;
    }

    public int getMaximumStacks() {
        return maximumStacks;
    }

    public boolean isStackable() {
        return stackable;
    }
}
