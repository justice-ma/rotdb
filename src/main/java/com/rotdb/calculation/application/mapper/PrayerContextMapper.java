package com.rotdb.calculation.application.mapper;

import com.rotdb.calculation.domain.model.enums.Prayer;
import com.rotdb.calculation.domain.model.player.PrayerContext;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.Set;

@Component
public class PrayerContextMapper {
    public PrayerContext from(Set<Prayer> request) {
        PrayerContext prayer = new PrayerContext();

        EnumSet<Prayer> selected = (request == null || request.isEmpty())
                ? EnumSet.noneOf(Prayer.class)
                : EnumSet.copyOf(request);

        prayer.setSelected(selected);
        return prayer;
    }
}
