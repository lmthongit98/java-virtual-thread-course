/*
 * HELLO WORLD VERSION MULTITHREADING
 * Version D01: Using Factory
 *
 * Thread.ofPlatform().factory() is thread-safe.
 *
 */

package com.tma.training.demo01_hello;

import java.util.concurrent.ThreadFactory;

public class AppD01 {

    public static void main(String[] args) {
        ThreadFactory factory = Thread.ofPlatform().factory();
        Thread t = factory.newThread(() -> {
            System.out.println("Hello from example thread");
        });
        t.start();
        System.out.println("Hello from main thread");
    }

}

