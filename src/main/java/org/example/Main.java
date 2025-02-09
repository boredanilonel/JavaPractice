package org.example;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Main {

    public static final String DEFAULT_INTEGER_FILE = "integers.txt";
    public static final String DEFAULT_FLOAT_FILE = "floats.txt";
    public static final String DEFAULT_STRING_FILE = "strings.txt";

    public static boolean appendMode = false;
    public static String outputPath = "";
    public static String filePrefix = "";

    public static boolean detailedStats = false;

    public static final List<Integer> integers = new ArrayList<>();
    public static final List<Double> floats = new ArrayList<>();
    public static final List<String> strings = new ArrayList<>();

    public static void main(String[] args) {
        try {
            parseArguments(args);
            processFiles(Arrays.stream(args).filter(arg -> !arg.startsWith("-")).collect(Collectors.toList()));
            writeResults();
            printStatistics();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void parseArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-a":
                    appendMode = true;
                    break;
                case "-o":
                    if (++i < args.length) outputPath = args[i];
                    else throw new IllegalArgumentException("Missing path for -o option.");
                    break;
                case "-p":
                    if (++i < args.length) filePrefix = args[i];
                    else throw new IllegalArgumentException("Missing prefix for -p option.");
                    break;
                case "-s":
                    detailedStats = false;
                    break;
                case "-f":
                    detailedStats = true;
                    break;
                default:
                    break;
            }
        }
    }

    public static void processFiles(List<String> filePaths) {
        for (String path : filePaths) {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    classifyData(line);
                }
            } catch (IOException e) {
                System.err.println("Failed to read file: " + path);
            }
        }
    }

    public static void classifyData(String line) {
        if (line.matches("^-?\\d+$")) {
            integers.add(Integer.parseInt(line));
        } else if (line.matches("^-?\\d*\\.\\d+$")) {
            floats.add(Double.parseDouble(line));
        } else {
            strings.add(line);
        }
    }

    public static void writeResults() {
        writeToFile(integers, DEFAULT_INTEGER_FILE);
        writeToFile(floats, DEFAULT_FLOAT_FILE);
        writeToFile(strings, DEFAULT_STRING_FILE);
    }

    public static <T> void writeToFile(List<T> data, String fileName) {
        if (data.isEmpty()) return;

        String filePath = Paths.get(outputPath, filePrefix + fileName).toString();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath),
                StandardOpenOption.CREATE,
                appendMode ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING)) {
            for (T item : data) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to write to file: " + filePath);
        }
    }

    public static void printStatistics() {
        printStats("Integers", integers);
        printStats("Floats", floats);
        printStats("Strings", strings);
    }

    public static void printStats(String label, List<?> data) {
        if (data.isEmpty()) {
            System.out.println(label + ": No data");
            return;
        }

        System.out.println(label + " Statistics:");
        System.out.println("Count: " + data.size());

        if (detailedStats) {
            if (data.getFirst() instanceof Number) {
                List<Double> numbers = data.stream().map(n -> ((Number) n).doubleValue()).toList();
                System.out.println("Min: " + Collections.min(numbers));
                System.out.println("Max: " + Collections.max(numbers));
                System.out.println("Sum: " + numbers.stream().mapToDouble(Double::doubleValue).sum());
                System.out.println("Average: " + numbers.stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
            } else {
                List<String> texts = (List<String>) data;
                int minLength = texts.stream().mapToInt(String::length).min().orElse(0);
                int maxLength = texts.stream().mapToInt(String::length).max().orElse(0);
                System.out.println("Shortest length: " + minLength);
                System.out.println("Longest length: " + maxLength);
            }
        }
    }
}