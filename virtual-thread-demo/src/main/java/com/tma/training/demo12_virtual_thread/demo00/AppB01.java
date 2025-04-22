package com.tma.training.demo12_virtual_thread.demo00;

import java.util.concurrent.CountDownLatch;

/*
    A demo to see how to create platform thread by using builder.
    By default, virtual threads are daemon threads.
 */

public class AppB01 {

    private static final int MAX_THREAD = 10;

    public static void main(String[] args) throws InterruptedException {
        platformThreadDemo();
    }

    private static void platformThreadDemo() throws InterruptedException {
        Thread.Builder builder = Thread.ofVirtual().name("routine-", 1);
        CountDownLatch countDownLatch = new CountDownLatch(MAX_THREAD);
        for (int i = 0; i < MAX_THREAD; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> {Task.ioIntensive(j);
                countDownLatch.countDown();
            });
            thread.start();
        }
        countDownLatch.await();
    }

}
