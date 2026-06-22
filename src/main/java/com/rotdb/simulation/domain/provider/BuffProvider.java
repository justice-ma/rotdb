package com.rotdb.simulation.domain.provider;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.context.SimulationState;

public final class BuffProvider {
    public BuffProvider() {
    }

    public static BuffDefinition get(BuffId id, SimulationState state) {
        return switch (id) {
            // Passives
            case ENCHANTMENTOFSAVAGERY, ENCHANTMENTOFAGONY, ENCHANTMENTOFHEROISM, ENCHANTMENTOFDISPELLING,
                 ENCHANTMENTOFDREAD, ENCHANTMENTOFSHADOWS, ENCHANTMENTOFAFFLICTION, ENCHANTMENTOFFLAMES,
                 ENCHANTMENTOFMETAPHYSICS, SHARDOFGENESIS, REAPERSCREW, KALG, ECLIPSEDSOUL,
                 CONCENTRATEDBLASTBUFF, GREATERCONCENTRATEDBLASTBUFF, BLOODLUST, STONEOFJAS, GUARDHOUSE,
                 PUZZLEBOX, NOPENOPENOPE, BALANCEOFPOWER, GUARDIANSTRIUMPH, SLAYERLODGE, NOFEAR,
                 PERFECTEQUILIBRIUMSTACKS, PRIMORDIALICESTACKS, TIMESINCELASTATTACK, REAPERSTACKS, BLEEDS,
                 BLACKSTONEARROWSTACKS, LORDOFBONESSTACKS, COMBUSTED, FLAMEBOUNDRIVAL, HAUNTED, VULNED, CURSED,
                 SMOKECLOUDED, OBLITERATED, BANDOSBOOK, CLOBBER, SUNDER, BACKSTAB, CROESUSSPORED, BERSERKERSFURY,
                 BOOKUPTIME, SLAYERHELM, DEATHSPARK, SOULSTACKS, NECROSIS, RAGE, VALOUR, STRENGTHCAPE,
                 HEIGHTENEDSENSES, RINGOFDEATHPROC, CONSERVATIONOFENERGY, RINGOFVIGOUR, WARPRIESTOFARMADYLPROC,
                 CLAWSOFGUTHIX, IMPATIENTPROC, FURYOFTHESMALL, RELENTLESSPROC -> passive(id);

            // Timed
            case DRACONICFRUIT -> timed(id, 100, 0, false);
            case SMASH, CHAOSROAR, GALES -> timed(id, 10, 0, false);
            case SUNSHINE, DEATHSWIFTNESS -> timed(id, 51, 0, false);
            case BERSERK -> timed(id, 33, 0, true);
            case LIVINGDEATH -> timed(id, 50, 150, true);
            case ZGS -> timed(id, 35, 0, false);
            case UNDEADSLAYERSIGIL, DRAGONSLAYERSIGIL, DEMONSLAYERSIGIL -> timed(id, 17, 100, false);
            case RUNICCHARGE -> timed(id, 25, 50, false);
            case SPLITSOUL -> state.getState().getEquipment().getCombatStyle() == CombatStyles.NECROMANCY
                    ? timed(id, 34, 100, true)
                    : timed(id, 25, 0, true);
            case INSTABILITY, BALANCEBYFORCE, ESSENCECORRUPTIONSTACKS -> timed(id, 50, 0, false);
            case DBA, DRAGONSCIMITAR -> timed(id, 100, 0, true);
            case FURYBUFF, GREATERFURYBUFF, CONFLAGRATE -> timed(id, 25, 0, false);
            case RAPIDFIREBUFF -> timedDracolich(state);
            case ASPHYXIATEBUFF -> state.getState().getEquipment().getTumekensPieces() >= 5
                    ? timed(id, 15, 0, false)
                    : timed(id, 6, 0, false);
            case FROSTBLADES -> state.getState().getEquipment().getOffhand().getEffect().contains(Effect.SHARDABLE)
                    ? timed(id, 15, 0, false)
                    : timed(id, 10, 0, false);
            case RUBYAURORA -> timed(id, 40, 0, false);
            case GRAVITATE -> timed(id, 50, 0, true);
            case WENSTACKS -> timed(id, 15, 0, false);
            case REVENGESTACKS -> timed(id, 32, 0, false);
            case RUTHELESSSTACKS, TITHESTACKS -> timed(id, 34, 0, false);
            case METEORSTRIKE, TSUNAMI, IMBUESHADOWS -> timed(id, 50, 100, false);
            case ADRENALINEPOTION, SUPERADRENALINEPOTION -> timed(id, 0, 200, false);
            case ADRENALINERENEWAL -> timed(id, 10, 200, false);
            case VESTMENTSBLEED -> timed(id, 30, 0, false);
            case ASYLUMSURGEONSRINGPROC -> timed(id, 0, 50, false);
            case NATURALINSTINCT -> timed(id, 34, 200, true);
        };
    }

    private static BuffDefinition passive(BuffId id) {
        return new BuffDefinition(id, 0, 0, false, true);
    }

    private static BuffDefinition timed(BuffId id, int duration, int cooldown, boolean gcdConsuming) {
        return new BuffDefinition(id, duration, cooldown, gcdConsuming, false);
    }

    private static BuffDefinition timedDracolich(SimulationState state) {
        int pieces = Math.max(state.getState().getEquipment().getDracolichPieces(), state.getState().getEquipment().getEliteDracolichPieces());
        int duration = (pieces * 3) + 5;
        return new BuffDefinition(BuffId.RAPIDFIREBUFF, duration, 0, false, false);
    }
}
