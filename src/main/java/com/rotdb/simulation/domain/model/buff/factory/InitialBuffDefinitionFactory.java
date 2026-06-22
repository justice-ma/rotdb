package com.rotdb.simulation.domain.model.buff.factory;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.BuffDefinition;
import com.rotdb.simulation.domain.model.buff.enums.BuffApplication;
import com.rotdb.simulation.domain.model.buff.enums.BuffLifecycle;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;

public class InitialBuffDefinitionFactory {
    public static BuffDefinition enchantmentOfSavagery() {
        return create(BuffId.ENCHANTMENTOFSAVAGERY, BuffSource.INITIAL, BuffLifecycle.PASSIVE);
    }

    private static BuffDefinition create(BuffId buffId, BuffSource source, BuffLifecycle lifecycle) {
        return new BuffDefinition(
                buffId,
                source,
                lifecycle,
                BuffApplication.PLAYER_BUFF_SET,
                null,
                null,
                false,
                true
        );
    }
}
