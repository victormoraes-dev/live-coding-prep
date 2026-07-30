package com.victormoraes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class SuspiciousAccessDetectorTest {

    private final SuspiciousAccessDetector detector = new SuspiciousAccessDetector();

    private AccessLogEntry entry(String userId, String resourceId, long timestamp) {
        return new AccessLogEntry(userId, resourceId, timestamp);
    }

    // 1. Three distinct resources within the window should be flagged.
    @Test
    void basicDetection_threeDistinctResourcesInWindow_flagged() {
        List<AccessLogEntry> logs = List.of(
                entry("alice", "/admin/users", 100),
                entry("alice", "/admin/config", 105),
                entry("alice", "/admin/roles", 110));

        Set<String> result = detector.detectSuspicious(logs, 3, 20);

        assertEquals(Set.of("alice"), result);
    }

    // 2. Repeated access to the same resource counts as only one distinct resource.
    @Test
    void repeatedSameResource_doesNotCountAsDistinct() {
        List<AccessLogEntry> logs = List.of(
                entry("bob", "/docs", 100),
                entry("bob", "/docs", 102),
                entry("bob", "/docs", 104));

        Set<String> result = detector.detectSuspicious(logs, 3, 60);

        assertTrue(result.isEmpty(), "Only one distinct resource should not be flagged");
    }

    // 3. Sliding window captures recon across time boundaries.
    @Test
    void slidingWindow_capturesReconAcrossTimeBoundaries() {
        List<AccessLogEntry> logs = List.of(
                entry("mallory", "/api/users", 10),
                entry("mallory", "/api/accounts", 15),
                entry("mallory", "/api/transactions", 25),
                entry("mallory", "/api/admin", 40));

        Set<String> result = detector.detectSuspicious(logs, 3, 20);

        // Window [10, 30] captures /users, /accounts, /transactions (3 distinct)
        assertTrue(result.contains("mallory"));
        assertEquals(1, result.size());
    }

    // 4. Only the user meeting the threshold should be flagged.
    @Test
    void onlyUserMeetingThresholdIsFlagged() {
        List<AccessLogEntry> logs = List.of(
                entry("alice", "/a", 0),
                entry("alice", "/b", 5),
                entry("bob", "/a", 0),
                entry("bob", "/b", 5),
                entry("bob", "/c", 10));

        Set<String> result = detector.detectSuspicious(logs, 3, 15);

        // Alice has only 2 distinct resources; Bob has 3.
        assertEquals(Set.of("bob"), result);
    }

    // 5. Empty log list returns an empty set.
    @Test
    void emptyLogList_returnsEmptySet() {
        List<AccessLogEntry> logs = List.of();

        Set<String> result = detector.detectSuspicious(logs, 3, 60);

        assertTrue(result.isEmpty());
    }

    // 6. Edge case: timestamp exactly at the window boundary is included.
    @Test
    void edgeCase_exactBoundaryTimestamp() {
        // Timestamps 50, 55, 60 with window=10s.
        // For right=60, windowStart = 60 - 10 = 50.
        // Entry at 50 is >= 50, so it stays within the window.
        // All three distinct resources are within the window -> flagged.
        List<AccessLogEntry> logs = List.of(
                entry("frank", "/r1", 50),
                entry("frank", "/r2", 55),
                entry("frank", "/r3", 60));

        Set<String> result = detector.detectSuspicious(logs, 3, 10);

        assertEquals(Set.of("frank"), result);
    }

    // 6b. Edge case: entry just outside the boundary is evicted.
    @Test
    void edgeCase_entryJustOutsideBoundaryEvicted() {
        // Timestamps 49, 55, 60 with window=10s.
        // For right=60, windowStart = 50. Entry at 49 < 50 -> evicted.
        // Only /r2 and /r3 remain (2 distinct) -> not flagged with threshold 3.
        List<AccessLogEntry> logs = List.of(
                entry("frank", "/r1", 49),
                entry("frank", "/r2", 55),
                entry("frank", "/r3", 60));

        Set<String> result = detector.detectSuspicious(logs, 3, 10);

        assertTrue(result.isEmpty(), "Entry at 49 should be evicted from window ending at 60 with size 10");
    }

    // 7. Unordered timestamps should still work after sorting.
    @Test
    void unorderedTimestamps_shouldStillWork() {
        // Provided out of order; after sorting: [100:/x, 150:/y, 200:/z].
        // With threshold=2 and window=100s, all three fit within [100, 200].
        List<AccessLogEntry> logs = List.of(
                entry("grace", "/api/z", 200),
                entry("grace", "/api/x", 100),
                entry("grace", "/api/y", 150));

        Set<String> result = detector.detectSuspicious(logs, 2, 100);

        assertEquals(Set.of("grace"), result);
    }

    // 7b. Unordered timestamps where the window excludes the earliest entry.
    @Test
    void unorderedTimestamps_windowExcludesEarliest() {
        // After sorting: [100:/x, 150:/y, 200:/z].
        // With threshold=3 and window=60s, window ending at 200 starts at 140.
        // 100 < 140 -> evicted. Only /y and /z remain (2 distinct) -> not flagged.
        List<AccessLogEntry> logs = List.of(
                entry("grace", "/api/z", 200),
                entry("grace", "/api/x", 100),
                entry("grace", "/api/y", 150));

        Set<String> result = detector.detectSuspicious(logs, 3, 60);

        assertTrue(result.isEmpty());
    }

    // 8. Multiple users, multiple flagged.
    @Test
    void multipleUsersMultipleFlagged() {
        List<AccessLogEntry> logs = List.of(
                entry("alice", "/a", 0),
                entry("alice", "/b", 1),
                entry("alice", "/c", 2),
                entry("bob", "/x", 0),
                entry("bob", "/y", 1),
                entry("bob", "/z", 2));

        Set<String> result = detector.detectSuspicious(logs, 3, 10);

        assertEquals(Set.of("alice", "bob"), result);
        assertEquals(2, result.size());
    }

    // 9. Threshold of 1 flags every user with at least one access.
    @Test
    void thresholdOne_flagsAnyAccess() {
        List<AccessLogEntry> logs = List.of(
                entry("alice", "/a", 0),
                entry("bob", "/b", 100));

        Set<String> result = detector.detectSuspicious(logs, 1, 60);

        assertEquals(Set.of("alice", "bob"), result);
    }

    // 10. Null logs input returns an empty set safely.
    @Test
    void nullLogs_returnsEmptySet() {
        Set<String> result = detector.detectSuspicious(null, 3, 60);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
