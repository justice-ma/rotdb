package com.rotdb.ability;

import com.rotdb.ability.factory.*;
import com.rotdb.domain.model.context.AbilityContext;
import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.Effect;
import com.rotdb.domain.model.enums.Slots;
import com.rotdb.domain.model.equipment.EquipmentModel;
import com.rotdb.domain.model.equipment.EquipmentSlot;

public final class AbilityProvider {

    private AbilityProvider() {}

    public static AbilityContext get(AbilityId id, CalculationContext context) {
        return switch (id) {
            // Melee Abilities
            case MELEEAUTO -> MeleeAbilityFactory.attack();
            case ASSAULT -> MeleeAbilityFactory.assault();
            case BLOODLUSTASSAULT -> MeleeAbilityFactory.bloodlustAssault();
            case ADAPTIVESTRIKE ->
                    context.getEquipment().getMainhand().getSlot() == Slots.TWOHANDED
                            ? MeleeAbilityFactory.adaptiveStrike2h()
                            : MeleeAbilityFactory.adaptiveStrikeDw();
            case OVERPOWER -> MeleeAbilityFactory.overpower();
            case OVERPOWERIGNEOUS -> MeleeAbilityFactory.overpowerIgneous();
            case REND -> MeleeAbilityFactory.rend();
            case FURY -> MeleeAbilityFactory.fury();
            case GREATERFURY -> MeleeAbilityFactory.greaterFury();
            case BACKHAND -> MeleeAbilityFactory.backhand();
            case HURRICANE -> MeleeAbilityFactory.hurricane();
            case BLOODLUSTHURRICANE -> MeleeAbilityFactory.bloodlustHurricane();
            case FLURRY -> MeleeAbilityFactory.flurry();
            case GREATERFLURRY -> MeleeAbilityFactory.greaterFlurry();
            case DISMEMBER -> MeleeAbilityFactory.dismember();
            case SLAUGHTER -> MeleeAbilityFactory.slaughter();
            case MASSACRE -> MeleeAbilityFactory.massacre();
            case PUNISH -> MeleeAbilityFactory.punish();
            case BARGE -> MeleeAbilityFactory.barge();
            case GREATERBARGE -> MeleeAbilityFactory.greaterBarge();
            case PULVERISE -> MeleeAbilityFactory.pulverise();
            case METEORSTRIKE -> MeleeAbilityFactory.meteorStrike();
            case CHAOSROAR -> MeleeAbilityFactory.chaosRoar();

            // Magic Abilities
            case MAGICAUTO -> MagicAbilityFactory.magic();
            case WILDMAGIC -> MagicAbilityFactory.wildMagic();
            case SONICWAVE -> MagicAbilityFactory.soniceWave();
            case GREATERSONICWAVE -> MagicAbilityFactory.greaterSonicWave();
            case OMNIPOWER -> MagicAbilityFactory.omnipower();
            case OMNIPOWERIGNEOUS -> MagicAbilityFactory.omnipowerIgneous();
            case DRAGONBREATH -> MagicAbilityFactory.dragonBreath();
            case IMPACT -> MagicAbilityFactory.impact();
            case COMBUST -> MagicAbilityFactory.combust();
            case CHAIN -> MagicAbilityFactory.chain();
            case GREATERCHAIN -> MagicAbilityFactory.greaterChain();
            case ASPHYXIATE -> hasAtLeastTumekensPieces(context.getEquipment(), 4)
                    ? MagicAbilityFactory.asphyxiateTumekens()
                    : MagicAbilityFactory.asphyxiate();
            case CONCENTRATEDBLAST -> MagicAbilityFactory.concentratedBlast();
            case GREATERCONCENTRATEDBLAST -> MagicAbilityFactory.greaterConcentratedBlast();
            case MAGMATEMPEST -> MagicAbilityFactory.magmaTempest();
            case CORRUPTIONBLAST -> MagicAbilityFactory.corruptionBlast();
            case SMOKETENDRILS -> MagicAbilityFactory.smokeTendrils();
            case TSUNAMI -> MagicAbilityFactory.tsunami();

            // Ranged Abilities
            case RANGEDAUTO -> RangedAbilityFactory.ranged();
            case SNAPSHOT -> RangedAbilityFactory.snapShot();
            case SNIPE -> RangedAbilityFactory.snipe();
            case PIERCINGSHOT -> RangedAbilityFactory.piercingShot();
            case DEADSHOT -> RangedAbilityFactory.deadshot();
            case DEADSHOTIGNEOUS -> RangedAbilityFactory.deadshotIgneous();
            case BINDINGSHOT -> RangedAbilityFactory.bindingShot();
            case BOMBARDMENT -> RangedAbilityFactory.bombardment();
            case GALESHOT -> RangedAbilityFactory.galeshot();
            case RAPIDFIRE -> RangedAbilityFactory.rapidFire();
            case RICOCHET -> RangedAbilityFactory.ricochet();
            case GREATERRICOCHET -> RangedAbilityFactory.greaterRicochet();
            case CORRUPTIONSHOT -> RangedAbilityFactory.corruptionShot();
            case SHADOWTENDRILS -> RangedAbilityFactory.shadowTendrils();

            // Necromancy Abilities
            case NECROMANCYAUTO -> NecromancyAbilityFactory.necromancy();
            case CONJURESKELETONWARRIOR -> NecromancyAbilityFactory.conjureSkeletonWarrior();
            case COMMANDSKELETONWARRIOR -> NecromancyAbilityFactory.commandSkeletonWarrior();
            case FINGEROFDEATH -> NecromancyAbilityFactory.fingerOfDeath();
            case TOUCHOFDEATH -> NecromancyAbilityFactory.touchOfDeath();
            case DEATHSKULLS -> NecromancyAbilityFactory.deathSkulls();
            case DEATHSKULLSIGNEOUS -> NecromancyAbilityFactory.deathSkullsIgneous();
            case BLOODSIPHON -> NecromancyAbilityFactory.bLoodSiphon();
            case BLOODSIPHONHEAL -> NecromancyAbilityFactory.bloodSiphonHeal();
            case CONJUREPUTRIDZOMBIE -> NecromancyAbilityFactory.conjurePutridZombie();
            case COMMANDPUTRIDZOMBIE -> NecromancyAbilityFactory.commandPutridZombie();
            case CONJUREVENGEFULGHOST -> NecromancyAbilityFactory.conjureVengefulGhost();
            case BLOAT -> NecromancyAbilityFactory.bloat();
            case SOULSAP -> NecromancyAbilityFactory.soulSap();
            case SOULSTRIKE -> NecromancyAbilityFactory.soulStrike();
            case SPECTRALSCYTHE -> NecromancyAbilityFactory.spectralScythe();
            case SPECTRALHURRICANE -> NecromancyAbilityFactory.spectralHurricane();
            case SPECTRALMETEORSTRIKE -> NecromancyAbilityFactory.spectralMeteorStrike();
            case VOLLEYOFSOULS -> NecromancyAbilityFactory.volleyOfSouls();
            case COMMANDPHANTOMGUARDIAN -> NecromancyAbilityFactory.commandPhantomGuardian();

            // Melee Specs
            case ENERGYDRAIN -> MeleeSpecialAttackFactory.energyDrain();
            case WEAKEN -> MeleeSpecialAttackFactory.weaken();
            case THEFINALFLURRY -> MeleeSpecialAttackFactory.theFinalFlurry();
            case SPEARWALL -> MeleeSpecialAttackFactory.spearWall();
            case CLOBBER -> MeleeSpecialAttackFactory.clobber();
            case QUICKSMASH -> MeleeSpecialAttackFactory.quickSmash();
            case SWEEP -> MeleeSpecialAttackFactory.sweep();
            case IMPALE -> MeleeSpecialAttackFactory.impale();
            case LIQUEFY -> MeleeSpecialAttackFactory.liquefy();
            case FAVOUROFTHEWARGOD -> MeleeSpecialAttackFactory.favourOfTheWarGod();
            case SUNDER -> MeleeSpecialAttackFactory.sunder();
            case DRACONICPUNCTURE -> MeleeSpecialAttackFactory.draconicPuncture();
            case BACKSTAB -> MeleeSpecialAttackFactory.backstab();
            case AIMEDSTRIKE -> MeleeSpecialAttackFactory.aimedStrike();
            case OBLITERATE -> MeleeSpecialAttackFactory.obliterate();
            case HEALINGBLADE -> MeleeSpecialAttackFactory.healingBlade();
            case ICECLEAVE -> MeleeSpecialAttackFactory.iceCleave();
            case DISRUPT -> MeleeSpecialAttackFactory.disrupt();
            case WARSTRIKE -> MeleeSpecialAttackFactory.warstrike();
            case DRACONICBLOW -> MeleeSpecialAttackFactory.draconicBlow();
            case DRACONICSLASH -> MeleeSpecialAttackFactory.draconicSlash();
            case FEINT -> MeleeSpecialAttackFactory.feint();
            case IGNEOUSSHOWDOWN -> MeleeSpecialAttackFactory.igneousShowdown();
            case IGNEOUSSHOWDOWNRECAST -> MeleeSpecialAttackFactory.igneousShowdownRecast();
            case DRACONICCLEAVE -> MeleeSpecialAttackFactory.draconicCleave();
            case SARADOMINSLIGHTNING -> MeleeSpecialAttackFactory.saradominsLightning();
            case SUNFALLSLAM -> MeleeSpecialAttackFactory.sunfallSlam();
            case POWERSTAB -> MeleeSpecialAttackFactory.powerstab();
            case ARMADYLSJUDGEMENT -> MeleeSpecialAttackFactory.armadylsJudgement();
            case BLACKHOLE -> MeleeSpecialAttackFactory.blackhole();
            case VINECALL -> MeleeSpecialAttackFactory.vineCall();
            case ICYTEMPEST -> MeleeSpecialAttackFactory.icyTempest();
            case SLICEANDDICE -> MeleeSpecialAttackFactory.sliceAndDice();

            // Magic Specs
            case FROMTHESHADOWS -> MagicSpecialAttackFactory.fromTheShadows();
            case INSTABILITY -> MagicSpecialAttackFactory.instability();
            case RUNEFLAME -> MagicSpecialAttackFactory.runeFlame();
            case CLAWSOFGUTHIX -> MagicSpecialAttackFactory.clawsOfGuthix();
            case DEVOUR -> MagicSpecialAttackFactory.devour();
            case SARADOMINSTRIKE -> MagicSpecialAttackFactory.saradominStrike();
            case FLAMESOFZAMORAK -> MagicSpecialAttackFactory.flamesOfZamorak();
            case MIASMICBARRAGE -> MagicSpecialAttackFactory.miasmicBarrage();
            case THELASTCOMMAND -> MagicSpecialAttackFactory.theLastCommand();
            case REAP -> MagicSpecialAttackFactory.reap();
            case TEMPESTOFARMADYL -> MagicSpecialAttackFactory.tempestOfArmadyl();
            case IBANBLAST -> MagicSpecialAttackFactory.ibanBlast();
            case SOULFIRE -> MagicSpecialAttackFactory.soulfire();

            // Ranged Specs
            case CHAINHIT -> RangedSpecialAttackFactory.chainHit();
            case TWINSHOT -> RangedSpecialAttackFactory.twinShot();
            case SHADOWFALL -> RangedSpecialAttackFactory.shadowfall();
            case SOULSHOT -> RangedSpecialAttackFactory.soulshot();
            case TWINFANG -> RangedSpecialAttackFactory.twinFang();
            case CRYSTALRAIN -> RangedSpecialAttackFactory.crystalRain();
            case HAMSTRING -> RangedSpecialAttackFactory.hamstring();
            case DESCENTOFDARKNESS -> RangedSpecialAttackFactory.descentOfDarkness();
            case POWERSHOT -> RangedSpecialAttackFactory.powershot();
            case DEFIANCE -> RangedSpecialAttackFactory.defiance();
            case BALANCEBYFORCE -> RangedSpecialAttackFactory.balanceByForce();
            case PHANTOMSTRIKE -> RangedSpecialAttackFactory.phantomStrike();
            case AIMEDSHOT -> RangedSpecialAttackFactory.aimedShot();
            case DESTRUCTIVESHOT -> RangedSpecialAttackFactory.destructiveShot();
            case RESTORATIVESHOT -> RangedSpecialAttackFactory.restorativeShot();
            case BALANCEDSHOT -> RangedSpecialAttackFactory.balancedShot();

            // Necromancy Specs
            case DEATHGRASP -> NecromancySpecialAttackFactory.deathGrasp();
            case SOULCRUSH -> NecromancySpecialAttackFactory.soulCrush();
            case DEATHESSENCE -> NecromancySpecialAttackFactory.deathEssence();
        };
    }
    private static boolean hasAtLeastTumekensPieces(EquipmentModel equipment, int required) {
        int count = 0;

        if (isTumekens(equipment.getHead())) count++;
        if (isTumekens(equipment.getBody())) count++;
        if (isTumekens(equipment.getLegs())) count++;
        if (isTumekens(equipment.getGloves())) count++;
        if (isTumekens(equipment.getBoots())) count++;

        return count >= required;
    }

    private static boolean isTumekens(EquipmentSlot item) {
        return item.getEffect().contains(Effect.TUMEKENS);
    }
}
