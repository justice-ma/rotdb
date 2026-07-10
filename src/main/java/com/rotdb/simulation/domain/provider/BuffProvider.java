package com.rotdb.simulation.domain.provider;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.enums.BuffApplication;
import com.rotdb.simulation.domain.model.buff.enums.BuffLifecycle;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
import com.rotdb.simulation.domain.model.buff.factory.*;
import com.rotdb.simulation.domain.model.context.SimulationState;

public final class BuffProvider {
    public BuffProvider() {
    }

    public static BuffDefinition get(BuffId id, BuffSource source, SimulationState state) {
        return switch (source) {
            case INITIAL -> getInitialDefinition(id, state);
            case USER_PLACED -> getUserPlacedDefinition(id, state);
            case ABILITY_GENERATED -> getAbilityGeneratedDefinition(id, state);
            case PROC -> getProcDefinition(id, state);
            case SYSTEM -> getSystemDefinition(id, state);
            case STACK -> getStackDefinition(id, state);
        };
    }

    private static BuffDefinition getInitialDefinition(BuffId id, SimulationState state) {
        return switch (id) {
            case ENCHANTMENTOFAGONY -> InitialBuffDefinitionFactory.enchantmentOfAgony();
            case ENCHANTMENTOFSAVAGERY -> InitialBuffDefinitionFactory.enchantmentOfSavagery();
            case ENCHANTMENTOFHEROISM -> InitialBuffDefinitionFactory.enchantmentOfHeroism();
            case ENCHANTMENTOFDISPELLING -> InitialBuffDefinitionFactory.enchantmentOfDispelling();
            case ENCHANTMENTOFDREAD -> InitialBuffDefinitionFactory.enchantmentOfDread();
            case ENCHANTMENTOFSHADOWS -> InitialBuffDefinitionFactory.enchantmentOfShadows();
            case ENCHANTMENTOFAFFLICTION -> InitialBuffDefinitionFactory.enchantmentOfAffliction();
            case ENCHANTMENTOFFLAMES -> InitialBuffDefinitionFactory.enchantmentOfFlames();
            case ENCHANTMENTOFMETAPHYSICS -> InitialBuffDefinitionFactory.enchantmentOfMetaphysics();
            case SHARDOFGENESIS -> InitialBuffDefinitionFactory.shardOfGenesis();
            case REAPERSCREW -> InitialBuffDefinitionFactory.reapersCrew();
            case KALG -> InitialBuffDefinitionFactory.kalg();
            case ECLIPSEDSOUL -> InitialBuffDefinitionFactory.eclipsedSoul();
            case STONEOFJAS -> InitialBuffDefinitionFactory.stoneOfJas();
            case GUARDHOUSE -> InitialBuffDefinitionFactory.guardhouse();
            case PUZZLEBOX -> InitialBuffDefinitionFactory.puzzlebox();
            case SLAYERLODGE -> InitialBuffDefinitionFactory.slayerLodge();
            case NOFEAR -> InitialBuffDefinitionFactory.noFear();
            case BERSERKERSFURY -> InitialBuffDefinitionFactory.berserkersFury();
            case SLAYERHELM -> InitialBuffDefinitionFactory.slayerHelm();
            case STRENGTHCAPE -> InitialBuffDefinitionFactory.strengthCape();
            case HEIGHTENEDSENSES -> InitialBuffDefinitionFactory.heightenedSenses();
            case FURYOFTHESMALL -> InitialBuffDefinitionFactory.furyOfTheSmall();
            case CONSERVATIONOFENERGY -> InitialBuffDefinitionFactory.conservationOfEnergy();
            case RINGOFVIGOUR -> InitialBuffDefinitionFactory.ringOfVigour();
            case NOPENOPENOPE -> InitialBuffDefinitionFactory.nopeNopeNope();
            case HAUNTED -> InitialBuffDefinitionFactory.haunted();
            case VULNED -> InitialBuffDefinitionFactory.vulned();
            case CURSED -> InitialBuffDefinitionFactory.cursed();
            case SMOKECLOUDED -> InitialBuffDefinitionFactory.smokeClouded();

            default -> throw new IllegalArgumentException("Unknown " + BuffSource.INITIAL + " buff type: " + id);
        };
    }

    private static BuffDefinition getUserPlacedDefinition(BuffId id, SimulationState state) {
        return switch (id) {
            case SUNSHINE -> UserPlacedBuffDefinitionFactory.sunshine();
            case DEATHSWIFTNESS -> UserPlacedBuffDefinitionFactory.deathSwiftness();
            case BERSERK -> UserPlacedBuffDefinitionFactory.berserk();
            case UNDEADSLAYERSIGIL -> UserPlacedBuffDefinitionFactory.undeadSlayerSigil();
            case DRAGONSLAYERSIGIL -> UserPlacedBuffDefinitionFactory.dragonSlayerSigil();
            case DEMONSLAYERSIGIL -> UserPlacedBuffDefinitionFactory.demonSlayerSigil();
            case RUNICCHARGE -> UserPlacedBuffDefinitionFactory.runicCharge();
            case DBA -> UserPlacedBuffDefinitionFactory.dba();
            case GRAVITATEBUFF -> UserPlacedBuffDefinitionFactory.gravitateBuff();
            case HAUNTED -> UserPlacedBuffDefinitionFactory.haunted();
            case VULNED -> UserPlacedBuffDefinitionFactory.vulned();
            case CURSED -> UserPlacedBuffDefinitionFactory.cursed();
            case SMOKECLOUDED -> UserPlacedBuffDefinitionFactory.smokeClouded();
            case LIVINGDEATH -> UserPlacedBuffDefinitionFactory.livingDeath();
            case ADRENALINEPOTION -> UserPlacedBuffDefinitionFactory.adrenalinePotion();
            case SUPERADRENALINEPOTION -> UserPlacedBuffDefinitionFactory.superAdrenalinePotion();
            case ADRENALINERENEWAL -> UserPlacedBuffDefinitionFactory.adrenalineRenewal();
            case IMBUESHADOWS -> UserPlacedBuffDefinitionFactory.imbueShadows();
            case NATURALINSTINCT -> UserPlacedBuffDefinitionFactory.naturalInstinct();
            case SPLITSOUL -> UserPlacedBuffDefinitionFactory.splitSoul();
            case COMMANDSKELETONWARRIOR -> UserPlacedBuffDefinitionFactory.commandSkeletonWarrior();

            default -> throw new IllegalArgumentException("Unknown " + BuffSource.USER_PLACED + " buff type: " + id);
        };
    }

    private static BuffDefinition getAbilityGeneratedDefinition(BuffId id, SimulationState state) {
        return switch (id) {
            case REND -> AbilityGeneratedBuffDefinitionFactory.rend();
            case RENDBLEED -> AbilityGeneratedBuffDefinitionFactory.rendBleed();
            case CHAOSROAR -> AbilityGeneratedBuffDefinitionFactory.chaosRoar();
            case BALANCEBYFORCE -> AbilityGeneratedBuffDefinitionFactory.balanceByForce();
            case DRAGONSCIMITAR -> AbilityGeneratedBuffDefinitionFactory.dragonScimitar();
            case FURYBUFF -> AbilityGeneratedBuffDefinitionFactory.furyBuff();
            case GREATERFURYBUFF -> AbilityGeneratedBuffDefinitionFactory.greaterFuryBuff();
            case CONCENTRATEDBLASTBUFF -> AbilityGeneratedBuffDefinitionFactory.concentratedBlastBuff();
            case GREATERCONCENTRATEDBLASTBUFF -> AbilityGeneratedBuffDefinitionFactory.greaterConcentratedBlastBuff();
            case RAPIDFIREBUFF -> AbilityGeneratedBuffDefinitionFactory.rapidFireBuff();
            case ASPHYXIATEBUFF -> AbilityGeneratedBuffDefinitionFactory.asphyxiateBuff();
            case CONFLAGRATE -> AbilityGeneratedBuffDefinitionFactory.conflagrate();
            case OBLITERATED -> AbilityGeneratedBuffDefinitionFactory.obliterated();
            case CLAWSOFGUTHIX -> AbilityGeneratedBuffDefinitionFactory.clawsOfGuthix();
            case CLOBBER -> AbilityGeneratedBuffDefinitionFactory.clobber();
            case SUNDER -> AbilityGeneratedBuffDefinitionFactory.sunder();
            case BACKSTAB -> AbilityGeneratedBuffDefinitionFactory.backstab();
            case METEORSTRIKE -> AbilityGeneratedBuffDefinitionFactory.meteorStrike();
            case VESTMENTSBLEED -> AbilityGeneratedBuffDefinitionFactory.vestmentsBleed();
            case TSUNAMI -> AbilityGeneratedBuffDefinitionFactory.tsunami();
            case INSTABILITY -> AbilityGeneratedBuffDefinitionFactory.instability();
            case COMBUSTED -> AbilityGeneratedBuffDefinitionFactory.combusted();
            case FLAMEBOUNDRIVAL -> AbilityGeneratedBuffDefinitionFactory.flameboundRival();
            case ZGS -> AbilityGeneratedBuffDefinitionFactory.zgs();
            case GALES -> AbilityGeneratedBuffDefinitionFactory.gales();
            case SONICWAVE -> AbilityGeneratedBuffDefinitionFactory.sonicWave();
            case GREATERSONICWAVE -> AbilityGeneratedBuffDefinitionFactory.greaterSonicWave();
            case GREATERBARGE -> AbilityGeneratedBuffDefinitionFactory.greaterBarge();
            case SUNFALLSLAM -> AbilityGeneratedBuffDefinitionFactory.sunfallSlam();
            case SOULCRUSH -> AbilityGeneratedBuffDefinitionFactory.soulCrush();
            case DEATHESSENCE -> AbilityGeneratedBuffDefinitionFactory.deathEssence();

            default -> throw new IllegalArgumentException("Unknown " + BuffSource.ABILITY_GENERATED + " buff type: " + id);
        };
    }

    private static BuffDefinition getProcDefinition(BuffId id, SimulationState state) {
        return switch (id) {
            case FROSTBLADES -> ProcBuffDefinitionFactory.frostblades();
            case RUBYAURORA -> ProcBuffDefinitionFactory.rubyAurora();
            case WENARROWPROC -> ProcBuffDefinitionFactory.wenArrowProc();
            case IMPATIENTPROC -> ProcBuffDefinitionFactory.impatientProc();
            case RELENTLESSPROC -> ProcBuffDefinitionFactory.relentlessProc();
            case ASYLUMSURGEONSRINGPROC -> ProcBuffDefinitionFactory.asylumSurgeonsRingProc();
            case RINGOFDEATHPROC -> ProcBuffDefinitionFactory.ringOfDeathProc();
            case WARPRIESTOFARMADYLPROC -> ProcBuffDefinitionFactory.warpriestOfArmadylProc();
            case FEASTINGSPORES -> ProcBuffDefinitionFactory.feastingSpores();

            default -> throw new IllegalArgumentException("Unknown " + BuffSource.PROC + " buff type: " + id);
        };
    }

    private static BuffDefinition getStackDefinition(BuffId id, SimulationState state) {
        return switch (id) {
            case WENARROWSTACKS -> StackBuffDefinitionFactory.wenArrowStacks();
            case GRAVITATESTACKS -> StackBuffDefinitionFactory.gravitateStacks();
            case PRIMORDIALICESTACKS -> StackBuffDefinitionFactory.primordialIceStacks();
            case PERFECTEQUILIBRIUMSTACKS -> StackBuffDefinitionFactory.perfectEquilibriumStacks();
            case DEATHSPORESTACKS -> StackBuffDefinitionFactory.deathsporeStacks();
            case TITHESTACKS -> StackBuffDefinitionFactory.titheStacks();
            case INCITEFEARSTACKS -> StackBuffDefinitionFactory.inciteFearStacks();
            case ESSENCECORRUPTIONSTACKS -> StackBuffDefinitionFactory.essenceCorruptionStacks();
            case SOULSTACKS -> StackBuffDefinitionFactory.soulStacks();
            case SOULREAVE -> StackBuffDefinitionFactory.soulReave();
            case DEATHSPARK -> StackBuffDefinitionFactory.deathSpark();
            case NECROSIS -> StackBuffDefinitionFactory.necrosis();
            case RAGE -> StackBuffDefinitionFactory.rage();

            default -> throw new IllegalArgumentException("Unknown " + BuffSource.STACK + " buff type: " + id);
        };
    }

    private static BuffDefinition getSystemDefinition(BuffId id, SimulationState state) {
        return switch (id) {
            case TIMESINCELASTATTACK -> new BuffDefinition(
                    BuffId.TIMESINCELASTATTACK,
                    BuffSource.SYSTEM,
                    BuffLifecycle.STACK,
                    BuffApplication.NONE,
                    null,
                    null,
                    false,
                    true,
                    null,
                    null
            );
            default -> throw new IllegalArgumentException("Unknown " + BuffSource.SYSTEM + " buff type: " + id);
        };
    }
}
