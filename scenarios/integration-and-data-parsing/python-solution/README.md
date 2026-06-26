# Live Coding Practice: The Payment Processor (Python)

## Context
In this "Stripe-style" interview scenario, the focus is on **Integration and Data Parsing**. You are tasked with implementing a component that processes raw transaction strings from a legacy system. These strings are often malformed, inconsistent, or contain invalid data.

## The Challenge
Implement the `TransactionProcessor` class. Your goal is to parse a list of strings and calculate the **total successful transaction value in USD**.

## Technical Requirements

### 1. Parsing
Each string follows a colon-separated format: `TRANSACTION_ID:AMOUNT:CURRENCY`.
*   **Example:** `TXN_001:150.50:BRL`

### 2. Validation
To be considered "successful" and included in the total, a transaction must meet these criteria:
*   **ID Format:** Must start with `TXN_` followed by digits (e.g., `TXN_123`).
*   **Amount:** Must be a positive numeric value.
*   **Resilience:** The processor must ignore malformed lines, empty strings, or invalid IDs without throwing exceptions or crashing the entire batch.

### 3. Currency Conversion
Convert all valid amounts to **USD** using the following fixed exchange rates:
*   **BRL to USD:** Divide by `5.0` (or multiply by `0.2`)
*   **EUR to USD:** Multiply by `1.1`
*   **USD to USD:** `1.0`

### 4. Output
The method should return the sum of all valid transactions in USD as a `float`.

---

## Test Data Examples

| Raw Input | Status | Reason/Result |
| :--- | :--- | :--- |
| `TXN_001:100.00:BRL` | ✅ Valid | $20.00 USD |
| `TXN_002:50.00:USD` | ✅ Valid | $50.00 USD |
| `TXN_999:20.00:EUR` | ✅ Valid | $22.00 USD |
| `INVALID_DATA` | ❌ Ignored | Malformed format |
| `TXN_003:-50.00:USD` | ❌ Ignored | Negative amount |
| `ABC_001:100.00:USD` | ❌ Ignored | Invalid ID prefix |
| `TXN_004:100.00:GBP` | ❌ Ignored | Unsupported currency |

---

## Senior Level Focus Points
*   **Pythonic Pipeline:** Use **generators or list comprehensions** to create a clean functional pipeline.
*   **Error Handling:** Demonstrate how to handle `ValueError` or `IndexError` gracefully without crashing the batch.
*   **Separation of Concerns:** Keep parsing logic, validation logic, and conversion logic distinct.
*   **Immutability:** Ensure the original list of strings remains unmodified.

---

## Setup & Running

### Requirements
- Python 3.13+
- [uv](https://docs.astral.sh/uv/)

### Install dependencies
```bash
uv sync
```

### Run tests
```bash
uv run pytest
```
