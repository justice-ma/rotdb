package com.rotdb.simulation.application.processors;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.config.ProcMode;
import com.rotdb.simulation.domain.model.context.SimulationState;

public class ProcProcessor {
    public static boolean determineProc(ProcMode procMode, Double procChance, SimulationState state, BuffId buff) {
        double EPSILON = 1e-9;
        if (procChance == null) {
            return true;
        } else if (procChance <= 0) {
            return false;
        }
        switch (procMode) {
            case FORCED -> {
                return true;
            }
            case EXPECTED_ACCUMULATED -> {
                if (state.getProcAccumulators().containsKey(buff)) {
                    state.getProcAccumulators().put(buff, state.getProcAccumulators().get(buff) + procChance);
                } else {
                    state.getProcAccumulators().put(buff, procChance);
                }
                if (state.getProcAccumulators().get(buff) + EPSILON >= 1.0) {
                    double remainder = state.getProcAccumulators().get(buff) - 1.0;
                    if (Math.abs(remainder) < EPSILON) {
                        remainder = 0.0;
                    }
                    state.getProcAccumulators().put(buff, remainder);
                    return true;
                } else {
                    return false;
                }
            }
            case SEEDED_RANDOM -> {
                if (state.getRandom().nextDouble() < procChance) {
                    return true;
                }
            }
            default -> {
                return false;
            }
        }
        return false;
    }
}
