package com.tma.training.demo12_virtual_thread.demo05;

import com.tma.training.demo12_virtual_thread.demo05.externalservice.Client;
import com.tma.training.demo12_virtual_thread.demo05.concurrencylimit.ConcurrencyLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

/*
    A demo to see how can we limit concurrency with virtual threads by using Semaphore.
 */

public class Demo05ConcurrencyLimitWithSemaphore {

    private static final Logger log = LoggerFactory.getLogger(Demo05ConcurrencyLimitWithSemaphore.class);

    public static void main(String[] args) throws Exception {

        var executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("routine-", 1).factory());
        var limiter = new ConcurrencyLimiter(executor, 3);
        execute(limiter, 20);
    }

    private static void execute(ConcurrencyLimiter concurrencyLimiter, int taskCount) throws Exception {
        try (concurrencyLimiter) {
            for (int i = 1; i < taskCount; i++) {
                int j = i;
                concurrencyLimiter.submit(() -> printProductInfo(j));
            }
        }
    }


    // 3rd party service
    // contract: 3 concurrent calls are allowed
    private static String printProductInfo(int id) {
        var product = Client.getProduct(id);
        log.info("{} => {}", id, product);
        return product;
    }

}
