package com.rotdb.model.enums;

import java.util.EnumSet;

public enum Prayer {
    // Damage Boosting
    BURSTOFSTRENGTH(true, 2, 0, EnumSet.of(CombatStyles.MELEE)),
    UNSTOPPABLEFORCE(true, 2, 0, EnumSet.of(CombatStyles.RANGED)),
    CHARGE(true, 2, 0, EnumSet.of(CombatStyles.MAGIC)),
    DECAY(true, 2, 0, EnumSet.of(CombatStyles.NECROMANCY)),
    SUPERHUMANSTRENGTH(true, 4, 0, EnumSet.of(CombatStyles.MELEE)),
    UNRELENTINGFORCE(true, 4, 0, EnumSet.of(CombatStyles.RANGED)),
    SUPERCHARGE(true,4, 0, EnumSet.of(CombatStyles.MAGIC)),
    HASTENEDDECAY(true, 4, 0, EnumSet.of(CombatStyles.NECROMANCY)),
    ULTIMATESTRENGTH(true, 6, 0, EnumSet.of(CombatStyles.MELEE)),
    OVERPOWERINGFORCE(true,6, 0, EnumSet.of(CombatStyles.RANGED)),
    OVERCHARGE(true, 6, 0, EnumSet.of(CombatStyles.MAGIC)),
    ACCELERATEDDECAY(true, 6, 0, EnumSet.of(CombatStyles.NECROMANCY)),
    LEECHRANGEDSTRENGTH(false, 8, 0, EnumSet.of(CombatStyles.RANGED)),
    LEECHMAGICSTRENGTH(false, 8, 0, EnumSet.of(CombatStyles.MAGIC)),
    LEECHMELEESTRENGTH(false, 8, 0, EnumSet.of(CombatStyles.MELEE)),
    LEECHNECROMANCYSTRENGTH(false, 8, 0, EnumSet.of(CombatStyles.NECROMANCY)),

    // Accuracy Boosting
    CLARITYOFTHOUGHT(true, 0, 2, EnumSet.of(CombatStyles.MELEE)),
    SHARPEYE(true, 0, 2, EnumSet.of(CombatStyles.RANGED)),
    MYSTICWILL(true, 0, 2, EnumSet.of(CombatStyles.MAGIC)),
    HANDOFJUDGEMENT(true, 0, 2, EnumSet.of(CombatStyles.NECROMANCY)),
    IMPROVEDREFLEXES(true, 0, 4, EnumSet.of(CombatStyles.MELEE)),
    HAWKEYE(true, 0, 4, EnumSet.of(CombatStyles.RANGED)),
    MYSTICLORE(true, 0, 4, EnumSet.of(CombatStyles.MAGIC)),
    HANDOFFATE(true, 0, 4, EnumSet.of(CombatStyles.NECROMANCY)),
    INCREDIBLEREFLEXES(true, 0, 6, EnumSet.of(CombatStyles.MELEE)),
    EAGLEEYE(true, 0, 6, EnumSet.of(CombatStyles.RANGED)),
    MYSTICMIGHT(true, 0, 6, EnumSet.of(CombatStyles.MAGIC)),
    HANDOFDOOM(true, 0, 6, EnumSet.of(CombatStyles.NECROMANCY)),
    LEECHMELEEATTACK(false, 0, 5, EnumSet.of(CombatStyles.MELEE)),
    LEECHRANGEDATTACK(false, 0, 5, EnumSet.of(CombatStyles.RANGED)),
    LEECHMAGICATTACK(false, 0, 5, EnumSet.of(CombatStyles.MAGIC)),
    LEECHNECROMANCYATTACK(false, 0, 5, EnumSet.of(CombatStyles.NECROMANCY)),

    // Accuracy and Damage Boosting
    CHIVALRY(true, 7, 7, EnumSet.of(CombatStyles.MELEE)),
    SANCTITY(true, 8, 8, EnumSet.of(CombatStyles.NECROMANCY)),
    RIGOUR(true, 8, 8, EnumSet.of(CombatStyles.RANGED)),
    AUGURY(true, 8, 8, EnumSet.of(CombatStyles.MAGIC)),
    PIETY(true, 8, 8, EnumSet.of(CombatStyles.MELEE)),
    DIVINERAGE(true, 5, 0, EnumSet.of(CombatStyles.MELEE, CombatStyles.RANGED, CombatStyles.NECROMANCY, CombatStyles.MAGIC)),
    TORMENT(false, 10, 10, EnumSet.of(CombatStyles.NECROMANCY)),
    ANGUISH(false, 10, 10, EnumSet.of(CombatStyles.RANGED)),
    SORROW(false, 10, 10, EnumSet.of(CombatStyles.NECROMANCY)),
    TURMOIL(false, 10, 10, EnumSet.of(CombatStyles.MELEE)),
    RUINATION(false, 12, 12, EnumSet.of(CombatStyles.NECROMANCY)),
    DESOLATION(false, 12, 12, EnumSet.of(CombatStyles.RANGED)),
    MALEVOLENCE(false, 12, 12, EnumSet.of(CombatStyles.MELEE)),
    AFFLICTION(false, 12, 12, EnumSet.of(CombatStyles.MAGIC));

    private final boolean stackableWithDivineRage;
    private final int prayerStrengthBonus;
    private final int prayerAccuracyBonus;
    private final EnumSet<CombatStyles> styles;

    Prayer(boolean stackableWithDivineRage, int prayerStrengthBonus, int prayerAccuracyBonus, EnumSet<CombatStyles> styles) {
        this.stackableWithDivineRage = stackableWithDivineRage;
        this.prayerStrengthBonus = prayerStrengthBonus;
        this.prayerAccuracyBonus = prayerAccuracyBonus;
        this.styles = styles;
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
}
