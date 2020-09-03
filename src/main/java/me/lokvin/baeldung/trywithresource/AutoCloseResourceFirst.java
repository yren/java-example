package me.lokvin.baeldung.trywithresource;

public class AutoCloseResourceFirst implements AutoCloseable {
    public AutoCloseResourceFirst() {
        System.out.println("constructor -> AutoCloseResourceFirst");
    }

    public static void main(String[] args) throws Exception {
        final AutoCloseResourceFirst af = new AutoCloseResourceFirst();
        AutoCloseResourceSecond as = new AutoCloseResourceSecond();
        try (af; as) {
            af.doSomething();
        }
    }

    public void doSomething() {
        System.out.println("AutoCloseResourceFirst -> doSomething");
    }

//    public static void main(String[] args) {
//        try (AutoCloseResourceFirst af = new AutoCloseResourceFirst();
//        AutoCloseResourceSecond as = new AutoCloseResourceSecond()) {
//            af.doSomething();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            System.out.println("finally block");
//        }
//    }

    @Override
    public void close() throws Exception {
        System.out.println("closed AutoCloseResourceFirst");
    }
}

class AutoCloseResourceSecond implements AutoCloseable {

    @Override
    public void close() throws Exception {
        System.out.println("closed AutoCloseResourceSecond");
    }
}

