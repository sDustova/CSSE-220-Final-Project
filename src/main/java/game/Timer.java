package game;

/**
 * Stopwatch for a level. Counts real seconds while running.
 */
public class Timer {
    private double currentTime = 0;  // seconds counted so far
    private long startNanos = 0;     // System.nanoTime() when the count (re)started
    private boolean running = false;

    /** Starts counting, or keeps going from where stop() left off. */
    public void start() {
        if (!running) {
            // Back-date the start so time already counted is kept
            startNanos = System.nanoTime() - (long) (currentTime * 1_000_000_000L);
            running = true;
        }
    }

    /** Freezes the time where it is. */
    public void stop() {
        currentTime = getCurrentTime();
        running = false;
    }

    /** Stops and sets the time back to 0. */
    public void reset() {
        running = false;
        currentTime = 0;
    }

    /** Returns the elapsed time in seconds. */
    public double getCurrentTime() {
        if (running) {
            currentTime = (System.nanoTime() - startNanos) / 1_000_000_000.0;
        }
        return currentTime;
    }

    public boolean isRunning() {return running;}
}
