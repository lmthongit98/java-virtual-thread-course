package com.tma.training.demo12_virtual_thread.demo00;

/*
    A demo to see how to create platform thread by using builder.
    By default, platform threads are user threads or non-daemon threads. The application will wait for them to complete.
 */
public class AppA02 {

    private static final int MAX_THREAD = 10;

    public static void main(String[] args) {
        platformThreadDemo();
    }

    private static void platformThreadDemo() {
        Thread.Builder builder = Thread.ofPlatform().name("platform-", 1);
        for (int i = 0; i < MAX_THREAD; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> Task.ioIntensive(j));
            thread.start();
        }
    }

}
