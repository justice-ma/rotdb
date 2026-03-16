package com.rotdb.calculation.application.mapper;

import com.rotdb.calculation.api.dto.DamageCalcRequestDto;
import com.rotdb.calculation.domain.model.player.PotionContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PotionContextMapper {
    public List<PotionContext> from(List<DamageCalcRequestDto.PotionSelection> request) {
        List<PotionContext> potions = new ArrayList<>();
        if (request.isEmpty()) {
            return List.of();
        }
        for (DamageCalcRequestDto.PotionSelection pot : request) {
            if (pot == null || pot.stat() == null || pot.pot() == null ) continue;
            potions.add(new PotionContext(pot.pot(), pot.stat()));
        }

        return potions;
    }
}
