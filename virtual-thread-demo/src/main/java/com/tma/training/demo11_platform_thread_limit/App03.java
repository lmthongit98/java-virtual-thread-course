package com.tma.training.demo11_platform_thread_limit;

import com.tma.training.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executors;

/*
 *  Same as App02 but using Virtual Threads
 */

public class App03 {

    private static final Logger log = LoggerFactory.getLogger(App03.class);
    private static final int NUMBER_OF_TASK = 10_000;

    public static void main(String[] args) throws InterruptedException {
        log.info("Running {} task", NUMBER_OF_TASK);
        long start = System.currentTimeMillis();
        handleUserRequest();
        log.info("Tasks took {} to complete", System.currentTimeMillis() - start);
    }

    private static void handleUserRequest() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < NUMBER_OF_TASK; i++) {
                executor.submit(() -> {
                    for (int j = 0;  j < 100; j++) {
                        blockingIoOperation();
                    }
                });
            }
        }
    }

    // simulate a long blocking i/o
    private static void blockingIoOperation() {
        log.info("Executing a blocking task");
        CommonUtils.sleep(Duration.ofMillis(10));
    }

}
