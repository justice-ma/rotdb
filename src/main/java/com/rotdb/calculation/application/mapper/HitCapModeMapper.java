package com.rotdb.calculation.application.mapper;

import com.rotdb.shared.combat.domain.model.enums.HitCapMode;
import org.springframework.stereotype.Component;

@Component
public class HitCapModeMapper {
    public HitCapMode from(HitCapMode hitCapMode) {
        return hitCapMode == null ? HitCapMode.CAP_30000 : hitCapMode;
    }
}
