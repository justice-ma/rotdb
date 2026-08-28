package com.rotdb.simulation.domain.model.context;

import com.rotdb.shared.ability.AbilityId;

public class AbilityPlacement {
    private AbilityId placedAbility;
    private int castTick;
    private int releaseTick = castTick;
    private Integer placementId;
    private int completionTick;

    public AbilityId getPlacedAbility() {
        return placedAbility;
    }

    public void setPlacedAbility(AbilityId placedAbility) {
        this.placedAbility = placedAbility;
    }

    public int getCastTick() {
        return castTick;
    }

    public void setCastTick(int castTick) {
        this.castTick = castTick;
        if (this.releaseTick < castTick) {
            this.releaseTick = castTick;
        }
    }

    public int getReleaseTick() {
        return releaseTick;
    }

    public void setReleaseTick(int releaseTick) {
        if (releaseTick < castTick) {
            this.releaseTick = this.castTick;
        } else {
            this.releaseTick = releaseTick;
        }
    }

    public Integer getPlacementId() {
        return placementId;
    }

    public void setPlacementId(Integer placementId) {
        this.placementId = placementId;
    }

    public int getCompletionTick() {
        return completionTick;
    }

    public void setCompletionTick(int completionTick) {
        this.completionTick = completionTick;
    }
}