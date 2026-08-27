package gh.ug.smartservice.util;

public final class Stopwatch {

    private long startNs;
    private boolean running;

    public void start() {
        startNs = System.nanoTime();
        running = true;
    }

    public long stop() {
        if (!running) {
            throw new IllegalStateException("Stopwatch was not started");
        }
        running = false;
        return System.nanoTime() - startNs;
    }
}
