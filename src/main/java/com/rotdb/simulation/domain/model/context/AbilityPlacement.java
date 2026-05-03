package com.rotdb.simulation.domain.model.context;

import com.rotdb.shared.ability.AbilityId;

public class AbilityPlacement {
    private AbilityId placedAbility;
    private int placementTick;

    public AbilityId getPlacedAbility() {
        return placedAbility;
    }

    public void setPlacedAbility(AbilityId placedAbility) {
        this.placedAbility = placedAbility;
    }

    public int getPlacementTick() {
        return placementTick;
    }

    public void setPlacementTick(int placementTick) {
        this.placementTick = placementTick;
    }
}