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

    public static long performComputation(int threadNumber) {
        long startTime = System.currentTimeMillis();
        long threadId = Thread.currentThread().threadId(); // Сохранение ID потока

        synchronized (consoleLock) {
            threadIds[threadNumber - 1] = threadId; // Сохранение ID в массив
            System.out.printf("Thread %d (ID: %d) started.%n", threadNumber, threadId);
        }

        for (int i = 0; i < PROGRESS_LENGTH; i++) {
            // Обновление прогресса для этого потока
            String progressBar = "[" + "#".repeat(i) + " ".repeat(PROGRESS_LENGTH - i) + "]";

            synchronized (consoleLock) {
                // Перемещение курсора в нужное положение и перезапись строки
                System.out.printf("\033[%d;0HThread %d (ID: %d) %s\n", threadNumber + 1, threadNumber, threadId, progressBar);
            }

            try {
                Thread.sleep(SLEEP_TIME_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return -1;
            }
        }

        long elapsedTime = System.currentTimeMillis() - startTime;
        synchronized (consoleLock) {
            System.out.printf("Thread %d (ID: %d) finished. Execution time: %d ms%n", threadNumber, threadId, elapsedTime);
        }

        return elapsedTime;
    }
}
