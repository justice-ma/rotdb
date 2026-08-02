package com.rotdb.analytics.application;

import com.rotdb.analytics.api.HeartbeatRequest;
import com.rotdb.analytics.domain.ClientActivity;
import com.rotdb.analytics.domain.HeartbeatResult;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HeartbeatService {
    private final ConcurrentHashMap<String, ClientActivity> activeClients = new ConcurrentHashMap<>();
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

    public HeartbeatResult getActiveUsers() {
        HeartbeatResult heartbeatResult = new HeartbeatResult();
        List<ClientActivity> clientActivities = new ArrayList<>();
        heartbeatResult.setActiveUsers(0);
        heartbeatResult.setActiveClients(clientActivities);
        Instant now = Instant.now();
        for (Map.Entry<String, ClientActivity> clientActivityEntry : activeClients.entrySet()) {
            if (now.minus(3, ChronoUnit.MINUTES).isBefore(clientActivityEntry.getValue().getLastActive())) {
                heartbeatResult.setActiveUsers(heartbeatResult.getActiveUsers() + 1);
                clientActivities.add(clientActivityEntry.getValue());
            }
        }
        return heartbeatResult;
    }
}
