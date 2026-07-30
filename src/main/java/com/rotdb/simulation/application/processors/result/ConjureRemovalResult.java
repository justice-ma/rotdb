package com.rotdb.simulation.application.processors.result;

public class ConjureRemovalResult {
    public boolean removed;

    public ConjureRemovalResult(boolean removed) {
        this.removed = removed;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}
