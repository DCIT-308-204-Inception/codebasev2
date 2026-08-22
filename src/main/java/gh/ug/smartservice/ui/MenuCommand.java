package gh.ug.smartservice.ui;

/**
 * One menu action = one class. A new console option is a new class
 * implementing this interface, registered in ConsoleMenu's command list.
 */
public interface MenuCommand {
    String label();
    void execute();
}
