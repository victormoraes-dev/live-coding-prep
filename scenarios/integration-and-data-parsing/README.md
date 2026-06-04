### Scenario: The Payment Processor (Java)

**Context:**  
In this "Stripe-style" interview, we focus on **Integration and Data Parsing**. You are tasked with implementing a component that processes raw transaction strings from a legacy system. These strings are often malformed or contain invalid data.

**The Challenge:**  
Implement the `TransactionProcessor` class. Your goal is to parse a list of strings and calculate the **total successful transaction value in USD**.

**Technical Requirements:**

*   **Parsing:** Each string follows the format: `TRANSACTION_ID:AMOUNT:CURRENCY`.
    *   *Example:* `TXN_001:150.50:BRL`
*   **Validation:**
    *   The ID must start with `TXN_` followed by digits (e.g., `TXN_123`).
    *   The amount must be a positive number.
    *   Ignore any malformed lines or invalid IDs without crashing the process.
*   **Currency Conversion:** Convert all valid amounts to **USD** using these fixed rates:
    *   `BRL` to `USD`: Divide by `5.0`
    *   `EUR` to `USD`: Multiply by `1.1`
    *   `USD` to `USD`: `1.0`
*   **Output:** Return the sum of all valid transactions in USD as a `double`.

---

### Summary
This scenario tests your ability to handle **unstructured data**, **input validation**, and **error resilience**. For a Senior position, interviewers will look for clean abstractions, efficient use of the Java Streams API, and how you handle edge cases like `null` values or unsupported currencies.

### Next Steps
1.  **Implement the solution:** You can write the code directly here.
2.  **Review:** Once you provide the code, I will perform a "Code Review" focusing on Senior-level expectations (Clean Code, SOLID, and performance).
3.  **Refactor:** We can discuss how to make this processor more extensible (e.g., adding new currencies without modifying the core logic).