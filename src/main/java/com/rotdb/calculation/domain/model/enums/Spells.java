package com.rotdb.calculation.domain.model.enums;

public enum Spells {
    AIRBLAST(61, "Air Blast"),
    AIRBOLT(40, "Air Bolt"),
    AIRSTRIKE(16, "Air Strike"),
    AIRSURGE(100, "Air Surge"),
    AIRWAVE(80, "Air Wave"),
    BLOODBARRAGE(100, "Blood Barrage"),
    BLOODBLITZ(92, "Blood Blitz"),
    BLOODBURST(79, "Blood Burst"),
    BLOODRUSH(67, "Blood Rush"),
    CRUMBLEUNDEAD(100, "Crumble Undead"),
    DIVINESTORM(78, "Divine Storm"),
    EARTHBLAST(69, "Earth Blast"),
    EARTHBOLT(52, "Earth Bolt"),
    EARTHSTRIKE(28, "Earth Strike"),
    EARTHSURGE(100, "Earth Surge"),
    EARTHWAVE(80, "Earth Wave"),
    EMERALDAURORA(100, "Emerald Aurora"),
    EXSANGUINATE(100, "Exsanguinate"),
    FIREBLAST(74, "Fire Blast"),
    FIREBOLT(58, "Fire Bolt"),
    FIRESTRIKE(34, "Fire Strike"),
    FIRESURGE(99, "Fire Surge"),
    FIREWAVE(80, "Fire Wave"),
    ICEBARRAGE(100, "Ice Barrage"),
    ICEBLITZ(92, "Ice Blitz"),
    ICEBURST(80, "Ice Burst"),
    ICERUSH(69, "Ice Rush"),
    INCITEFEAR(100, "Incite Fear"),
    OPALAURORA(100, "Opal Aurora"),
    POLYPORESTRIKE(90, "Polypore Strike"),
    RUBYAURORA(100, "Ruby Aurora"),
    SAPPHIREAURORA(100, "Sapphire Aurora"),
    SHADOWBARRAGE(100, "Shadow Barrage"),
    SHADOWBLITZ(92, "Shadow Blitz"),
    SHADOWBURST(75, "Shadow Burst"),
    SHADOWRUSH(63, "Shadow Rush"),
    SLAYERDART(65, "Slayer Dart"),
    SMOKEBARRAGE(100, "Smoke Barrage"),
    SMOKEBLITZ(92, "Smoke Blitz"),
    SMOKEBURST(73, "Smoke Burst"),
    SMOKERUSH(60, "Smoke Rush"),
    STORMOFARMADYL(85, "Storm of Armadyl"),
    WATERBLAST(64, "Water Blast"),
    WATERBOLT(45, "Water Bolt"),
    WATERSTRIKE(22, "Water Strike"),
    WATERSURGE(100, "Water Surge"),
    WATERWAVE(80, "Water Wave"),
    WINDRUSH(1, "Wind Rush");

    private final int damageTier;
    private final String name;

    Spells(int damageTier, String name) {
        this.damageTier = damageTier;
        this.name = name;
    }

    public int getDamageTier() {
        return damageTier;
    }

    public String getName() {
        return name;
    }
}
