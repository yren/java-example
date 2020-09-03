package me.lokvin.baeldung.trywithresource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Resource1 {
    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(new File("target/test.txt"))) {
            writer.println("hello");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
