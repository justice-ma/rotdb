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
        newClient.setLastCalculated(null);
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

    public void recordCalculationActivity(String clientId, String sessionId) {
        if (clientId == null || clientId.isBlank()) return;

        Instant now = Instant.now();

        ClientActivity newClient = new ClientActivity();
        newClient.setClientId(clientId);
        newClient.setSessionId(sessionId);
        newClient.setFirstVisit(now);
        newClient.setLastActive(now);
        newClient.setLastCalculated(now);

        activeClients.merge(
                clientId,
                newClient,
                (existingClient, incomingClient) -> {
                    existingClient.setSessionId(sessionId);
                    existingClient.setLastActive(now);
                    existingClient.setLastCalculated(now);
                    return existingClient;
                }
        );
    }

    public HeartbeatResult getActiveUsers() {
        HeartbeatResult heartbeatResult = new HeartbeatResult();
        List<ClientActivity> openUsers = new ArrayList<>();
        List<ClientActivity> activeUsers = new ArrayList<>();
        heartbeatResult.setOpenUserCount(0);
        heartbeatResult.setActiveUserCount(0);
        heartbeatResult.setOpenUsers(openUsers);
        heartbeatResult.setActiveUsers(activeUsers);
        Instant now = Instant.now();
        for (Map.Entry<String, ClientActivity> clientActivityEntry : activeClients.entrySet()) {
            if (now.minus(3, ChronoUnit.MINUTES).isBefore(clientActivityEntry.getValue().getLastActive())) {
                heartbeatResult.setOpenUserCount(heartbeatResult.getOpenUserCount() + 1);
                openUsers.add(clientActivityEntry.getValue());
            }

            Instant lastCalculated = clientActivityEntry.getValue().getLastCalculated();

            if (lastCalculated != null && now.minus(3, ChronoUnit.MINUTES).isBefore(lastCalculated)) {
                heartbeatResult.setActiveUserCount(heartbeatResult.getActiveUserCount() + 1);
                activeUsers.add(clientActivityEntry.getValue());
            }
        }
        return heartbeatResult;
    }
}
