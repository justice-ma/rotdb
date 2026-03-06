package com.rotdb.application.mapper;

import com.rotdb.api.dto.DamageCalcRequestDto;
import com.rotdb.domain.model.enums.Perks;
import com.rotdb.domain.model.equipment.PerkContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PerkContextMapper {
    public PerkContext from(DamageCalcRequestDto.PerkSelection request) {
        PerkContext perks = new PerkContext();
        Map<Perks, Integer> cleanedPerks = new HashMap<>();
        if (request == null || request.selectedPerks().isEmpty()) {
            perks.setPerk(cleanedPerks);
            return perks;
        }

        perks.setEquipmentLevel20(request.itemLevel20() != null && request.itemLevel20());

        for (Map.Entry<Perks, Integer> perk : request.selectedPerks().entrySet()) {
            if (perk.getKey() == null) continue;
            if (perk.getValue() == null) {
                cleanedPerks.put(perk.getKey(), perk.getKey().getMinTier());
                continue;
            }
            if (perk.getValue() > perk.getKey().getMaxTier()) {
                cleanedPerks.put(perk.getKey(), perk.getKey().getMaxTier());
            } else if (perk.getValue() < perk.getKey().getMinTier()) {
                cleanedPerks.put(perk.getKey(), perk.getKey().getMinTier());
            } else {
                cleanedPerks.put(perk.getKey(), perk.getValue());
            }
        }
        perks.setPerk(cleanedPerks);
        return perks;
    }
}
