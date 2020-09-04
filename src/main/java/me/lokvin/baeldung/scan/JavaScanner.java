package me.lokvin.baeldung.scan;

import java.util.Scanner;

public class JavaScanner {
    public static void main(String[] args) {
        System.out.println("input string");
        Scanner scanner = new Scanner(System.in);
        String result = scanner.next();
        System.out.println(result);
    }
}
