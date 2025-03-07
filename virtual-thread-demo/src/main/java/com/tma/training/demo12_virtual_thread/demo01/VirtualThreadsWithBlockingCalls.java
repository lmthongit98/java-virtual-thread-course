package com.tma.training.demo12_virtual_thread.demo01;

import java.util.ArrayList;
import java.util.List;

/*
    A demo to see when performing blocking IO, the virtual threads are unmounted from platform threads.
 */

public class VirtualThreadsWithBlockingCalls {
    private static final int NUMBER_OF_VIRTUAL_THREADS = 100;

    public static void main(String[] args) throws InterruptedException {
        List<Thread> virtualThreads = new ArrayList<>();

        Thread.Builder.OfVirtual builder = Thread.ofVirtual().name("routine-", 1);
        for (int i = 0; i < NUMBER_OF_VIRTUAL_THREADS; i++) {
            Thread thread = builder.unstarted(new BlockingTask());
            virtualThreads.add(thread);
        }

        for (Thread virtualThread : virtualThreads) {
            virtualThread.start();
        }

        for (Thread virtualThread : virtualThreads) {
            virtualThread.join();
        }
    }

    private static class BlockingTask implements Runnable {

        @Override
        public void run() {
            System.out.println("Inside thread: " + Thread.currentThread() + " before blocking call");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Inside thread: " + Thread.currentThread() + " after blocking call");
        }
    }
}