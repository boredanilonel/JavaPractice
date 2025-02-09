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