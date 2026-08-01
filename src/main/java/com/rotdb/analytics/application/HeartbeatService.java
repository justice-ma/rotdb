package com.rotdb.analytics.application;

import com.rotdb.analytics.api.HeartbeatRequest;
import com.rotdb.analytics.domain.ClientActivity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HeartbeatService {
    private final ConcurrentHashMap<String, ClientActivity> activeClients = new ConcurrentHashMap();
    public void recordHeartbeat(HeartbeatRequest request) {
        Instant now = Instant.now();
        ClientActivity newClient = new ClientActivity();
        newClient.setFirstVisit(now);
        newClient.setLastActive(now);
        newClient.setClientId(request.clientId());
        newClient.setSessionId(request.sessionId());
        activeClients.merge(
                request.clientId(),
                newClient,
                (existingClient, incomingClient) -> {
                    existingClient.setLastActive(now);
                    existingClient.setSessionId(request.sessionId());
                    return existingClient;
                }
        );

        for (Map.Entry<String, ClientActivity> clientActivityEntry : activeClients.entrySet()) {
            if (clientActivityEntry.getValue().getLastActive().isBefore(now.minus(30, ChronoUnit.MINUTES))) {
                activeClients.remove(clientActivityEntry.getValue().getClientId());
            }
        }
    }

    public int getActiveUsers() {
        int activeCount = 0;
        Instant now = Instant.now();
        for (Map.Entry<String, ClientActivity> clientActivityEntry : activeClients.entrySet()) {
            if (now.minus(3, ChronoUnit.MINUTES).isBefore(clientActivityEntry.getValue().getLastActive())) {
                activeCount++;
            }
        }
        return activeCount;
    }
}
