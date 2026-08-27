package gh.ug.smartservice;

import gh.ug.smartservice.db.DatabaseMenuCommand;
import gh.ug.smartservice.engine.SchedulingMenuCommand;
import gh.ug.smartservice.experiments.EfficiencyLabMenuCommand;
import gh.ug.smartservice.ui.ConsoleMenu;

/**
 * Single entry point. Wires the database, loads data, and registers each
 * MenuCommand with ConsoleMenu — no feature logic lives here.
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Smart Service Operations Optimizer — starting up...");

        ConsoleMenu menu = new ConsoleMenu();

        menu.register(new DatabaseMenuCommand());
        menu.register(new SchedulingMenuCommand());
        menu.register(new EfficiencyLabMenuCommand());
        // Register further MenuCommand instances here.

        menu.run();
    }
}
