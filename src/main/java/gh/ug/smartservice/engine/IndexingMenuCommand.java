package gh.ug.smartservice.engine;

import gh.ug.smartservice.db.BookRequestDAO;
import gh.ug.smartservice.db.ShelfDAO;
import gh.ug.smartservice.model.Shelf;
import gh.ug.smartservice.ui.MenuCommand;

import java.util.List;

public class IndexingMenuCommand implements MenuCommand {

    @Override
    public String label() {
        return "Search shelf/request indexes (category/section/status)";
    }

    @Override
    public void execute() {
        IndexingEngine engine = new IndexingEngine(new ShelfDAO(), new BookRequestDAO());

        System.out.println("Categories: " + engine.sortedCategories());
        System.out.println("Sections: " + engine.sortedSections());
        System.out.println("Statuses: " + engine.sortedStatuses());

        List<String> categories = engine.sortedCategories();
        if (!categories.isEmpty()) {
            String sample = categories.get(0);
            List<Shelf> shelves = engine.findShelvesByCategory(sample);
            System.out.println("\nShelves in category \"" + sample + "\": " + shelves.size());
            shelves.stream().limit(5).forEach(shelf -> System.out.println("  " + shelf));
        }
    }
}
