package org.example;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private static final String TEST_DIRECTORY = "test_output";

    @BeforeEach
    void setUp() throws IOException {
        Files.createDirectories(Paths.get(TEST_DIRECTORY));
        Main.integers.clear();
        Main.floats.clear();
        Main.strings.clear();
        Main.appendMode = false;
        Main.outputPath = TEST_DIRECTORY;
        Main.filePrefix = "";
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walk(Paths.get(TEST_DIRECTORY))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    void testParseArguments() {
        String[] args = {"-a", "-o", "testPath", "-p", "testPrefix"};
        Main.parseArguments(args);
        assertTrue(Main.appendMode);
        assertEquals("testPath", Main.outputPath);
        assertEquals("testPrefix", Main.filePrefix);
    }

    @Test
    void testClassifyData() {
        Main.classifyData("22");
        Main.classifyData("5.1");
        Main.classifyData("Hello World");

        assertEquals(List.of(22), Main.integers);
        assertEquals(List.of(5.1), Main.floats);
        assertEquals(List.of("Hello World"), Main.strings);
    }

    @Test
    void testWriteToFile() throws IOException {
        Main.integers.addAll(List.of(1, 2, 3));
        Main.writeToFile(Main.integers, Main.DEFAULT_INTEGER_FILE);

        Path filePath = Paths.get(TEST_DIRECTORY, Main.DEFAULT_INTEGER_FILE);
        assertTrue(Files.exists(filePath));
        List<String> lines = Files.readAllLines(filePath);
        assertEquals(List.of("1", "2", "3"), lines);
    }

    @Test
    void testWriteToFileAppendMode() throws IOException {
        Main.appendMode = true;
        Main.integers.add(1);
        Main.writeToFile(Main.integers, Main.DEFAULT_INTEGER_FILE);
        Main.integers.clear();
        Main.integers.add(2);
        Main.writeToFile(Main.integers, Main.DEFAULT_INTEGER_FILE);

        Path filePath = Paths.get(TEST_DIRECTORY, Main.DEFAULT_INTEGER_FILE);
        assertTrue(Files.exists(filePath));
        List<String> lines = Files.readAllLines(filePath);
        assertEquals(List.of("1", "2"), lines);
    }

    @Test
    void testPrintStatsIntegers() {
        Main.integers.addAll(List.of(10, 20, 30));
        assertDoesNotThrow(() -> Main.printStats("Test Integers", Main.integers));
    }

    @Test
    void testDetailedStats() {
        Main.detailedStats = true;
        Main.integers.addAll(List.of(1, 2, 3));
        assertDoesNotThrow(() -> Main.printStats("Detailed Integers", Main.integers));
    }

    @Test
    void testProcessFiles() throws IOException {
        Path tempFile = Paths.get(TEST_DIRECTORY, "test.txt");
        Files.write(tempFile, List.of("42", "3.14", "Hello"));

        Main.processFiles(List.of(tempFile.toString()));
        assertEquals(List.of(42), Main.integers);
        assertEquals(List.of(3.14), Main.floats);
        assertEquals(List.of("Hello"), Main.strings);
    }

    @Test
    void testMissingArgumentHandling() {
        String[] args = {"-o"};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Main.parseArguments(args));
        assertEquals("Missing path for -o option.", exception.getMessage());
    }
}
