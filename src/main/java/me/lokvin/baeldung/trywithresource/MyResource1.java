package me.lokvin.baeldung.trywithresource;

import java.io.Closeable;
import java.io.IOException;

public class MyResource1 implements Closeable {
    @Override
    public void close() throws IOException {
        System.out.println("close MyResource1");
    }

    public void print() {
        System.out.printf("print %s\n", "MyResource1");
    }

    public static void main(String[] args) {
        try (MyResource1 mr1 = new MyResource1()) {
            mr1.print();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
