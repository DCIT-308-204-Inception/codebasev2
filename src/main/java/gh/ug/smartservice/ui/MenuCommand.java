package gh.ug.smartservice.ui;

/**
 * One menu action = one class. To add a new console option, create a new
 * class implementing this interface and register it in ConsoleMenu's
 * command list (one line). You never edit anyone else's command class,
 * and registration touches only your own added line -> minimal merge risk.
 */
public interface MenuCommand {
    String label();
    void execute();
}
