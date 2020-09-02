package me.lokvin.example.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CalculateCirclePerimeter {
    public static void main(String[] args) {
        int radius = 0;
        System.out.println("pls enter radius of circle");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            radius = Integer.parseInt(br.readLine());
        } catch (IOException ioe) {
            System.out.println("io error " + ioe);
            System.exit(0);
        } catch (NumberFormatException nfe) {
            System.out.println("invalid radius value " + nfe);
            System.exit(0);
        }

        double perimeter = 2 * Math.PI * radius;
        System.out.println("perimeter of a circle is " + perimeter);
    }
}
