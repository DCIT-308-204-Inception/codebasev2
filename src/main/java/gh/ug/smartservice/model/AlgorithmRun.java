package gh.ug.smartservice.model;

import java.time.LocalDateTime;

/** Maps 1:1 to the `algorithm_runs` table — one row per performance experiment sample. */
public class AlgorithmRun {
    private int runId;
    private String algorithmName;
    private int inputSize;
    private long timeNs;
    private long memoryKb;
    private LocalDateTime dateRun;

    public AlgorithmRun() {}

    public AlgorithmRun(int runId, String algorithmName, int inputSize,
                         long timeNs, long memoryKb, LocalDateTime dateRun) {
        this.runId = runId;
        this.algorithmName = algorithmName;
        this.inputSize = inputSize;
        this.timeNs = timeNs;
        this.memoryKb = memoryKb;
        this.dateRun = dateRun;
    }

    public int getRunId() { return runId; }
    public void setRunId(int runId) { this.runId = runId; }
    public String getAlgorithmName() { return algorithmName; }
    public void setAlgorithmName(String algorithmName) { this.algorithmName = algorithmName; }
    public int getInputSize() { return inputSize; }
    public void setInputSize(int inputSize) { this.inputSize = inputSize; }
    public long getTimeNs() { return timeNs; }
    public void setTimeNs(long timeNs) { this.timeNs = timeNs; }
    public long getMemoryKb() { return memoryKb; }
    public void setMemoryKb(long memoryKb) { this.memoryKb = memoryKb; }
    public LocalDateTime getDateRun() { return dateRun; }
    public void setDateRun(LocalDateTime dateRun) { this.dateRun = dateRun; }
}
