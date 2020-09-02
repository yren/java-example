package me.lokvin.example.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CalculateCircleAreaExample {
    public static void main(String[] args) {
        int radius = 0;
        System.out.println("pls enter radius of circle");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            radius = Integer.parseInt(br.readLine());
        } catch (NumberFormatException nfe) {
            System.out.println("invalid radius value " + nfe);
            System.exit(0);
        } catch (IOException ioe) {
            System.out.println("io error " + ioe);
            System.exit(0);
        }

        double area = Math.PI * radius * radius;
        System.out.println("area of a circle is " + area);
    }
}
