package com.rotdb.analytics.domain;

import java.time.Instant;

public class ClientActivity {
    private String clientId;
    private String sessionId;
    private Instant firstVisit;
    private Instant lastActive;
    private Instant lastCalculated;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Instant getFirstVisit() {
        return firstVisit;
    }

    public void setFirstVisit(Instant firstVisit) {
        this.firstVisit = firstVisit;
    }

    public Instant getLastActive() {
        return lastActive;
    }

    public void setLastActive(Instant lastActive) {
        this.lastActive = lastActive;
    }

    public Instant getLastCalculated() {
        return lastCalculated;
    }

    public void setLastCalculated(Instant lastCalculated) {
        this.lastCalculated = lastCalculated;
    }
}
