package com.tma.training.demo12_virtual_thread.demo00;

public class AppA01 {

    private static final int MAX_THREAD = 10;

    public static void main(String[] args) {
        platformThreadDemo();
    }

    private static void platformThreadDemo() {
        for (int i = 0; i < MAX_THREAD; i++) {
            int j = i;
            Thread thread = new Thread(() -> Task.ioIntensive(j));
            thread.start();
        }
    }

}
