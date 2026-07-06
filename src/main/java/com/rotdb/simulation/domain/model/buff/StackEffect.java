package com.rotdb.simulation.domain.model.buff;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;
import com.rotdb.simulation.domain.model.buff.enums.StackClampingBehaviour;

public class StackEffect {
    BuffId buffId;
    Integer stackDelta;
    BuffSource buffSource;
    Double procChance;
    Integer durationOverride;
    Integer maximumStacksOverride;
    StackClampingBehaviour stackClampingBehaviour;


    public StackEffect(BuffId buffId, Integer stackDelta, BuffSource buffSource, Double procChance,
                       Integer durationOverride, Integer maximumStacksOverride, StackClampingBehaviour stackClampingBehaviour) {
        this.buffId = buffId;
        this.stackDelta = stackDelta;
        this.buffSource = buffSource;
        this.procChance = procChance;
        this.durationOverride = durationOverride;
        this.maximumStacksOverride = maximumStacksOverride;
        this.stackClampingBehaviour = stackClampingBehaviour;
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

    public Integer getMaximumStacksOverride() {
        return maximumStacksOverride;
    }

    public void setMaximumStacksOverride(Integer maximumStacksOverride) {
        this.maximumStacksOverride = maximumStacksOverride;
    }

    public StackClampingBehaviour getStackClampingBehaviour() {
        return stackClampingBehaviour;
    }

    public void setStackClampingBehaviour(StackClampingBehaviour stackClampingBehaviour) {
        this.stackClampingBehaviour = stackClampingBehaviour;
    }
}
