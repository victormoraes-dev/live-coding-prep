# 📘 Day 1: N-ary Trees — Coding Interview Prep

> **Role:** Staff Software Engineer
> **Platform:** Codility | **Duration:** 45 minutes
> **Language:** Java
> **Focus:** N-ary Trees

N-ary trees are ideal for modeling location hierarchies (Country → State → City → Neighborhood). A commonly reported interview problem is **"comparison of trees when child order doesn't matter"**.

---

## Table of Contents

1. [The 20-Hour Focus (80/20)](#1-the-20-hour-focus-8020)
2. [One-Page Cheat Sheet](#2-one-page-cheat-sheet)
3. [Baby Steps — Theory & Implementation](#3-baby-steps--theory--implementation)
4. [Progressive Test](#4-progressive-test)
5. [Practice Scenarios](#5-practice-scenarios)
6. [Feynman Technique](#6-feynman-technique)
7. [Resources](#7-resources)

---

## 1. The 20-Hour Focus (80/20)

**The 20% of content that delivers 80% of results:**

| Priority | Topic | Why |
|---|---|---|
| 🔴 Critical | TreeNode class (data, parent, children) | Foundation of ALL N-ary tree code |
| 🔴 Critical | DFS Pre-order traversal | Most commonly asked traversal |
| 🔴 Critical | **Comparing trees ignoring child order** | **THE most reported interview problem** |
| 🟡 High | getDepth / getHeight | Interviewers ask Big-O of these operations |
| 🟡 High | BFS (level-order) | Alternative traversal for proximity problems |
| 🟢 Medium | Tree visualization | Shows structured thinking during interview |
| ⚪ Skip | Binary search trees, AVL, Red-Black | Not N-ary — won't appear |

**10 sessions of 2 hours each — today is Session 1/10.**

---

## 2. One-Page Cheat Sheet

### TreeNode Class (Java)

```java
class TreeNode<T> {
    T data;                      // node value
    TreeNode<T> parent;          // null if root
    List<TreeNode<T>> children;  // ArrayList
}
```

### Essential Methods

| Method | Complexity | What it does |
|---|---|---|
| `addChild(data)` | O(1) amortized | Creates child, sets parent, adds to list |
| `isLeaf()` | O(1) | `children.isEmpty()` |
| `isRoot()` | O(1) | `parent == null` |
| `getDepth()` | O(h) | Count edges from this node up to root |
| `getHeight()` | O(n) | Count edges from this node down to deepest leaf |

> `h` = height of tree, `n` = total number of nodes

### Traversals — All O(n)

| Traversal | Order | Stack/Queue space |
|---|---|---|
| **DFS Pre-order** | Root → children | O(h) recursion stack |
| **DFS Post-order** | Children → root | O(h) recursion stack |
| **BFS (Level-order)** | Level by level | O(w) queue, w = max width |

### Tree Comparison (Order-Insensitive)

```
sameStructure(a, b):
  1. Different data values? → false
  2. Different child count? → false
  3. For each child in A:
       find an unmatched child in B with sameStructure
       if none found → false
       else mark that child in B as matched
  4. All children matched? → true

Time: O(n²) worst case
Space: O(h) recursion stack
```

---

## 3. Baby Steps — Theory & Implementation

### Step 1: What is an N-ary Tree?

An N-ary tree is a hierarchical structure where each node can have **any number of children** (0, 1, 5, 20 — no fixed limit).

**Real-world example:** A property's location is naturally hierarchical:

```
Brazil → SP → São Paulo → Pinheiros
```

Each level has a variable number of children — a country has N states, a state has N cities.

### Step 2: Key Terminology

| Term | Definition | Example |
|---|---|---|
| **Node** | Each element in the tree | `"Brazil"`, `"SP"`, `"Curitiba"` |
| **Root** | Top node, has no parent | `"Brazil"` |
| **Leaf** | Node with no children | `"Pinheiros"` |
| **Degree** | Number of direct children | `"SP"` with São Paulo + Campinas → degree 2 |
| **Depth** | Number of edges from root to this node (root = 0) | `"São Paulo"` depth = 2 |
| **Height** | Number of edges from this node down to its deepest leaf (leaf = 0) | `"Brazil"` in a 4-level tree → height 3 |

> Depth counts **upward** (toward root). Height counts **downward** (toward leaves).

### Step 3: The TreeNode Class

Just **3 fields**:

```java
class TreeNode<T> {
    T data;
    TreeNode<T> parent;
    List<TreeNode<T>> children;
}
```

**Why store `parent`?** Without it, you can only navigate downward. With it, operations like `getDepth()` and `getPath()` become trivial — just climb up the parent chain.

**Why `List`?** Because it's N-ary — the number of children is unknown at design time. `ArrayList` is the standard choice.

### Step 4: Essential Methods

```java
public TreeNode<T> addChild(T childData) {
    TreeNode<T> child = new TreeNode<>(childData);
    child.parent = this;
    children.add(child);
    return child;
}

public boolean isLeaf() { return children.isEmpty(); }
public boolean isRoot() { return parent == null; }

// Recursive: climbs up the parent chain
public int getDepth() {
    if (isRoot()) return 0;
    return 1 + parent.getDepth();
}

// Recursive: finds the longest path downward
public int getHeight() {
    if (isLeaf()) return 0;
    int max = 0;
    for (TreeNode<T> child : children)
        max = Math.max(max, child.getHeight());
    return 1 + max;
}
```

> **Note:** `getDepth()` and `getHeight()` work correctly when called from **any node**, not just the root. Each method only traverses in one direction (up or down) from wherever it's called.

> **Staff-level detail:** In production, use getters/setters for proper encapsulation. Direct field access is used here for readability.

### Step 5: DFS (Depth-First Search) Traversals

DFS goes **as deep as possible** down one branch before backtracking and exploring the next.

**Mental model:** Think of exploring a maze. You always take the first available path and keep going until you hit a dead end, then backtrack to the last junction and try the next path.

**Why recursion is a natural fit:** The call stack _is_ the backtracking mechanism. When a recursive call returns, you automatically go back to the previous junction — no extra bookkeeping needed.

```
        Brazil
       /   |   \
      PR   SP   RJ
     / \    \    \
  Curi Lon  SP  Niterói
```

DFS visits: `Brazil → PR → Curitiba → Londrina → SP → São Paulo → RJ → Niterói`

It finishes the entire PR branch before moving to SP, and SP before RJ.

---

**Two flavors of DFS:**

| | Pre-order | Post-order |
|---|---|---|
| When root is visited | Before children | After children |
| Use case | Print hierarchy, clone tree | Calculate height, delete tree |
| Code pattern | `visit → recurse` | `recurse → visit` |

---

**Pre-order** — `visit → recurse`:

```java
void dfsPreOrder(TreeNode<String> node) {
    // visit first
    System.out.println(node.data);       
    
    // then go deep
    for (TreeNode<String> child : node.children)
        dfsPreOrder(child);                    
}
```

```
Brazil → PR → Curitiba → Londrina → SP → São Paulo → Campinas → RJ → Rio → Niterói
```

**Post-order** — `recurse → visit`:

```java
void dfsPostOrder(TreeNode<String> node) {
    // go deep first
    for (TreeNode<String> child : node.children)
        dfsPostOrder(child);

    // visit last
    System.out.println(node.data);             
}
```

```
Curitiba → Londrina → PR → São Paulo → Campinas → SP → Rio → Niterói → RJ → Brazil
```

> Each `dfs(child)` call fully exhausts that entire subtree before the loop moves to the next child — that's exactly DFS behavior.

**When to use each:**
- **Pre-order:** display hierarchy, copy/clone a tree
- **Post-order:** delete nodes, calculate heights, aggregate values from leaves up

### Step 6: BFS (Breadth-First Search) Traversal

BFS visits **all nodes at the current level before going deeper**.

**Mental model:** Think of a water ripple. You drop a stone in the center (root) and the wave expands outward one ring at a time — it never goes deep until the entire current ring is done.

**Why a Queue instead of recursion:** BFS needs to remember all nodes at the current level before moving to the next. A Queue processes them in the order they were discovered (FIFO), which naturally produces level-by-level traversal.

```
        Brazil          ← level 0
       /   |   \
      PR   SP   RJ      ← level 1
     / \   |   / \
  Curi Lon SP Rio Nit   ← level 2
```

BFS visits: `Brazil → PR → SP → RJ → Curitiba → Londrina → São Paulo → Rio → Niterói`

It finishes the entire level 1 (PR, SP, RJ) before visiting any level 2 node.

```java
void bfs(TreeNode<String> root) {
    Queue<TreeNode<String>> queue = new LinkedList<>();
    queue.offer(root);                         // start with root

    while (!queue.isEmpty()) {
        TreeNode<String> current = queue.poll();  // take next in line
        System.out.println(current.data);         // visit it
        queue.addAll(current.children);           // enqueue its children for later
    }
}
```

> Each node's children are added to the **back** of the queue, so they are only visited after all nodes at the current level have been processed.

**BFS vs DFS — when to choose:**

| | BFS | DFS |
|---|---|---|
| Traversal | Level by level | Branch by branch |
| Data structure | Queue — O(w) | Recursion stack — O(h) |
| Use case | Shortest path, proximity search | Full traversal, tree comparison |
| Example | "Find nearest neighborhood" | "Compare two hierarchies" |

### Step 7: Tree Comparison — Ignoring Child Order

**The scenario:** Two data sources provide location hierarchies. Cities may appear in different orders. You need to check if they represent the **same structure**.

```
Source A:              Source B:
Brazil                 Brazil
  ├── PR                 ├── RJ
  │   ├── Londrina       │   ├── Niterói
  │   └── Curitiba       │   └── Rio
  ├── SP                 └── PR
  │   ├── São Paulo          ├── Curitiba
  │   └── Campinas           └── Londrina
  └── RJ
      ├── Rio
      └── Niterói
```

Same structure? **Yes** — only child order differs.

**The comparison algorithm:**

```java
boolean sameStructure(TreeNode<String> a, TreeNode<String> b) {
    if (!a.data.equals(b.data)) return false;
    if (a.children.size() != b.children.size()) return false;

    List<TreeNode<String>> unmatched = new ArrayList<>(b.children);

    for (TreeNode<String> childA : a.children) {
        boolean found = false;
        for (int i = 0; i < unmatched.size(); i++) {
            if (sameStructure(childA, unmatched.get(i))) {
                unmatched.remove(i);  // mark as used — prevents double-matching
                found = true;
                break;
            }
        }
        if (!found) return false;
    }
    return true;
}
```

> **Why track `unmatched`?** Without it, a single child in B could match multiple children in A, producing a false positive. Removing matched nodes ensures each child in B is only used once.

**Complexity analysis:**
- **Time:** O(n²) worst case — for each child in A, we scan remaining children in B recursively
- **Space:** O(h) — recursion stack depth equals tree height
- **Can we optimize?** Sorting children by a canonical key (e.g. `data`) before comparing brings it down to O(n log n). A full O(n) solution requires tree isomorphism algorithms (AHU), which is beyond typical interview scope.

---

## 4. Progressive Test

### Level 1 (Easy): What's the difference between depth and height?

**Depth:** number of edges from the root **down to the node** (root depth = 0).
**Height:** number of edges from the node **down to its deepest leaf** (leaf height = 0).

### Level 2 (Medium): What is the time complexity of `getDepth()` and why?

O(h) — it walks the parent chain up to the root. `h` is the tree height, which in the worst case (a linear chain) equals `n`, but is typically much smaller.

### Level 3 (Interview): Two trees have identical data but different child order. Does `sameStructure` return true or false?

**True.** The algorithm scans all children in B for each child in A regardless of position, and marks matched nodes as used to avoid false positives.

### Level 4 (Staff): `sameStructure` is O(n²). How would you optimize it?

Sort children by a canonical key (e.g. node data) before comparing → O(n log n). For a true O(n) solution, use the AHU (Aho, Hopcroft, Ullman) tree isomorphism algorithm, but that's rarely expected in interviews. Always present the clean recursive O(n²) solution first, then discuss trade-offs.

---

## 5. Practice Scenarios

Four scenarios based on real business cases are available in **`QuintoAndarLocationScenario.java`**:

| # | Scenario | Context | What It Tests |
|---|---|---|---|
| 1 | **Database Merge** | Data from 2 partner integrations | Comparison ignoring child order |
| 2 | **New Market Expansion** | Adding a new country/region hierarchy | Detection of different structures |
| 3 | **Corrupted Hierarchy** | API returning inconsistent data | Edge cases & error detection |
| 4 | **Neighborhoods** | 4-level depth hierarchy | Algorithm works at any depth |

**How to practice:**
1. Compile and run the file
2. Read each scenario's output
3. Modify tree structures and observe results
4. Break the code intentionally to discover edge cases
5. Explain the output using the Feynman Technique below

---

## 6. Feynman Technique

**Your task:** Explain in simple terms (as if to a non-technical person):

> *"What does `sameStructure` do, and why is it useful for a real estate platform?"*

After explaining, verify you covered:
- Why child order doesn't matter
- What happens when structures genuinely differ
- Why we track unmatched nodes to avoid false positives
- Where this is used in the real business (property data from multiple partners)

---

## 7. Resources

| Resource | Link | Why |
|---|---|---|
| **Demo Test Codility** | https://codility.com/demo/take-sample-test/ | Practice on the actual interview platform |
| **NeetCode — Tree playlist** | https://neetcode.io/practice | Free, focused on interview patterns |
| **LeetCode — N-ary Tree** | https://leetcode.com/tag/n-ary-tree/ | Targeted N-ary tree problems |
| **System Design Primer** | https://github.com/donnemartin/system-design-primer | Broader system design reference |

---

## 2-Hour Session Plan

```
⏰ 0min–20min:   Read Steps 1–4 (Fundamentals + Implementation)
⏰ 20min–30min:  Answer the Progressive Test
⏰ 30min–50min:  Study Scenario 1 (Database Merge) — compile, run, understand every line
⏰ 50min–70min:  Study Scenarios 2–3 (Expansion + Corrupted Hierarchy)
⏰ 70min–90min:  Study Scenario 4 (4-level Neighborhoods)
⏰ 90min–100min: Break the code intentionally — add edge cases, observe results
⏰ 100min–110min: Feynman Technique — explain sameStructure out loud
⏰ 110min–120min: Review the One-Page Cheat Sheet
```
