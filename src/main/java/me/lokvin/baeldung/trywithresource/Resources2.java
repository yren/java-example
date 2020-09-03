package me.lokvin.baeldung.trywithresource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Resources2 {
    public static void main(String[] args) {
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
    }
}
