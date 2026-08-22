-- Smart Library Operations Optimizer — SQLite schema
-- Local context: single library building, shelves/sections as the internal
-- navigation graph, book/issue requests routed and dispatched to staff.
-- Matches Section 4 (database requirements) and Section 6 tables of the brief,
-- renamed to library-domain terms per the localisation requirement (Section 2).
-- Load with: sqlite3 data/smart_library.db < data/schema.sql

CREATE TABLE IF NOT EXISTS shelves (
    shelfId     INTEGER PRIMARY KEY,
    name        TEXT NOT NULL,
    section     TEXT,
    category    TEXT,
    xCoordinate REAL,
    yCoordinate REAL
);

CREATE TABLE IF NOT EXISTS paths (
    pathId            INTEGER PRIMARY KEY AUTOINCREMENT,
    fromShelfId       INTEGER NOT NULL REFERENCES shelves(shelfId),
    toShelfId         INTEGER NOT NULL REFERENCES shelves(shelfId),
    distance          REAL,
    travelTime        REAL,
    congestionWeight  REAL
);

CREATE TABLE IF NOT EXISTS book_requests (
    requestId          INTEGER PRIMARY KEY,
    sourceShelfId      INTEGER NOT NULL REFERENCES shelves(shelfId),
    destinationShelfId INTEGER NOT NULL REFERENCES shelves(shelfId),
    category           TEXT,   -- issue, return, reservation, reshelve, search
    urgency            INTEGER,
    timeSubmitted      TEXT,
    deadline           TEXT,
    status             TEXT
);

CREATE TABLE IF NOT EXISTS staff (
    staffId             INTEGER PRIMARY KEY,
    role                TEXT,   -- librarian, shelver, trolley, intern
    homeShelfId         INTEGER REFERENCES shelves(shelfId),
    capacity            INTEGER,
    availabilityStatus  TEXT
);

CREATE TABLE IF NOT EXISTS algorithm_runs (
    runId          INTEGER PRIMARY KEY AUTOINCREMENT,
    algorithmName  TEXT NOT NULL,
    inputSize      INTEGER,
    timeNs         INTEGER,
    memoryKb       INTEGER,
    dateRun        TEXT
);

CREATE TABLE IF NOT EXISTS audit_events (
    eventId     INTEGER PRIMARY KEY AUTOINCREMENT,
    eventType   TEXT,
    description TEXT,
    timestamp   TEXT
);

CREATE INDEX IF NOT EXISTS idx_paths_from ON paths(fromShelfId);
CREATE INDEX IF NOT EXISTS idx_paths_to ON paths(toShelfId);
CREATE INDEX IF NOT EXISTS idx_book_requests_status ON book_requests(status);
