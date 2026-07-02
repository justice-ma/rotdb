package com.rotdb.simulation.domain.model.buff;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.enums.BuffApplication;
import com.rotdb.simulation.domain.model.buff.enums.BuffLifecycle;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;

public class BuffDefinition {
    private final BuffId buffId;
    private final BuffSource source;
    private final BuffLifecycle lifecycle;
    private final BuffApplication application;
    private final Integer durationTicks;
    private final Integer cooldownTicks;
    private final boolean gcdConsuming;
    private final boolean passive;
    private final Double activationAdrenalineDelta;
    private final Double tickAdrenalineDelta;

    public BuffDefinition(BuffId buffId, BuffSource source, BuffLifecycle lifecycle, BuffApplication application, Integer durationTicks, Integer cooldownTicks, boolean gcdConsuming, boolean passive, Double adrenalineDelta, Double tickAdrenalineDelta) {
        this.buffId = buffId;
        this.source = source;
        this.lifecycle = lifecycle;
        this.application = application;
        this.durationTicks = durationTicks;
        this.cooldownTicks = cooldownTicks;
        this.gcdConsuming = gcdConsuming;
        this.passive = passive;
        this.activationAdrenalineDelta = adrenalineDelta;
        this.tickAdrenalineDelta = tickAdrenalineDelta;
    }

    public BuffId getBuffId() {
        return buffId;
    }

    public BuffSource getSource() {
        return source;
    }

    public BuffLifecycle getLifecycle() {
        return lifecycle;
    }

    public BuffApplication getApplication() {
        return application;
    }

    public Integer getDurationTicks() {
        return durationTicks;
    }

    public Integer getCooldownTicks() {
        return cooldownTicks;
    }

    public boolean isGcdConsuming() {
        return gcdConsuming;
    }

    public boolean isPassive() {
        return passive;
    }

    public Double getActivationAdrenalineDelta() {
        return activationAdrenalineDelta;
    }

    public Double getTickAdrenalineDelta() {
        return tickAdrenalineDelta;
    }
}
