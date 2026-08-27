package gh.ug.smartservice.db;

import gh.ug.smartservice.model.BookRequest;
import gh.ug.smartservice.model.Path;
import gh.ug.smartservice.model.Shelf;
import gh.ug.smartservice.model.Staff;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class CsvLoader {

    private CsvLoader() {
    }

    /** Loads the seed CSVs into the database. Returns false (and does nothing) if data is already loaded. */
    public static boolean loadAll() {
        if (!new ShelfDAO().findAll().isEmpty()) {
            return false;
        }
        loadShelves();
        loadPaths();
        loadBookRequests();
        loadStaff();
        return true;
    }

    private static void loadShelves() {
        ShelfDAO dao = new ShelfDAO();
        for (String[] row : readRows("data/locations.csv")) {
            dao.insert(new Shelf(
                parseId(row[0]),
                row[1],
                row[2],
                row[3],
                Double.parseDouble(row[4]),
                Double.parseDouble(row[5])));
        }
    }

    private static void loadPaths() {
        PathDAO dao = new PathDAO();
        for (String[] row : readRows("data/roads.csv")) {
            dao.insert(new Path(
                parseId(row[1]),
                parseId(row[2]),
                Double.parseDouble(row[3]),
                Double.parseDouble(row[4]),
                Double.parseDouble(row[5])));
        }
    }

    private static void loadBookRequests() {
        BookRequestDAO dao = new BookRequestDAO();
        for (String[] row : readRows("data/service_requests.csv")) {
            dao.insert(new BookRequest(
                parseId(row[0]),
                parseId(row[1]),
                parseId(row[2]),
                row[3],
                Integer.parseInt(row[4]),
                LocalDateTime.parse(row[5]),
                LocalDateTime.parse(row[6]),
                row[7]));
        }
    }

    private static void loadStaff() {
        StaffDAO dao = new StaffDAO();
        for (String[] row : readRows("data/resources.csv")) {
            dao.insert(new Staff(
                parseId(row[0]),
                row[1],
                parseId(row[2]),
                Integer.parseInt(row[3]),
                row[4]));
        }
    }

    /** Strips a non-numeric prefix (e.g. "L", "Q", "S") and parses the rest as an int id. */
    private static int parseId(String rawId) {
        String digits = rawId.replaceAll("^\\D+", "");
        return Integer.parseInt(digits);
    }

    private static List<String[]> readRows(String path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        List<String[]> rows = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            if (!lines.get(i).isBlank()) {
                rows.add(splitCsvLine(lines.get(i)));
            }
        }
        return rows;
    }

    /** Splits one CSV line on commas, respecting double-quoted fields that may contain commas. */
    private static String[] splitCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString());
        return fields.toArray(new String[0]);
    }
}
