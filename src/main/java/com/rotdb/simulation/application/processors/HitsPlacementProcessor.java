package com.rotdb.simulation.application.processors;

import com.rotdb.simulation.application.service.HitsGrouper;
import com.rotdb.simulation.domain.model.context.ScheduledHit;
import com.rotdb.simulation.domain.model.context.TimelineHit;

import java.util.List;
import java.util.Map;

public class HitsPlacementProcessor {
    public static void addScheduledHits(Map<Integer, List<ScheduledHit>> timelineHitMap, List<ScheduledHit> hits) {
        for (Map.Entry<Integer, List<ScheduledHit>> entry : HitsGrouper.groupScheduledHits(hits).entrySet()) {
            if (timelineHitMap.containsKey(entry.getKey())) {
                List<ScheduledHit> timelineHits = timelineHitMap.get(entry.getKey());
                timelineHits.addAll(entry.getValue());
                timelineHitMap.put(entry.getKey(), timelineHits);
            } else {
                timelineHitMap.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public static void addTimelineHits(Map<Integer, List<TimelineHit>> timelineHitMap, List<TimelineHit> hits) {
        for (Map.Entry<Integer, List<TimelineHit>> entry : HitsGrouper.groupTimelineHits(hits).entrySet()) {
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
