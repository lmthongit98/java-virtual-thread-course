package com.tma.training.demo12_virtual_thread.demo00;

import java.util.ArrayList;
import java.util.List;

/*
    A demo to see how to create platform thread by using builder.
    By default, platform threads are user threads or non-daemon threads. The application will wait for them to complete.
    We can use thread.join for the main thead to wait for all daemon threads to complete.
 */

public class AppA03 {

    private static final int MAX_THREAD = 10;

    public static void main(String[] args) throws InterruptedException {
        platformThreadDemo();
    }

    private static void platformThreadDemo() throws InterruptedException {
        Thread.Builder builder = Thread.ofPlatform().daemon().name("daemon-", 1);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < MAX_THREAD; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> {
                Task.ioIntensive(j);
            });
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
        }

    }

}
