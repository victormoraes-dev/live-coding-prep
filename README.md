# The Anti-Freeze Framework

## Part 1 — The 60-Minute Interview Map

A FAANG-style loop runs on a fixed clock. If you know where you should be at each minute, you never panic about "am I on track?"

**Minutes 0–5:** Introductions. Have a 30-second pitch ready: who you are, what you've built, why Oscilar.

**Minutes 5–10:** Problem presented. You are in **requirements gathering**. You do NOT touch the keyboard yet.

**Minutes 10–25:** Pattern identification + verbalized approach + trade-off discussion. This is where the interview is won or lost.

**Minutes 25–45:** Write the code in Java, narrating as you go.

**Minutes 45–55:** Trace test cases, hit edge cases, state final complexity.

**Minutes 55–60:** Your questions for the interviewer (have 2–3 prepared: "How does your team run risk decisioning in production?", "What does a typical day look like?", "How does the team balance latency vs. model accuracy?").

---

## Part 2 — Phase 0: Requirements Gathering (the 5-minute opening)

Before any algorithm thinking, confirm these out loud. Interviewers literally grade this — the Oscilar email says "gather requirements and discuss tradeoffs."

1. **Input size and value range?** (This is the most important question you will ever ask in a coding interview — it dictates everything that follows.)
2. Can the input be empty, null, or contain duplicates?
3. What are the edge expectations (e.g., `k == n`, all elements identical)?
4. May I mutate the input? How much extra memory is acceptable?
5. Return format — does ordering of the output matter?

Say it like this: *"Before I think about an approach — what's the maximum input size, and can the input be empty or have duplicates? That tells me what complexity I can get away with."*

---

## Part 3 — Phase 1: Constraint → Complexity Reading

The constraints tell you the answer before you think about algorithms.

**If `n` ≤ 10:**
Target: $$O(n!)$$ or $$O(2^n)$$
Allowed: backtracking, brute force, permutations

**If `n` ≤ 1,000:**
Target: $$O(n^2)$$
Allowed: nested loops

**If `n` ≤ 10^5:**
Target: $$O(n \log n)$$ or $$O(n)$$
Allowed: sorting, hashmap, two-pointer, heap

**If `n` ≤ 10^6 or larger:**
Target: $$O(n)$$ or $$O(\log n)$$
Allowed: single pass, binary search, math

The verbalization that impresses interviewers: *"n is 10^5, so I need O(n log n) or better. An O(n²) brute force is out — that already tells me I'm looking at a hashmap, two-pointer, or heap."*

---

## Part 4 — Phase 2: Signal → Data Structure Library

Memorize these mappings. When you read a problem and one of these phrases appears, the data structure is already chosen for you.

**"Find if exists / count occurrences / most frequent"** → HashMap, HashSet
**"Top K / kth largest / kth closest"** → Heap (priority queue)
**"Longest substring / subarray with a condition"** → Sliding window (+ HashMap)
**"Ordered output / closest pairs"** → Sort + two-pointer
**"Connected components / paths / reachability"** → Graph: BFS or DFS
**"Shortest path / minimum moves / levels"** → BFS
**"Parent-child / hierarchy / ancestors"** → Tree traversal (DFS or recursion)
**"All combinations / permutations / subsets"** → Backtracking
**"Maximize / minimize / overlapping subproblems"** → Dynamic programming
**"Sorted input"** → Binary search or two-pointer

---

## Part 5 — Phase 3: The 3-Sentence Defense Template

This is the verbalization that separates candidates who pass from candidates who are "close." You deliver it right after requirements gathering, before writing code.

> **Sentence 1 — The constraint verdict:** "The constraints require at most O(n log n), so a brute force at O(n²) is off the table."
>
> **Sentence 2 — The signal:** "The problem asks for the k most frequent elements, which is the classic signal for a HashMap plus a Heap — the map counts, the heap keeps the top k."
>
> **Sentence 3 — The trade-off:** "The heap approach gives O(n log k) time with O(n) space for the map. The trade-off is that I could get O(n) with bucket sort, but that costs extra memory proportional to n — and the heap version is simpler to defend and plenty fast for 10^5 inputs."

Three sentences. That's the whole defense. The interviewer wants the *chain*: constraints → signal → structure → trade-off. Candidates who just blurt the conclusion ("I'll use a heap") lose points even when right.

---

## Part 6 — Phase 4: Brute Force First

Counter-intuitive but critical: **state the brute force out loud before the optimal solution.**

- It proves you can get a working solution (interviewers grade correctness first).
- It gives you a fallback if time runs out.
- It makes your optimization look like engineering judgment, not pattern-matching.

Template: *"The brute force is to count all frequencies, then scan the map k times to pull the max each time — that's O(n·k). The bottleneck is the repeated scan. What if I keep the top k in a structure that maintains order for me? That's a heap — O(n log k)."*

---

## Part 7 — Phase 5: Code with Narration

Narrate while typing — short sentences, not silence:

- *"I'll build the frequency map first — one pass, O(n)."*
- *"Now a min-heap of size k, ordered by frequency."*
- *"For each entry, if the heap is smaller than k, add it; otherwise compare with the root and evict if needed."*

Java-specific habits that interviewers notice: explicit types (`Map<Integer, Integer> freq = new HashMap<>()`), a `PriorityQueue` with a `Comparator.comparingInt(e -> e.getValue())`, meaningful variable names, and small, readable methods.

---

## Part 8 — Phase 6: Edge Cases & Verification

Before you say "done," run these mentally:

- Empty input or `k = 0` → what does the code do?
- `k = n` → you should return everything.
- All elements identical → heap behavior.
- Negative numbers and large values → your map keys handle `[-10^9, 10^9]`?
- Duplicates → counted correctly?

Then trace a concrete example line by line, saying each step aloud: *"First element 1 goes in the map with count 1... second 1 makes it 2..."* — the interviewer follows your reasoning in real time.

---

## Part 9 — Phase 7: Complexity Close-Out

End with the final statement:

*"Time: O(n log k) — one pass to build the map, and each heap operation is O(log k). Space: O(n) for the map, plus O(k) for the heap. If k is much smaller than n, this is essentially linear."*

Then add one optional thought that shows depth: *"If the data were streaming, this exact approach still works since the heap never exceeds size k. If k were 1, I'd skip the heap and do a single-pass max."*

---

## Part 10 — Time Management Cheat Sheet

**First 10 minutes:** requirements + constraint reading + pattern identification. If you haven't named a data structure by minute 10, you're spending too long.

**Minutes 10–25:** verbalize the approach + trade-offs. Get interviewer agreement before coding.

**Minutes 25–45:** write the code. If you hit a wall, use the Anti-Freeze Protocol: breathe → name structures you know → solve a tiny version (3 elements) → say a partial thought out loud.

**Minutes 45–55:** test + edge cases + complexity.

**Minutes 55–60:** questions for the interviewer.

---

That's the full framework. Read it once, then — when you're ready — we start the drill with the same problem from before. It's the perfect first case because it exercises *every* phase at once: constraints (10^5), signal ("most frequent" → HashMap, "top k" → Heap), trade-offs (heap vs. bucket sort at O(n)), and the 3-sentence defense.

Take your time studying. When you're ready to practice, just say so and I'll put you on the clock.