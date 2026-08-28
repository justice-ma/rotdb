package com.rotdb.analytics.api;

public record HeartbeatRequest(
        String clientId,
        String sessionId
) {
}
