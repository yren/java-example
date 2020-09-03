package me.lokvin.baeldung.scan;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

public class JavaScannerTest {

    @Before
    public void init() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("target/test.txt"));
        try (writer) {
            writer.append("hello my friend\n");
            writer.append("we are the best\n");
            writer.append("I like it.");
        }
    }

    @Test
    public void readFileWithScanner() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("target/test.txt"));
        try (scanner) {
            while (scanner.hasNext()) {
                System.out.println(scanner.next());
            }
        }
    }

    @Test
    public void whenConvertInputStreamToString() throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("target/test.txt");
        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("A");
        System.out.println(scanner.next());
        scanner.close();
    }

    @Test
    public void readUsingBufferedReader() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("target/test.txt"));
        String line;
        StringBuilder content = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }
        System.out.println(content.toString());
        reader.close();
    }

    @Test
    public void readUsingScanner() throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("target/test.txt");
        Scanner scanner = new Scanner(inputStream);
        String result = scanner.nextLine();
        System.out.println(result);
        scanner.useDelimiter(", ");
        System.out.println(scanner.next());
        scanner.close();
    }
}
