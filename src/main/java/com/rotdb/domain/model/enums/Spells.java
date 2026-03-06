package com.rotdb.domain.model.enums;

public enum Spells {
    AIRBLAST(61),
    AIRBOLT(40),
    AIRSTRIKE(16),
    AIRSURGE(100),
    AIRWAVE(80),
    BLOODBARRAGE(100),
    BLOODBLITZ(92),
    BLOODBURST(79),
    BLOODRUSH(67),
    CRUMBLEUNDEAD(100),
    DIVINESTORM(78),
    EARTHBLAST(69),
    EARTHBOLT(52),
    EARTHSTRIKE(28),
    EARTHSURGE(100),
    EARTHWAVE(80),
    EMERALDAURORA(100),
    EXSANGUINATE(100),
    FIREBLAST(74),
    FIREBOLT(58),
    FIRESTRIKE(34),
    FIRESURGE(99),
    FIREWAVE(80),
    ICEBARRAGE(100),
    ICEBLITZ(92),
    ICEBURST(80),
    ICERUSH(69),
    INCITEFEAR(100),
    OPALAURORA(100),
    POLYPORESTRIKE(90),
    RUBYAURORA(100),
    SAPPHIREAURORA(100),
    SHADOWBARRAGE(100),
    SHADOWBLITZ(92),
    SHADOWBURSY(75),
    SHADOWRUSH(63),
    SLAYERDART(65),
    SMOKEBARRAGE(100),
    SMOKEBLITZ(92),
    SMOKEBURST(73),
    SMOKERUSH(60),
    STORMOFARMADYL(85),
    WATERBLAST(64),
    WATERBOLT(45),
    WATERSTRIKE(22),
    WATERSURGE(100),
    WATERWAVE(80),
    WINDRUSH(1);

    private final int damageTier;

    Spells(int damageTier) {
        this.damageTier = damageTier;
    }

    public int getDamageTier() {
        return damageTier;
    }
}
