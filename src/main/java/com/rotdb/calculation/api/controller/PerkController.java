package com.rotdb.calculation.api.controller;

import com.rotdb.calculation.api.dto.PerkDto;
import com.rotdb.calculation.domain.model.enums.Perks;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/perks")
public class PerkController {
    @GetMapping
    public List<PerkDto> getPerks() {
        return Arrays.stream(Perks.values()).map(perk -> new PerkDto(
                perk.name(),
                perk.getMinTier(),
                perk.getMaxTier(),
                perk.isAffectedByLevel20(),
                perk.getName()
        )).toList();
    }
}
