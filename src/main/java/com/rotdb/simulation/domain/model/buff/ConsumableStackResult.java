package com.rotdb.simulation.domain.model.buff;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.simulation.domain.model.buff.enums.StackConsumptionTiming;

public record ConsumableStackResult(
        AppliedBuffResult appliedBuffResult,
        BuffId consumedStackId,
        Integer consumedAmount,
        Integer effectiveAmount,
        StackConsumptionTiming consumptionTiming
) {
}
