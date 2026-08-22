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
├── pom.xml                     # Maven build — deps: sqlite-jdbc, JUnit 5
├── data/
│   ├── schema.sql               # CREATE TABLE statements
│   └── *.csv                    # seed data
├── docs/                        # report drafts, diagrams
├── src/main/java/gh/ug/smartservice/
│   ├── App.java                  # entry point
│   ├── model/                    # Shelf, Path, BookRequest, Staff, AlgorithmRun
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
# Build
mvn compile

# Run tests
mvn test

# Initialize the SQLite DB (once)
sqlite3 data/smart_service.db < data/schema.sql

# Run the app
mvn compile exec:java -Dexec.mainClass="gh.ug.smartservice.App"
# (or: mvn package && java -jar target/smart-service-optimizer.jar)
```

## Implementation constraint

Built-in `HashMap`, `TreeMap`, `PriorityQueue`, `Stack`, `ArrayDeque` and
similar are not used for assessed core logic — only for file I/O, JDBC,
plotting export, and test scaffolding. All list/stack/queue/tree/hash/graph
structures under `datastructures/` are implemented from scratch and exposed
through the interfaces in `datastructures/api/`.
