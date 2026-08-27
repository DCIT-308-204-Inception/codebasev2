package gh.ug.smartservice.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvLoaderTest {

    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        DatabaseConnection.useUrl("jdbc:sqlite:" + tempDir.resolve("test.db"));
        DatabaseInitializer.initialize();
    }

    @AfterEach
    void tearDown() {
        DatabaseConnection.close();
    }

    @Test
    void loadAllMeetsBriefMinimums() {
        assertTrue(CsvLoader.loadAll());

        assertTrue(new ShelfDAO().findAll().size() >= 50);
        assertTrue(new PathDAO().findAll().size() >= 100);
        assertTrue(new BookRequestDAO().findAll().size() >= 300);
        assertTrue(new StaffDAO().findAll().size() >= 30);
    }

    @Test
    void loadAllIsIdempotent() {
        assertTrue(CsvLoader.loadAll());
        int shelfCountAfterFirstLoad = new ShelfDAO().findAll().size();

        assertFalse(CsvLoader.loadAll());
        assertEquals(shelfCountAfterFirstLoad, new ShelfDAO().findAll().size());
    }
}
