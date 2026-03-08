package com.rotdb.api.controller;

import com.rotdb.api.dto.PrayerDto;
import com.rotdb.domain.model.enums.Prayer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/prayers")
public class PrayerController {
    @GetMapping
    public List<PrayerDto> getPrayers(@RequestParam(required = false) String q) {
        String query = q == null ? "" : q.trim().toLowerCase();

        return Arrays.stream(Prayer.values()).filter(prayer -> query.isBlank() ||
                prayer.getName().toLowerCase().contains(query)).map(prayer -> new PrayerDto(
                    prayer.name(),
                    prayer.isStackableWithDivineRage(),
                    prayer.getPrayerStrengthBonus(),
                    prayer.getPrayerAccuracyBonus(),
                    prayer.getStyles(),
                    prayer.getBook(),
                    prayer.getName(),
                    prayer.getExclusivityGroup()
        )).toList();
    }
}
