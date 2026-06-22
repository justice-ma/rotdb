package com.rotdb.simulation.domain.model.context;

import com.rotdb.shared.combat.domain.model.enums.BuffId;

public class BuffPlacement {
    private BuffId buffId;
    private int placementTick;

    public BuffId getBuffId() {
        return buffId;
    }

    public void setBuffId(BuffId buffId) {
        this.buffId = buffId;
    }

    public int getPlacementTick() {
        return placementTick;
    }

    public void setPlacementTick(int placementTick) {
        this.placementTick = placementTick;
    }
}
