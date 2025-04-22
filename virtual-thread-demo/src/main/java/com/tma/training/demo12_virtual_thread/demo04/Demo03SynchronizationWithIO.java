/*
 *
 *  Demo Virtual Thread Pinning (Java 24 partially fixed this issue)
 *
 *  When a virtual thread blocks on a synchronized method:
 *    + The platform thread is pinned to the virtual thread.
 *    + The platform thread cannot be used to run other virtual threads until the lock is released.
 *  In this demo:
 *    + The ioTask method takes 5 seconds to complete.
 *    + The "*** Test Message ***" is printed after 5 seconds, when the first platform thread becomes available.
 *
 */

package com.tma.training.demo12_virtual_thread.demo04;

import com.tma.training.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Demo03SynchronizationWithIO {

    private static final Logger log = LoggerFactory.getLogger(Demo03SynchronizationWithIO.class);

    private static final int NUMBER_OF_CPU_CORES = CommonUtils.getNumberOfCpuCores();

    // Use this to check if virtual threads are getting pinned in your application
    static {
//        System.setProperty("jdk.tracePinnedThreads", "short");
    }

    public static void main(String[] args) {

        Runnable runnable = () -> log.info("*** Test Message ***");

//// We will not see this issue with Platform threads
//        demo(Thread.ofPlatform());
//        Thread.ofPlatform().start(runnable);

        demo(Thread.ofVirtual().name("routine-", 1));
        Thread.ofVirtual().start(runnable);

        CommonUtils.sleep(Duration.ofSeconds(15));

    }

    private static void demo(Thread.Builder builder){
        for (int i = 0; i < NUMBER_OF_CPU_CORES; i++) {
           builder.start(Demo03SynchronizationWithIO::ioTask);
        }
    }

    private static synchronized void ioTask(){
        log.info("Task started. {}", Thread.currentThread());
        // JVM cannot see blocking operation inside a synchronized block
        CommonUtils.sleep(Duration.ofSeconds(5));
        log.info("Task ended. {}", Thread.currentThread());

    }

}
