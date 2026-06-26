# Live Coding Practice: Sparse Vector Dot Product (Java)

## Context
In this "QuintoAndar-style" interview scenario, the focus is on **Data Structures, Memory Optimization, and High Performance**. You are tasked with implementing the core of a recommendation algorithm that calculates the affinity between a user profile and a property profile. These profiles are represented as massive vectors (e.g., 100,000 dimensions) where the vast majority of values are zeros (highly sparse).

## The Challenge
Implement the `SparseVector` class. Your goal is to efficiently store the non-zero elements upon initialization and calculate the dot product between two instances of this class.

## Technical Requirements

### 1. Storage Optimization
The constructor receives a large array of integers `nums`. You must store only the non-zero elements in a way that minimizes memory footprint.
*   **Constraint:** Avoid storing zeros to save memory.

### 2. Dot Product Calculation
Implement the `dotProduct(SparseVector vec)` method. The dot product is the sum of the products of the corresponding entries of the two sequences of numbers.
*   **Formula:** `(A[0]*B[0]) + (A[1]*B[1]) + ... + (A[n]*B[n])`

### 3. Performance Strictness
*   **Time Complexity:** The dot product calculation must run in `O(L1 + L2)` time, where `L1` and `L2` are the number of non-zero elements in the two vectors. An `O(N)` solution (where `N` is the total length of the vector) will result in a Time Limit Exceeded (TLE) error on Codility.
*   **Space Complexity:** Must be `O(L)` to store only the non-zero elements.

### 4. Output
The method should return the final dot product as an `int`.

---

## Test Data Examples

| Vector 1 (`nums1`) | Vector 2 (`nums2`) | Expected Output | Explanation |
| :--- | :--- | :--- | :--- |
| `[1, 0, 0, 2, 3]` | `[0, 3, 0, 4, 0]` | `8` | `(1*0) + (0*3) + (0*0) + (2*4) + (3*0) = 8` |
| `[0, 1, 0, 0, 0]` | `[0, 0, 0, 0, 2]` | `0` | No overlapping non-zero indices. |
| `[0, 0, 0, 0, 0]` | `[0, 0, 0, 0, 0]` | `0` | Both vectors are entirely empty/zero. |
| `[1, 2, 3]` | `[4, 5, 6]` | `32` | Dense vectors: `(1*4) + (2*5) + (3*6) = 32` |

---

## Staff Level Focus Points

*   **Primitive Data Structures:** Avoid object overhead (like `HashMap` or `Integer` wrappers) to drastically reduce Garbage Collection (GC) pressure. Prefer primitive arrays (`int[] indices` and `int[] values`).
*   **Two Pointers Technique:** Implement the `dotProduct` using two pointers to efficiently traverse and multiply only the overlapping non-zero indices.
*   **CPU Cache Locality:** Be prepared to explain how contiguous memory allocation (using primitive arrays) benefits CPU cache performance (L1/L2) compared to scattered objects in the heap (like HashMaps).
*   **Production Readiness:** Discuss the trade-offs of your chosen data structure in a high-throughput, low-latency environment (e.g., executing this calculation millions of times per second in a recommendation API).