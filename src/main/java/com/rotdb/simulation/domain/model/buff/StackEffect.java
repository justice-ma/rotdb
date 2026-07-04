package com.rotdb.simulation.domain.model.buff;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;

public class StackEffect {
    BuffId buffId;
    Integer stackDelta;
    BuffSource buffSource;
    Double procChance;
    Integer durationOverride;

    public StackEffect(BuffId buffId, Integer stackDelta, BuffSource buffSource, Double procChance, Integer durationOverride) {
        this.buffId = buffId;
        this.stackDelta = stackDelta;
        this.buffSource = buffSource;
        this.procChance = procChance;
        this.durationOverride = durationOverride;
    }

    public BuffId getBuffId() {
        return buffId;
    }

    public void setBuffId(BuffId buffId) {
        this.buffId = buffId;
    }

    public Integer getStackDelta() {
        return stackDelta;
    }

    public void setStackDelta(Integer stackDelta) {
        this.stackDelta = stackDelta;
    }

    public BuffSource getBuffSource() {
        return buffSource;
    }

    public void setBuffSource(BuffSource buffSource) {
        this.buffSource = buffSource;
    }

    public Double getProcChance() {
        return procChance;
    }

    public void setProcChance(Double procChance) {
        this.procChance = procChance;
    }

    public Integer getDurationOverride() {
        return durationOverride;
    }

    public void setDurationOverride(Integer durationOverride) {
        this.durationOverride = durationOverride;
    }
}
