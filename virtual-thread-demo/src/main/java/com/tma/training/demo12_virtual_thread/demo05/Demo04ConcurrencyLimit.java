package com.tma.training.demo12_virtual_thread.demo05;

import com.tma.training.demo12_virtual_thread.demo05.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 *  In some cases, we need to limit concurrency
 *  We normally use a Single/FixedThreadPool, but we should not use it with virtual threads since they are not supposed to be pooled.
 */

public class Demo04ConcurrencyLimit {

    private static final Logger log = LoggerFactory.getLogger(Demo04ConcurrencyLimit.class);

    public static void main(String[] args) {
        execute(Executors.newFixedThreadPool(3), 20);
//        execute(Executors.newFixedThreadPool(3, Thread.ofVirtual().name("my-vt-", 1).factory()), 20); // VTs are not supposed to be pooled
    }

    private static void execute(ExecutorService executorService, int taskCount) {
        try (executorService) {
            for (int i = 1; i < taskCount; i++) {
                int j = i;
                executorService.submit(() -> printProductInfo(j));
            }
        }
    }

    // 3rd party service
    // contract: 3 concurrent calls are allowed
    private static void printProductInfo(int id) {
        log.info("{} => {}", id, Client.getProduct(id));
    }


}
