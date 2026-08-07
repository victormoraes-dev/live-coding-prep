# Live Coding Practice: Last Stone Weight (LeetCode #1046)

## Context
Focus on **Heap Data Structures, Priority Queues, and Simulation Algorithms**. Simulate a process where stones are repeatedly smashed against each other until at most one remains. **Confirmed as a real Metropolis interview question** (Glassdoor, Oct 2025).

## The Challenge
Implement `int lastStoneWeight(int[] stones)` that simulates the smashing process.

## Technical Requirements

### Input Format
Array of integers `stones` where each value is a weight.
- `1 <= stones.length <= 30`
- `1 <= stones[i] <= 1000`

### The Smashing Rule
- Select the **two heaviest** stones: `x` (heavier or equal) and `y` (lighter or equal)
- If `x == y` → both are **destroyed**
- If `x != y` → heavier stone destroyed, lighter stone gets **new weight** `x - y` and goes back
- Repeat until **at most one stone remains**

### Performance
- **Time:** `O(N log N)` — heap polls/offers are `O(log N)`, at most N operations
- **Space:** `O(N)` — the heap

### Output
Return `int` — weight of last stone, or `0` if none remain.

## Test Data Examples

| Stones | Output | Simulation |
|---|---|---|
| `[2, 7, 4, 1, 8, 1]` | `1` | 8-7=1, 4-2=2, 2-1=1, 1-1=0 → [1] |
| `[1, 3]` | `2` | 3-1=2 → [2] |
| `[1, 1]` | `0` | Both destroyed → [] |
| `[5]` | `5` | Only one stone |
| `[10, 10, 10]` | `10` | 10-10=0, remaining [10] |

## Java Solution

```java
class Solution {
    public int lastStoneWeight(int[] stones) {

    }
}
```

## Pair Programming Script

**1. CLARIFY (30s):**
> *"Can I use Java's PriorityQueue? Should I handle empty input? Are weights always positive?"*

**2. OUTLINE (1m):**
> *"Max heap because we repeatedly need the two largest elements and the set changes dynamically. Add all stones, poll top two, compute diff, push back if non-zero, repeat."*

**3. TRACE on `[2, 7, 4, 1, 8, 1]`:**
```
Heap: [8, 7, 4, 2, 1, 1]
Poll 8 & 7 → diff=1 → heap: [4, 2, 1, 1, 1]
Poll 4 & 2 → diff=2 → heap: [2, 1, 1, 1]
Poll 2 & 1 → diff=1 → heap: [1, 1, 1]
Poll 1 & 1 → destroyed → heap: [1]
Return 1 ✅
```

**4. EDGE CASES to proactively mention:**
- Single stone → return its weight
- Two equal stones → both destroyed, return 0
- Empty heap at the end → return 0

**5. Why Max Heap (not sort each time):**
- Sort each iteration = `O(N² log N)`
- Max heap = `O(N log N)` — one-time build, fast re-insertion
