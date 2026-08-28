package com.rotdb.analytics.domain;

import java.util.List;

public class HeartbeatResult {
    private int openUserCount;
    private int activeUserCount;
    private List<ClientActivity> openUsers;
    private List<ClientActivity> activeUsers;

    public int getOpenUserCount() {
        return openUserCount;
    }

    public void setOpenUserCount(int openUserCount) {
        this.openUserCount = openUserCount;
    }

    public List<ClientActivity> getOpenUsers() {
        return openUsers;
    }

    public void setOpenUsers(List<ClientActivity> openUsers) {
        this.openUsers = openUsers;
    }

    public List<ClientActivity> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(List<ClientActivity> activeUsers) {
        this.activeUsers = activeUsers;
    }

    public int getActiveUserCount() {
        return activeUserCount;
    }

    public void setActiveUserCount(int activeUserCount) {
        this.activeUserCount = activeUserCount;
    }
}
