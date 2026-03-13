package com.rotdb.api.dto;

import com.rotdb.domain.model.enums.CombatStyles;
import com.rotdb.domain.model.enums.PrayerBook;
import com.rotdb.domain.model.enums.PrayerExclusivityGroup;

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
