# Java Maps | Concept Review

*Part of a structured interview-prep program to build deep, verbalizable DS&A knowledge for live-coding interviews.*

## Table of Contents
1. What Was Covered
2. The Learning Structure
3. Key Concepts
4. Analogies & Mental Models
5. Interview Questions to Expect

---

## 1. What Was Covered

A **5-level mastery ladder** for Java's `Map` family, progressing from the high-level concept (why HashMap is O(1)) down to internal mechanics (hashing, collisions, load factor, resizing, amortized O(1)) and the surrounding ecosystem (`HashSet`, `LinkedHashMap`, `TreeMap`, `Hashtable`, `ConcurrentHashMap`). Each level ended with a Feynman-style exit test.

## 2. The Learning Structure

| Level | Focus | Core Question | Exit Test |
|---|---|---|---|
| 1 | Foundations | What is a HashMap and why is it fast? | Explain why `get` is O(1) without scanning |
| 2 | Hashing & Buckets | How do hashCode/equals handle collisions? | Walk through `get("cat")` step by step |
| 3 | Growth & Performance | Why is it still O(1) after resizing? | Verbalize the amortized argument |
| 4 | Map Ecosystem | Which Map do you pick and why? | Choose correct Map for 4 scenarios |
| 5 | Mastery | How is HashSet related to HashMap? | Correct the "no collisions" misconception |

## 3. Key Concepts

### Level 1 — Foundations
- **Definition:** A key→value store that finds values in ~O(1) by using the key's `hashCode()` to jump directly to a bucket.
- **How it works:** `put(key, value)` computes the hash → maps to a bucket index → stores. `get(key)` repeats the hash → same bucket → retrieves. No scanning.
- **The contract/rule:** Keys need consistent `hashCode()` and correct `equals()`. Primitives can't be keys — wrappers via autoboxing.
- **Tradeoffs:** O(1) average lookups vs. unordered iteration and O(n) worst-case collisions.
- **Failure modes:** Broken `hashCode`/`equals` (keys unreachable); mutable keys (entries orphaned).
- **Complexity:** O(1) average get/put; O(n) worst case.

### Level 2 — Hashing & Buckets
- **Definition:** A bucket is a slot in the internal array; collisions occur when different keys hash to the same bucket.
- **How it works:** `get("cat")` → hash → bucket → if multiple entries, `equals()` disambiguates → return value; else null.
- **The contract/rule:** `equals()` → true **implies** same `hashCode()` (mandatory). Same hash does **NOT** imply equal (collision, legal).
- **Tradeoffs:** More buckets = fewer collisions but more memory.
- **Failure modes:** Equal keys with different hashes → phantom entries; all keys in one bucket → O(n) degradation.
- **Complexity:** O(1) average; O(n) when collisions degrade.

### Level 3 — Growth & Performance
- **Definition:** The map grows capacity and rehashes when the load factor is exceeded.
- **How it works:** Load factor 0.75 → resize when size > capacity × 0.75. Resize **doubles** capacity and rehashes all entries.
- **The contract/rule:** Capacity must grow by a constant *factor* (doubling), not a constant *amount* — doubling keeps total work linear.
- **Tradeoffs:** Lower load factor = fewer collisions but more memory; higher = denser buckets.
- **Failure modes:** Additive growth would make total work O(n²). Mutable keys during resize → orphaned entries.
- **Complexity:** Amortized O(1) — geometric series $$16 + 32 + 64 + \dots + n \approx 2n$$. Treeification: >8 entries per bucket → red-black tree, worst case O(log n).

### Level 4 — Map Ecosystem
- **Definition:** The family of `Map` implementations, interchangeable at the API level, differing in ordering, thread-safety, performance.
- **How it works:** `HashMap` (fastest, unordered), `LinkedHashMap` (insertion/access order — LRU pattern), `TreeMap` (sorted, red-black tree), `Hashtable` (legacy, avoid), `ConcurrentHashMap` (lock striping, high concurrency).
- **The contract/rule:** All implement `Map<K,V>`; `HashSet` is a `HashMap` with a shared dummy value — same O(1), uniqueness on the key.
- **Tradeoffs:** O(1) unordered vs O(log n) sorted; thread-safety vs performance.
- **Failure modes:** Modifying while iterating → `ConcurrentModificationException` (fail-fast).
- **Complexity:** HashMap/LinkedHashMap O(1); TreeMap O(log n); ConcurrentHashMap ~O(1).

### Level 5 — Mastery
- **Definition:** The `HashSet` ↔ `HashMap` relationship and interview-grade verbalization.
- **How it works:** `HashSet<E>` is a `HashMap<E, Object>` with a shared dummy value; the element is the key.
- **The contract/rule:** Uniqueness is on the **key** (element); collisions are about *keys*, not values — HashSet handles them like HashMap.
- **Tradeoffs:** Set = collection semantics (no duplicates, no order, no index) vs Map = key-value semantics.
- **Failure modes:** Treating a Set as a "list" (ordering/index access) is a conceptual trap.
- **Complexity:** Same as HashMap — O(1) amortized.

## 4. Analogies & Mental Models

| Concept | Analogy |
|---|---|
| hashCode vs equals | Library: hashCode = which shelf; equals = which book |
| Amortized O(1) | Moving house: rare expensive moves absorbed by many cheap operations |
| LRU cache | Coffee table that fits 3 magazines; evict the one untouched longest |
| Collision | Two books sharing the same shelf; equals finds the right one |
| Resize doubling | Starting at 16 and doubling to 1,048,576 is 16 doublings, not 65,535 steps |

## 5. Interview Questions to Expect

| Question | What They're Testing | How to Answer in One Line |
|---|---|---|
| "Why is HashMap get/put O(1)?" | Understanding of amortized complexity, not recall | "Doubling makes resizes rare; total rehash work is ~2n, so the average per op is constant." |
| "How does HashMap handle collisions?" | Knowledge of chaining + hashCode/equals | "Multiple entries per bucket, disambiguated by equals(); Java 8+ converts long buckets to red-black trees." |
| "What's the load factor, and why 0.75?" | Tradeoff reasoning (memory vs. collision length) | "It balances memory usage against bucket length; 0.75 keeps collisions low without wasting space." |
| "What if you mutate a key after inserting it?" | Edge-case awareness | "The hash changes, so the entry is orphaned in the wrong bucket and becomes unreachable." |
| "Which Map do you pick and why?" | Ecosystem knowledge and judgment | "HashMap for speed, LinkedHashMap for order/LRU, TreeMap for sorted, ConcurrentHashMap for concurrency." |
| "How is HashSet implemented?" | Understanding the HashMap backing | "It's a HashMap with a shared dummy value; uniqueness is on the key." |
| "Why can't primitives be HashMap keys?" | Autoboxing / wrapper knowledge | "Primitives have no methods, so hashCode()/equals() can't be called; wrappers are used instead." |
| "What happens during a resize?" | Internals + amortized reasoning | "Capacity doubles and all entries are rehashed — O(n) per event, but rare enough to keep the average O(1)." |

