# Scenario 2: New Market Expansion

## Context

A real estate platform is expanding into a new country. The internal system already has a
location hierarchy for that country built by the product team. The data team independently
pulled the same hierarchy from an external partner API.

Before going live, you need to confirm both hierarchies are identical — same nodes, same
structure, regardless of the order children were inserted.

If they match → safe to merge.
If they don't → there's a discrepancy that must be investigated before launch.

---

## The Problem

Given two N-ary trees representing location hierarchies, return `true` if they have the
same structure ignoring child order, or `false` if they differ in any way.

---

## Instructions

1. Build Tree A (internal system) and Tree B (partner API) as shown in each example
2. Call `treeA.sameStructure(treeB)`
3. Verify the output matches the expected result
4. For each `false` case, identify **which node** causes the mismatch

---

## Example 1 — Same structure, different child order → `true`

**Scenario:** Both sources have Argentina with the same 3 provinces and their cities, but
the partner API returned them in a different order.

```
Tree A (internal):          Tree B (partner API):
Argentina                   Argentina
  ├── Buenos Aires            ├── Córdoba
  │   ├── CABA                │   ├── Villa Carlos Paz
  │   └── La Plata            │   └── Río Cuarto
  ├── Córdoba                 ├── Mendoza
  │   ├── Río Cuarto          │   └── Ciudad de Mendoza
  │   └── Villa Carlos Paz    └── Buenos Aires
  └── Mendoza                     ├── La Plata
      └── Ciudad de Mendoza       └── CABA
```

**Expected output:** `true`

**Java setup:**

```java
// Tree A — internal system
TreeNode<String> treeA = new TreeNode<>("Argentina");
TreeNode<String> buenosAires = treeA.addChild("Buenos Aires");
buenosAires.addChild("CABA");
buenosAires.addChild("La Plata");
TreeNode<String> cordoba = treeA.addChild("Córdoba");
cordoba.addChild("Río Cuarto");
cordoba.addChild("Villa Carlos Paz");
TreeNode<String> mendoza = treeA.addChild("Mendoza");
mendoza.addChild("Ciudad de Mendoza");

// Tree B — partner API
TreeNode<String> treeB = new TreeNode<>("Argentina");
TreeNode<String> cordobaB = treeB.addChild("Córdoba");
cordobaB.addChild("Villa Carlos Paz");
cordobaB.addChild("Río Cuarto");
TreeNode<String> mendozaB = treeB.addChild("Mendoza");
mendozaB.addChild("Ciudad de Mendoza");
TreeNode<String> buenosAiresB = treeB.addChild("Buenos Aires");
buenosAiresB.addChild("La Plata");
buenosAiresB.addChild("CABA");

System.out.println(treeA.sameStructure(treeB)); // true
```

---

## Example 2 — Partner API is missing a province → `false`

**Scenario:** The partner API returned only 2 provinces for Argentina. Mendoza is missing
entirely. Child count mismatch at the root level — caught at Step 2 of the algorithm.

```
Tree A (internal):          Tree B (partner API):
Argentina                   Argentina
  ├── Buenos Aires            ├── Córdoba
  │   ├── CABA                │   ├── Villa Carlos Paz
  │   └── La Plata            │   └── Río Cuarto
  ├── Córdoba                 └── Buenos Aires
  │   ├── Río Cuarto              ├── La Plata
  │   └── Villa Carlos Paz        └── CABA
  └── Mendoza
      └── Ciudad de Mendoza
```

**Expected output:** `false`

**Where it fails:** `sameStructure(Argentina_A, Argentina_B)` — Step 2 catches
`a.children.size() = 3` vs `b.children.size() = 2`.

**Java setup:**

```java
// Tree A — same as Example 1

// Tree B — Mendoza missing
TreeNode<String> treeB = new TreeNode<>("Argentina");
TreeNode<String> cordobaB = treeB.addChild("Córdoba");
cordobaB.addChild("Villa Carlos Paz");
cordobaB.addChild("Río Cuarto");
TreeNode<String> buenosAiresB = treeB.addChild("Buenos Aires");
buenosAiresB.addChild("La Plata");
buenosAiresB.addChild("CABA");

System.out.println(treeA.sameStructure(treeB)); // false
```

---

## Example 3 — A city name is wrong in the partner data → `false`

**Scenario:** The partner API has a typo — `"La Plata"` was recorded as `"La Plates"`.
Structure and child count are identical, but the data mismatch is caught deep in the
recursion at the leaf level.

```
Tree A (internal):          Tree B (partner API):
Argentina                   Argentina
  ├── Buenos Aires            ├── Buenos Aires
  │   ├── CABA                │   ├── CABA
  │   └── La Plata            │   └── La Plates   ← typo
  ├── Córdoba                 ├── Córdoba
  │   ├── Río Cuarto          │   ├── Río Cuarto
  │   └── Villa Carlos Paz    │   └── Villa Carlos Paz
  └── Mendoza                 └── Mendoza
      └── Ciudad de Mendoza       └── Ciudad de Mendoza
```

**Expected output:** `false`

**Where it fails:** `sameStructure(La Plata_A, La Plates_B)` — Step 1 catches
`"La Plata" != "La Plates"`. Every candidate in Buenos Aires_B's `unmatched` list
is exhausted without a match for `La Plata_A`, so `found = false` propagates up
as `false`.

**Java setup:**

```java
// Tree A — same as Example 1

// Tree B — typo in La Plata
TreeNode<String> treeB = new TreeNode<>("Argentina");
TreeNode<String> buenosAiresB = treeB.addChild("Buenos Aires");
buenosAiresB.addChild("CABA");
buenosAiresB.addChild("La Plates"); // typo
TreeNode<String> cordobaB = treeB.addChild("Córdoba");
cordobaB.addChild("Río Cuarto");
cordobaB.addChild("Villa Carlos Paz");
TreeNode<String> mendozaB = treeB.addChild("Mendoza");
mendozaB.addChild("Ciudad de Mendoza");

System.out.println(treeA.sameStructure(treeB)); // false
```

---

## What Each Example Tests

| Example | Mismatch type         | Step that catches it | Expected |
|---------|-----------------------|----------------------|----------|
| 1       | None — order only     | —                    | `true`   |
| 2       | Missing province      | Step 2 (child count) | `false`  |
| 3       | Wrong city name       | Step 1 (data equals) | `false`  |
