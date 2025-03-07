package com.tma.training.demo05_thread_states;

import com.tma.training.util.CommonUtils;

import java.time.Duration;

/*
 * A demo to see thread state transitions.
 *
 * Messi (t1):      RUNNABLE → TIMED_WAITING (sleep 5s) → WAITING (calls wait()) → RUNNABLE (resumes after notify()) → TERMINATED
 *
 * Ronaldo (t2):    BLOCKED (waiting for lock) → RUNNABLE (acquires lock) → TIMED_WAITING (sleep 10s) → RUNNABLE (calls notify()) → TERMINATED
 *
 * */

class App {
    private static final Object LOCK = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (LOCK) {
                try {
                    System.out.println("Messi acquired the monitor lock");
                    CommonUtils.sleep(Duration.ofSeconds(5)); // TIME_WAITING
                    System.out.println("Messi wait (released lock)...");
                    LOCK.wait(); // WAITING
                    System.out.println("Messi Finished");
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

            }
        }, "Messi");

        Thread t2 = new Thread(() -> {
            synchronized (LOCK) { // BLOCKED
                System.out.println("Ronaldo acquired the monitor lock");
                CommonUtils.sleep(Duration.ofSeconds(10));
                LOCK.notify();
                System.out.println("Ronaldo finished and released the monitor lock");
            }
        }, "Ronaldo");

        t1.start();
        CommonUtils.sleep(Duration.ofMillis(100)); // Ensures t1 starts first
        t2.start();

    }
}
