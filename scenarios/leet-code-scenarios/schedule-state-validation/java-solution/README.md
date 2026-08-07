# Live Coding Practice: Schedule State Validation (Java)

## Context
At , real estate agents have daily schedules that are constantly optimized by the routing engine. A schedule is represented as a string where:
*   `L` represents a visit in the **East Zone** (Leste). Due to traffic rules, this visit can only be moved to an **earlier** time slot.
*   `R` represents a visit in the **West Zone** (Oeste). This visit can only be moved to a **later** time slot.
*   `_` represents a **free** time slot.

The backend optimization service suggests a new schedule (`target`). You need to write an algorithm to validate if it is physically possible for the agent to transition from their current schedule (`start`) to the suggested schedule (`target`) by shifting visits into adjacent free slots, strictly following the directional rules.

## The Challenge
Implement the `ScheduleValidator` class. Your goal is to determine if the `start` string can be transformed into the `target` string.

## Technical Requirements

### 1. Movement Rules
*   An `L` piece can only move to the **left** (replacing an adjacent `_`).
*   An `R` piece can only move to the **right** (replacing an adjacent `_`).
*   Pieces **cannot cross each other**. The relative order of `L` and `R` pieces must remain identical in both strings.

### 2. Performance Strictness
*   **Time Complexity:** Must be `O(N)`, where `N` is the length of the string.
*   **Space Complexity:** Must be `O(1)`. 
*   **Constraint:** You **cannot** use `String.replace()`, `String.toCharArray()`, or create new strings. In a web platform processing millions of schedule validations per minute, allocating new strings for every state check will cause a massive Memory Leak and crash the JVM.

### 3. Output
The method `canTransform(String start, String target)` should return a `boolean`.

---

## Test Data Examples

| `start` | `target` | Expected Output | Explanation |
| :--- | :--- | :--- | :--- |
| `"_L__R__R_"` | `"L______RR"` | `true` | `L` moves left. First `R` moves right. Second `R` stays in place. |
| `"R_L_"` | `"__LR"` | `false` | `R` needs to move right and `L` needs to move left, but they cannot cross each other. |
| `"_R"` | `"R_"` | `false` | `R` can only move to the right, so it cannot go back to index 0. |
| `"_L__R"` | `"_L__R"` | `true` | The schedules are already identical. |

---

## Staff Level Focus Points

### 1. Avoiding Brute Force (State Simulation)
A junior approach might try to simulate every possible move by creating new strings (e.g., swapping characters) and using Breadth-First Search (BFS) to see if the target is reachable. This results in `O(N^2)` or worse time complexity and catastrophic memory allocation. A Staff Engineer recognizes that this is a **mathematical index validation** problem, not a simulation problem.

### 2. The Two Pointers Motivation
This problem is a perfect candidate for the **Two Pointers (Parallel Traversal)** archetype. 
Instead of moving pieces, we place one pointer (`p1`) on the `start` string and another (`p2`) on the `target` string. We skip all the `_` characters and only compare the actual visits (`L` and `R`).

### 3. The Mathematical Logic (Trade-offs)
By using Two Pointers, we achieve `O(1)` space complexity. The core logic relies on three strict conditions:
1.  **Relative Order:** If `start.charAt(p1) != target.charAt(p2)`, return `false` (pieces crossed each other).
2.  **L Rule:** If the piece is `L`, its index in `start` must be `>=` its index in `target` (it can only move left). If `p1 < p2`, return `false`.
3.  **R Rule:** If the piece is `R`, its index in `start` must be `<=` its index in `target` (it can only move right). If `p1 > p2`, return `false`.