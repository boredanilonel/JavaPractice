package org.example;

import org.junit.jupiter.api.Test;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private static final int THREAD_COUNT = 10;
    private static final int PROGRESS_LENGTH = 20;

    @Test
    void testPerformComputation() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Long> future = executorService.submit(() -> Main.performComputation(1));
        executorService.shutdown();

        try {
            long executionTime = future.get(5, TimeUnit.SECONDS);
            assertTrue(executionTime >= PROGRESS_LENGTH * Main.SLEEP_TIME_MS, "Execution time should be reasonable");
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            fail("Execution interrupted or took too long");
        }
    }

    @Test
    void testThreadExecution() {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        Future<Long>[] futures = new Future[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            int threadNumber = i + 1;
            futures[i] = executorService.submit(() -> Main.performComputation(threadNumber));
        }

        executorService.shutdown();

        for (Future<Long> future : futures) {
            assertDoesNotThrow(() -> future.get(10, TimeUnit.SECONDS), "Thread should complete successfully");
        }
    }
}
