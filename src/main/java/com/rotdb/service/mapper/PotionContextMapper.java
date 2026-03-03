package com.rotdb.service.mapper;

import com.rotdb.api.dto.DamageCalcRequest;
import com.rotdb.model.enums.Potions;
import com.rotdb.model.enums.Stats;
import com.rotdb.model.player.PotionContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PotionContextMapper {
    public List<PotionContext> from(List<DamageCalcRequest.PotionSelection> request) {
        System.out.println(request);
        List<PotionContext> potions = new ArrayList<>();
        if (request.isEmpty()) {
            return List.of();
        }
        for (DamageCalcRequest.PotionSelection pot : request) {
            if (pot == null || pot.stat() == null || pot.pot() == null ) continue;
            potions.add(new PotionContext(pot.pot(), pot.stat()));
        }

        return potions;
    }
}
