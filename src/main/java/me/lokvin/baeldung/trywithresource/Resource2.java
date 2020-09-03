package me.lokvin.baeldung.trywithresource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Resource2 {
    public static void main(String[] args) {
        // try-catch-finally
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("target/test.txt"));
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        // try-with-resource
        try (Scanner scanner1 = new Scanner(new File("target/test.ext"))) {
            while (scanner1.hasNext()) {
                System.out.println(scanner1.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
