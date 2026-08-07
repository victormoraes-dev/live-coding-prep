# Technical Interview Preparation Plan

> Senior/Staff Software Engineer • Live coding in Java • Full-time prep (unemployed)
> Last updated: August 6, 2026

---

## 1. Context & Diagnosis

- **Target level:** Senior and Staff.
- **Language:** Java.
- **Prep mode:** Full-time (mornings dedicated to job hunting, afternoons to prep).
---

## 2. Company Interview Landscape (3 Archetypes)

### Archetype A — Classic LeetCode Live Coding (medium-heavy)
| Company | Process |
|---|---|
| **QuintoAndar** | Live coding assessing reasoning, code structure, algorithm knowledge, communication. Problem set: 7 Easy / 18 Medium / 3 Hard (Hash Table, String, Stack, Array, Tree/DFS). Confirmed questions: *Daily Temperatures* (monotonic stack), *Same Tree* (tree DFS). Senior adds **Modeling & SQL round** (1h live case study) + system design. |
| **Truelogic** | Screening assessment → HackerRank (basic programming + simple LeetCode) → technical interview, often with end client. |
| **Micro1** | Proprietary **AI interviewer** (audio transcribed live) → coding optimization round → human technical with client. Verbalized reasoning is scored. |
| **Taller Technologies** | HR screen → scripted technical → **client's own technical interview** (reported difficult). |

### Archetype B — Practical / Project-Based (DS&A secondary)
| Company | Process |
|---|---|
| **DuckDuckGo** | **No live coding.** Six stages with **two paid asynchronous test projects** (three for senior) + ~3-page writeup of a challenging project. No brain teasers. |
| **Hunter Douglas** | HR → technical evaluation → final. Practical fundamentals: "how would you optimize a slow-running application", code reading, performance reasoning. |

---

## 3. Prioritized DS&A Coverage

| Tier | Topic | Recognize it when… | Java patterns to drill |
|---|---|---|---|
| 1 | Hash Maps / Sets | "find", "count", "exists", duplicates | HashMap/HashSet, `equals`/`hashCode` contract |
| 1 | Arrays & Strings + Two Pointers / Sliding Window | subarray, substring, sorted pair, window condition | left/right pointers, window shrink/expand, StringBuilder |
| 1 | Stacks & Queues (incl. monotonic stack) | "next greater", parentheses, Daily Temperatures | ArrayDeque, monotonic stack idiom |
| 1 | Linked Lists | reverse, middle, cycle, merge | iterative reversal, fast/slow pointer, dummy nodes |
| 1 | Trees — BST, DFS/BFS traversal | hierarchy, ancestor, depth, Same Tree | recursive traversal, queue-based BFS |
| 2 | Sorting + Binary Search | sorted input, "search in", rotated arrays | Arrays.sort, binary search invariants |
| 2 | Heaps / PriorityQueue | top K, k smallest/largest, median | PriorityQueue with custom Comparator |
| 2 | Recursion / Backtracking | all combinations, permutations, subsets | choose/explore/undo recursion |
| 2 | Graphs — BFS/DFS, components, topological sort | connected, dependencies, shortest path | adjacency list, visited set, Kahn's algorithm |
| 3 | Dynamic Programming (1D, LCS, knapsack) | "maximize/minimize", overlapping subproblems | memoization → bottom-up table |
| 3 | Intervals, Prefix Sum, Union-Find | merge intervals, range queries, connectivity | sort by start, running totals, path compression |
| ⚡ | NON-DS&A: SQL + Data Modeling | QuintoAndar senior round | JOINs, GROUP BY, window functions |

---

## 4. The Protocol (Pre-Code Ritual)

For EVERY problem, before writing any code:

1. **Clarify** — restate the problem; ask: input size? negatives? duplicates? empty input? overflow?
2. **Approach** — state brute force first, then optimal. **Name the pattern out loud** ("This is a sliding window because…").
3. **Complexity** — time and space BEFORE coding.
4. **Code** — narrate while writing; handle edge cases explicitly.

---

## 5. The Decision Framework (Pattern Recognition)

**Constraints hint at the answer:**
- n ≤ 10 → brute force / backtracking
- n ≤ 1000 → O(n²) acceptable
- n ≤ 10⁵ → O(n log n) or O(n)
- n ≤ 10⁶+ → O(n) or O(log n) only

**Problem signals → Data structure:**
- "find if exists / count" → HashSet/HashMap
- "ordered, sorted, top K" → Heap, sorted array
- "parent/child, hierarchy" → Tree
- "connected components, paths, dependencies" → Graph
- "shortest path, levels" → BFS
- "subarray/substring with condition" → Sliding window / two-pointer
- "optimal, maximize/minimize, overlapping subproblems" → DP
- "all combinations/permutations" → Backtracking

---

## 6. The Timeline (Accelerated 5-Week Full-Time)

| Week | Focus | Internals (weakness) | Problem volume | Company payoff |
|---|---|---|---|---|
| 1 | Foundations + Arrays/Strings/HashMaps + Linked Lists/Stacks/Queues | Big-O & amortized; HashMap get/put, equals/hashCode, load factor, treeification; pointer rewiring for middle insert | ~36 problems | QuintoAndar (Daily Temperatures, hash/string heavy), Truelogic HackerRank |
| 2 | Trees/Recursion/Backtracking + Sorting/Binary Search/Heaps | Recursion = stack frames; TreeMap/TreeSet red-black tree; binary search invariants; PriorityQueue = heap in array | ~36 problems | QuintoAndar (Same Tree), Micro1 optimization, Taller clients |
| 3 | Graphs/Union-Find/Intervals + Dynamic Programming | Adjacency list vs matrix; BFS/DFS visited states; Kahn's algorithm; union-find path compression; DP state + transition | ~33 problems | QuintoAndar hard tier, Micro1, Taller |
| 4 | Senior Ammunition: SQL + System Design + Behavioral Depth + first mocks | Window functions; CAP, caching, load balancing, back-of-envelope; 5–7 STAR stories with trade-offs | 2 full mocks + SQL drills + 2 system design walkthroughs | QuintoAndar Modeling & SQL; Taller client system design; Metropolis feedback fix |
| 5 | Company-Specific Finale + full mocks | DuckDuckGo: portfolio + 2–3 page project writeup (no live coding); Hunter Douglas: optimization discussions, code reading | 3+ full timed mocks graded on 6 axes | All 13 companies, format-matched |

---

## 7. Daily Template (Job Hunt AM + Prep PM)

| Block | Time | What I do |
|---|---|---|
| 🎯 Morning | 08:30–12:00 | **Opportunities:** applications, recruiter follow-ups, portfolio touches. Note DS&A signals in job descriptions → feed into afternoon prep. |
| 🧠 Core 1 | 13:30–15:00 | **Internals + Feynman** (weakness): one structure under the hood, explained out loud as if teaching a junior. |
| 💻 Core 2 | 15:00–16:30 | **2 timed problems (25 min each)** under the Protocol. |
| 🔄 Review | 16:45–17:45 | **1–2 more drills + cheat sheet update.** Re-explain hardest problem cold, no notes. |
| 🧪 Optional | 17:45–18:15 | Verbalization reps — one solved problem re-explained as if to an interviewer. |

---

## 8. The HashMap Internal Model (Week 1 Foundation)

- **Layer 1 — Bucket array:** `put(key, value)` calls `key.hashCode()` → int, compressed to index = `hash & (capacity - 1)`.
- **Layer 2 — Collision chain:** collisions → each bucket is a linked list (or red-black tree after 8+ collisions). "Bucket-and-chain."
- **Layer 3 — equals contract:** `hashCode()` decides **which bucket**; `equals()` decides **which node inside the bucket**. `get(key)`: compute bucket via hashCode, walk chain calling `key.equals(candidate)`. Contract: if two objects are `equals()`, they MUST have the same `hashCode()`. Overriding `equals` without `hashCode` breaks `get()` — classic interview trap.
- **Layer 4 — Degradation:** all keys in one bucket → O(n). Amortized O(1) holds with a good hash + load factor 0.75 triggering resize (resize is O(n) but rare).

## 9. Linked List Middle-Insert Reasoning (Week 1 Foundation)

- Inserting a node in the middle is **O(1)** IF you hold a reference to the predecessor (just rewire pointers, no shifting) — but **O(n) to FIND** that position.
- **Fast/slow pointer** finds the middle in one pass; **dummy head** simplifies head-insert edge cases.
- **Singly vs doubly:** doubly gives O(1) deletion with a node reference but doubles pointer memory — discuss this trade-off to show senior depth.

---

## 10. Problem Sets by Week

**Week 1 — Arrays/Hash:** Two Sum, Contains Duplicate, Valid Anagram, Group Anagrams, Longest Substring Without Repeating Characters, Minimum Window Substring, 3Sum, Subarray Sum Equals K, Product of Array Except Self, Container With Most Water + easy warmups.

**Week 1 — Linked/Stack:** Reverse Linked List, Middle of Linked List, Linked List Cycle, Merge Two Sorted Lists, Remove Nth Node From End, insert into the middle, Daily Temperatures, Next Greater Element, Valid Parentheses, Min Stack, Remove All Adjacent Duplicates, Sliding Window Maximum.

**Week 2 — Tree/Backtracking:** Same Tree, Maximum Depth, Invert Tree, Validate BST, Binary Tree Level Order Traversal, Lowest Common Ancestor, Kth Smallest in BST, Subsets, Permutations, Combination Sum, Word Search.

**Week 2 — Sort/Search/Heap:** Binary Search, First and Last Position, Search in Rotated Sorted Array, Kth Largest Element, Top K Frequent Elements, Find Median from Data Stream, K Closest Points to Origin, Sort Colors.

**Week 3 — Graphs:** Number of Islands, Clone Graph, Course Schedule I & II, Rotting Oranges, Alien Dictionary, Accounts Merge, Merge Intervals, Insert Interval, Meeting Rooms II.

**Week 3 — DP:** Climbing Stairs, House Robber, Coin Change, Longest Increasing Subsequence, Longest Common Subsequence, Word Break, Partition Equal Subset Sum, Edit Distance, 0/1 Knapsack.

---

## 11. Senior Polish (Week 4)

- **SQL + data modeling:** JOINs, GROUP BY/HAVING, window functions (ROW_NUMBER, RANK, LAG). QuintoAndar senior has a dedicated 1-hour Modeling & SQL round.
- **System design fundamentals:** load balancing, caching, databases, queues, CAP, back-of-envelope estimation — for Taller clients and senior/staff rounds.
- **Behavioral depth:** 5–7 STAR stories articulating trade-offs, architecture decisions, context, impact, lessons learned.
- **Mocks:** 2 full mocks per week from here on.

## 12. Company-Specific Finale (Week 5)

- **3+ full timed mocks** graded on 6 axes: problem identification, communication, correctness, efficiency, edge cases, calmness.
- **DuckDuckGo:** no live coding — two paid test projects; prepare strongest portfolio piece + 2–3 page writeup of a challenging project.
- **Hunter Douglas:** practice "how would you optimize a slow application" discussions and code reading.

---

## 13. First Action (Week 1, Day 1)

1. HashMap `equals`/`hashCode` contract drill — explain out loud, under the Protocol.
2. Linked-list middle-insert reasoning.
3. Then first timed Java problem.