package com.tma.training.demo12_virtual_thread.demo00;

import java.util.concurrent.CountDownLatch;

/*
    A demo to see how to create platform thread by using builder.
    By default, platform threads are user threads or non-daemon threads. The application will wait for them to complete.
    We can use countDownLatch for the main thead to wait for all daemon threads to complete.
 */

public class AppA04 {

    private static final int MAX_THREAD = 10;

    public static void main(String[] args) throws InterruptedException {
        platformThreadDemo();
    }

    private static void platformThreadDemo() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(MAX_THREAD);
        Thread.Builder builder = Thread.ofPlatform().daemon().name("daemon-", 1);
        for (int i = 0; i < MAX_THREAD; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> {
                Task.ioIntensive(j);
                countDownLatch.countDown();
            });
            thread.start();
        }

        countDownLatch.await();

    }

}
