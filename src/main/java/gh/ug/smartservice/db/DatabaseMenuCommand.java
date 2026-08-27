package gh.ug.smartservice.db;

import gh.ug.smartservice.ui.MenuCommand;

public class DatabaseMenuCommand implements MenuCommand {

    @Override
    public String label() {
        return "Initialize database and load seed data";
    }

    @Override
    public void execute() {
        System.out.println("Creating tables (if not already present)...");
        DatabaseInitializer.initialize();
        System.out.println("Loading seed CSVs into the database...");
        boolean loaded = CsvLoader.loadAll();
        System.out.println(loaded ? "Done." : "Data already loaded, skipped.");
    }
}
