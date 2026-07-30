# Live Coding Practice: Meeting Rooms II (LeetCode #253)

## Context
Focused on **Interval Management, Greedy Algorithms, and Priority Queues**. Determine the minimum number of conference rooms to host all meetings without overlap. **Confirmed as a real Metropolis interview question** (Glassdoor, Oct 2025).

## The Challenge
Implement `minMeetingRooms(int[][] intervals)` to calculate the minimum number of conference rooms needed.

## Technical Requirements

### Input Format
Array of interval objects `[[start, end], ...]`:
- Not guaranteed to be sorted
- Non-negative integer times
- `[start, end]` exclusive of `end`

### Algorithm Design
- **Core logic:** Peak number of simultaneous meetings = answer
- **Recommended approach:** Sort by start time + Min-Heap for end times

### Performance Strictness
- **Time:** `O(N log N)` — sort dominates, heap ops are `O(log N)`
- **Space:** `O(N)` — heap in worst case

### Output
Return `int` — minimum rooms needed

## Test Data Examples

| Intervals | Output | Explanation |
|---|---|---|
| `[[0,30],[5,10],[15,20]]` | `2` | [0,30] needs room. [5,10] needs second. [15,20] uses freed second room. |
| `[[7,10],[2,4]]` | `1` | No overlap |
| `[[1,5],[2,6],[3,7],[4,8]]` | `4` | All overlap at time 4 |
| `[[13,15],[1,13]]` | `1` | [1,13] ends exactly when [13,15] starts |
| `[]` | `0` | Empty input |

## Staff Level Focus Points

### 1. Two-Pointer Alternative
Sort starts and ends separately. Two pointers:
- `starts[s] < ends[e]` → new meeting starts before earliest ends → `rooms++`, advance `s`
- Otherwise → meeting ended → `rooms--`, advance `e`
- Track max rooms

### 2. Why Priority Queue Works
Min-heap stores **end times** of active meetings. Top = earliest ending. For each new meeting (in start order):
- Remove meetings that ended **before or at** the new start (`<=`)
- Add current meeting's end time
- Heap size = rooms in use
- Track maximum heap size

### 3. Edge Cases
- **Same start time:** Separate rooms needed
- **End == next start:** `[1,5]` and `[5,10]` share a room
- **Empty:** Return 0
- **Single meeting:** Return 1

### 4. Production Readiness
- Variable durations (15min vs 8h)? Algorithm holds.
- Real-time scheduling? Use BST or Segment Tree.
- Room assignments, not just count? Second heap of available room IDs.

## Java Solution — Heap Approach

```java
class Solution {
    public int minMeetingRooms(int[][] intervals) {

    }
}
```

## Java Solution — Two-Pointer (No Heap)

```java
class Solution {
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;
        
        int n = intervals.length;
        int[] starts = new int[n];
        int[] ends = new int[n];
        
        for (int i = 0; i < n; i++) {
            starts[i] = intervals[i][0];
            ends[i] = intervals[i][1];
        }
        
        Arrays.sort(starts);
        Arrays.sort(ends);
        
        int rooms = 0;
        int endIdx = 0;
        
        for (int start : starts) {
            if (start < ends[endIdx]) {
                rooms++;  // Need a new room
            } else {
                endIdx++; // Room freed up
            }
        }
        
        return rooms;
    }
}
```

## Pair Programming Script

**1. CLARIFY (30s):**
> *"Is the input always int[][]? Is 'end' exclusive? Can I sort the array? What's the expected input size?"*

**2. OUTLINE (1min):**
> *"I'll sort by start time, then use a min-heap for end times. For each meeting, pop ended meetings from the heap, push the current end time, and track the max heap size."*

**3. TRACE on `[[0,30],[5,10],[15,20]]`:**
- Sort: `[0,30]` → heap `[30]`, max=1
- `[5,10]`: `30 > 5` → no pop → heap `[10, 30]`, max=2
- `[15,20]`: `10 <= 15` → pop 10 → heap `[20, 30]`, max=2
- **Output: 2** ✅

**4. EDGE CASES to proactively mention:**
- Empty → return 0
- Use `<=` (not `<`) because if end == next start, room is free
- If all overlap, heap size == N

**5. FOLLOW-UP you can already answer:**
> *"To track which meeting goes to which room, I'd use a second min-heap of available room IDs. When a room frees up, its ID goes back. When a new meeting needs a room, I assign the next available ID."*

---

The document with this complete content is available in the chat history above 👆. Practice the flow **out loud 2-3 times** — it'll feel natural by the interview. Want me to create a similar practice guide for **Last Stone Weight** (LeetCode #1046), the other confirmed Metropolis question?