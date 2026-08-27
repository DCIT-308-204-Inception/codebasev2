package gh.ug.smartservice.util;

import gh.ug.smartservice.model.AlgorithmRun;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class CsvExporter {

    private CsvExporter() {
    }

    public static void export(List<AlgorithmRun> runs, Path outputFile) {
        StringBuilder csv = new StringBuilder("runId,algorithmName,inputSize,timeNs,memoryKb,dateRun\n");
        for (AlgorithmRun run : runs) {
            csv.append(run.getRunId()).append(',')
               .append(run.getAlgorithmName()).append(',')
               .append(run.getInputSize()).append(',')
               .append(run.getTimeNs()).append(',')
               .append(run.getMemoryKb()).append(',')
               .append(run.getDateRun()).append('\n');
        }
        try {
            Files.createDirectories(outputFile.toAbsolutePath().getParent());
            Files.writeString(outputFile, csv.toString());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
