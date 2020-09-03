package me.lokvin.baeldung.trywithresource;

public class MyResource implements AutoCloseable {
    @Override
    public void close() throws Exception {
        System.out.println("close MyResource");
    }

    public void print() {
        System.out.println("it's MyResource");
    }

    public static void main(String[] args) {
        try (MyResource mr = new MyResource()) {
            mr.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
