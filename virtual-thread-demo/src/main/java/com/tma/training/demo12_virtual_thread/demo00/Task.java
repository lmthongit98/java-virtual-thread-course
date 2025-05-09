package com.tma.training.demo12_virtual_thread.demo00;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Task {

    private static final Logger log = LoggerFactory.getLogger(Task.class);

    public static void ioIntensive(int i){

        try {
            log.info("starting I/O task {}.", i);
            Thread.sleep(Duration.ofSeconds(1));
            log.info("ending I/O task {}.", i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
