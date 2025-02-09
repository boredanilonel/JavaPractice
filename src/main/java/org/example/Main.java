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
