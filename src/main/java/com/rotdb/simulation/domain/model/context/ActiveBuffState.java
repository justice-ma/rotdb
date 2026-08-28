package com.rotdb.simulation.domain.model.context;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.enums.BuffSource;

public class ActiveBuffState {
    private BuffId buffId;
    private BuffSource source;
    private int duration;

    public ActiveBuffState(BuffId buffId, BuffSource source, int duration) {
        this.buffId = buffId;
        this.source = source;
        this.duration = duration;
    }

    public BuffId getBuffId() {
        return buffId;
    }

    public void setBuffId(BuffId buffId) {
        this.buffId = buffId;
    }

    public BuffSource getSource() {
        return source;
    }

    public void setSource(BuffSource source) {
        this.source = source;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
