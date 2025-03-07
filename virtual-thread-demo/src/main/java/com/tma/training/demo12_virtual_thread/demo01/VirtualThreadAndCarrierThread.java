package com.tma.training.demo12_virtual_thread.demo01;

import java.util.ArrayList;
import java.util.List;

/*
    A simple demo to see virtual threads are executed within platform threads called carrier threads.
 */
public class VirtualThreadAndCarrierThread {

    private static final int NUMBER_OF_VIRTUAL_THREADS = 1000;

    public static void main(String[] args) throws InterruptedException {

        List<Thread> virtualThreads = new ArrayList<>();
        Thread.Builder.OfVirtual builder = Thread.ofVirtual().name("routine-", 1);
        for (int i = 0; i < NUMBER_OF_VIRTUAL_THREADS; i++) {
            Thread thread = builder.unstarted(() -> System.out.println("Inside thread: " + Thread.currentThread()));
            virtualThreads.add(thread);
        }

        for (Thread virtualThread : virtualThreads) {
            virtualThread.start();
        }

        for (Thread virtualThread : virtualThreads) {
            virtualThread.join();
        }
    }
}
