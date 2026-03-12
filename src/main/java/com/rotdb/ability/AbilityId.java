package com.rotdb.ability;

import com.rotdb.domain.model.enums.AbilityTier;
import com.rotdb.domain.model.enums.CombatStyles;

import static com.rotdb.domain.model.enums.AbilityTier.*;
import static com.rotdb.domain.model.enums.CombatStyles.*;

public enum AbilityId {
    MELEEAUTO(MELEE, BASIC,"Attack", "/ability_icons/attack.png"),
    ASSAULT(MELEE, THRESHOLD,"Assault", "/ability_icons/assault.png"),
    BLOODLUSTASSAULT(MELEE, THRESHOLD,"Bloodlust Assault", "/ability_icons/assault.png"),
    SEVER2H(MELEE, BASIC,"Adaptive Strike DW", "/ability_icons/adaptive-strike.png"),
    SEVERDW(MELEE, BASIC,"Adaptive Strike 2H", "/ability_icons/adaptive-strike.png"),
    OVERPOWER(MELEE, ULTIMATE,"Overpower", "/ability_icons/overpower.png"),
    OVERPOWERIGNEOUS(MELEE, ULTIMATE,"Igneous Overpower", "/ability_icons/overpower.png"),
    REND(MELEE, BASIC,"Rend", "/ability_icons/rend.png"),
    FURY(MELEE, BASIC,"Fury", "/ability_icons/fury.png"),
    GREATERFURY(MELEE, BASIC,"Greater Fury", "/ability_icons/greater-fury.png"),
    BACKHAND(MELEE, BASIC,"Backhand", "/ability_icons/backhand.png"),
    HURRICANE(MELEE, THRESHOLD,"Hurricane", "/ability_icons/hurricane.png"),
    BLOODLUSTHURRICANE(MELEE, THRESHOLD,"Bloodlust Hurricane", "/ability_icons/hurricane.png"),
    FLURRY(MELEE, THRESHOLD,"Flurry", "/ability_icons/flurry.png"),
    GREATERFLURRY(MELEE, THRESHOLD,"Greater Flurry", "/ability_icons/greater-flurry.png"),
    DISMEMBER(MELEE, BASIC,"Dismember", "/ability_icons/dismember.png"),
    SLAUGHTER(MELEE, THRESHOLD,"Slaughter", "/ability_icons/slaughter.png"),
    MASSACRE(MELEE, THRESHOLD,"Massacre", "/ability_icons/massacre.png"),
    PUNISH(MELEE, BASIC,"Punish", "/ability_icons/punish.png"),
    BARGE(MELEE, BASIC,"Barge", "/ability_icons/barge.png"),
    GREATERBARGE(MELEE, BASIC,"Greater Barge", "/ability_icons/greater-barge.png"),
    PULVERISE(MELEE, ULTIMATE,"Pulverise", "/ability_icons/pulverise.png"),
    METEORSTRIKE(MELEE, ULTIMATE,"Meteor Strike", "/ability_icons/meteor-strike.png"),
    CHAOSROAR(MELEE, BASIC,"Chaos Roar", "/ability_icons/chaos-roar.png"),

    MAGICAUTO(MAGIC, BASIC,"Magic", "/ability_icons/magic.png"),
    WILDMAGIC(MAGIC, THRESHOLD,"Wild Magic", "/ability_icons/wild-magic.png"),
    SONICWAVE(MAGIC, BASIC,"Sonic Wave", "/ability_icons/sonic-wave.png"),
    GREATERSONICWAVE(MAGIC, BASIC,"Greater Sonic Wave", "/ability_icons/greater-sonic-wave.png"),
    OMNIPOWER(MAGIC, ULTIMATE,"Omnipower", "/ability_icons/omnipower.png"),
    OMNIPOWERIGNEOUS(MAGIC, ULTIMATE,"Igneous Omnipower", "/ability_icons/omnipower.png"),
    DRAGONBREATH(MAGIC, BASIC,"Dragon Breath", "/ability_icons/dragon-breath.png"),
    IMPACT(MAGIC, BASIC,"Impact", "/ability_icons/impact.png"),
    COMBUST(MAGIC, BASIC,"Combust", "/ability_icons/combust.png"),
    CHAIN(MAGIC, BASIC,"Chain", "/ability_icons/chain.png"),
    GREATERCHAIN(MAGIC, BASIC,"Greater Chain", "/ability_icons/greater-chain.png"),
    ASPHYXIATE(MAGIC, THRESHOLD,"Asphyxiate", "/ability_icons/asphyxiate.png"),
    ASPHYXIATETUMEKENS(MAGIC, THRESHOLD,"Tumeken's Asphyxiate", "/ability_icons/asphyxiate.png"),
    CONCENTRATEDBLAST(MAGIC, BASIC,"Concentrated Blast", "/ability_icons/concentrated-blast.png"),
    GREATERCONCENTRATEDBLAST(MAGIC, BASIC,"Greater Concentrated Blast", "/ability_icons/greater-concentrated-blast.png"),
    MAGMATEMPEST(MAGIC, THRESHOLD,"Magma Tempest", "/ability_icons/magma-tempest.png"),
    CORRUPTIONBLAST(MAGIC, THRESHOLD,"Corruption Blast", "/ability_icons/corruption-blast.png"),
    SMOKETENDRILS(MAGIC, THRESHOLD,"Smoke Tendrils", "/ability_icons/smoke-tendrils.png"),
    TSUNAMI(MAGIC, ULTIMATE,"Tsunami", "/ability_icons/tsunami.png"),

    RANGEDAUTO(RANGED, BASIC,"Ranged", "/ability_icons/ranged.png"),
    SNAPSHOT(RANGED, THRESHOLD,"Snap Shot", "/ability_icons/snap-shot.png"),
    SNIPE(RANGED, BASIC,"Snipe", "/ability_icons/snipe.png"),
    PIERCINGSHOT(RANGED, BASIC,"Piercing Shot", "/ability_icons/piercing-shot.png"),
    DEADSHOT(RANGED, ULTIMATE,"Deadshot", "/ability_icons/deadshot.png"),
    DEADSHOTIGNEOUS(RANGED, ULTIMATE,"Igneous Deadshot", "/ability_icons/deadshot.png"),
    BINDINGSHOT(RANGED, BASIC,"Binding Shot", "/ability_icons/binding-shot.png"),
    BOMBARDMENT(RANGED, THRESHOLD,"Bombardment", "/ability_icons/bombardment.png"),
    GALESHOT(RANGED, BASIC,"Galeshot", "/ability_icons/galeshot.png"),
    RAPIDFIRE(RANGED, THRESHOLD,"Rapid Fire", "/ability_icons/rapid-fire.png"),
    RICOCHET(RANGED, BASIC,"Ricochet", "/ability_icons/ricochet.png"),
    GREATERRICOCHET(RANGED, BASIC,"Greater Ricochet", "/ability_icons/greater-ricochet.png"),
    CORRUPTIONSHOT(RANGED, THRESHOLD,"Corruption Shot", "/ability_icons/corruption-shot.png"),
    SHADOWTENDRILS(RANGED, THRESHOLD,"Shadow Tendrils", "/ability_icons/shadow-tendrils.png"),

    NECROMANCYAUTO(NECROMANCY, BASIC,"Necromancy", "/ability_icons/necromnacy.png"),
    CONJURESKELETONWARRIOR(NECROMANCY, BASIC,"Conjure Skeleton Warrior", "/ability_icons/conjure-skeleton-warrior.png"),
    COMMANDSKELETONWARRIOR(NECROMANCY, BASIC,"Command Skeleton Warrior", "/ability_icons/command-skeleton-warrior.png"),
    FINGEROFDEATH(NECROMANCY, THRESHOLD,"Finger of Death", "/ability_icons/finger-of-death.png"),
    TOUCHOFDEATH(NECROMANCY, BASIC,"Touch of Death", "/ability_icons/touch-of-death.png"),
    DEATHSKULLS(NECROMANCY, ULTIMATE,"Death Skulls", "/ability_icons/death-skulls.png"),
    DEATHSKULLSIGNEOUS(NECROMANCY, ULTIMATE,"Igneous Death Skulls", "/ability_icons/death-skulls.png"),
    BLOODSIPHON(NECROMANCY, BASIC,"Blood Siphon", "/ability_icons/blood-siphon.png"),
    BLOODSIPHONHEAL(NECROMANCY, BASIC,"Blood Siphon AOE", "/ability_icons/blood-siphon.png"),
    CONJUREPUTRIDZOMBIE(NECROMANCY, BASIC,"Conjure Putrid Zombie", "/ability_icons/conjure-putrid-zombie.png"),
    COMMANDPUTRIDZOMBIE(NECROMANCY, BASIC,"Command Putrid Zombie", "/ability_icons/command-putrid-zombie.png"),
    CONJUREVENGEFULGHOST(NECROMANCY, BASIC,"Conjure Vengeful Ghost", "/ability_icons/conjure-vengeful-ghost.png"),
    BLOAT(NECROMANCY, THRESHOLD,"Bloat", "/ability_icons/bloat.png"),
    SOULSAP(NECROMANCY, BASIC,"Soul Sap", "/ability_icons/soul-sap.png"),
    SOULSTRIKE(NECROMANCY, THRESHOLD,"Soul Strike", "/ability_icons/soul-strike.png"),
    SPECTRALSCYTHE(NECROMANCY, THRESHOLD,"Spectral Scythe", "/ability_icons/spectral-scythe.png"),
    SPECTRALHURRICANE(NECROMANCY, THRESHOLD,"Spectral Scythe", "/ability_icons/spectral-scythe-2.png"),
    SPECTRALMETEORSTRIKE(NECROMANCY, THRESHOLD,"Spectral Scythe", "/ability_icons/spectral-scythe-3.png"),
    VOLLEYOFSOULS(NECROMANCY, THRESHOLD,"Volley of Souls", "/ability_icons/volley-of-souls.png"),
    COMMANDPHANTOMGUARDIAN(NECROMANCY, BASIC,"Command Phantom Guardian", "/ability_icons/command-phantom-guardian.png"),

    ENERGYDRAIN(MELEE, SPECIAL,"Energy Drain", "special_attack_weapon_icons/abyssal-whip.png", false),
    WEAKEN(MELEE, SPECIAL,"Weaken", "special_attack_weapon_icons/darklight.png", false),
    THEFINALFLURRY(MELEE, SPECIAL,"The Final Flurry", "special_attack_weapon_icons/varanus-s-mercy.png", true),
    SPEARWALL(MELEE, SPECIAL,"Spear Wall", "special_attack_weapon_icons/vesta-s-spear.png", false),
    CLOBBER(MELEE, SPECIAL,"Clobber", "special_attack_weapon_icons/dragon-hatchet.png", false),
    QUICKSMASH(MELEE, SPECIAL,"Quick Smash", "special_attack_weapon_icons/granite-maul.png", true),
    SWEEP(MELEE, SPECIAL,"Sweep", "special_attack_weapon_icons/dragon-halberd.png", true),
    IMPALE(MELEE, SPECIAL,"Impale", "special_attack_weapon_icons/rune-claws.png", false),
    LIQUEFY(MELEE, SPECIAL,"Liquefy", "special_attack_weapon_icons/brackish-blade.png", false),
    FAVOUROFTHEWARGOD(MELEE, SPECIAL,"Favour of the War God", "special_attack_weapon_icons/ancient-mace.png", false),
    SUNDER(MELEE, SPECIAL,"Sunder", "special_attack_weapon_icons/barrelchest-anchor.png", false),
    DRACONICPUNCTURE(MELEE, SPECIAL,"Draconic Puncture", "special_attack_weapon_icons/dragon-dagger.png", false),
    BACKSTAB(MELEE, SPECIAL,"Backstab", "special_attack_weapon_icons/bone-dagger.png", false),
    AIMEDSTRIKE(MELEE, SPECIAL,"Aimed Strike", "special_attack_weapon_icons/keenblade.png", false),
    OBLITERATE(MELEE, SPECIAL,"Obliterate", "special_attack_weapon_icons/statius-s-warhammer.png", true),
    HEALINGBLADE(MELEE, SPECIAL,"Healing Blade", "special_attack_weapon_icons/saradomin-godsword.png", false),
    ICECLEAVE(MELEE, SPECIAL,"Ice Cleave", "special_attack_weapon_icons/zamorak-godsword.png", false),
    DISRUPT(MELEE, SPECIAL,"Disrupt", "special_attack_weapon_icons/korasi-s-sword.png", false),
    WARSTRIKE(MELEE, SPECIAL,"Warstrike", "special_attack_weapon_icons/bandos-godsword.png", false),
    DRACONICBLOW(MELEE, SPECIAL,"Draconic Blow", "special_attack_weapon_icons/dragon-mace.png", false),
    DRACONICSLASH(MELEE, SPECIAL,"Draconic Slash", "special_attack_weapon_icons/dragon-scimitar.png", true),
    FEINT(MELEE, SPECIAL,"Feint", "special_attack_weapon_icons/vesta-s-longsword.png", false),
    IGNEOUSSHOWDOWN(MELEE, SPECIAL,"Igneous Showdown", "special_attack_weapon_icons/ek-zekkil.png", true),
    IGNEOUSSHOWDOWNRECAST(MELEE, SPECIAL,"Igneous Showdown Recast", "special_attack_weapon_icons/ek-zekkil.png", true),
    DRACONICCLEAVE(MELEE, SPECIAL,"Draconic Cleave", "special_attack_weapon_icons/dragon-longsword.png", true),
    SARADOMINSLIGHTNING(MELEE, SPECIAL,"Saradomin's Light", "special_attack_weapon_icons/saradomin-sword.png", false),
    SUNFALLSLAM(MELEE, SPECIAL,"Sunfall Slam", "special_attack_weapon_icons/tumeken-s-light.png", true),
    POWERSTAB(MELEE, SPECIAL,"Powerstab", "special_attack_weapon_icons/dragon-2h-sword.png", true),
    ARMADYLSJUDGEMENT(MELEE, SPECIAL,"Armadyl's Judgement", "special_attack_weapon_icons/armadyl-godsword.png", false),
    BLACKHOLE(MELEE, SPECIAL,"Blackhole", "special_attack_weapon_icons/zaros-godsword.png", false),
    VINECALL(MELEE, SPECIAL,"Vine Call", "special_attack_weapon_icons/abyssal-vine-whip.png", false),
    ICYTEMPEST(MELEE, SPECIAL,"Icy Tempest", "special_attack_weapon_icons/dark-shard-of-leng.png", true),
    SLICEANDDICE(MELEE, SPECIAL,"Slice & Dice", "special_attack_weapon_icons/dragon-claw.png", true),

    FROMTHESHADOWS(MAGIC, SPECIAL,"From the Shadows", "special_attack_weapon_icons/staff-of-sliske.png", false),
    INSTABILITY(MAGIC, SPECIAL,"Instability", "special_attack_weapon_icons/fractured-staff-of-armadyl.png", true),
    RUNEFLAME(MAGIC, SPECIAL,"Rune Flame", "special_attack_weapon_icons/mindspike.png", false),
    CLAWSOFGUTHIX(MAGIC, SPECIAL,"Claws of Guthix", "special_attack_weapon_icons/guthix-staff.png", true),
    DEVOUR(MAGIC, SPECIAL,"Devour", "special_attack_weapon_icons/obliteration.png", false),
    SARADOMINSTRIKE(MAGIC, SPECIAL,"Saradomin Strike", "special_attack_weapon_icons/saradomin-staff.png", false),
    FLAMESOFZAMORAK(MAGIC, SPECIAL,"Flames of Zamorak", "special_attack_weapon_icons/zamorak-staff.png", false),
    MIASMICBARRAGE(MAGIC, SPECIAL,"Miasmic Barrage", "special_attack_weapon_icons/zuriel-s-staff.png", false),
    THELASTCOMMAND(MAGIC, SPECIAL,"The Last Command", "special_attack_weapon_icons/legatus-s-emberstaff.png", true),
    REAP(MAGIC, SPECIAL,"Reap", "special_attack_weapon_icons/penance-trident.png", false),
    TEMPESTOFARMADYL(MAGIC, SPECIAL,"Tempest of Armadyl", "special_attack_weapon_icons/armadyl-battlestaff.png", true),
    IBANBLAST(MAGIC, SPECIAL,"Iban Blast", "special_attack_weapon_icons/iban-s-staff.png", true),
    SOULFIRE(MAGIC, SPECIAL,"Soulfire", "special_attack_weapon_icons/roar-of-awakening.png", true),

    CHAINHIT(RANGED, SPECIAL,"Chain Hit", "special_attack_weapon_icons/rune-throwing-axe.png", false),
    TWINSHOT(RANGED, SPECIAL,"Twin Shot", "special_attack_weapon_icons/quickbow.png", false),
    SHADOWFALL(RANGED, SPECIAL,"Shadowfall", "special_attack_weapon_icons/gloomfire-bow.png", true),
    SOULSHOT(RANGED, SPECIAL,"Soulshot", "special_attack_weapon_icons/seercull.png", false),
    TWINFANG(RANGED, SPECIAL,"Twin Fang", "special_attack_weapon_icons/magic-shortbow.png", false),
    CRYSTALRAIN(RANGED, SPECIAL,"Crystal Rain", "special_attack_weapon_icons/seren-godbow.png", true),
    HAMSTRING(RANGED, SPECIAL,"Hamstring", "special_attack_weapon_icons/morrigan-s-throwing-axe.png", false),
    DESCENTOFDARKNESS(RANGED, SPECIAL,"Descent of Darkness", "special_attack_weapon_icons/dark-bow.png", true),
    POWERSHOT(RANGED, SPECIAL,"Powershot", "special_attack_weapon_icons/magic-composite-bow.png", false),
    DEFIANCE(RANGED, SPECIAL,"Defiance", "special_attack_weapon_icons/zanik-s-crossbow.png", false),
    BALANCEBYFORCE(RANGED, SPECIAL,"Balance by Force", "special_attack_weapon_icons/bow-of-the-last-guardian.png", true),
    PHANTOMSTRIKE(RANGED, SPECIAL,"Phantom Strike", "special_attack_weapon_icons/morrigan-s-javelin.png", false),
    AIMEDSHOT(RANGED, SPECIAL,"Aimed Shot", "special_attack_weapon_icons/hand-cannon.png", false),
    DESTRUCTIVESHOT(RANGED, SPECIAL,"Destructive Shot", "special_attack_weapon_icons/zamorak-bow.png", true),
    RESTORATIVESHOT(RANGED, SPECIAL,"Restorative Shot", "special_attack_weapon_icons/saradomin-bow.png", false),
    BALANCEDSHOT(RANGED, SPECIAL,"Balanced Shot", "special_attack_weapon_icons/guthix-bow.png", false),

    DEATHGRASP(NECROMANCY, SPECIAL,"Death Grasp", "special_attack_weapon_icons/death-guard.png", true),
    SOULCRUSH(NECROMANCY, SPECIAL,"Soul Crush", "special_attack_weapon_icons/devourer-s-guard.png", true),
    DEATHESSENCE(NECROMANCY, SPECIAL,"Death Essence", "special_attack_weapon_icons/omni-guard.png", true);

    private final CombatStyles style;
    private final AbilityTier tier;
    private final String name;
    private final String iconPath;
    private final boolean common;

    AbilityId(CombatStyles style, AbilityTier tier, String name, String iconPath) {
        this(style, tier, name, iconPath, false);
    }

    AbilityId(CombatStyles style, AbilityTier tier, String name, String iconPath, boolean common) {
        this.style = style;
        this.tier = tier;
        this.name = name;
        this.iconPath = iconPath;
        this.common = common;
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

    public String getIconPath() {
        return iconPath;
    }

    public boolean isCommon() {
        return common;
    }
}
