# Live Coding Practice: Suspicious Access Pattern Detector

## Context

In this interview scenario, the focus is on **HashMap Aggregation, Sliding Window, and Security Threat Detection**. You are working on the Security Information and Event Management (SIEM) team at a financial institution. Your task is to build a detection engine that identifies potentially malicious behavior — users who access an unusually high number of distinct sensitive resources within a short time window. This is a classic reconnaissance detection pattern used in fraud prevention and intrusion detection systems.

---

## The Challenge

Implement the `SuspiciousAccessDetector` class. Given a list of access log entries, identify all users who accessed **at least `threshold` distinct resources** within any **`windowSizeInSeconds` sliding window**. A user doing this may be performing reconnaissance (scanning for vulnerable endpoints) or attempting privilege escalation.

---

## Technical Requirements

### 1. Log Entry Model

```java
public class AccessLogEntry {
    private String userId;       // user who made the request
    private String resourceId;   // the resource/endpoint accessed (e.g., "/admin/users", "/api/accounts/123")
    private long timestamp;      // epoch seconds

    // Constructor, getters, setters
}
```

### 2. Detection Method

```java
public Set<String> detectSuspicious(List<AccessLogEntry> logs, int threshold, long windowSizeInSeconds)
```

- Returns a `Set<String>` of `userId`s who accessed **threshold or more distinct resources** within any window of size `windowSizeInSeconds`.
- The window slides continuously — it is **not** aligned to fixed clock boundaries.
- Each user's accesses must be evaluated independently.
- A single user accessing the same resource multiple times within the window counts as **1 distinct access** for that resource.

### 3. Performance Strictness

- **Time Complexity:** `O(N log N)` where `N` is the number of log entries — dominated by sorting per user. The sliding window detection per user must run in `O(M)` where `M` is that user's entry count.
- **Space Complexity:** `O(N)` in the worst case to store grouped entries.

### 4. Edge Cases

- Empty log list → return empty set
- User with fewer entries than `threshold` → never flagged
- Repeated access to the same resource within window → counts once
- Timestamps are not guaranteed to be sorted (you must sort them)
- A user may be flagged only once, even if they trigger the pattern multiple times

---

## Test Data Examples

| Logs | Threshold | Window (s) | Expected Output | Explanation |
|---|---|---|---|---|
| `[(alice, /admin/users, 100), (alice, /admin/config, 105), (alice, /admin/roles, 110)]` | `3` | `20` | `{alice}` | Alice accessed 3 distinct resources within 10 seconds (100 to 110), threshold=3 met |
| `[(bob, /docs, 100), (bob, /docs, 102), (bob, /docs, 104)]` | `3` | `60` | `{}` | Bob accessed only 1 distinct resource (`/docs`) 3 times — distinct count is 1, below threshold |
| `[(mallory, /api/users, 10), (mallory, /api/accounts, 15), (mallory, /api/transactions, 25), (mallory, /api/admin, 40)]` | `3` | `20` | `{mallory}` | At window [15..35], mallory accessed 3 distinct resources (`/api/accounts`, `/api/transactions`, `/api/admin`... wait, `/api/admin` is at 40 which is outside the 20s window starting at 15... Let me recalculate: window [15,35] includes accounts(15) and transactions(25) = 2 distinct. Window [25,45] includes transactions(25) and admin(40) = 2 distinct. Window [10,30] includes users(10), accounts(15), transactions(25) = 3 distinct in 20s → threshold met. Output: `{mallory}` |
| `[(alice, /a, 0), (alice, /b, 5), (bob, /a, 0), (bob, /b, 5), (bob, /c, 10)]` | `3` | `15` | `{bob}` | Alice has only 2 distinct resources, Bob has 3 within [0..15] |
| `[]` | `3` | `60` | `{}` | Empty log list |

---

## Staff Level Focus Points

- **HashMap + Sliding Window on Sorted Data:** The core pattern is `Map<String, List<AccessLogEntry>>` to group by user, sort each user's entries by timestamp, then slide a window keeping track of distinct resource IDs using a `Set<String>` that you update as timestamps enter and leave the window.

- **Two Pointers within Each User:** After sorting a user's entries, maintain `left` and `right` pointers. Advance `right` to include entries within the window, advance `left` to evict entries that fall out. Maintain a `Set<String>` or `Map<String, Integer>` of distinct resources in the current window and their counts.

```
User: bob
Entries sorted: [0:/a, 5:/b, 10:/c, 30:/d]

Window [0..15]:
  right=0 → {/a}, right=1 → {/a,/b}, right=2 → {/a,/b,/c} → size=3 ≥ threshold → SUSPICIOUS
```

- **Distinct Resource Counting:** When a resource enters the window, add to the set. When a resource leaves (because the left pointer advanced past it), decrement its count. Only remove from the set when count reaches 0. A `Map<String, Integer>` (resource → count in current window) is more practical than a `Set` here.

- **Production Readiness:** Discuss how you would handle:
  - Real-time streaming (infinite log stream, not a batch)
  - Memory management for long-running detection (TTL-based eviction of user entries)
  - False positive reduction (whitelist known aggressive but legitimate users, or require access to specific high-sensitivity resources)
  - Distributed environment (multiple SIEM nodes, how to correlate across regions)

- **Security Relevance:** This exact pattern is used to detect:
  - **Reconnaissance scanning** — attacker probes multiple endpoints to find vulnerable surface
  - **Privilege escalation attempts** — user accesses resources they shouldn't know about
  - **Credential stuffing** — multiple login attempts across different accounts from same IP (adapt the resource dimension)