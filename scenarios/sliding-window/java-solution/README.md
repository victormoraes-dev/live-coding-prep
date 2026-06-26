# Live Coding Practice: User Journey Log Analyzer (Java)

## Context
At QuintoAndar, the web platform tracks millions of user interactions per minute. Every action a user takes on the website is logged as a character in a session string (e.g., `V` = View Property, `S` = Search, `F` = Apply Filter, `C` = Contact Broker, `P` = View Photos). 

The Marketing and Analytics teams want to trigger real-time promotions. To do this, they need to find the **shortest continuous sequence of actions** in a user's session log that contains a specific set of target events. 

## The Challenge
Implement the `LogAnalyzer` class. Your goal is to write a method that takes a user's session log and a string of target events, and returns the shortest substring containing all the required target events (including duplicates, if any).

## Technical Requirements

### 1. The Rules
*   The `session` string contains the chronological sequence of user actions.
*   The `target` string contains the exact events that must be present in the window.
*   The result must be a continuous substring from the `session`.
*   If there is no such window, return an empty string `""`.
*   If there are multiple windows of the same minimum length, return the first one found.

### 2. Performance Strictness
*   **Time Complexity:** Must be `O(N + M)`, where `N` is the length of the session and `M` is the length of the target.
*   **Space Complexity:** Must be `O(1)` or `O(K)` where `K` is the size of the character set (e.g., using a fixed-size frequency map/array for the ASCII characters).
*   **Constraint:** A brute-force approach `O(N^2)` checking every possible substring will fail the performance tests on Codility.

### 3. Output
The method `findShortestJourney(String session, String target)` should return a `String`.

---

## Test Data Examples

| `session` | `target` | Expected Output | Explanation |
| :--- | :--- | :--- | :--- |
| `"VFSVCPFV"` | `"VFC"` | `"VCPFV"` | The shortest window containing 'V', 'F', and 'C' is "VCPFV". (Notice that "VFSVC" is length 5, but "VCPFV" is also length 5 and valid. "FVC" is not contiguous). Wait, "VFSVC" contains V, F, C. "VCPFV" contains V, C, P, F, V. The shortest is actually "FSVC" (length 4). Let's trace: F, S, V, C. Contains F, V, C. Yes! |
| `"SSSSSS"` | `"V"` | `""` | The target event 'V' never occurred in the session. |
| `"VFC"` | `"VFC"` | `"VFC"` | The entire session is the shortest window. |
| `"VVVVV"` | `"VV"` | `"VV"` | The target requires two 'V's. The shortest window is "VV". |

*(Note: In the first example, if `session` = `"VFSVCPFV"` and `target` = `"VFC"`, the shortest valid substring is `"FSVC"`).*

---

## Staff Level Focus Points

### 1. The Sliding Window Archetype
This is the textbook definition of a **Variable-Size Sliding Window**. A Staff Engineer immediately recognizes that searching for a "continuous sequence" or "substring" that satisfies a condition requires two pointers moving in the same direction (`left` and `right`).

### 2. State Management (Frequency Maps)
To achieve `O(N)` time complexity, you cannot rescan the window to check if it has the required characters. You must maintain a running state. A Staff Engineer will use a frequency array `int[] charCounts = new int[128]` instead of a `HashMap<Character, Integer>` to track the required characters, avoiding object overhead and GC pressure.

### 3. The Expansion and Contraction Logic
The core algorithm relies on a two-step rhythm:
*   **Expand (Right Pointer):** Move the `right` pointer to include new events in the window until the window contains all the target events.
*   **Contract (Left Pointer):** Once the window is valid, move the `left` pointer to shrink the window as much as possible while keeping it valid. This is how you find the *shortest* window. Update the minimum length during this contraction phase.