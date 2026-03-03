package com.rotdb.service.mapper;

import com.rotdb.api.dto.DamageCalcRequest;
import com.rotdb.model.enums.Prayer;
import com.rotdb.model.player.PrayerContext;
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
