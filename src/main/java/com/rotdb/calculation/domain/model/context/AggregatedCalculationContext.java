package com.rotdb.calculation.domain.model.context;

public class AggregatedCalculationContext {
    private final CalculationContext snapshotContext;
    private final CalculationContext liveContext;

    public AggregatedCalculationContext(CalculationContext snapshotContext, CalculationContext liveContext) {
        this.snapshotContext = snapshotContext;
        this.liveContext = liveContext;
    }

    public CalculationContext getSnapshotContext() {
        return snapshotContext;
    }

    public CalculationContext getLiveOrSnapshotContext() {
        if (liveContext == null) {
            return snapshotContext;
        } else {
            return liveContext;
        }
    }
}
