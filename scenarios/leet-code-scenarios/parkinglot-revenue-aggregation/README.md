# Live Coding Practice: CSV Parking Lot Revenue Aggregation

## Context
Focus on **Data Processing, File Parsing, HashMap Aggregation, and Domain-Specific Logic**. Process a CSV with parking lot revenue data — directly inspired by Metropolis's core business. Parse, group by location, compute revenue breakdowns by customer type.

**High-priority:** Confirmed across **two separate Glassdoor reports (2023 and 2025)** — the most frequently recurring Metropolis coding problem.

## The Challenge
Implement a solution that reads a CSV String, parses parking lot transactions, aggregates total revenue by location, and breaks it down by customer type (Monthly, Hourly, Valet, Event, Commercial).

## Technical Requirements

### Input Format
```csv
location_id,location_name,customer_type,revenue,transaction_date
101,Downtown Garage,Monthly,350.00,2026-01-15
101,Downtown Garage,Hourly,12.50,2026-01-15
102,Airport Parking,Valet,45.00,2026-01-15
```

### Performance
- **Time:** `O(N)` — single pass, HashMap ops are O(1)
- **Space:** `O(L × C)` — locations × customer types

### Output
Structured report showing revenue per location with customer type breakdowns + grand total.

## Test Data Examples

| Input | Expected |
|---|---|
| 3 transactions across 2 locations | Location 101: Monthly=$350, Hourly=$12.50, Total=$362.50. Location 102: Valet=$45, Total=$45. Grand Total=$407.50 |
| Single location, multiple same-type | Merged correctly |
| Header only / empty | "No data found." |

## Staff Level Focus Points

### Data Structure: Nested HashMap
```
Map<locationId, Map<customerType, totalRevenue>>
```
Use **`merge(key, value, Double::sum)`** — handles "add or create" in one call.

### Parsing Considerations
- **Header row:** Skip it, or use it to map column indices dynamically
- **Number parsing:** `Double.parseDouble()` — handle `NumberFormatException`
- **Empty lines:** Skip to avoid `ArrayIndexOutOfBoundsException`
- **Column order:** Don't assume fixed positions — parse header to find indices

### Production Readiness Topics
- **Real-time streaming:** Use state store instead of static file
- **SQL equivalent:** `SELECT location_id, customer_type, SUM(revenue) GROUP BY ...`
- **10M rows:** Stream line-by-line (BufferedReader), don't load entire CSV
- **Data validation:** Handle malformed rows without crashing

## Java Solution

```java
import java.util.*;

public class ParkingRevenueProcessor {
    
    public String processParkingRevenue(String csvData) {

    }
}
```

## Pair Programming Script

**1. CLARIFY (45s):**
> *"Is the input always comma-separated with a header? Should I handle quoted fields? What output format do you prefer — formatted String or Map?"*

**2. OUTLINE (1m):**
> *"I'll use a nested HashMap. Outer key is location_id, inner key is customer_type. Use `merge()` for clean aggregation. Parse line by line, skip header, format output at the end."*

**3. TRACE on example:**
```
Row 1: Downtown Garage, Monthly, $350 → map[101][Monthly] = 350
Row 2: Downtown Garage, Hourly, $12.50 → map[101][Hourly] = 12.50
Row 3: Airport Parking, Valet, $45 → map[102][Valet] = 45

Output:
Location: 101 - Downtown Garage
  Monthly:    $350.00
  Hourly:     $12.50
  Total:      $362.50
Location: 102 - Airport Parking
  Valet:      $45.00
  Total:      $45.00
Grand Total: $407.50 ✅
```

**4. EDGE CASES to proactively mention:**
- "Empty CSV → return 'No data found.'"
- "Missing revenue → handle NumberFormatException gracefully"
- "Unknown column order → use header to map indices"
- "Negative revenue → clarify if valid"

**5. What they're really testing:**
- **Domain relevance:** Can you handle Metropolis's actual data (parking lot revenue)?
- **HashMap fluency:** Do you know `merge()`, `computeIfAbsent()`?
- **Defensive coding:** Handle malformed input without crashing
- **Output quality:** Clean, professional report from raw data
