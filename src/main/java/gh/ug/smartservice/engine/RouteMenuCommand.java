package gh.ug.smartservice.engine;

import gh.ug.smartservice.algorithms.graphalgo.Edge;
import gh.ug.smartservice.algorithms.graphalgo.MstResult;
import gh.ug.smartservice.algorithms.graphalgo.ShortestPathResult;
import gh.ug.smartservice.db.PathDAO;
import gh.ug.smartservice.db.ShelfDAO;
import gh.ug.smartservice.ui.MenuCommand;

public class RouteMenuCommand implements MenuCommand {

    private static final int START_SHELF_ID = 0;

    @Override
    public String label() {
        return "Find routes between shelves (fastest path, reachability, MST)";
    }

    @Override
    public void execute() {
        RouteEngine engine = new RouteEngine(new ShelfDAO(), new PathDAO());

        System.out.println("Reachable from shelf " + START_SHELF_ID + ": "
            + engine.reachableShelves(START_SHELF_ID).size() + " shelves");

        ShortestPathResult distances = engine.fastestRoutesFrom(START_SHELF_ID);
        System.out.println("Shortest-path tree reachable: " + distances.isReachable());

        MstResult mst = engine.minimumConnectionNetwork();
        System.out.println("Minimum connection network: " + mst.getEdges().size()
            + " aisles, total cost " + mst.getTotalWeight() + ", spanning=" + mst.isSpanning());
        for (Edge edge : mst.getEdges().stream().limit(5).toList()) {
            System.out.printf("  %d -> %d (cost %.2f)%n", edge.getFrom(), edge.getTo(), edge.getWeight());
        }
    }
}
