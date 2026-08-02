package com.rotdb.analytics.domain;

import java.util.List;

public class HeartbeatResult {
    private int activeUsers;
    private List<ClientActivity> activeClients;

    public int getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(int activeUsers) {
        this.activeUsers = activeUsers;
    }

    public List<ClientActivity> getActiveClients() {
        return activeClients;
    }

    public void setActiveClients(List<ClientActivity> activeClients) {
        this.activeClients = activeClients;
    }
}
