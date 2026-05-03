package com.rotdb.simulation.application.processors;

import com.rotdb.simulation.application.service.ScheduledHitsGrouper;
import com.rotdb.simulation.domain.model.context.TimelineHit;

import java.util.List;
import java.util.Map;

public class HitsPlacementProcessor {
    public static void addScheduledHits(Map<Integer, List<TimelineHit>> timelineHitMap, List<TimelineHit> hits) {
        for (Map.Entry<Integer, List<TimelineHit>> entry : ScheduledHitsGrouper.group(hits).entrySet()) {
            if (timelineHitMap.containsKey(entry.getKey())) {
                List<TimelineHit> timelineHits = timelineHitMap.get(entry.getKey());
                timelineHits.addAll(entry.getValue());
                timelineHitMap.put(entry.getKey(), timelineHits);
            } else {
                timelineHitMap.put(entry.getKey(), entry.getValue());
            }
        }
    }
}
