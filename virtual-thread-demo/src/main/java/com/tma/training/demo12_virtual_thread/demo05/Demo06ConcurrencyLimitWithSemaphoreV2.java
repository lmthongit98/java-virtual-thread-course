package com.tma.training.demo12_virtual_thread.demo05;

import com.tma.training.demo12_virtual_thread.demo05.concurrencylimit.ConcurrencyLimiterV2;
import com.tma.training.demo12_virtual_thread.demo05.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

/*
     If you run Demo05, you will notice that Virtual Threads (VTs) are not executed in order, unlike Demo05.
     This is because when we submit tasks to a thread pool, they are stored in a blocking queue, ensuring the order of execution.
     However, with VTs, tasks are not stored in any queue; instead, the JVM schedules and runs them on carrier threads.

     We need to work around this issue.

 */

public class Demo06ConcurrencyLimitWithSemaphoreV2 {

    private static final Logger log = LoggerFactory.getLogger(Demo06ConcurrencyLimitWithSemaphoreV2.class);

    public static void main(String[] args) throws Exception {

        var executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("my-vt-", 1).factory());
        var limiter = new ConcurrencyLimiterV2(executor, 3);
        execute(limiter, 20);
    }

    private static void execute(ConcurrencyLimiterV2 concurrencyLimiter, int taskCount) throws Exception {
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
