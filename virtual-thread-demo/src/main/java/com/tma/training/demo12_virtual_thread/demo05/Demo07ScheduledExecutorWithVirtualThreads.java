package com.tma.training.demo12_virtual_thread.demo05;

import com.tma.training.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
    The JDK has no built-in task-scheduling tool that uses virtual threads.
    Thus, we must write a work around that use Platform Thread to schedule and Virtual Thread to execute.

    Reference: https://stackoverflow.com/questions/76587253/how-to-use-virtual-threads-with-scheduledexecutorservice
*/

public class Demo07ScheduledExecutorWithVirtualThreads {

    private static final Logger log = LoggerFactory.getLogger(Demo07ScheduledExecutorWithVirtualThreads.class);

    public static void main(String[] args) {
//        scheduled();
        scheduledWithVT();
    }

    private static void scheduled() {
        try (var executor = Executors.newSingleThreadScheduledExecutor()) {
            executor.scheduleAtFixedRate(() -> {
                log.info("executing task");
            }, 0, 1, TimeUnit.SECONDS);
            CommonUtils.sleep(Duration.ofSeconds(5)); // prevent auto closeable from 5s
        }
    }

    // offloading the work to virtual threads, and schedule the work with a scheduler.
    private static void scheduledWithVT() {
        var scheduler = Executors.newSingleThreadScheduledExecutor();
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        try (scheduler; executor) {
            scheduler.scheduleAtFixedRate(() -> {
                executor.submit(() -> log.info("executing task"));
            }, 0, 1, TimeUnit.SECONDS);
            CommonUtils.sleep(Duration.ofSeconds(5)); // prevent auto closeable from 5s
        }
    }

}
