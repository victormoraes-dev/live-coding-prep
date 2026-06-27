# 📘 Practice Scenario 1: Database Merge (Order Independence)

In this scenario, you will practice implementing a core algorithm for a **Staff Engineer** at QuintoAndar: comparing two hierarchical structures where the data is identical, but the order of elements may differ due to different source systems.

---

## 📋 Scenario Context

QuintoAndar is integrating property data from two different partners. 
- **Partner A** sends a hierarchy where states and cities are ordered alphabetically.
- **Partner B** sends the same data but ordered by the date the region was added to their system.

Your goal is to implement a robust comparison method that returns `true` if the geographical structures are the same, regardless of the order in which the states or cities appear in the children list.

---

## 🛠️ Base Class (To be implemented)

Save this as `LocationService.java`. Your task is to fill in the `sameStructure` method.

```java
import java.util.*;

class TreeNode<T> {
    public T data;
    public List<TreeNode<T>> children;

    public TreeNode(T data) {
        this.data = data;
        this.children = new ArrayList&lt;>();
    }

    public void addChild(TreeNode<T> child) {
        this.children.add(child);
    }
}

public class LocationService {

    /**
     * Compares two N-ary trees to check if they have the same structure
     * and data, ignoring the order of children.
     * 
     * @param a Root of the first tree
     * @param b Root of the second tree
     * @return true if structures are identical (ignoring order), false otherwise
     */
    public boolean sameStructure(TreeNode<String> a, TreeNode<String> b) {
        // TODO: Implement the comparison logic
        return false;
    }

    public static void main(String[] args) {
        // Use the test scenarios below to verify your implementation
    }
}
```

---

## 🧪 Test Scenarios

### Test Case 1: Identical Structure, Different Order (Should be TRUE)
**Input A:**
```text
Brazil
  ├── PR
  │   ├── Curitiba
  │   └── Londrina
  └── SP
      └── São Paulo
```
**Input B:**
```text
Brazil
  ├── SP
  │   └── São Paulo
  └── PR
      ├── Londrina
      └── Curitiba
```

### Test Case 2: Missing Node (Should be FALSE)
**Input A:**
```text
Brazil
  ├── PR
  └── SP
```
**Input B:**
```text
Brazil
  └── PR
```

### Test Case 3: Different Data (Should be FALSE)
**Input A:**
```text
Brazil
  └── PR
```
**Input B:**
```text
Brazil
  └── RJ
```

---

## 💡 Implementation Hints (Baby Steps)

1.  **Identity Check**: If both nodes are the same object or both are `null`, they are equal.
2.  **Data Validation**: Check if `a.data` is equal to `b.data`. If not, return `false`.
3.  **Children Count**: If `a.children.size()` != `b.children.size()`, they cannot be identical.
4.  **The Matching Logic**: For each child in `a`, you must find a corresponding child in `b` that satisfies `sameStructure`. 
    *   *Note:* Since this is a Staff-level challenge, consider the complexity. A nested loop is $O(n^2)$. How could you use a `Map` or `Sorting` to improve this?

---

### 📈 Complexity Goals
- **Time Complexity:** Aim for $O(N^2)$ for the basic recursive solution, or $O(N \log N)$ if you decide to sort children.
- **Space Complexity:** $O(H)$ where $H$ is the height of the tree (due to the recursion stack).