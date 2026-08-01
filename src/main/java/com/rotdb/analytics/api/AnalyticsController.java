package com.rotdb.analytics.api;

import com.rotdb.analytics.application.HeartbeatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    private final HeartbeatService heartbeatService;

    public AnalyticsController(HeartbeatService heartbeatService) {
        this.heartbeatService = heartbeatService;
    }

    @PostMapping("/heartbeat")
    public void heartbeat(@RequestBody HeartbeatRequest request) {
        heartbeatService.recordHeartbeat(request);
    }

    @GetMapping("/active")
    public int active() {
        return heartbeatService.getActiveUsers();
    }
}
