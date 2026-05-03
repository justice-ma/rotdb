package com.rotdb.simulation.application.service;

import com.rotdb.simulation.domain.model.context.TimelineHit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduledHitsGrouper {
    public static Map<Integer, List<TimelineHit>> group(List<TimelineHit> hits) {
        Map<Integer, List<TimelineHit>> scheduledHitsPerTick = new HashMap<>();
        for (TimelineHit hit : hits) {
            if (scheduledHitsPerTick.get(hit.getLandingTick()) != null) {
                scheduledHitsPerTick.get(hit.getLandingTick()).add(hit);
            } else {
                List<TimelineHit> list = new ArrayList<>();
                list.add(hit);
                scheduledHitsPerTick.put(hit.getLandingTick(), list);
            }
        }
        return scheduledHitsPerTick;
    }
}
