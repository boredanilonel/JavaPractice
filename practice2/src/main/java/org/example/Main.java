package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static final int THREAD_COUNT = 10; // Количество потоков
    public static final int PROGRESS_LENGTH = 20; // Длина выполнения
    public static final int SLEEP_TIME_MS = 200; // Задержка в мс между шагами выполнения

    // Объект для синхронизации вывода в консоль
    public static final Object consoleLock = new Object();
    public static final long[] threadIds = new long[THREAD_COUNT]; // Массив для хранения ID потоков

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<Long>> futures = new ArrayList<>();

        for (int i = 1; i <= THREAD_COUNT; i++) {
            int threadNumber = i;
            futures.add(executorService.submit(() -> performComputation(threadNumber)));
        }

        executorService.shutdown();

        for (Future<Long> future : futures) {
            try {
                future.get(); // Ожидание завершения всех потоков
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Вывод содержимого массива threadIds
        synchronized (consoleLock) {
            System.out.println("\nThread IDs:");
            for (int i = 0; i < THREAD_COUNT; i++) {
                System.out.printf("Thread %d (ID: %d)%n", i + 1, threadIds[i]);
            }
        }
    }
}
