package com.victormoraes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SuspiciousAccessDetector {

    public Set<String> detectSuspicious(List<AccessLogEntry> logs, int threshold, long windowSizeInSeconds) {
        Set<String> suspiciousUsers = new HashSet<>();

        if (logs == null || logs.isEmpty() || threshold <= 0) {
            return suspiciousUsers;
        }

        // 1. Window anchored to the log data, not wall clock
        long maxTimestamp = Long.MIN_VALUE;
        for (AccessLogEntry log : logs) {
            if (log != null && log.getTimestamp() > maxTimestamp) {
                maxTimestamp = log.getTimestamp();
            }
        }

        long windowSizeMillis = windowSizeInSeconds * 1000L;
        long windowStartMillis = maxTimestamp - windowSizeMillis;

        // 2. Count DISTINCT resources per user (Set, not frequency map)
        Map<String, Set<String>> userToDistinctResources = new HashMap<>();

        for (AccessLogEntry log : logs) {
            if (log == null)
                continue;

            long ts = log.getTimestamp();
            if (ts < windowStartMillis || ts > maxTimestamp)
                continue;

            String userId = log.getUserId();
            String resourceId = log.getResourceId();

            // 3. Handles BOTH seen and unseen users (original only handled seen)
            userToDistinctResources.computeIfAbsent(userId, k -> new HashSet<>()).add(resourceId);
        }

        // 4. Uses threshold to flag users
        for (Map.Entry<String, Set<String>> entry : userToDistinctResources.entrySet()) {
            if (entry.getValue().size() >= threshold) {
                suspiciousUsers.add(entry.getKey()); // adds USER ID, not resource
            }
        }

        return suspiciousUsers;
    }

}