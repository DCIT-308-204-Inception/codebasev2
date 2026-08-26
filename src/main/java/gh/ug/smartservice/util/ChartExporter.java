package gh.ug.smartservice.util;

import gh.ug.smartservice.model.AlgorithmRun;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class ChartExporter {

    private ChartExporter() {
    }

    /** Plots average timeNs per inputSize, one line per distinct algorithmName. */
    public static void exportLineChart(String title, List<AlgorithmRun> runs, Path outputFile) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<String, Map<Integer, long[]>> totalsByAlgorithm = new LinkedHashMap<>();
        for (AlgorithmRun run : runs) {
            totalsByAlgorithm
                .computeIfAbsent(run.getAlgorithmName(), k -> new TreeMap<>())
                .computeIfAbsent(run.getInputSize(), k -> new long[2])[0] += run.getTimeNs();
            totalsByAlgorithm.get(run.getAlgorithmName()).get(run.getInputSize())[1]++;
        }

        for (Map.Entry<String, Map<Integer, long[]>> algorithm : totalsByAlgorithm.entrySet()) {
            for (Map.Entry<Integer, long[]> bySize : algorithm.getValue().entrySet()) {
                long sumNs = bySize.getValue()[0];
                long count = bySize.getValue()[1];
                dataset.addValue(sumNs / (double) count, algorithm.getKey(), bySize.getKey());
            }
        }

        JFreeChart chart = ChartFactory.createLineChart(
            title, "Input size", "Average time (ns)", dataset);

        try {
            Files.createDirectories(outputFile.toAbsolutePath().getParent());
            ChartUtils.saveChartAsPNG(outputFile.toFile(), chart, 900, 600);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
