package gh.ug.smartservice.experiments;

import gh.ug.smartservice.ui.MenuCommand;

import java.nio.file.Path;

public class EfficiencyLabMenuCommand implements MenuCommand {

    @Override
    public String label() {
        return "Run efficiency lab (sort/search/hash experiments)";
    }

    @Override
    public void execute() {
        System.out.println("Running sort/search/hash experiments across input sizes 100-20,000...");
        ExperimentRunner.runAll(Path.of("results"));
        System.out.println("Done. CSV in results/csv/, graphs in results/graphs/.");
    }
}
