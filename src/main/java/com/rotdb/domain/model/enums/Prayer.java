package com.rotdb.domain.model.enums;

import java.util.EnumSet;

public enum Prayer {
    // Damage Boosting
    BURSTOFSTRENGTH(true, 2, 0, EnumSet.of(CombatStyles.MELEE), PrayerBook.NORMAL, "Burst of Strength", EnumSet.of(PrayerExclusivityGroup.DAMAGE)),
    UNSTOPPABLEFORCE(true, 2, 0, EnumSet.of(CombatStyles.RANGED), PrayerBook.NORMAL, "Unstoppable Force", EnumSet.of(PrayerExclusivityGroup.DAMAGE)),
    CHARGE(true, 2, 0, EnumSet.of(CombatStyles.MAGIC), PrayerBook.NORMAL, "Charge", EnumSet.of(PrayerExclusivityGroup.DAMAGE)),
    DECAY(true, 2, 0, EnumSet.of(CombatStyles.NECROMANCY), PrayerBook.NORMAL, "Decay", EnumSet.of(PrayerExclusivityGroup.DAMAGE)),
    SUPERHUMANSTRENGTH(true, 4, 0, EnumSet.of(CombatStyles.MELEE), PrayerBook.NORMAL, "Superhuman Strength", EnumSet.of(PrayerExclusivityGroup.DAMAGE)),
    UNRELENTINGFORCE(true, 4, 0, EnumSet.of(CombatStyles.RANGED), PrayerBook.NORMAL, "Unrelenting Force", EnumSet.of(PrayerExclusivityGroup.DAMAGE)),
    SUPERCHARGE(true,4, 0, EnumSet.of(CombatStyles.MAGIC), PrayerBook.NORMAL, "Supercharge", EnumSet.of(PrayerExclusivityGroup.DAMAGE)),
    HASTENEDDECAY(true, 4, 0, EnumSet.of(CombatStyles.NECROMANCY), PrayerBook.NORMAL, "Hastened Decay", EnumSet.of(PrayerExclusivityGroup.DAMAGE)),
    ULTIMATESTRENGTH(true, 6, 0, EnumSet.of(CombatStyles.MELEE), PrayerBook.NORMAL, "Ultimate Strength", EnumSet.of(PrayerExclusivityGroup.DAMAGE)),
    OVERPOWERINGFORCE(true,6, 0, EnumSet.of(CombatStyles.RANGED), PrayerBook.NORMAL, "Overpowering Force", EnumSet.of(PrayerExclusivityGroup.DAMAGE)),
    OVERCHARGE(true, 6, 0, EnumSet.of(CombatStyles.MAGIC), PrayerBook.NORMAL, "Overcharge", EnumSet.of(PrayerExclusivityGroup.DAMAGE)),
    ACCELERATEDDECAY(true, 6, 0, EnumSet.of(CombatStyles.NECROMANCY), PrayerBook.NORMAL, "Accelerated Decay", EnumSet.of(PrayerExclusivityGroup.DAMAGE)),
    LEECHRANGEDSTRENGTH(false, 8, 0, EnumSet.of(CombatStyles.RANGED), PrayerBook.CURSES, "Leech Ranged Strength", EnumSet.of(PrayerExclusivityGroup.DAMAGE)),
    LEECHMAGICSTRENGTH(false, 8, 0, EnumSet.of(CombatStyles.MAGIC), PrayerBook.CURSES, "Leech Magic Strength", EnumSet.of(PrayerExclusivityGroup.DAMAGE)),
    LEECHMELEESTRENGTH(false, 8, 0, EnumSet.of(CombatStyles.MELEE), PrayerBook.CURSES, "Leech Melee Strength", EnumSet.of(PrayerExclusivityGroup.DAMAGE)),
    LEECHNECROMANCYSTRENGTH(false, 8, 0, EnumSet.of(CombatStyles.NECROMANCY), PrayerBook.CURSES, "Leech Necromancy Strength", EnumSet.of(PrayerExclusivityGroup.DAMAGE)),
    DIVINERAGE(true, 5, 0, EnumSet.of(CombatStyles.MELEE, CombatStyles.RANGED, CombatStyles.NECROMANCY, CombatStyles.MAGIC), PrayerBook.NORMAL, "Divine Rage", EnumSet.of(PrayerExclusivityGroup.NONE)),

    // Accuracy Boosting
    CLARITYOFTHOUGHT(true, 0, 2, EnumSet.of(CombatStyles.MELEE), PrayerBook.NORMAL, "Clarity of Thought", EnumSet.of(PrayerExclusivityGroup.ACCURACY)),
    SHARPEYE(true, 0, 2, EnumSet.of(CombatStyles.RANGED), PrayerBook.NORMAL, "Sharp Eye", EnumSet.of(PrayerExclusivityGroup.ACCURACY)),
    MYSTICWILL(true, 0, 2, EnumSet.of(CombatStyles.MAGIC), PrayerBook.NORMAL, "Mystic Will", EnumSet.of(PrayerExclusivityGroup.ACCURACY)),
    HANDOFJUDGEMENT(true, 0, 2, EnumSet.of(CombatStyles.NECROMANCY), PrayerBook.NORMAL, "Hand of Judgement", EnumSet.of(PrayerExclusivityGroup.ACCURACY)),
    IMPROVEDREFLEXES(true, 0, 4, EnumSet.of(CombatStyles.MELEE), PrayerBook.NORMAL, "Improved Reflexes", EnumSet.of(PrayerExclusivityGroup.ACCURACY)),
    HAWKEYE(true, 0, 4, EnumSet.of(CombatStyles.RANGED), PrayerBook.NORMAL, "Hawk Eye", EnumSet.of(PrayerExclusivityGroup.ACCURACY)),
    MYSTICLORE(true, 0, 4, EnumSet.of(CombatStyles.MAGIC), PrayerBook.NORMAL, "Mystic Lore", EnumSet.of(PrayerExclusivityGroup.ACCURACY)),
    HANDOFFATE(true, 0, 4, EnumSet.of(CombatStyles.NECROMANCY), PrayerBook.NORMAL, "Hand of Fate", EnumSet.of(PrayerExclusivityGroup.ACCURACY)),
    INCREDIBLEREFLEXES(true, 0, 6, EnumSet.of(CombatStyles.MELEE), PrayerBook.NORMAL, "Incredible Reflexes", EnumSet.of(PrayerExclusivityGroup.ACCURACY)),
    EAGLEEYE(true, 0, 6, EnumSet.of(CombatStyles.RANGED), PrayerBook.NORMAL, "Eagle Eye", EnumSet.of(PrayerExclusivityGroup.ACCURACY)),
    MYSTICMIGHT(true, 0, 6, EnumSet.of(CombatStyles.MAGIC), PrayerBook.NORMAL, "Mystic Might", EnumSet.of(PrayerExclusivityGroup.ACCURACY)),
    HANDOFDOOM(true, 0, 6, EnumSet.of(CombatStyles.NECROMANCY), PrayerBook.NORMAL, "Hand of Doom", EnumSet.of(PrayerExclusivityGroup.ACCURACY)),
    LEECHMELEEATTACK(false, 0, 5, EnumSet.of(CombatStyles.MELEE), PrayerBook.CURSES, "Leech Melee Attack", EnumSet.of(PrayerExclusivityGroup.ACCURACY)),
    LEECHRANGEDATTACK(false, 0, 5, EnumSet.of(CombatStyles.RANGED), PrayerBook.CURSES, "Leech Ranged Attack", EnumSet.of(PrayerExclusivityGroup.ACCURACY)),
    LEECHMAGICATTACK(false, 0, 5, EnumSet.of(CombatStyles.MAGIC), PrayerBook.CURSES, "Leech Magic Attack", EnumSet.of(PrayerExclusivityGroup.ACCURACY)),
    LEECHNECROMANCYATTACK(false, 0, 5, EnumSet.of(CombatStyles.NECROMANCY), PrayerBook.CURSES, "Leech Necromancy Attack", EnumSet.of(PrayerExclusivityGroup.ACCURACY)),

    // Accuracy and Damage Boosting
    CHIVALRY(true, 7, 7, EnumSet.of(CombatStyles.MELEE), PrayerBook.NORMAL, "Chivalry", EnumSet.of(PrayerExclusivityGroup.ACCURACY, PrayerExclusivityGroup.DAMAGE)),
    SANCTITY(true, 8, 8, EnumSet.of(CombatStyles.NECROMANCY), PrayerBook.NORMAL, "Sanctity", EnumSet.of(PrayerExclusivityGroup.ACCURACY, PrayerExclusivityGroup.DAMAGE)),
    RIGOUR(true, 8, 8, EnumSet.of(CombatStyles.RANGED), PrayerBook.NORMAL, "Rigour", EnumSet.of(PrayerExclusivityGroup.ACCURACY, PrayerExclusivityGroup.DAMAGE)),
    AUGURY(true, 8, 8, EnumSet.of(CombatStyles.MAGIC), PrayerBook.NORMAL, "Augury", EnumSet.of(PrayerExclusivityGroup.ACCURACY, PrayerExclusivityGroup.DAMAGE)),
    PIETY(true, 8, 8, EnumSet.of(CombatStyles.MELEE), PrayerBook.NORMAL, "Piety", EnumSet.of(PrayerExclusivityGroup.ACCURACY, PrayerExclusivityGroup.DAMAGE)),
    TORMENT(false, 10, 10, EnumSet.of(CombatStyles.NECROMANCY), PrayerBook.CURSES, "Torment", EnumSet.of(PrayerExclusivityGroup.ACCURACY, PrayerExclusivityGroup.DAMAGE)),
    ANGUISH(false, 10, 10, EnumSet.of(CombatStyles.RANGED), PrayerBook.CURSES, "Anguish", EnumSet.of(PrayerExclusivityGroup.ACCURACY, PrayerExclusivityGroup.DAMAGE)),
    SORROW(false, 10, 10, EnumSet.of(CombatStyles.NECROMANCY), PrayerBook.CURSES, "Sorrow", EnumSet.of(PrayerExclusivityGroup.ACCURACY, PrayerExclusivityGroup.DAMAGE)),
    TURMOIL(false, 10, 10, EnumSet.of(CombatStyles.MELEE), PrayerBook.CURSES, "Turmoil", EnumSet.of(PrayerExclusivityGroup.ACCURACY, PrayerExclusivityGroup.DAMAGE)),
    RUINATION(false, 12, 12, EnumSet.of(CombatStyles.NECROMANCY), PrayerBook.CURSES, "Ruination", EnumSet.of(PrayerExclusivityGroup.ACCURACY, PrayerExclusivityGroup.DAMAGE)),
    DESOLATION(false, 12, 12, EnumSet.of(CombatStyles.RANGED), PrayerBook.CURSES, "Desolation", EnumSet.of(PrayerExclusivityGroup.ACCURACY, PrayerExclusivityGroup.DAMAGE)),
    MALEVOLENCE(false, 12, 12, EnumSet.of(CombatStyles.MELEE), PrayerBook.CURSES, "Malevolence", EnumSet.of(PrayerExclusivityGroup.ACCURACY, PrayerExclusivityGroup.DAMAGE)),
    AFFLICTION(false, 12, 12, EnumSet.of(CombatStyles.MAGIC), PrayerBook.CURSES, "Affliction", EnumSet.of(PrayerExclusivityGroup.ACCURACY, PrayerExclusivityGroup.DAMAGE));

    private final boolean stackableWithDivineRage;
    private final int prayerStrengthBonus;
    private final int prayerAccuracyBonus;
    private final EnumSet<CombatStyles> styles;
    private final PrayerBook book;
    private final String name;
    private final EnumSet<PrayerExclusivityGroup> exclusivityGroup;

    Prayer(boolean stackableWithDivineRage, int prayerStrengthBonus, int prayerAccuracyBonus, EnumSet<CombatStyles> styles, PrayerBook book, String name, EnumSet<PrayerExclusivityGroup> exclusivityGroup) {
        this.stackableWithDivineRage = stackableWithDivineRage;
        this.prayerStrengthBonus = prayerStrengthBonus;
        this.prayerAccuracyBonus = prayerAccuracyBonus;
        this.styles = styles;
        this.book = book;
        this.name = name;
        this.exclusivityGroup = exclusivityGroup;
    }

    public boolean isStackableWithDivineRage() {
        return stackableWithDivineRage;
    }

    public int getPrayerStrengthBonus() {
        return prayerStrengthBonus;
    }

    public int getPrayerAccuracyBonus() {
        return prayerAccuracyBonus;
    }

    public EnumSet<CombatStyles> getStyles() {
        return styles;
    }

    public boolean appliesTo(CombatStyles style) {
        return this.styles.contains(style); // styles is an EnumSet<CombatStyles>
    }

    public PrayerBook getBook() {
        return book;
    }

    public String getName() {
        return name;
    }

    public EnumSet<PrayerExclusivityGroup> getExclusivityGroup() {
        return exclusivityGroup;
    }
}
