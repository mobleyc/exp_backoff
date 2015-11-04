package cpm;

import java.util.Random;

public class ExponentialBackoff {

    private static final int DEFAULT_MAX_ATTEMPTS = 10;
    private static final long DEFAULT_BASE_SLEEP_TIME_MS = 1000;
    private static final long DEFAULT_MAX_SLEEP_TIME_MS = -1;

    private final Random random = new Random();
    private final long baseSleepTimeMs;
    private final int maxAttempts;
    private final long maxSleepTimeMs;

    private int attempts = 0;

    public ExponentialBackoff() {
        this(DEFAULT_BASE_SLEEP_TIME_MS, DEFAULT_MAX_ATTEMPTS, DEFAULT_MAX_SLEEP_TIME_MS);
    }

    public ExponentialBackoff(long baseSleepTimeMs, int maxAttempts, long maxSleepTimeMs) {
        this.baseSleepTimeMs = baseSleepTimeMs;
        this.maxAttempts = maxAttempts;
        this.maxSleepTimeMs = maxSleepTimeMs;
    }

    public long getSleepTimeMs() {
        attempts = attempts + 1;
        if (attempts > maxAttempts) {
            attempts = maxAttempts;
        }

        int upperBound = 1 << attempts;
        long sleepTimeMs = baseSleepTimeMs * Math.max(1, random.nextInt(upperBound));
        if (DEFAULT_MAX_SLEEP_TIME_MS == maxSleepTimeMs) {
            return sleepTimeMs;
        }

        return Math.min(maxSleepTimeMs, sleepTimeMs);
    }

    public boolean allowRetry() {
        if (attempts >= maxAttempts) {
            return false;
        }

        try {
            Thread.sleep(getSleepTimeMs());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
        attempts = attempts + 1;
        return true;
    }

    public void reset() {
        attempts = 0;
    }
}
