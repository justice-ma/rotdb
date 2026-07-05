package com.rotdb.simulation.application.service;

import com.rotdb.simulation.domain.model.context.ScheduledHit;
import com.rotdb.simulation.domain.model.context.TimelineHit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduledHitsGrouper {
    public static Map<Integer, List<ScheduledHit>> groupScheduledHits(List<ScheduledHit> hits) {
        Map<Integer, List<ScheduledHit>> scheduledHitsPerTick = new HashMap<>();
        for (ScheduledHit hit : hits) {
            if (scheduledHitsPerTick.get(hit.landingTick()) != null) {
                scheduledHitsPerTick.get(hit.landingTick()).add(hit);
            } else {
                List<ScheduledHit> list = new ArrayList<>();
                list.add(hit);
                scheduledHitsPerTick.put(hit.landingTick(), list);
            }
        }
        return scheduledHitsPerTick;
    }

    public static Map<Integer, List<TimelineHit>> groupTimelineHits(List<TimelineHit> hits) {
        Map<Integer, List<TimelineHit>> timelineHitsPerTick = new HashMap<>();
        for (TimelineHit hit : hits) {
            if (timelineHitsPerTick.get(hit.getLandingTick()) != null) {
                timelineHitsPerTick.get(hit.getLandingTick()).add(hit);
            } else {
                List<TimelineHit> list = new ArrayList<>();
                list.add(hit);
                timelineHitsPerTick.put(hit.getLandingTick(), list);
            }
        }
        return timelineHitsPerTick;
    }
}
