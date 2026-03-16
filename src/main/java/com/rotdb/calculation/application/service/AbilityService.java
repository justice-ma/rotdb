package com.rotdb.calculation.application.service;

import com.rotdb.calculation.ability.AbilityId;
import com.rotdb.calculation.api.dto.AbilityDto;
import com.rotdb.calculation.domain.model.enums.CombatStyles;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AbilityService {
    public List<AbilityDto> listByStyle(CombatStyles style) {
        List<AbilityDto> out = new ArrayList<>();

        for (AbilityId a : AbilityId.values()) {
            if (a.getStyle() == style) {
                out.add(new AbilityDto(a, a.getName(), a.getTier(), a.getIconPath(), a.isCommon()));
            }
        }

        return out;
    }
}
