package com.victormoraes;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class RateLimiter {

    private int maxRequests;
    private long windowSizeInMillis;
    private HashMap<String, Deque<Long>> usersTimeStamps = new HashMap<>();

    public RateLimiter(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
    }

    public boolean allowRequest(String userId) {

        // Session
        long now = System.currentTimeMillis();
        long begin = now - windowSizeInMillis;

        // Maintain a list of timestamps requests
        Deque<Long> userTimeStamps = usersTimeStamps.computeIfAbsent(userId, k -> new ArrayDeque<>());

        // Use FIFO strategy
        while (!userTimeStamps.isEmpty() && userTimeStamps.peekFirst() <= begin) {
            // Invalidate expired user requests
            userTimeStamps.pollFirst();
        }

        if (userTimeStamps.size() < maxRequests) {
            userTimeStamps.addLast(now);
            return true;
        }
        return false;
    }

}
