package me.lokvin.baeldung.trywithresource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Resource3 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File("target/test.txt"));
             PrintWriter printWriter = new PrintWriter(new File("target/testWrite.txt"))) {
            while (scanner.hasNext()) {
                printWriter.print(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
