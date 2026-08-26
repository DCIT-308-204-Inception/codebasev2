package gh.ug.smartservice;

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

        menu.register(new EfficiencyLabMenuCommand());
        // Register further MenuCommand instances here, e.g.:
        // menu.register(new gh.ug.smartservice.engine.SchedulingMenuCommand(...));

        menu.run();
    }
}
