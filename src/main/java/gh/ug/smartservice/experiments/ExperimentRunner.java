package gh.ug.smartservice.experiments;

import gh.ug.smartservice.model.AlgorithmRun;
import gh.ug.smartservice.util.ChartExporter;
import gh.ug.smartservice.util.CsvExporter;

import java.nio.file.Path;
import java.util.List;

public final class ExperimentRunner {

    private static final int[] INPUT_SIZES = {100, 1000, 5000, 10000, 20000};
    private static final int TRIALS_PER_SIZE = 3;

    private ExperimentRunner() {
    }

    public static void runAll(Path outputDir) {
        List<AlgorithmRun> sortResults = SortExperiment.run(INPUT_SIZES, TRIALS_PER_SIZE);
        CsvExporter.export(sortResults, outputDir.resolve("csv/sort_results.csv"));
        ChartExporter.exportLineChart("Sorting time by input size", sortResults, outputDir.resolve("graphs/sorting_time.png"));

        List<AlgorithmRun> searchResults = SearchExperiment.run(INPUT_SIZES, TRIALS_PER_SIZE);
        CsvExporter.export(searchResults, outputDir.resolve("csv/search_results.csv"));
        ChartExporter.exportLineChart("Search time by input size", searchResults, outputDir.resolve("graphs/search_time.png"));

        List<AlgorithmRun> hashResults = HashExperiment.run(INPUT_SIZES, TRIALS_PER_SIZE);
        CsvExporter.export(hashResults, outputDir.resolve("csv/hash_results.csv"));
        ChartExporter.exportLineChart("Hash table collision impact by input size", hashResults, outputDir.resolve("graphs/hash_collision_impact.png"));

        List<AlgorithmRun> heapResults = HeapExperiment.run(INPUT_SIZES, TRIALS_PER_SIZE);
        CsvExporter.export(heapResults, outputDir.resolve("csv/heap_results.csv"));
        ChartExporter.exportLineChart("Heap operation scaling by input size", heapResults, outputDir.resolve("graphs/heap_operation_scaling.png"));

        List<AlgorithmRun> treeResults = TreeExperiment.run(INPUT_SIZES, TRIALS_PER_SIZE);
        CsvExporter.export(treeResults, outputDir.resolve("csv/tree_results.csv"));
        ChartExporter.exportLineChart("Tree insert/search time by input size", treeResults, outputDir.resolve("graphs/tree_time.png"));

        List<AlgorithmRun> graphResults = GraphExperiment.run(INPUT_SIZES, TRIALS_PER_SIZE);
        CsvExporter.export(graphResults, outputDir.resolve("csv/graph_results.csv"));
        ChartExporter.exportLineChart("Dijkstra graph-size effect", graphResults, outputDir.resolve("graphs/dijkstra_graph_size.png"));
    }
}
