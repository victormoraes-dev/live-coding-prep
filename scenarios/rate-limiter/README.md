# Live Coding Practice: Sliding Window Rate Limiter 

## Context

In this interview scenario, the focus is on **Data Structures, Concurrency, and Security Controls**. You are working on the API gateway team for a major financial institution. Rate limiting is a critical security control (OWASP API Security Top 10) that prevents brute-force attacks, credential stuffing, credential stuffing, and DoS abuse on authentication and transaction endpoints. You must implement an efficient sliding window rate limiter that can handle thousands of requests per second.

---

## The Challenge

Implement the `RateLimiter` class. Your goal is to track the number of requests made by each user within a configurable rolling time window and determine whether a new request should be allowed or denied.

---

## Technical Requirements

### 1. Constructor

```java
public RateLimiter(int maxRequests, long windowSizeInMillis)
```

- `maxRequests`: Maximum number of requests allowed within the time window.
- `windowSizeInMillis`: The length of the sliding window in milliseconds.

### 2. Request Evaluation

```java
public boolean allowRequest(String userId)
```

- Returns `true` if the request should be allowed (under the limit).
- Returns `false` if the request exceeds the limit.
- Must use a **sliding window** approach. A fixed window that resets at boundaries is NOT acceptable — it allows double traffic at boundary edges (e.g., 100 requests at 11:59:59 and another 100 at 12:00:00).

### 3. Performance Strictness

- **Time Complexity:** `allowRequest` must run in **O(1) amortized** time. Iterating over all timestamps linearly will result in TLE.
- **Space Complexity:** Must be `O(U × L)` where `U` is the number of active users and `L` is the average number of requests per user within a window.

### 4. Concurrency (Bonus)

The API gateway runs in a multi-threaded environment. Be prepared to discuss how you would make the rate limiter thread-safe without creating a throughput bottleneck.

---

## Test Data Examples

| Initial Config | Request Sequence | Expected Output | Explanation |
|---|---|---|---|
| `maxRequests=2`, `windowSize=1000ms` | `allow("alice")` at T=0, `allow("alice")` at T=100ms, `allow("alice")` at T=200ms | `true, true, false` | First 2 requests allowed, 3rd exceeds limit within window |
| `maxRequests=2`, `windowSize=1000ms` | `allow("alice")` at T=0, `allow("alice")` at T=1100ms, `allow("alice")` at T=1200ms | `true, true, true` | After 1000ms the window slides — first request expires, a new slot opens |
| `maxRequests=5`, `windowSize=1000ms` | 6 rapid requests from `"bob"` at T=0 | `true(×5), false` | 5 allowed, 6th blocked |
| `maxRequests=2`, `windowSize=1000ms` | `allow("alice")` at T=0, `allow("bob")` at T=0, `allow("alice")` at T=0 | `true, true, false` | Per-user tracking — alice has used her 2 slots |
| `maxRequests=3`, `windowSize=5000ms` | 3 requests from `"mallory"` at T=0, then wait 5s, then 1 more at T=5001ms | `true(×3), true` | Window fully slides after 5s — all slots are available again |

---

## Staff Level Focus Points

- **Data Structure Choice:** Discuss the trade-offs between `LinkedHashMap` (insertion-ordered, easy eviction), a `HashMap<String, Deque<Long>>` (timestamps per user), or a single `ConcurrentHashMap<String, Deque<Long>>`. Explain why object overhead and GC pressure matter at scale (millions of calls/day).

- **Stale Entry Cleanup:** An efficient solution must evict expired timestamps. Describe how you'd prevent memory leaks from inactive users accumulating — periodic background cleanup vs. on-request lazy eviction.

- **Sliding Window vs Fixed Window:** Be ready to explain the "boundary burst" problem. A fixed window of 100 req/min allows a user to send 100 requests at 11:59:59 and another 100 at 12:00:00 — effectively 200 requests in 2 seconds. The sliding window eliminates this by evaluating a rolling window of the last 60 seconds at every request.

- **Concurrency Model:** Discuss `synchronized` (simple but bottlenecks), `ReentrantLock` (fairness policies), `ConcurrentHashMap` with `computeIfAbsent` (fine-grained locking), or striped locks. Which one would you choose for a gateway handling 50k+ requests/second?

- **Production Hardening:**
  - How would you handle clock drift in distributed deployments?
  - When would you choose an in-memory limiter vs. a distributed one (Redis)?
  - How would you rate-limit by IP vs. by user vs. by API key?
  - How would you return proper HTTP 429 headers (`Retry-After`, `X-RateLimit-Remaining`)?

---

**Summary**

- **Core skill tested:** Data structures (HashMap + Deque), sliding window algorithm, per-user tracking
- **Security relevance:** Rate limiting prevents brute-force login, credential stuffing, API abuse, and DoS — OWASP API Security Top 10
- **Java specifics:** `ConcurrentHashMap`, `Deque<Long>`, `System.currentTimeMillis()`, amortized O(1) with lazy eviction
- **Expected solution approach:** `Map<String, Deque<Long>>` — on each request, remove expired timestamps from the front of the deque, append the current timestamp at the back, then check if the deque size exceeds `maxRequests`
