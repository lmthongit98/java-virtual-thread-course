/*
 * HELLO WORLD VERSION MULTITHREADING
 * Version D02: Thread Builder
 *
 *   Each Thread.Builder instance is not thread-safe. You should not share an instance across multiple threads.
 *   However, the threads it creates are safe to use if you properly manage thread execution.
 *   Thread objects created are safe, but the builder instance should not be shared across threads.
 *
 */

package com.tma.training.demo01_hello;

public class AppD02 {

    public static void main(String[] args) {
        Thread.Builder builder = Thread.ofPlatform().name("my-platform-thread");
        Thread thread = builder.unstarted(() -> System.out.println("Hello from example thread"));
        thread.start();
        System.out.println("Hello from main thread");
    }

}

