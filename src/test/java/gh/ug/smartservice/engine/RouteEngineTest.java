package gh.ug.smartservice.engine;

import gh.ug.smartservice.algorithms.graphalgo.MstResult;
import gh.ug.smartservice.algorithms.graphalgo.ShortestPathResult;
import gh.ug.smartservice.db.DatabaseConnection;
import gh.ug.smartservice.db.DatabaseInitializer;
import gh.ug.smartservice.db.PathDAO;
import gh.ug.smartservice.db.ShelfDAO;
import gh.ug.smartservice.model.Path;
import gh.ug.smartservice.model.Shelf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RouteEngineTest {

    private final ShelfDAO shelfDAO = new ShelfDAO();
    private final PathDAO pathDAO = new PathDAO();

    @BeforeEach
    void setUp(@TempDir java.nio.file.Path tempDir) {
        DatabaseConnection.useUrl("jdbc:sqlite:" + tempDir.resolve("test.db"));
        DatabaseInitializer.initialize();

        for (int i = 0; i < 4; i++) {
            shelfDAO.insert(new Shelf(i, "Shelf " + i, "Section", "Category", 0, 0));
        }
        // 0-1-2-3 chain plus a shortcut 0-2, so shortest 0->3 goes via the shortcut.
        pathDAO.insert(new Path(0, 1, 5.0, 1.0, 1.0));
        pathDAO.insert(new Path(1, 2, 5.0, 1.0, 1.0));
        pathDAO.insert(new Path(2, 3, 5.0, 1.0, 1.0));
        pathDAO.insert(new Path(0, 2, 1.0, 1.0, 1.0));
    }

    @AfterEach
    void tearDown() {
        DatabaseConnection.close();
    }

    @Test
    void reachableShelvesIncludesAllConnectedVertices() {
        RouteEngine engine = new RouteEngine(shelfDAO, pathDAO);
        List<Integer> reachable = engine.reachableShelves(0);
        assertEquals(4, reachable.size());
    }

    @Test
    void fastestRouteUsesShortcut() {
        RouteEngine engine = new RouteEngine(shelfDAO, pathDAO);
        ShortestPathResult result = engine.fastestRoutesFrom(0);
        assertTrue(result.isReachable());
        // 0->2 direct (1.0) + 2->3 (5.0) = 6.0, cheaper than 0->1->2->3 (15.0)
        assertEquals(6.0, result.getDistances()[3]);
    }

    @Test
    void minimumConnectionNetworkSpansAllShelves() {
        RouteEngine engine = new RouteEngine(shelfDAO, pathDAO);
        MstResult mst = engine.minimumConnectionNetwork();
        assertTrue(mst.isSpanning());
        assertEquals(3, mst.getEdges().size());
    }

    @Test
    void isolatedShelfBoundary(@TempDir java.nio.file.Path tempDir) {
        DatabaseConnection.useUrl("jdbc:sqlite:" + tempDir.resolve("isolated.db"));
        DatabaseInitializer.initialize();
        ShelfDAO isolatedShelfDAO = new ShelfDAO();
        isolatedShelfDAO.insert(new Shelf(0, "Lonely Shelf", "Section", "Category", 0, 0));

        RouteEngine engine = new RouteEngine(isolatedShelfDAO, new PathDAO());
        assertEquals(1, engine.reachableShelves(0).size());
        assertTrue(engine.minimumConnectionNetwork().isSpanning());
    }
}
