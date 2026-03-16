package com.rotdb.calculation.api.dto;

import com.rotdb.calculation.domain.model.enums.CombatStyles;
import com.rotdb.calculation.domain.model.enums.PrayerBook;
import com.rotdb.calculation.domain.model.enums.PrayerExclusivityGroup;

import java.util.EnumSet;

public record PrayerDto(
        String id,
        boolean divineRage,
        int strBonus,
        int accBonus,
        EnumSet<CombatStyles> styles,
        PrayerBook book,
        String name,
        EnumSet<PrayerExclusivityGroup> exclusivityGroups) {
}
