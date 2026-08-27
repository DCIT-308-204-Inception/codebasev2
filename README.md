# Smart Library Operations Optimizer

DCIT 308 project: a library/records-office service-operations system. Shelves
and sections are nodes in an internal navigation graph, aisles/walkways are
weighted edges, and patron actions (issue, return, reservation, search) are
routed as book requests and dispatched to staff. Core data structures and
algorithms are implemented from scratch; the database and console UI wrap
them into a runnable system.

## Project structure

```
smart-service-optimizer/
├── pom.xml                     # Maven build — deps: sqlite-jdbc, JFreeChart, JUnit 5
├── data/
│   ├── schema.sql               # CREATE TABLE statements
│   └── *.csv                    # seed data
├── docs/                        # report drafts, diagrams
├── src/main/java/gh/ug/smartservice/
│   ├── App.java                  # entry point
│   ├── model/                    # Shelf, Path, BookRequest, Staff, AlgorithmRun, AuditEvent
│   ├── datastructures/
│   │   ├── api/                   # interfaces: SimpleList, SimpleStack, SimpleGraph...
│   │   └── list/ stack/ queue/ heap/ tree/ hash/ disjointset/ graph/
│   ├── algorithms/
│   │   └── search/ sort/ graphalgo/ optimisation/
│   ├── engine/                   # wires data structures + algorithms to operational rules
│   ├── db/                       # DatabaseConnection, DAOs, CsvLoader
│   ├── experiments/              # performance measurement harness
│   ├── ui/                       # console menu
│   └── util/                     # CsvExporter, Stopwatch
└── src/test/java/gh/ug/smartservice/   # mirrors src/main, one test class per implementation
```

## Running it

```bash
# Build, run tests, and package a runnable jar
mvn clean package

# Run the app
java -jar target/smart-service-optimizer.jar
```

This opens an interactive console menu:

```
1. Initialize database and load seed data
2. Dispatch pending book requests (FIFO/urgency/shift/interrupt)
3. Search shelf/request indexes (category/section/status)
4. Find routes between shelves (fastest path, reachability, MST)
5. Optimise staff assignment and request selection (greedy/DP)
6. Dispatch a request and undo it (audit trail demo)
7. Run efficiency lab (sort/search/hash experiments)
0. Exit
```

**Run option `1` first.** It creates `data/smart_library.db` and loads
`data/*.csv` into it — options 2–6 read from that database, so they'll show
nothing until it's been loaded once. Re-running option 1 later is safe; it
detects existing data and skips instead of erroring. Option 7 doesn't need
the database — it generates its own data and writes CSVs/graphs to
`results/csv/` and `results/graphs/`.

Enter a number and press Enter to pick an option; `0` exits.

## Implementation constraint

Built-in `HashMap`, `TreeMap`, `PriorityQueue`, `Stack`, `ArrayDeque` and
similar are not used for assessed core logic — only for file I/O, JDBC,
plotting export, and test scaffolding. All list/stack/queue/tree/hash/graph
structures under `datastructures/` are implemented from scratch and exposed
through the interfaces in `datastructures/api/`.
