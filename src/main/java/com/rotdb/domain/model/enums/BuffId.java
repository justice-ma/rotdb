package com.rotdb.domain.model.enums;

public enum BuffId {
    ENCHANTMENTOFSAVAGERY(0, 0, false, "Savagery"),
    ENCHANTMENTOFAGONY(0, 0, false, "Agony"),
    ENCHANTMENTOFHEROISM(0, 0, false, "Heroism"),
    ENCHANTMENTOFDISPELLING(0, 0, false, "Dispelling"),
    ENCHANTMENTOFDREAD(0, 0, false, "Dread"),
    ENCHANTMENTOFSHADOWS(0, 0, false, "Shadows"),
    ENCHANTMENTOFAFFLICTION(0, 0, false, "Affliction"),
    ENCHANTMENTOFFLAMES(0, 0, false, "Flames"),
    ENCHANTMENTOFMETAPHYSICS(0, 0, false, "Metaphysics"),
    SHARDOFGENESIS(0, 0, false, "Shard of Genesis"),
    DRACONICFRUIT(0, 0, false, "Draconic Fruit"),
    SMASH(0, 0, false, "Rend"),
    CHAOSROAR(0, 0, false, "Chaos Roar"),
    SUNSHINE(0, 0, false, "Sunshine"),
    DEATHSWIFTNESS(0, 0, false, "Death Swiftness"),
    BERSERK(0, 0, false, "Berserk"),
    ZGS(0, 0, false, "Zaros Godsword"),
    UNDEADSLAYERSIGIL(0, 0, false, "Undead Slayer Sigil"),
    DRAGONSLAYERSIGIL(0, 0, false, "Dragon Slayer Sigil"),
    DEMONSLAYERSIGIL(0, 0, false, "Demon Slayer Sigil"),
    REAPERSCREW(0, 0, false, "Reapers Crew"),
    RUNICCHARGE(0, 0, false, "Runic Charge"),
    KALG(0, 0, false, "Kal'gerion Scroll"),
    ECLIPSEDSOUL(0, 0, false, "Eclipsed Soul"),
    SPLITSOUL(0, 0, false, "Split Soul"),
    INSTABILITY(0, 0, false, "Instability"),
    BALANCEBYFORCE(0, 0, false, "Balance by Force"),
    DBA(0, 0, false, "Dragon Battleaxe"),
    DRAGONSCIMITAR(0, 0, false, "Dragon Scimitar"),
    GALES(0, 0, false, "Galeshot"),
    FURYBUFF(0, 0, false, "Fury"),
    GREATERFURYBUFF(0, 0, false, "Greater Fury"),
    CONCENTRATEDBLASTBUFF(0, 0, false, "Concentrated Blast"),
    GREATERCONCENTRATEDBLASTBUFF(0, 0, false, "Greater Concentrated Blast"),
    RAPIDFIREBUFF(0, 0, false, "Rapidfire"),
    ASPHYXIATEBUFF(0, 0, false, "Tumeken's"),
    CHILL(0, 0, false, "Chill"),
    BLOODLUST(0, 0, false, "Bloodlust"),
    CONFLAGRATE(0, 0, false, "Conflagrate"),
    STONEOFJAS(0, 6, true, "Stone of Jas"),
    RUBYAURORA(0, 3, true, "Ruby Aurora"),
    GRAVITATE(0, 20, true, "Gravitate"),
    WENSTACKS(0, 10, true, "Wen Stacks"),
    REVENGESTACKS(0, 10, true, "Revenge Stacks"),
    RUTHELESSSTACKS(0, 5, true, "Ruthless Stacks"),
    GUARDHOUSE(0, 3, true, "Guardhouse Tier"),
    TITHESTACKS(0, 12, true, "Tithe Stacks"),
    PUZZLEBOX(0, 6, true, "Puzzlebox"),
    NOPENOPENOPE(0, 2, true, "Nope Nope Nope"),
    BALANCEOFPOWER(0, 6, true, "Balance of Power"),
    GUARDIANSTRIUMPH(0, 6, true, "Guardians Triumph"),
    SLAYERLODGE(0, 3, true, "Slayer Lodge"),
    ESSENCECORRUPTIONSTACKS(0, 100, true, "Essence Corruption"),
    NOFEAR(0, 2, true, "No Fear"),
    PERFECTEQUILIBRIUMSTACKS(0, 7, true, "Perfect Equilibrium"),
    PRIMORDIALICESTACKS(0, 10, true, "Primordial Ice"),
    TIMESINCELASTATTACK(0, 10, true, "Time Since Last Attack"),
    REAPERSTACKS(0, 30, true, "Reaper Stacks"),
    BLEEDS(0, 4, true, "Bleeds on Target"),
    BLACKSTONEARROWSTACKS(0, 21, true, "BSA Stacks"),
    LORDOFBONESSTACKS(0, 200, true, "LoB Stacks"),
    COMBUSTED(0, 0, false, "Combusted"),
    FLAMEBOUNDRIVAL(0, 0, false, "Flamebound Rival"),
    HAUNTED(0, 0, false, "Haunted"),
    VULNED(0, 0, false, "Vulned"),
    CURSED(0, 0, false, "Cursed"),
    SMOKECLOUDED(0, 0, false, "Smoke Clouded"),
    OBLITERATED(0, 0, false, "Statius' Warhammer"),
    BANDOSBOOK(0, 0, false, "Bandos Book"),
    CLAWSOFGUTHIX(0, 0, false, "Claws of Guthix"),
    CLOBBER(0, 0, false, "Clobber"),
    SUNDER(0, 0, false, "Sunder"),
    BACKSTAB(0, 0, false, "Backstab"),
    CROESUSSPORED(0, 0, false, "Croesus Spored"),
    BERSERKERSFURY(0, 0, false, "Berserker's Fury"),
    LIVINGDEATH(0, 0, false, "Living Death");

    private final int minimumStacks, maximumStacks;
    private final boolean stackable;
    private final String label;

    BuffId(int minimumStacks, int maximumStacks, boolean stackable, String label) {
        this.minimumStacks = minimumStacks;
        this.maximumStacks = maximumStacks;
        this.stackable = stackable;
        this.label = label;
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

    public String getLabel() {
        return label;
    }
}
