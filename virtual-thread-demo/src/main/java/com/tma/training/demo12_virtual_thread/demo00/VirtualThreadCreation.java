/*
 *   Virtual Thread class is not public, we have to create it by using factory.
 *   virtual Threads are daemon threads by default.
 */

package com.tma.training.demo12_virtual_thread.demo00;

import java.util.concurrent.CountDownLatch;

public class VirtualThreadCreation {


    private static final int MAX_THREAD = 10;

    public static void main(String[] args) throws InterruptedException {
        var latch = new CountDownLatch(MAX_THREAD);

        Thread.Builder.OfVirtual builder = Thread.ofVirtual().name("routine-", 1);

        for (int i = 0; i < MAX_THREAD; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> {
                Task.ioIntensive(j);
                latch.countDown();
            });
            thread.start();
        }

        // wait for all virtual threads to complete
        latch.await();
    }

}
