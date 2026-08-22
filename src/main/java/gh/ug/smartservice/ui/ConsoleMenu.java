package gh.ug.smartservice.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Generic menu runner. Each teammate's feature is registered here as a
 * MenuCommand rather than as a hand-written switch/case branch — that keeps
 * this file almost never touched after the initial setup, which is exactly
 * where merge conflicts like to happen in group projects.
 */
public class ConsoleMenu {
    private final List<MenuCommand> commands = new ArrayList<>();

    public void register(MenuCommand command) {
        commands.add(command);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Smart Service Operations Optimizer ===");
            for (int i = 0; i < commands.size(); i++) {
                System.out.println((i + 1) + ". " + commands.get(i).label());
            }
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine().trim();
            if (input.equals("0")) {
                System.out.println("Goodbye.");
                break;
            }
            try {
                int choice = Integer.parseInt(input) - 1;
                if (choice >= 0 && choice < commands.size()) {
                    commands.get(choice).execute();
                } else {
                    System.out.println("Invalid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a number.");
            }
        }
    }
}
