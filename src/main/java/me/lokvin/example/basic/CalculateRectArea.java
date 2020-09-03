package me.lokvin.example.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CalculateRectArea {
    public static void main(String[] args) {
        int width = 0;
        int length = 0;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("pls enter length of a rectangle");
            length = Integer.parseInt(br.readLine());

            System.out.println("pls enter width");
            width = Integer.parseInt(br.readLine());
        } catch (IOException ioe) {
            System.out.println("io error " + ioe);
            System.exit(0);

        } catch (NumberFormatException nfe) {
            System.out.println("invalid value " + nfe);
            System.exit(0);
        }

        int area = length * width;
        System.out.println("area of a rectangle is " + area);
    }
}
