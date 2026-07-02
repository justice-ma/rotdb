package com.rotdb.simulation.domain.model.buff;

public record AppliedBuffResult(
        BuffDefinition buffDefinition,
        Integer resolvedDurationTicks
) {
}
