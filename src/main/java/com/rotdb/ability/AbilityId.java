package com.rotdb.ability;

import com.rotdb.domain.model.enums.AbilityTier;
import com.rotdb.domain.model.enums.CombatStyles;

import static com.rotdb.domain.model.enums.AbilityTier.*;
import static com.rotdb.domain.model.enums.CombatStyles.*;

public enum AbilityId {
    MELEEAUTO(MELEE, BASIC,"Attack"),
    ASSAULT(MELEE, THRESHOLD,"Assault"),
    BLOODLUSTASSAULT(MELEE, THRESHOLD,"Bloodlust Assault"),
    SEVER2H(MELEE, BASIC,"Sever"),
    SEVERDW(MELEE, BASIC,"Sever"),
    OVERPOWER(MELEE, ULTIMATE,"Overpower"),
    OVERPOWERIGNEOUS(MELEE, ULTIMATE,"Igneous Overpower"),
    REND(MELEE, BASIC,"Rend"),
    FURY(MELEE, BASIC,"Fury"),
    GREATERFURY(MELEE, BASIC,"Greater Fury"),
    BACKHAND(MELEE, BASIC,"Backhand"),
    HURRICANE(MELEE, THRESHOLD,"Hurricane"),
    BLOODLUSTHURRICANE(MELEE, THRESHOLD,"Bloodlust Hurricane"),
    FLURRY(MELEE, THRESHOLD,"Flurry"),
    GREATERFLURRY(MELEE, THRESHOLD,"Flurry"),
    DISMEMBER(MELEE, BASIC,"Dismember"),
    SLAUGHTER(MELEE, THRESHOLD,"Slaughter"),
    MASSACRE(MELEE, THRESHOLD,"Massacre"),
    PUNISH(MELEE, BASIC,"Punish"),
    BARGE(MELEE, BASIC,"Barge"),
    GREATERBARGE(MELEE, BASIC,"Greater Barge"),
    PULVERISE(MELEE, ULTIMATE,"Pulverise"),
    METEORSTRIKE(MELEE, ULTIMATE,"Meteor Strike"),

    CHAOSROAR(MELEE, BASIC,"Chaos Roar"),
    MAGICAUTO(MAGIC, BASIC,"Magic"),
    WILDMAGIC(MAGIC, THRESHOLD,"Wild Magic"),
    SONICWAVE(MAGIC, BASIC,"Sonic Wave"),
    GREATERSONICWAVE(MAGIC, BASIC,"Greater Sonic Wave"),
    OMNIPOWER(MAGIC, ULTIMATE,"Omnipower"),
    OMNIPOWERIGNEOUS(MAGIC, ULTIMATE,"Igneous Omnipower"),
    DRAGONBREATH(MAGIC, BASIC,"Dragon Breath"),
    IMPACT(MAGIC, BASIC,"Impact"),
    COMBUST(MAGIC, BASIC,"Combust"),
    CHAIN(MAGIC, BASIC,"Chain"),
    GREATERCHAIN(MAGIC, BASIC,"Greater Chain"),
    ASPHYXIATE(MAGIC, THRESHOLD,"Asphyxiate"),
    ASPHYXIATETUMEKENS(MAGIC, THRESHOLD,"Tumeken's Asphyxiate"),
    CONCENTRATEDBLAST(MAGIC, BASIC,"Concentrated Blast"),
    GREATERCONCENTRATEDBLAST(MAGIC, BASIC,"Greater Concentrated Blast"),
    MAGMATEMPEST(MAGIC, THRESHOLD,"Magma Tempest"),
    CORRUPTIONBLAST(MAGIC, THRESHOLD,"Corruption Blast"),
    SMOKETENDRILS(MAGIC, THRESHOLD,"Smoke Tendrils"),
    TSUNAMI(MAGIC, ULTIMATE,"Tsunami"),

    RANGEDAUTO(RANGED, BASIC,"Ranged"),
    SNAPSHOT(RANGED, THRESHOLD,"Snap Shot"),
    SNIPE(RANGED, BASIC,"Snipe"),
    PIERCINGSHOT(RANGED, BASIC,"Piercing Shot"),
    DEADSHOT(RANGED, ULTIMATE,"Deadshot"),
    DEADSHOTIGNEOUS(RANGED, ULTIMATE,"Igneous Deadshot"),
    BINDINGSHOT(RANGED, BASIC,"Binding Shot"),
    BOMBARDMENT(RANGED, THRESHOLD,"Bombardment"),
    GALESHOT(RANGED, BASIC,"Galeshot"),
    RAPIDFIRE(RANGED, THRESHOLD,"Rapid Fire"),
    RICOCHET(RANGED, BASIC,"Ricochet"),
    GREATERRICOCHET(RANGED, BASIC,"Greater Ricochet"),
    CORRUPTIONSHOT(RANGED, THRESHOLD,"Corruption Shot"),
    SHADOWTENDRILS(RANGED, THRESHOLD,"Shadow Tendrils"),

    NECROMANCYAUTO(NECROMANCY, BASIC,"Necromancy"),
    CONJURESKELETONWARRIOR(NECROMANCY, BASIC,"Conjure Skeleton Warrior"),
    COMMANDSKELETONWARRIOR(NECROMANCY, BASIC,"Command Skeleton Warrior"),
    FINGEROFDEATH(NECROMANCY, THRESHOLD,"Finger of Death"),
    TOUCHOFDEATH(NECROMANCY, BASIC,"Touch of Death"),
    DEATHSKULLS(NECROMANCY, ULTIMATE,"Death Skulls"),
    DEATHSKULLSIGNEOUS(NECROMANCY, ULTIMATE,"Igneous Death Skulls"),
    BLOODSIPHON(NECROMANCY, BASIC,"Blood Siphon"),
    BLOODSIPHONHEAL(NECROMANCY, BASIC,"Blood Siphon AOE"),
    CONJUREPUTRIDZOMBIE(NECROMANCY, BASIC,"Conjure Putrid Zombie"),
    COMMANDPUTRIDZOMBIE(NECROMANCY, BASIC,"Command Putrid Zombie"),
    CONJUREVENGEFULGHOST(NECROMANCY, BASIC,"Conjure Vengeful Ghost"),
    BLOAT(NECROMANCY, THRESHOLD,"Bloat"),
    SOULSAP(NECROMANCY, BASIC,"Soul Sap"),
    SOULSTRIKE(NECROMANCY, THRESHOLD,"Soul Strike"),
    SPECTRALSCYTHE(NECROMANCY, THRESHOLD,"Spectral Scythe"),
    SPECTRALHURRICANE(NECROMANCY, THRESHOLD,"Spectral Scythe"),
    SPECTRALMETEORSTRIKE(NECROMANCY, THRESHOLD,"Spectral Scythe"),
    VOLLEYOFSOULS(NECROMANCY, THRESHOLD,"Volley of Souls"),
    COMMANDPHANTOMGUARDIAN(NECROMANCY, BASIC,"Command Phantom Guardian"),

    ENERGYDRAIN(MELEE, SPECIAL,"Energy Drain"),
    WEAKEN(MELEE, SPECIAL,"Weaken"),
    THEFINALFLURRY(MELEE, SPECIAL,"The Final Flurry"),
    SPEARWALL(MELEE, SPECIAL,"Spear Wall"),
    CLOBBER(MELEE, SPECIAL,"Clobber"),
    QUICKSMASH(MELEE, SPECIAL,"Quick Smash"),
    SWEEP(MELEE, SPECIAL,"Sweep"),
    IMPALE(MELEE, SPECIAL,"Impale"),
    LIQUEFY(MELEE, SPECIAL,"Liquefy"),
    FAVOUROFTHEWARGOD(MELEE, SPECIAL,"Favour of the War God"),
    SUNDER(MELEE, SPECIAL,"Sunder"),
    DRACONICPUNCTURE(MELEE, SPECIAL,"Draconic Puncture"),
    BACKSTAB(MELEE, SPECIAL,"Backstab"),
    AIMEDSTRIKE(MELEE, SPECIAL,"Aimed Strike"),
    OBLITERATE(MELEE, SPECIAL,"Obliterate"),
    HEALINGBLADE(MELEE, SPECIAL,"Healing Blade"),
    ICECLEAVE(MELEE, SPECIAL,"Ice Cleave"),
    DISRUPT(MELEE, SPECIAL,"Disrupt"),
    WARSTRIKE(MELEE, SPECIAL,"Warstrike"),
    DRACONICBLOW(MELEE, SPECIAL,"Draconic Blow"),
    DRACONICSLASH(MELEE, SPECIAL,"Draconic Slash"),
    FEINT(MELEE, SPECIAL,"Feint"),
    IGNEOUSSHOWDOWN(MELEE, SPECIAL,"Igneous Showdown"),
    IGNEOUSSHOWDOWNRECAST(MELEE, SPECIAL,"Igneous Showdown Recast"),
    DRACONICCLEAVE(MELEE, SPECIAL,"Draconic Cleave"),
    SARADOMINSLIGHTNING(MELEE, SPECIAL,"Saradomin's Light"),
    SUNFALLSLAM(MELEE, SPECIAL,"Sunfall Slam"),
    POWERSTAB(MELEE, SPECIAL,"Powerstab"),
    ARMADYLSJUDGEMENT(MELEE, SPECIAL,"Armadyl's Judgement"),
    BLACKHOLE(MELEE, SPECIAL,"Blackhole"),
    VINECALL(MELEE, SPECIAL,"Vine Call"),
    ICYTEMPEST(MELEE, SPECIAL,"Icy Tempest"),
    SLICEANDDICE(MELEE, SPECIAL,"Slice & Dice"),

    FROMTHESHADOWS(MAGIC, SPECIAL,"Fromt the Shadows"),
    INSTABILITY(MAGIC, SPECIAL,"Instability"),
    RUNEFLAME(MAGIC, SPECIAL,"Rune Flame"),
    CLAWSOFGUTHIX(MAGIC, SPECIAL,"Claws of Guthix"),
    DEVOUR(MAGIC, SPECIAL,"Devour"),
    SARADOMINSTRIKE(MAGIC, SPECIAL,"Saradomin Strike"),
    FLAMESOFZAMORAK(MAGIC, SPECIAL,"Flames of Zamorak"),
    MIASMICBARRAGE(MAGIC, SPECIAL,"Miasmic Barrage"),
    THELASTCOMMAND(MAGIC, SPECIAL,"The Last Command"),
    REAP(MAGIC, SPECIAL,"Reap"),
    TEMPESTOFARMADYL(MAGIC, SPECIAL,"Tempest of Armadyl"),
    IBANBLAST(MAGIC, SPECIAL,"Iban Blast"),
    SOULFIRE(MAGIC, SPECIAL,"Soulfire"),

    CHAINHIT(RANGED, SPECIAL,"Chain Hit"),
    TWINSHOT(RANGED, SPECIAL,"Twin Shot"),
    SHADOWFALL(RANGED, SPECIAL,"Shadowfall"),
    SOULSHOT(RANGED, SPECIAL,"Soulshot"),
    TWINFANG(RANGED, SPECIAL,"Twin Fang"),
    CRYSTALRAIN(RANGED, SPECIAL,"Crystal Rain"),
    HAMSTRING(RANGED, SPECIAL,"Hamstring"),
    DESCENTOFDARKNESS(RANGED, SPECIAL,"Descent of Darkness"),
    POWERSHOT(RANGED, SPECIAL,"Powershot"),
    DEFIANCE(RANGED, SPECIAL,"Defiance"),
    BALANCEBYFORCE(RANGED, SPECIAL,"Balance by Force"),
    PHANTOMSTRIKE(RANGED, SPECIAL,"Phantom Strike"),
    AIMEDSHOT(RANGED, SPECIAL,"Aimed Shot"),
    DESTRUCTIVESHOT(RANGED, SPECIAL,"Destructive Shot"),
    RESTORATIVESHOT(RANGED, SPECIAL,"Restorative Shot"),
    BALANCEDSHOT(RANGED, SPECIAL,"Balanced Shot"),

    DEATHGRASP(NECROMANCY, SPECIAL,"Death Grasp"),
    SOULCRUSH(NECROMANCY, SPECIAL,"Soul Crush"),
    DEATHESSENCE(NECROMANCY, SPECIAL,"Death Essence");

    private final CombatStyles style;
    private final AbilityTier tier;
    private final String name;

    AbilityId(CombatStyles style, AbilityTier type, String name) {
        this.style = style;
        this.tier = type;
        this.name = name;
    }

    public CombatStyles getStyle() {
        return style;
    }

    public AbilityTier getTier() {
        return tier;
    }

    public String getName() {
        return name;
    }
}
