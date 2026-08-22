package gh.ug.smartservice;

import gh.ug.smartservice.ui.ConsoleMenu;

/**
 * Single entry point. IMPORTANT: do not add feature logic here.
 * This file only wires the database, loads data, and registers each
 * teammate's MenuCommand with ConsoleMenu. Because it's this thin,
 * everyone can add ONE registration line each without colliding.
 *
 * Example (uncomment once your engine/DAO exist):
 *
 *   ConsoleMenu menu = new ConsoleMenu();
 *   menu.register(new gh.ug.smartservice.engine.SchedulingMenuCommand(...));
 *   menu.register(new gh.ug.smartservice.engine.RouteMenuCommand(...));
 *   menu.run();
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Smart Service Operations Optimizer — starting up...");

        ConsoleMenu menu = new ConsoleMenu();

        // TODO: each teammate registers their own MenuCommand here.
        // menu.register(new YourMenuCommand());

        menu.run();
    }
}
