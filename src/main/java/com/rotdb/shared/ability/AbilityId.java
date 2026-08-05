package com.rotdb.shared.ability;

import com.rotdb.shared.combat.domain.model.enums.AbilityTier;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import static com.rotdb.shared.combat.domain.model.enums.AbilityTier.*;
import static com.rotdb.shared.combat.domain.model.enums.CombatStyles.*;

public enum AbilityId {
    MELEEAUTO(MELEE, BASIC,"Attack", "/ability_icons/attack.png"),
    ASSAULT(MELEE, ENHANCED,"Assault", "/ability_icons/assault.png"),
    BLOODLUSTASSAULT(MELEE, ENHANCED,"Bloodlust Assault", "/ability_icons/assault.png"),
    ADAPTIVESTRIKE(MELEE, BASIC, "Adaptive Strike", "/ability_icons/adaptive-strike.png"),
    OVERPOWER(MELEE, ULTIMATE,"Overpower", "/ability_icons/overpower.png"),
    OVERPOWERIGNEOUS(MELEE, ULTIMATE,"Igneous Overpower", "/ability_icons/overpower.png"),
    REND(MELEE, BASIC,"Rend", "/ability_icons/rend.png"),
    FURY(MELEE, BASIC,"Fury", "/ability_icons/fury.png"),
    GREATERFURY(MELEE, BASIC,"Greater Fury", "/ability_icons/greater-fury.png"),
    BACKHAND(MELEE, BASIC,"Backhand", "/ability_icons/backhand.png"),
    HURRICANE(MELEE, ENHANCED,"Hurricane", "/ability_icons/hurricane.png"),
    BLOODLUSTHURRICANE(MELEE, ENHANCED,"Bloodlust Hurricane", "/ability_icons/hurricane.png"),
    FLURRY(MELEE, ENHANCED,"Flurry", "/ability_icons/flurry.png"),
    GREATERFLURRY(MELEE, ENHANCED,"Greater Flurry", "/ability_icons/greater-flurry.png"),
    DISMEMBER(MELEE, ENHANCED,"Dismember", "/ability_icons/dismember.png"),
    SLAUGHTER(MELEE, ENHANCED,"Slaughter", "/ability_icons/slaughter.png"),
    MASSACRE(MELEE, ENHANCED,"Massacre", "/ability_icons/massacre.png"),
    PUNISH(MELEE, BASIC,"Punish", "/ability_icons/punish.png"),
    BARGE(MELEE, BASIC,"Barge", "/ability_icons/barge.png"),
    GREATERBARGE(MELEE, BASIC,"Greater Barge", "/ability_icons/greater-barge.png"),
    PULVERISE(MELEE, ULTIMATE,"Pulverise", "/ability_icons/pulverise.png"),
    METEORSTRIKE(MELEE, ULTIMATE,"Meteor Strike", "/ability_icons/meteor-strike.png"),
    CHAOSROAR(MELEE, BASIC,"Chaos Roar", "/ability_icons/chaos-roar.png"),

    MAGICAUTO(MAGIC, BASIC,"Magic", "/ability_icons/magic.png"),
    WILDMAGIC(MAGIC, ENHANCED,"Wild Magic", "/ability_icons/wild-magic.png"),
    SONICWAVE(MAGIC, BASIC,"Sonic Wave", "/ability_icons/sonic-wave.png"),
    GREATERSONICWAVE(MAGIC, BASIC,"Greater Sonic Wave", "/ability_icons/greater-sonic-wave.png"),
    OMNIPOWER(MAGIC, ULTIMATE,"Omnipower", "/ability_icons/omnipower.png"),
    OMNIPOWERIGNEOUS(MAGIC, ULTIMATE,"Igneous Omnipower", "/ability_icons/omnipower.png"),
    DRAGONBREATH(MAGIC, BASIC,"Dragon Breath", "/ability_icons/dragon-breath.png"),
    IMPACT(MAGIC, BASIC,"Impact", "/ability_icons/impact.png"),
    COMBUST(MAGIC, BASIC,"Combust", "/ability_icons/combust.png"),
    CHAIN(MAGIC, BASIC,"Chain", "/ability_icons/chain.png"),
    GREATERCHAIN(MAGIC, BASIC,"Greater Chain", "/ability_icons/greater-chain.png"),
    ASPHYXIATE(MAGIC, ENHANCED,"Asphyxiate", "/ability_icons/asphyxiate.png"),
    CONCENTRATEDBLAST(MAGIC, BASIC,"Concentrated Blast", "/ability_icons/concentrated-blast.png"),
    GREATERCONCENTRATEDBLAST(MAGIC, BASIC,"Greater Concentrated Blast", "/ability_icons/greater-concentrated-blast.png"),
    MAGMATEMPEST(MAGIC, ENHANCED,"Magma Tempest", "/ability_icons/magma-tempest.png"),
    CORRUPTIONBLAST(MAGIC, ENHANCED,"Corruption Blast", "/ability_icons/corruption-blast.png"),
    SMOKETENDRILS(MAGIC, ENHANCED,"Smoke Tendrils", "/ability_icons/smoke-tendrils.png"),
    TSUNAMI(MAGIC, ULTIMATE,"Tsunami", "/ability_icons/tsunami.png"),

    RANGEDAUTO(RANGED, BASIC,"Ranged", "/ability_icons/ranged.png"),
    SNAPSHOT(RANGED, ENHANCED,"Snap Shot", "/ability_icons/snap-shot.png"),
    SNIPE(RANGED, ENHANCED,"Snipe", "/ability_icons/snipe.png"),
    PIERCINGSHOT(RANGED, BASIC,"Piercing Shot", "/ability_icons/piercing-shot.png"),
    DEADSHOT(RANGED, ULTIMATE,"Deadshot", "/ability_icons/deadshot.png"),
    DEADSHOTIGNEOUS(RANGED, ULTIMATE,"Igneous Deadshot", "/ability_icons/deadshot.png"),
    BINDINGSHOT(RANGED, BASIC,"Binding Shot", "/ability_icons/binding-shot.png"),
    BOMBARDMENT(RANGED, ENHANCED,"Bombardment", "/ability_icons/bombardment.png"),
    GALESHOT(RANGED, BASIC,"Galeshot", "/ability_icons/galeshot.png"),
    RAPIDFIRE(RANGED, ENHANCED,"Rapid Fire", "/ability_icons/rapid-fire.png"),
    RICOCHET(RANGED, BASIC,"Ricochet", "/ability_icons/ricochet.png"),
    GREATERRICOCHET(RANGED, BASIC,"Greater Ricochet", "/ability_icons/greater-ricochet.png"),
    CORRUPTIONSHOT(RANGED, ENHANCED,"Corruption Shot", "/ability_icons/corruption-shot.png"),
    SHADOWTENDRILS(RANGED, ENHANCED,"Shadow Tendrils", "/ability_icons/shadow-tendrils.png"),

    NECROMANCYAUTO(NECROMANCY, BASIC,"Necromancy", "/ability_icons/necromancy.png"),
    CONJURESKELETONWARRIOR(NECROMANCY, CONJURE,"Conjure Skeleton Warrior", "/ability_icons/conjure-skeleton-warrior.png"),
    COMMANDSKELETONWARRIOR(NECROMANCY, CONJURE,"Command Skeleton Warrior", "/ability_icons/command-skeleton-warrior.png"),
    FINGEROFDEATH(NECROMANCY, ENHANCED,"Finger of Death", "/ability_icons/finger-of-death.png"),
    TOUCHOFDEATH(NECROMANCY, BASIC,"Touch of Death", "/ability_icons/touch-of-death.png"),
    DEATHSKULLS(NECROMANCY, ULTIMATE,"Death Skulls", "/ability_icons/death-skulls.png"),
    DEATHSKULLSIGNEOUS(NECROMANCY, ULTIMATE,"Igneous Death Skulls", "/ability_icons/death-skulls.png"),
    BLOODSIPHON(NECROMANCY, ENHANCED,"Blood Siphon", "/ability_icons/blood-siphon.png"),
    BLOODSIPHONHEAL(NECROMANCY, ENHANCED,"Blood Siphon AOE", "/ability_icons/blood-siphon.png"),
    CONJUREPUTRIDZOMBIE(NECROMANCY, CONJURE,"Conjure Putrid Zombie", "/ability_icons/conjure-putrid-zombie.png"),
    COMMANDPUTRIDZOMBIE(NECROMANCY, CONJURE,"Command Putrid Zombie", "/ability_icons/command-putrid-zombie.png"),
    CONJUREVENGEFULGHOST(NECROMANCY, CONJURE,"Conjure Vengeful Ghost", "/ability_icons/conjure-vengeful-ghost.png"),
    BLOAT(NECROMANCY, ENHANCED,"Bloat", "/ability_icons/bloat.png"),
    SOULSAP(NECROMANCY, BASIC,"Soul Sap", "/ability_icons/soul-sap.png"),
    SOULSTRIKE(NECROMANCY, ENHANCED,"Soul Strike", "/ability_icons/soul-strike.png"),
    SPECTRALSCYTHE(NECROMANCY, ENHANCED,"Spectral Scythe", "/ability_icons/spectral-scythe.png"),
    SPECTRALHURRICANE(NECROMANCY, ENHANCED,"Spectral Scythe", "/ability_icons/spectral-scythe-2.png"),
    SPECTRALMETEORSTRIKE(NECROMANCY, ENHANCED,"Spectral Scythe", "/ability_icons/spectral-scythe-3.png"),
    VOLLEYOFSOULS(NECROMANCY, ENHANCED,"Volley of Souls", "/ability_icons/volley-of-souls.png"),
    COMMANDPHANTOMGUARDIAN(NECROMANCY, CONJURE,"Command Phantom Guardian", "/ability_icons/command-phantom-guardian.png"),

    IGNEOUSSHOWDOWN(MELEE, SPECIAL,"Igneous Showdown", "special_attack_weapon_icons/ek-zekkil.png", true),
    IGNEOUSSHOWDOWNRECAST(MELEE, SPECIAL,"Igneous Showdown Recast", "special_attack_weapon_icons/ek-zekkil.png", true),
    ICYTEMPEST(MELEE, SPECIAL,"Icy Tempest", "special_attack_weapon_icons/dark-shard-of-leng.png", true),
    THEFINALFLURRY(MELEE, SPECIAL,"The Final Flurry", "special_attack_weapon_icons/varanus-s-mercy.png", true),
    SLICEANDDICE(MELEE, SPECIAL,"Slice & Dice", "special_attack_weapon_icons/dragon-claw.png", true),
    QUICKSMASH(MELEE, SPECIAL,"Quick Smash", "special_attack_weapon_icons/granite-maul.png", true),
    SWEEP(MELEE, SPECIAL,"Sweep", "special_attack_weapon_icons/dragon-halberd.png", true),
    DRACONICSLASH(MELEE, SPECIAL,"Draconic Slash", "special_attack_weapon_icons/dragon-scimitar.png", true),
    POWERSTAB(MELEE, SPECIAL,"Powerstab", "special_attack_weapon_icons/dragon-2h-sword.png", true),
    DRACONICCLEAVE(MELEE, SPECIAL,"Draconic Cleave", "special_attack_weapon_icons/dragon-longsword.png", true),
    SUNFALLSLAM(MELEE, SPECIAL,"Sunfall Slam", "special_attack_weapon_icons/tumeken-s-light.png", true),
    OBLITERATE(MELEE, SPECIAL,"Obliterate", "special_attack_weapon_icons/statius-s-warhammer.png", true),
    SPEARWALL(MELEE, SPECIAL,"Spear Wall", "special_attack_weapon_icons/vesta-s-spear.png", false),
    CLOBBER(MELEE, SPECIAL,"Clobber", "special_attack_weapon_icons/dragon-hatchet.png", false),
    IMPALE(MELEE, SPECIAL,"Impale", "special_attack_weapon_icons/rune-claws.png", false),
    LIQUEFY(MELEE, SPECIAL,"Liquefy", "special_attack_weapon_icons/brackish-blade.png", false),
    FAVOUROFTHEWARGOD(MELEE, SPECIAL,"Favour of the War God", "special_attack_weapon_icons/ancient-mace.png", false),
    SUNDER(MELEE, SPECIAL,"Sunder", "special_attack_weapon_icons/barrelchest-anchor.png", false),
    DRACONICPUNCTURE(MELEE, SPECIAL,"Draconic Puncture", "special_attack_weapon_icons/dragon-dagger.png", false),
    BACKSTAB(MELEE, SPECIAL,"Backstab", "special_attack_weapon_icons/bone-dagger.png", false),
    AIMEDSTRIKE(MELEE, SPECIAL,"Aimed Strike", "special_attack_weapon_icons/keenblade.png", false),
    HEALINGBLADE(MELEE, SPECIAL,"Healing Blade", "special_attack_weapon_icons/saradomin-godsword.png", false),
    ICECLEAVE(MELEE, SPECIAL,"Ice Cleave", "special_attack_weapon_icons/zamorak-godsword.png", false),
    DISRUPT(MELEE, SPECIAL,"Disrupt", "special_attack_weapon_icons/korasi-s-sword.png", false),
    WARSTRIKE(MELEE, SPECIAL,"Warstrike", "special_attack_weapon_icons/bandos-godsword.png", false),
    DRACONICBLOW(MELEE, SPECIAL,"Draconic Blow", "special_attack_weapon_icons/dragon-mace.png", false),
    FEINT(MELEE, SPECIAL,"Feint", "special_attack_weapon_icons/vesta-s-longsword.png", false),
    SARADOMINSLIGHTNING(MELEE, SPECIAL,"Saradomin's Light", "special_attack_weapon_icons/saradomin-sword.png", false),
    ARMADYLSJUDGEMENT(MELEE, SPECIAL,"Armadyl's Judgement", "special_attack_weapon_icons/armadyl-godsword.png", false),
    BLACKHOLE(MELEE, SPECIAL,"Blackhole", "special_attack_weapon_icons/zaros-godsword.png", false),
    VINECALL(MELEE, SPECIAL,"Vine Call", "special_attack_weapon_icons/abyssal-vine-whip.png", false),
    ENERGYDRAIN(MELEE, SPECIAL,"Energy Drain", "special_attack_weapon_icons/abyssal-whip.png", false),
    WEAKEN(MELEE, SPECIAL,"Weaken", "special_attack_weapon_icons/darklight.png", false),

    SOULFIRE(MAGIC, SPECIAL,"Soulfire", "special_attack_weapon_icons/roar-of-awakening.png", true),
    INSTABILITY(MAGIC, SPECIAL,"Instability", "special_attack_weapon_icons/fractured-staff-of-armadyl.png", true),
    TEMPESTOFARMADYL(MAGIC, SPECIAL,"Tempest of Armadyl", "special_attack_weapon_icons/armadyl-battlestaff.png", true),
    IBANBLAST(MAGIC, SPECIAL,"Iban Blast", "special_attack_weapon_icons/iban-s-staff.png", true),
    THELASTCOMMAND(MAGIC, SPECIAL,"The Last Command", "special_attack_weapon_icons/legatus-s-emberstaff.png", true),
    CLAWSOFGUTHIX(MAGIC, SPECIAL, "Claws of Guthix", "special_attack_weapon_icons/guthix-staff.png", true),
    SARADOMINSTRIKE(MAGIC, SPECIAL,"Saradomin Strike", "special_attack_weapon_icons/saradomin-staff.png", false),
    FLAMESOFZAMORAK(MAGIC, SPECIAL,"Flames of Zamorak", "special_attack_weapon_icons/zamorak-staff.png", false),
    MIASMICBARRAGE(MAGIC, SPECIAL,"Miasmic Barrage", "special_attack_weapon_icons/zuriel-s-staff.png", false),
    REAP(MAGIC, SPECIAL,"Reap", "special_attack_weapon_icons/penance-trident.png", false),
    FROMTHESHADOWS(MAGIC, SPECIAL,"From the Shadows", "special_attack_weapon_icons/staff-of-sliske.png", false),
    RUNEFLAME(MAGIC, SPECIAL,"Rune Flame", "special_attack_weapon_icons/mindspike.png", false),
    DEVOUR(MAGIC, SPECIAL,"Devour", "special_attack_weapon_icons/obliteration.png", false),

    SHADOWFALL(RANGED, SPECIAL,"Shadowfall", "special_attack_weapon_icons/gloomfire-bow.png", true),
    DESCENTOFDARKNESS(RANGED, SPECIAL,"Descent of Darkness", "special_attack_weapon_icons/dark-bow.png", true),
    DESTRUCTIVESHOT(RANGED, SPECIAL,"Destructive Shot", "special_attack_weapon_icons/zamorak-bow.png", true),
    CRYSTALRAIN(RANGED, SPECIAL,"Crystal Rain", "special_attack_weapon_icons/seren-godbow.png", true),
    BALANCEBYFORCE(RANGED, SPECIAL,"Balance by Force", "special_attack_weapon_icons/bow-of-the-last-guardian.png", true),
    CHAINHIT(RANGED, SPECIAL,"Chain Hit", "special_attack_weapon_icons/rune-throwing-axe.png", false),
    TWINSHOT(RANGED, SPECIAL,"Twin Shot", "special_attack_weapon_icons/quickbow.png", false),
    SOULSHOT(RANGED, SPECIAL,"Soulshot", "special_attack_weapon_icons/seercull.png", false),
    TWINFANG(RANGED, SPECIAL,"Twin Fang", "special_attack_weapon_icons/magic-shortbow.png", false),
    HAMSTRING(RANGED, SPECIAL,"Hamstring", "special_attack_weapon_icons/morrigan-s-throwing-axe.png", false),
    POWERSHOT(RANGED, SPECIAL,"Powershot", "special_attack_weapon_icons/magic-composite-bow.png", false),
    DEFIANCE(RANGED, SPECIAL,"Defiance", "special_attack_weapon_icons/zanik-s-crossbow.png", false),
    PHANTOMSTRIKE(RANGED, SPECIAL,"Phantom Strike", "special_attack_weapon_icons/morrigan-s-javelin.png", false),
    AIMEDSHOT(RANGED, SPECIAL,"Aimed Shot", "special_attack_weapon_icons/hand-cannon.png", false),
    RESTORATIVESHOT(RANGED, SPECIAL,"Restorative Shot", "special_attack_weapon_icons/saradomin-bow.png", false),
    BALANCEDSHOT(RANGED, SPECIAL,"Balanced Shot", "special_attack_weapon_icons/guthix-bow.png", false),

    DEATHGRASP(NECROMANCY, SPECIAL,"Death Grasp", "special_attack_weapon_icons/death-guard.png", true),
    SOULCRUSH(NECROMANCY, SPECIAL,"Soul Crush", "special_attack_weapon_icons/devourer-s-guard.png", true),
    DEATHESSENCE(NECROMANCY, SPECIAL,"Death Essence", "special_attack_weapon_icons/omni-guard.png", true),

    LIGHT_OF_SARADOMIN_MAGIC(MAGIC, BLESSING, "Light of Saradomin", "blessings/Striking_Light.png"),
    LIGHT_OF_SARADOMIN_MELEE(MELEE, BLESSING, "Light of Saradomin", "blessings/Striking_Light.png"),
    LIGHT_OF_SARADOMIN_RANGED(RANGED, BLESSING, "Light of Saradomin", "blessings/Striking_Light.png"),
    LIGHT_OF_SARADOMIN_NECROMANCY(NECROMANCY, BLESSING, "Light of Saradomin", "blessings/Striking_Light.png"),

    LORD_OF_LIGHT_MAGIC(MAGIC, BLESSING, "Lord of Light", "blessings/Lord_of_Light.png"),
    LORD_OF_LIGHT_MELEE(MELEE, BLESSING, "Lord of Light", "blessings/Lord_of_Light.png"),
    LORD_OF_LIGHT_RANGED(RANGED, BLESSING, "Lord of Light", "blessings/Lord_of_Light.png"),
    LORD_OF_LIGHT_NECROMANCY(NECROMANCY, BLESSING, "Lord of Light", "blessings/Lord_of_Light.png"),

    BASH_MAGIC(MAGIC, BLESSING, "Bash", "blessings/Steadfast_Will.png"),
    BASH_MELEE(MELEE, BLESSING, "Bash", "blessings/Steadfast_Will.png"),
    BASH_RANGED(RANGED, BLESSING, "Bash", "blessings/Steadfast_Will.png"),
    BASH_NECROMANCY(NECROMANCY, BLESSING, "Bash", "blessings/Steadfast_Will.png"),

    BARKSCALES_MAGIC(MAGIC, BLESSING, "Grasp of Guthix", "blessings/Barkscales.png"),
    BARKSCALES_MELEE(MELEE, BLESSING, "Grasp of Guthix", "blessings/Barkscales.png"),
    BARKSCALES_RANGED(RANGED, BLESSING, "Grasp of Guthix", "blessings/Barkscales.png"),
    BARKSCALES_NECROMANCY(NECROMANCY, BLESSING, "Grasp of Guthix", "blessings/Barkscales.png"),

    INFERNO_OF_ZAMORAK_MAGIC(MAGIC, BLESSING, "Inferno of Zamorak", "blessings/Abyssal_Cinders.png"),
    INFERNO_OF_ZAMORAK_MELEE(MELEE, BLESSING, "Inferno of Zamorak", "blessings/Abyssal_Cinders.png"),
    INFERNO_OF_ZAMORAK_RANGED(RANGED, BLESSING, "Inferno of Zamorak", "blessings/Abyssal_Cinders.png"),
    INFERNO_OF_ZAMORAK_NECROMANCY(NECROMANCY, BLESSING, "Inferno of Zamorak", "blessings/Abyssal_Cinders.png");

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
