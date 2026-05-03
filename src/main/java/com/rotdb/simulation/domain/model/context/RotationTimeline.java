package com.rotdb.simulation.domain.model.context;

import java.util.List;

public class RotationTimeline {
    private List<TickSnapshot> timeline;

    public List<TickSnapshot> getTimeline() {
        return timeline;
    }

    public void setTimeline(List<TickSnapshot> timeline) {
        this.timeline = timeline;
    }
}
