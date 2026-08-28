package com.rotdb.simulation.domain.model.buff.factory;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.enums.BuffApplication;
import com.rotdb.simulation.domain.model.buff.enums.BuffLifecycle;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;

public class InitialBuffDefinitionFactory {
    public static BuffDefinition enchantmentOfSavagery() {
        return create(BuffId.ENCHANTMENTOFSAVAGERY, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition enchantmentOfAgony() {
        return create(BuffId.ENCHANTMENTOFAGONY, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition enchantmentOfHeroism() {
        return create(BuffId.ENCHANTMENTOFHEROISM, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition enchantmentOfDispelling() {
        return create(BuffId.ENCHANTMENTOFDISPELLING, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition enchantmentOfDread() {
        return create(BuffId.ENCHANTMENTOFDREAD, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition enchantmentOfShadows() {
        return create(BuffId.ENCHANTMENTOFSHADOWS, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition enchantmentOfAffliction() {
        return create(BuffId.ENCHANTMENTOFAFFLICTION, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition enchantmentOfFlames() {
        return create(BuffId.ENCHANTMENTOFFLAMES, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition enchantmentOfMetaphysics() {
        return create(BuffId.ENCHANTMENTOFMETAPHYSICS, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition shardOfGenesis() {
        return create(BuffId.SHARDOFGENESIS, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition reapersCrew() {
        return create(BuffId.REAPERSCREW, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition kalg() {
        return create(BuffId.KALG, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition eclipsedSoul() {
        return create(BuffId.ECLIPSEDSOUL, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition stoneOfJas() {
        return create(BuffId.STONEOFJAS, BuffLifecycle.PASSIVE, BuffApplication.PLAYER_STACKS);
    }

    public static BuffDefinition guardhouse() {
        return create(BuffId.GUARDHOUSE, BuffLifecycle.PASSIVE, BuffApplication.PLAYER_STACKS);
    }

    public static BuffDefinition puzzlebox() {
        return create(BuffId.PUZZLEBOX, BuffLifecycle.PASSIVE, BuffApplication.PLAYER_STACKS);
    }

    public static BuffDefinition slayerLodge() {
        return create(BuffId.SLAYERLODGE, BuffLifecycle.PASSIVE, BuffApplication.PLAYER_STACKS);
    }

    public static BuffDefinition noFear() {
        return create(BuffId.NOFEAR, BuffLifecycle.PASSIVE, BuffApplication.PLAYER_STACKS);
    }

    public static BuffDefinition berserkersFury() {
        return create(BuffId.BERSERKERSFURY, BuffLifecycle.PASSIVE, BuffApplication.PLAYER_STACKS);
    }

    public static BuffDefinition slayerHelm() {
        return create(BuffId.SLAYERHELM, BuffLifecycle.PASSIVE, BuffApplication.PLAYER_STACKS);
    }

    public static BuffDefinition strengthCape() {
        return create(BuffId.STRENGTHCAPE, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition heightenedSenses() {
        return create(BuffId.HEIGHTENEDSENSES, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition furyOfTheSmall() {
        return create(BuffId.FURYOFTHESMALL, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition conservationOfEnergy() {
        return create(BuffId.CONSERVATIONOFENERGY, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition ringOfVigour() {
        return create(BuffId.RINGOFVIGOUR, BuffLifecycle.PASSIVE);
    }

    public static BuffDefinition nopeNopeNope() {
        return create(BuffId.NOPENOPENOPE, BuffLifecycle.PASSIVE, BuffApplication.PLAYER_STACKS);
    }

    public static BuffDefinition haunted() {
        return create(BuffId.HAUNTED, BuffLifecycle.PASSIVE, BuffApplication.TARGET_BUFF_SET);
    }

    public static BuffDefinition vulned() {
        return create(BuffId.VULNED, BuffLifecycle.PASSIVE, BuffApplication.TARGET_BUFF_SET);
    }

    public static BuffDefinition cursed() {
        return create(BuffId.CURSED, BuffLifecycle.PASSIVE, BuffApplication.TARGET_BUFF_SET);
    }

    public static BuffDefinition smokeClouded() {
        return create(BuffId.SMOKECLOUDED, BuffLifecycle.PASSIVE, BuffApplication.TARGET_BUFF_SET);
    }

    private static BuffDefinition create(BuffId buffId, BuffLifecycle lifecycle) {
        return new BuffDefinition(
                buffId,
                BuffSource.INITIAL,
                lifecycle,
                BuffApplication.PLAYER_BUFF_SET,
                null,
                null,
                false,
                true,
                null,
                null
        );
    }

    private static BuffDefinition create(BuffId buffId, BuffLifecycle lifecycle, BuffApplication application) {
        return new BuffDefinition(
                buffId,
                BuffSource.INITIAL,
                lifecycle,
                application,
                null,
                null,
                false,
                true,
                null,
                null
        );
    }
}
