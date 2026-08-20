# Two Sum | Concept Review

*Part of a structured interview-prep program to build deep, verbalizable DS&A knowledge for live-coding interviews.*

## Table of Contents
1. What Was Covered
2. The Learning Structure
3. Key Concepts
4. Analogies & Mental Models
5. Interview Questions to Expect

---

## 1. What Was Covered

A **live-coding** session on the classic **Two Sum** problem, applying the HashMap pattern and exploring the **time/space tradeoff** between the HashMap approach and the sort + two-pointer approach. Covered the one-pass complement-matching pattern, edge cases (duplicates, negatives, zero target), and the two-pointer technique decision framework.

## 2. The Learning Structure

| Level | Focus | Core Question | Exit Test |
|---|---|---|---|
| 1 | HashMap approach | How do you apply HashMap to find a pair? | Write one-pass O(n) solution |
| 2 | Two-pointer approach | How do you get O(1) space? | Sort + opposite-direction two-pointer |
| 3 | Tradeoff reasoning | When do you prefer each? | Verbalize time/space tradeoff |
| 4 | Two-pointer technique | Does it always need sorted data? | Distinguish opposite-direction vs sliding window |

## 3. Key Concepts

### Level 1 — HashMap Approach
- **Definition:** Given `nums` and `target`, return indices of the two numbers that sum to `target`. Exactly one solution guaranteed.
- **How it works (one-pass):** For each `nums[i]`, compute `complement = target - nums[i]`; check if `complement` is in a `HashMap<value, index>` *before* inserting the current element; if found, return `[map.get(complement), i]`; else store `nums[i] → i`.
- **The contract/rule:** Check the complement *before* inserting the current element — prevents using the same element twice (handles `[3, 3]`, target 6 correctly).
- **Tradeoffs:** O(n) time / O(n) space, no sorting needed.
- **Failure modes:** Reusing the same element (fixed by one-pass order); two-pass with overwrite can lose needed indices on duplicates.
- **Complexity:** O(n) time / O(n) space.

### Level 2 — Sort + Two-Pointer Approach
- **Definition:** The O(1)-space alternative: sort the array, then use opposite-direction two pointers.
- **How it works:** Sort ascending; `left=0`, `right=n-1`. If `sum < target`, move `left` right; if `sum > target`, move `right` left; if equal, return.
- **The contract/rule:** Requires **sorted** data — pointer movement relies on sum monotonicity (moving `right` left always decreases the sum).
- **Tradeoffs:** O(1) space but O(n log n) time from the sort. If input is already sorted, the sort is free → O(n) time + O(1) space.
- **Failure modes:** Applying opposite-direction two-pointer to unsorted data gives no reliable information.
- **Complexity:** O(n log n) time / O(1) space.

### Level 3 — Tradeoff Reasoning
- **Definition:** The two approaches sit on opposite sides of the time/space tradeoff curve.
- **How it works:** HashMap trades memory for speed; sort + two-pointer trades time for memory.
- **The contract/rule:** The right choice depends on which constraint actually bites (memory vs. time).
- **Tradeoffs:** HashMap's O(n) space is not "one array" — per entry: a `Node` + boxed `Integer` key + boxed `Integer` value + backing array (~1.33×) + resize spikes. On millions of items, that's real heap pressure.
- **Failure modes:** Choosing HashMap when memory is the hard constraint; choosing sort when the input is already sorted (wasting the free sort).
- **Complexity:** HashMap O(n)/O(n); sort + two-pointer O(n log n)/O(1).

### Level 4 — Two-Pointer Technique
- **Definition:** A family of techniques using two indices to traverse a structure.
- **How it works:** Opposite-direction pointers (sum comparison) require sorted data. Same-direction (fast/slow) and sliding window work on unsorted data.
- **The contract/rule:** Only the opposite-direction sum-comparison variant needs sorting; sliding window and fast/slow rely on contiguity/structure, not ordering.
- **Tradeoffs:** O(1) space vs. sort cost; sliding window gives O(n) on unsorted contiguous problems.
- **Failure modes:** Confusing the variants — assuming all two-pointer needs sorted data.
- **Complexity:** O(n) traversal after O(n log n) sort (opposite-direction); O(n) for sliding window.

## 4. Analogies & Mental Models

| Concept | Analogy |
|---|---|
| Complement matching | For each value, ask "is my missing half already here?" — one lookup per element |
| One-pass before insert | Check the complement before adding yourself, so you never match yourself |
| HashMap memory cost | O(n) space is not "one array" — it's several objects per element |
| Two-pointer needs sorted | Opposite-direction pointers only work when the data is ordered — the sum is monotonic |
| Time/space tradeoff | Fast but memory-hungry vs. memory-light but slower — pick by the constraint that bites |

## 5. Interview Questions to Expect

| Question | What They're Testing | How to Answer in One Line |
|---|---|---|
| "Two Sum — what's your approach?" | Pattern recognition + HashMap application | "Store value→index as I scan; check complement before inserting — O(n) time, O(n) space." |
| "Two Sum — can you do O(1) space?" | Time/space tradeoff reasoning | "Sort the array, then use opposite-direction two-pointer — O(n log n) time, O(1) space." |
| "Why check the complement before inserting?" | Edge-case awareness (same element twice) | "So I never match an element with itself — handles `[3, 3]`, target 6 correctly." |
| "Does two-pointer always need sorted data?" | Technique understanding | "Only the opposite-direction sum-comparison variant; sliding window and fast/slow work on unsorted data." |
| "When would you prefer sort + two-pointer over HashMap?" | Production/memory awareness | "When memory is the constraint or the input is already sorted — O(1) space at the cost of O(n log n) time." |
| "What's the memory cost of the HashMap approach?" | Production awareness | "Several objects per entry — Node + 2 boxed Integers + backing array — real heap pressure on millions of items." |
| "How does this extend to Three Sum / subarray-sum-K?" | Pattern transfer | "Fix one element and solve Two Sum on the rest; for subarray sum, use prefix sums + a map." |